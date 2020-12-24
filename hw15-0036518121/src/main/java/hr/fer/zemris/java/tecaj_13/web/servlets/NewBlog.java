package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet that adds new blog entry to database with data given in form. After that dispatches request to jsp file
 * containing all entries by currently logged in user.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/servleti/author/newBlog/*")
public class NewBlog extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser author = DAOProvider.getDAO().getBlogUser((Long) req.getSession().getAttribute("current.user.id"));
		
		BlogEntry blogEntry = new BlogEntry();
		
		blogEntry.setCreatedAt(new Date());
		blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
		blogEntry.setTitle(req.getParameter("blogTitle"));
		blogEntry.setText(req.getParameter("blogText"));
		blogEntry.setCreator(author);
		
		DAOProvider.getDAO().insertBlogEntry(blogEntry);
		
		author.getBlogEntries().add(blogEntry);
		
		resp.sendRedirect("/blog/servleti/author/" + req.getSession().getAttribute("current.user.nick"));
	}

}
