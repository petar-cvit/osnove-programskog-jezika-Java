package hr.fer.zemris.java.p11.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Gets colour as parameter. Sets that parameter to session attributes and dispatches request to jsp file.
 * 
 * @author Petar Cvitanović
 *
 */
public class SetColorServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("pickedBgCol");
		req.getSession().setAttribute("pickedBgCol", color);
		
		req.getRequestDispatcher("color.jsp").forward(req, resp);
	}
	
}
