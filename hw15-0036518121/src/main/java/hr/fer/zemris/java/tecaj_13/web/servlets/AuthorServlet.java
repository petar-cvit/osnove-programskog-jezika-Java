package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet mapped to "servleti/author/*". After /author servlet expects nickname of some author. If requested path
 * ends with user's nickname, dispatches request to jsp file that shows all blog entries by this user. If url has
 * /new or /edit servlet checks whether or not user that is logged in is same as the user provided in url. If it's
 * not dispatches request to jsp file with error, otherwise dispatches file to servlets for editing or creating new
 * blog entry. If some id number is provided after /new or /edit servlet dispatches request to jsp with blog entry
 * title, text and comments.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] parts = req.getRequestURI().split("/");
		
		switch (parts.length) {
		case 5:
			authorsBlogs(req, resp, parts[parts.length - 1]);
			break;
			
		case 6:
			if(parts[parts.length - 1].equals("new") || parts[parts.length - 1].equals("edit")) {
				newBlog(req, resp, parts[parts.length - 1], parts[parts.length - 2]);
			} else {
				visitBlog(req, resp, parts[parts.length - 1], parts[parts.length - 2]);
			}
			break;
			
		default:
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			break;	
		}
	}
	
	/**
	 * Dispatches request to jsp file with all entries by user whose nickname is provided as method argument.
	 * 
	 * @param req
	 * @param resp
	 * @param nick
	 * @throws ServletException
	 * @throws IOException
	 */
	private void authorsBlogs(HttpServletRequest req, HttpServletResponse resp, String nick) throws ServletException, IOException {
		List<BlogUser> user = DAOProvider.getDAO().getBlogUserByNick(nick);
		
		if(user.size() != 1) {
			return;
		}
		
		List<BlogEntry> blogEntries = DAOProvider.getDAO().getEntriesByAuthor(user.get(0));
		req.setAttribute("blogEntries", blogEntries);
		req.setAttribute("authorsNick", user.get(0).getNick());
		
		req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
	}
	
	/**
	 * Dispatches request to jsp file for creating new blog entry or editing existing blog entry.
	 * 
	 * @param req
	 * @param resp
	 * @param method
	 * @param nick
	 * @throws ServletException
	 * @throws IOException
	 */
	private void newBlog(HttpServletRequest req, HttpServletResponse resp, String method, String nick) throws ServletException, IOException {
		List<BlogUser> user = DAOProvider.getDAO().getBlogUserByNick(nick);
		
		if(user == null || user.size() != 1 || !user.get(0).getNick().equals(req.getSession().getAttribute("current.user.nick"))) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		if(method.equals("new")) {			
			req.getRequestDispatcher("/WEB-INF/pages/newBlog.jsp").forward(req, resp);
		} else {
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong((String) req.getSession().getAttribute("blog.id")));
			req.setAttribute("blogTitle", blogEntry.getTitle());
			req.setAttribute("blogText", blogEntry.getText());
			
			req.getRequestDispatcher("/WEB-INF/pages/editBlog.jsp").forward(req, resp);
		}
	}
	
	/**
	 * Dispatches request to jsp file that shows title, text and comments for some blog entry.
	 * 
	 * @param req
	 * @param resp
	 * @param blogID
	 * @param nick
	 * @throws ServletException
	 * @throws IOException
	 */
	private void visitBlog(HttpServletRequest req, HttpServletResponse resp, String blogID, String nick) throws ServletException, IOException {
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(blogID));
		
		if(blogEntry == null || !blogEntry.getCreator().getNick().equals(nick)) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("blogTitle", blogEntry.getTitle());
		req.setAttribute("blogText", blogEntry.getText());
		req.setAttribute("blogComments", blogEntry.getComments());
		req.setAttribute("blogCreated", blogEntry.getCreatedAt());
		req.setAttribute("blogModified", blogEntry.getLastModifiedAt());
		
		req.getSession().setAttribute("blog.id", blogID);
		req.getSession().setAttribute("authorsNick", nick);
		
		req.getRequestDispatcher("/WEB-INF/pages/blogEntry.jsp").forward(req, resp);
	}

}
