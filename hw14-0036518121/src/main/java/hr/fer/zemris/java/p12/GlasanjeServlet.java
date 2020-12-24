package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Option;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * Dispatches request to jsp page that contains list of links so you can vote for some band.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollID = Long.parseLong(req.getParameter("pollID"));
		
		req.getSession().setAttribute("pollID", pollID);
		
		List<Option> options = DAOProvider.getDao().getPollOptions(pollID);
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		
		req.getSession().setAttribute("title", poll.getTitle());
		req.getSession().setAttribute("message", poll.getMessage());
		
		req.getSession().setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/voting.jsp").forward(req, resp);
	}

}
