package hr.fer.zemris.java.webserver;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class that is used as server. This server can take requests for method GET and for version HTTP/1.1.
 * Server takes it's configuration from configuration files. Main server thread every time it gets a request,
 * delegates creating of response to {@link ClientWorker} instance. This class takes care for responses.
 * 
 * @author Petar Cvitanović
 *
 */
public class SmartHttpServer {
	
	/**
	 * address
	 */
	private String address;
	
	/**
	 * domain name
	 */
	private String domainName;
	
	/**
	 * port
	 */
	private int port;
	
	/**
	 * number of worker threads
	 */
	private int workerThreads;
	
	/**
	 * session length
	 */
	private int sessionTimeout;
	
	/**
	 * map of mime types
	 */
	private Map<String,String> mimeTypes = new HashMap<String, String>();
	
	/**
	 * server thread that delegates responses
	 */
	private ServerThread serverThread;
	
	/**
	 * pool of threads that generate responses
	 */
	private ExecutorService threadPool;
	
	/**
	 * document root
	 */
	private Path documentRoot;
	
	/**
	 * map of worker names to instances of workers
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<String, IWebWorker>();

	/**
	 * map of session id's to {@link SessionMapEntry} instances
	 */
	private Map<String, SessionMapEntry> sessions =
			new HashMap<String, SmartHttpServer.SessionMapEntry>();
	
	/**
	 * randomizer
	 */
	private Random sessionRandom = new Random();

	/**
	 * Constructor with configuration file path.
	 * 
	 * @param configFileName
	 */
	public SmartHttpServer(String configFileName) {
		try {
			Properties prop = readPropertiesFile(configFileName);

			address = prop.getProperty("server.address");
			domainName = prop.getProperty("server.domainName");
			port = Integer.parseInt(prop.getProperty("server.port"));
			workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
			sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));
			documentRoot = Paths.get(prop.getProperty("server.documentRoot"));

			Properties mimeProp = readPropertiesFile(
					prop.getProperty("server.mimeConfig"));

			for(String key : mimeProp.stringPropertyNames()) {
				mimeTypes.put(key, mimeProp.getProperty(key));
			}

			Properties workersProp = readPropertiesFile(
					prop.getProperty("server.workers"));

			for(String key : workersProp.stringPropertyNames()) {
				String fqcn = workersProp.getProperty(key);

				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);

				@SuppressWarnings("deprecation")
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				
				if(workersMap.containsKey(key)) {
					throw new RuntimeException();
				}

				workersMap.put(key, iww);
			}
		} catch (IOException ex) {
			System.out.println("Invalid file!");
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | SecurityException | ClassNotFoundException ex) {
			System.out.println("Invocation exception!");
		}

	}

	/**
	 * Loads properties file whose path is given as argument.
	 * 
	 * @throws IOException if file name is invalid
	 * 
	 * @param fileName
	 * @return Properties instance
	 */
	private static Properties readPropertiesFile(String fileName) throws IOException {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(fis);
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}

		return prop;
	}

	/**
	 * Starts server thread and garbage collector thread. Garbage collector thread goes through sessions map
	 * every 5 minutes and removes sessions that expired.
	 */
	protected synchronized void start() {
		serverThread = new ServerThread();
		serverThread.start();

		threadPool = Executors.newFixedThreadPool(workerThreads);
		
		Thread garbageCollector = new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(5 * 60 * 1000);
				} catch (InterruptedException e) {
				}
				
				for(Entry<String, SessionMapEntry> entry : sessions.entrySet()) {
					if(entry.getValue().validUntil < new Date().getTime()) {
						sessions.remove(entry.getKey());
					}
				}
			}
		});
		
		garbageCollector.setDaemon(true);
		
		garbageCollector.start();
	}

	/**
	 * Stops server's thread.
	 */
	@SuppressWarnings("deprecation")
	protected synchronized void stop() {
		serverThread.stop();
		threadPool.shutdown();
	}

	/**
	 * Thread that creates new socket and waits for clients requests.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	protected class ServerThread extends Thread {
		@Override
		public void run() {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket();

				serverSocket.bind(
						new InetSocketAddress(InetAddress.getByName(address), port)
						);

				while(true) {
					Socket toClient = serverSocket.accept();
					ClientWorker clientWorker = new ClientWorker(toClient);
					threadPool.submit(clientWorker);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Class that processes requests and generates responses.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		/**
		 * socket
		 */
		private Socket csocket;
		
		/**
		 * input socket from client
		 */
		private InputStream istream;
		
		/**
		 * output socket toward client
		 */
		private OutputStream ostream;
		
		/**
		 * protocol version
		 */
		private String version;
		
		/**
		 * request method
		 */
		private String method;
		
		/**
		 * server host
		 */
		private String host;
		
		/**
		 * parameters
		 */
		private Map<String,String> params = new HashMap<String, String>();
		
		/**
		 * temporary parameters
		 */
		private Map<String,String> tempParams = new HashMap<String, String>();
		
		/**
		 * persistent parameters
		 */
		private Map<String,String> permPrams = new HashMap<String, String>();
		
		/**
		 * list of cookies that need to be sent
		 */
		private List<RCCookie> outputCookies =
				new ArrayList<RequestContext.RCCookie>();
		
		/**
		 * session id
		 */
		private String SID;
		
		/**
		 * request context
		 */
		private RequestContext context;

		/**
		 * Constructor with socket between server and client.
		 * 
		 * @param csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new BufferedInputStream(
						csocket.getInputStream()
						);
				ostream = new BufferedOutputStream(
						csocket.getOutputStream()
						);

				byte[] request = readRequest();
				if(request==null) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				String requestStr = new String(
						request,
						StandardCharsets.US_ASCII
						);

				List<String> headers = extractHeaders(requestStr);

				String[] firstLine = headers.isEmpty() ? 
						null : headers.get(0).split(" ");
				if(firstLine==null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				if(!method.equals("GET")) {
					sendError(ostream, 405, "Method Not Allowed");
					return;
				}

				version = firstLine[2].toUpperCase();
				if(!version.equals("HTTP/1.1")) {
					sendError(ostream, 505, "HTTP Version Not Supported");
					return;
				}

				String sidCandidate = "";

				for(String header : headers) {
					if(header.substring(0, 5).equals("Host:")) {
						host = header.substring(5).trim();
					}

					if(header.substring(0, 7).equals("Cookie:")) {
						String[] cookies = header.substring(7, header.length()).trim().split(";");
						for(String c : cookies) {
							String[] entry = c.split("=");
							if(entry[0].equals("sid")) {
								sidCandidate = entry[1].substring(3, entry[1].length() - 3);
							}
						}
					}
				}
				
				if(host == null) {
					host = domainName;
				}

				if(host.contains(":")) {
					host = host.substring(0, host.indexOf(":"));
				}

				if(sidCandidate.isBlank()) {
					addSession();
				} else {
					SessionMapEntry entry = sessions.get(sidCandidate);

					if(entry == null) {
						entry = addSession();
					}
					
					if(!entry.host.equals(host) || entry.validUntil < new Date().getTime()) {
						addSession();
					} else {
						entry.validUntil = new Date().getTime() + sessionTimeout * 1000;
						permPrams = entry.map;
					}
				}

				if(firstLine[1].contains("?")) {
					String paramString = firstLine[1].split("\\?")[1];
					parseParameters(paramString);
				}

				internalDispatchRequest(firstLine[1], true);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
				}
			}
		}

		/**
		 * Adds new session to map to sessions and new output cookie to list of cookies.
		 * 
		 * @return new {@link SessionMapEntry} instance
		 */
		private synchronized SessionMapEntry addSession() {
			StringBuilder sb = new StringBuilder(20);

			for(int i = 0;i < 20;i++) {
				sb.append((char)((sessionRandom.nextInt(90-65))+65));
			}

			String sid = sb.toString();

			SessionMapEntry entry = new SessionMapEntry(sid, host, new Date().getTime() + sessionTimeout * 1000,
					permPrams);

			RCCookie cookie = new RCCookie("sid", sid, null, host, "/");

			sessions.put(sid, entry);
			outputCookies.add(cookie);

			return entry;
		}

		/**
		 * Parses parameters from request.
		 * 
		 * @param paramString
		 */
		private void parseParameters(String paramString) {
			String[] mappings = paramString.split("&");

			for(String entry : mappings) {
				params.put(entry.split("=")[0],
						entry.split("=").length == 1 ? "" : entry.split("=")[1]);
			}
		}

		/**
		 * Returns image in response.
		 * 
		 * @param image
		 * @return image as byte array
		 * @throws IOException
		 */
		private byte[] returnImage(Path image) throws IOException {
			BufferedImage bim = ImageIO.read(image.toFile());

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bim, "jpg", bos);
			byte[] podatci = bos.toByteArray();

			return podatci;
		}

		/**
		 * Reads characters until first "\r\n\r\n" sequence. Implemented as state machine.
		 * 
		 * @return bytes that are read before mentioned sequence
		 * @throws IOException
		 */
		private byte[] readRequest() 
				throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l:		while(true) {
				int b = istream.read();
				if(b==-1) return null;
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1: 
					if(b==10) { state=2; } else state=0;
					break;
				case 2: 
					if(b==13) { state=3; } else state=0;
					break;
				case 3: 
					if(b==10) { break l; } else state=0;
					break;
				case 4: 
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			return bos.toByteArray();
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Tries to find worker depending on requested URL. If worker isn's found, tries to
		 * find requested file. If that file isn't found or isn't readable returns error.
		 * Otherwise generates request or delegates it to worker.
		 * 
		 * @param urlPath
		 * @param directCall
		 * @throws Exception
		 */
		private void internalDispatchRequest(String urlPath, boolean directCall)
				throws Exception {

			if(urlPath.contains("/private/") && directCall) {
				sendError(ostream, 404, "File not found");
				return;
			}

			String[] parts = urlPath.split("\\\\");
			String worker = parts[parts.length - 1];

			int beginIndex = 0;

			if(urlPath.contains("/ext/")) {
				beginIndex = 5;
			}

			int endIndex = urlPath.indexOf('?') == -1 ? urlPath.length() : urlPath.indexOf('?');

			worker = urlPath.substring(beginIndex, endIndex);

			IWebWorker webWorker = workersMap.get(worker);
			
			if(webWorker == null) {
				try {
					Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(
							"hr.fer.zemris.java.webserver.workers." + worker);

					@SuppressWarnings("deprecation")
					Object newObject = referenceToClass.newInstance();
					webWorker = (IWebWorker)newObject;
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | SecurityException | ClassNotFoundException ex) {
				}
			}
			
			if(webWorker != null) {
				if(context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
					context.setStatusCode(200);
					context.setStatusText("OK");
				}

				webWorker.processRequest(context);

				return;
			}

			Path filePath = documentRoot.resolve(Paths.get(urlPath.substring(1, endIndex)));
			
			if(!Files.exists(filePath)) {
				sendError(ostream, 403, "Forbidden");
				return;
			}

			if(!Files.isReadable(filePath)) {
				sendError(ostream, 404, "File not found");
				return;
			}

			String mimeType = mimeTypes.get(
					urlPath.split("\\.")[1]);

			mimeType = mimeType == null ? "application/octet-stream" :
				mimeType;

			context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
			context.setMimeType(mimeType);
			context.setStatusCode(200);
			context.setStatusText("OK");

			if(mimeType.substring(0, 6).equals("image/")) {
				byte[] image = returnImage(filePath);
				context.write(image);
				return;
			}

			String documentBody = "";
			try {
				documentBody = new String(Files.readAllBytes(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}

			new SmartScriptEngine(
					new SmartScriptParser(documentBody).getDocumentNode(), context
					).execute();
		}

		/**
		 * Header is split into lines and returned as list of strings.
		 * 
		 * @param requestHeader
		 * @return list of header lines
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}


		/**
		 * Sends error with given status code and status text.
		 * 
		 * @param cos
		 * @param statusCode
		 * @param statusText
		 * @throws IOException
		 */
		private void sendError(OutputStream cos, 
				int statusCode, String statusText) throws IOException {

			cos.write(
					("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
							"Server: simple java server\r\n"+
							"Content-Type: text/plain;charset=UTF-8\r\n"+
							"Content-Length: 0\r\n"+
							"Connection: close\r\n"+
							"\r\n").getBytes(StandardCharsets.US_ASCII)
					);
			cos.flush();
		}
	}

	/**
	 * One entry in session map. Represents one session.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	private static class SessionMapEntry {
		
		/**
		 * session id
		 */
		String sid;
		
		/**
		 * host
		 */
		String host;
		
		/**
		 * time that session is valid
		 */
		long validUntil;
		
		/**
		 * map of parameters
		 */
		Map<String,String> map;

		/**
		 * Constructor with all needed parameters.
		 * 
		 * @param sid
		 * @param host
		 * @param validUntil
		 * @param map
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}

	/**
	 * Main method that creates new server with configuration file.
	 * 
	 * @param no arguments
	 */
	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer("config/server.properties");

		server.start();
	}
}

