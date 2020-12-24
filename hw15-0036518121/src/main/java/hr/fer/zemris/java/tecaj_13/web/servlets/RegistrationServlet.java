package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.util.Util;

/**
 * Servlet used for registration of new users. If any attribute given in form isn't provided servlet dispatches request to
 * same jsp file, but error message is rendered. If user provides all needed information servlet checks whether or not given
 * nickname is already in use. If it is client is warned by error message above form, on the other hand user is added to
 * database and logged in.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/servleti/registration")
public class RegistrationServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("nick").isBlank() ||
				req.getParameter("firstName").isBlank() ||
				req.getParameter("lastName").isBlank() ||
				req.getParameter("email").isBlank()) {
			req.setAttribute("fillAll", true);
			
			req.setAttribute("reg.fn", (String) req.getParameter("firstName"));
			req.setAttribute("reg.ln", (String) req.getParameter("lastName"));
			req.setAttribute("reg.email", (String) req.getParameter("email"));
			req.setAttribute("reg.nick", (String) req.getParameter("nick"));
			
			req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
			return;
		}
		
		List<BlogUser> blogUsers = DAOProvider.getDAO().getBlogUserByNick(req.getParameter("nick"));
		
		if(!blogUsers.isEmpty()) {
				req.setAttribute("nickTaken", true);
				req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
				return;
		} else {
			MessageDigest md = null;
			
			try {
				md = MessageDigest.getInstance("SHA-1");
			} catch (NoSuchAlgorithmException e) {
				System.out.println("No such alogrithm!");
				return;
			}
			
			md.update(req.getParameter("password").getBytes());
			String digested = Util.bytetohex(md.digest());
			
			BlogUser newUser = new BlogUser();
			
			newUser.setFirstName(req.getParameter("firstName"));
			newUser.setLastName(req.getParameter("lastName"));
			newUser.setEmail(req.getParameter("email"));
			newUser.setNick(req.getParameter("nick"));
			newUser.setPasswordHash(digested);
			
			DAOProvider.getDAO().insertNewUser(newUser);
			
			req.getSession().setAttribute("current.user.id", newUser.getId());
			req.getSession().setAttribute("current.user.fn", newUser.getFirstName());
			req.getSession().setAttribute("current.user.ln", newUser.getLastName());
			req.getSession().setAttribute("current.user.nick", newUser.getNick());
			
			req.getRequestDispatcher("/WEB-INF/pages/successful.jsp").forward(req, resp);
		}
	}
}
