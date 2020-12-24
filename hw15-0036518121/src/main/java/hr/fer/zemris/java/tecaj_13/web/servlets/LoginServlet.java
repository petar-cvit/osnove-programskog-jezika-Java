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
 * Main servlet. On doGet method dispatches request to jsp file that shows login form and list of all authors.
 * On doPost method saves data given in login form to session map and holds information about user that is
 * logged in.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/servleti/main")
public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> blogUsers = DAOProvider.getDAO().getBlogUsers();
		req.getSession().setAttribute("blogUsers", blogUsers);
		
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> nickUser = DAOProvider.getDAO().getBlogUserByNick(req.getParameter("nick"));
		
		if(nickUser.size() != 1) {
			req.setAttribute("notFound", true);
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);			
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
			
			if(!nickUser.get(0).getPasswordHash().equals(digested)) {
				req.setAttribute("invalidPassword", true);
				req.setAttribute("nick", req.getParameter("nick"));
				req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			} else {
				req.getSession().setAttribute("current.user.id", nickUser.get(0).getId());
				req.getSession().setAttribute("current.user.fn", nickUser.get(0).getFirstName());
				req.getSession().setAttribute("current.user.ln", nickUser.get(0).getLastName());
				req.getSession().setAttribute("current.user.nick", nickUser.get(0).getNick());
				resp.sendRedirect("/blog/servleti/main");
			}
		}
	}
}
