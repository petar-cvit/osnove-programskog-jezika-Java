package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Option;

/**
 * Used to dispatch poll results. Dispatches request to jsp page and sends all bands data and list of bands that have
 * the most votes.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollID = (Long) req.getSession().getAttribute("pollID");
		
		List<Option> options = DAOProvider.getDao().getPollOptions(pollID);
		
		options.sort((Option o1, Option o2) -> o2.getVotes().compareTo(o1.getVotes()));
		
		req.getSession().setAttribute("options", options);
		req.getSession().setAttribute("winners", topOptions(options));
		req.getRequestDispatcher("/WEB-INF/pages/votingResults.jsp").forward(req, resp);
	}
	
	private List<Option> topOptions(List<Option> options) {
		List<Option> winners = new ArrayList<Option>();
		int maxVotes = options.get(0).getVotes();
		
		for(Option o : options) {
			if(o.getVotes() < maxVotes) {
				break;
			}
			
			winners.add(o);
		}
		
		return winners;
	}

}
