package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Context of received requested. Holds information about request.
 * 
 * @author Petar Cvitanović
 *
 */
public class RequestContext {

	/**
	 * output stream to send header and data
	 */
	private OutputStream outputStream;

	/**
	 * charset
	 */
	private Charset charset;

	/**
	 * string representation of encoding
	 */
	public String encoding;

	/**
	 * status code - three digit number
	 */
	public int statusCode;

	/**
	 * status text
	 */
	public String statusText;

	/**
	 * mime type
	 */
	public String mimeType;

	/**
	 * content length
	 */
	public Long contentLength;

	/**
	 * parameters map
	 */
	private Map<String, String> parameters;

	/**
	 * temporary parameters map
	 */
	private Map<String, String> temporaryParameters;

	/**
	 * persistent parameters map
	 */
	private Map<String, String> persistentParameters;

	/**
	 * returned cookies
	 */
	private List<RCCookie> outputCookies;

	/**
	 * true if header is already generated, false otherwise
	 */
	private boolean headerGenerated;

	/**
	 * implementation of {@link IDispatcher} interface
	 */
	private IDispatcher dispatcher;

	/**
	 * syssion id
	 */
	private String sid;

	/**
	 * Constructor with all needed parameters.
	 * 
	 * @param outputStream
	 * @param parameters
	 * @param persistentParameters
	 * @param outputCookies
	 * @param temporaryParameters
	 * @param dispatcher
	 * @param sid
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies, Map<String, String> temporaryParameters,
			IDispatcher dispatcher, String sid) {
		this(outputStream, parameters, persistentParameters, outputCookies);

		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
		this.sid = sid;
	}

	/**
	 * Constructor with output stream, parameters, persistent parameters and cookies.
	 * 
	 * @param outputStream
	 * @param parameters
	 * @param persistentParameters
	 * @param outputCookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		super();
		if(outputStream == null) {
			throw new NullPointerException();
		}

		this.outputStream = outputStream;

		this.parameters = parameters == null ? new HashMap<String, String>() : parameters;
		this.persistentParameters = persistentParameters == null ?
				new HashMap<String, String>() : persistentParameters;
				this.outputCookies = parameters == null ?
						new ArrayList<RequestContext.RCCookie>() : outputCookies;

						statusCode = 200;
						statusText = "OK";
						mimeType = "text/html";
						encoding = "UTF-8";
						contentLength = null;
						
						charset = Charset.forName(encoding);

						headerGenerated = false;
	}

	/**
	 * Class that represents cookie that needs to be returned in response.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	public static class RCCookie {

		/**
		 * cookie name
		 */
		private final String name;

		/**
		 * cookie value
		 */
		private final String value;

		/**
		 * domain
		 */
		private final String domain;

		/**
		 * path
		 */
		private final String path;

		/**
		 * cookie's max age
		 */
		private final Integer maxAge;

		/**
		 * Constructor with all needed parameters
		 * 
		 * @param name
		 * @param value
		 * @param maxAge
		 * @param domain
		 * @param path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
	}

	/**
	 * Encoding setter.
	 * 
	 * @throws RuntimeException if header has already been generated
	 * 
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated) {
			throw new RuntimeException();
		}

		this.encoding = encoding;

		charset = Charset.forName(encoding);
	}

	/**
	 * Status code setter.
	 * 
	 * @throws RuntimeException if header has already been generated
	 * 
	 * @param status code
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated) {
			throw new RuntimeException();
		}

		this.statusCode = statusCode;
	}

	/**
	 * Status text setter.
	 * 
	 * @throws RuntimeException if header has already been generated
	 * 
	 * @param status text
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated) {
			throw new RuntimeException();
		}

		this.statusText = statusText;
	}

	/**
	 * Mime type setter.
	 * 
	 * @throws RuntimeException if header has already been generated
	 * 
	 * @param mime type
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated) {
			throw new RuntimeException();
		}

		this.mimeType = mimeType;
	}

	/**
	 * Content length setter.
	 * 
	 * @throws RuntimeException if header has already been generated
	 * 
	 * @param content length
	 */
	public void setContentLength(Long contentLength) {
		if(headerGenerated) {
			throw new RuntimeException();
		}

		this.contentLength = contentLength;
	}

	/**
	 * Returns parameter with mapped to given name
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Returns all parameter names.
	 * 
	 * @return parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Returns persistent parameter with mapped to given name
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Returns all persistent parameter names.
	 * 
	 * @return persistent parameter names
	 */
	public Set<String> getPersistentParametersNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Sets persistent parameter.
	 * 
	 * @param name
	 * @param value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Removes persistent parameter.
	 * 
	 * @param name
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Returns temporary parameter mapped to given name.
	 * 
	 * @param name
	 * @return temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Returns temporary parameter names.
	 * 
	 * @return temporary parameters names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Session id getter.
	 * 
	 * @return session id
	 */
	public String getSessionID() {
		return sid;
	}

	/**
	 * Adds temporary parameter.
	 * 
	 * @param name
	 * @param value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes temporary parameter mapped to given name.
	 * 
	 * @param name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Adds given cookie to list of output cookies.
	 * 
	 * @param cookie
	 */
	public void addRCCookie(RCCookie cookie) {
		outputCookies.add(cookie);
	}

	/**
	 * Writes header and given data to output stream.
	 * 
	 * @param data
	 * @return this
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			outputStream.write(createHeader().getBytes());
		}
		outputStream.write(data);
		outputStream.flush();

		return this;
	}

	/**
	 * Writes header and given data to output stream.
	 * 
	 * @param data
	 * @return this
	 * @throws IOException
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if(!headerGenerated) {
			outputStream.write(createHeader().getBytes());
		}
		outputStream.write(data, offset, len);
		outputStream.flush();

		return this;
	}

	/**
	 * Writes header and given string to output stream.
	 * 
	 * @param data
	 * @return this
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		return write(text.getBytes(charset));
	}

	/**
	 * Returns header in a form of string.
	 * 
	 * @return header
	 */
	private String createHeader() {
		if(charset == null) {
			charset = Charset.forName("UTF-8");
		}

		StringBuilder header = new StringBuilder();

		header.append(String.join(" ", "HTTP/1.1",
				Integer.valueOf(statusCode).toString(),
				statusText)
				);
		header.append("\r\n");

		header.append("Content-Type: " + mimeType);

		if(mimeType.substring(0, 5).equals("text/")) {
			header.append("; charset=" + encoding);
		}
		header.append("\r\n");

		if(contentLength != null) {
			header.append("Content-Length: " + contentLength + "\n");
		}

		for(RCCookie cookie : outputCookies) {
			header.append("Set-Cookie: " + cookie.name + "=”" + cookie.value + "”");

			if(cookie.domain != null) {
				header.append("; Domain=" + cookie.domain);
			}

			if(cookie.path != null) {
				header.append("; Path=" + cookie.path);
			}

			if(cookie.maxAge != null) {
				header.append("; Max-Age=" + cookie.maxAge);
			}

			header.append("\r\n");
		}

		header.append("\r\n");

		headerGenerated = true;

		return header.toString();
	}

	/**
	 * IDispatcher getter.
	 * 
	 * @return {@link IDispatcher} implementation
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
}