package hr.fer.zemris.java.p11.servleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Dispatches request to jsp page that contains list of links so you can vote for some band.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		try(BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
			Map<String, String> bands = new HashMap<String, String>();
			String line;
			
			while(true) {
				line = br.readLine();
				if(line == null) {
					break;
				}
				bands.put(line.split("\t")[0], line.split("\t")[1]);
			}
			
			req.getSession().setAttribute("bands", bands);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
