package hr.fer.zemris.java.p11.servleti;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Used to dispatch poll results. Dispatches request to jsp page and sends all bands data and list of bands that have
 * the most votes.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String results = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		File file = new File(results);

		if(!file.exists()) {
			file.createNewFile();
		}

		String line;
		
		Map<String, String> bandsResults = new HashMap<String, String>();

		try(BufferedReader br = Files.newBufferedReader(Paths.get(results))) {

			while(true) {
				line = br.readLine();
				if(line == null) {
					break;
				}
				bandsResults.put(line.split("\t")[0], line.split("\t")[1]);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		//citam imena bendova
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		Map<String, String> bandNames = new HashMap<String, String>();
		List<Quadraple> bands = new ArrayList<Quadraple>();
		
		try(BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

			while(true) {
				line = br.readLine();
				if(line == null) {
					break;
				}
				bandNames.put(line.split("\t")[0], line.split("\t")[1]);
				String[] parts = line.split("\t");
				
				Integer votes = 0;
				try {	
					votes = Integer.parseInt(bandsResults.get(parts[0]));
				} catch (NumberFormatException e) {
					e.printStackTrace();
					return;
				}
				
				Quadraple entry = new Quadraple(parts[0], parts[1], parts[2], votes);
				bands.add(entry);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Quadraple> topBands = topBands(bands);
		
		req.getSession().setAttribute("bandsResults", bands);
		req.getSession().setAttribute("topBands", topBands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	private List<Quadraple> topBands(List<Quadraple> bands) {
		List<Quadraple> topBands = new ArrayList<Quadraple>();
		int maxVotes = 0;
		
		for(Quadraple q : bands) {
			if(q.getVotes() == maxVotes) {
				topBands.add(q);
			}
			
			if(q.getVotes() > maxVotes) {
				maxVotes = q.getVotes();
				topBands = new ArrayList<Quadraple>();
				topBands.add(q);
			}
		}
		
		return topBands;
	}

}
