package hr.fer.zemris.java.p11.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Takes two parameters. First parameter is the starting angle, second is the ending angle.
 * Servlet calculates sine and cosine for every angle in that range. Then stores lists of sine
 * and cosine values as session attributes. Those values are used for table in jsp file to which
 * the request is dispatched.
 * 
 * @author Petar Cvitanović
 *
 */
public class TrigonometricServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String first = req.getParameter("a");
		if(first == null) {
			first = "0";
		}

		String second = req.getParameter("b");
		if(second == null) {
			second = "360";
		}

		int a, b;

		try {
			a = Math.min(
					Integer.parseInt(first),
					Integer.parseInt(second)
					);
		} catch (NumberFormatException e) {
			a = 0;
		}

		try {
			b = Math.max(
					Integer.parseInt(first),
					Integer.parseInt(second)
					);
		} catch (NumberFormatException e) {
			b = 360;
		}
		
		if(b > a + 720) {
			b = a + 720;
		}

		List<Double> sin = new ArrayList<Double>();
		List<Double> cos = new ArrayList<Double>();

		for(int i = a;i <= b;i++) {
			sin.add(Math.sin(i * 2 * Math.PI / 360.));
			cos.add(Math.cos(i * 2 * Math.PI / 360.));
		}

		req.getSession().setAttribute("sin", sin);
		req.getSession().setAttribute("cos", cos);
		req.getSession().setAttribute("a", a);

		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp");
		rd.forward(req, resp);
	}

}
