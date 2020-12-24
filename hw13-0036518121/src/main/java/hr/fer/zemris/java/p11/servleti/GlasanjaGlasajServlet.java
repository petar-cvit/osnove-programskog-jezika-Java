package hr.fer.zemris.java.p11.servleti;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that updates values of votes in files that contain poll results.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjaGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = (String) req.getParameter("id");
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		File file = new File(fileName);
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		List<String> lines = new ArrayList<String>();
		
		try(BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
			String line = "";
			
			while(true) {
				line = br.readLine();
				if(line == null) {
					break;
				}
				
				if(line.split("\t")[0].equals(id)) {
					Integer votes = 0;
					try {
						votes = Integer.parseInt(line.split("\t")[1]);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						return;
					}
					line = id + "\t" + (votes + 1); 
				}
				
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try(BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName))) {
			for(String line : lines) {
				bw.write(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
	
}






















