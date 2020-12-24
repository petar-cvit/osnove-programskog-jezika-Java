package hr.fer.zemris.java.p12;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Servlet that updates values of votes in files that contain poll results.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjaGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long optionID = Long.parseLong(req.getParameter("id"));
		DAOProvider.getDao().incrementVote(optionID);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati");
	}
	
}






















