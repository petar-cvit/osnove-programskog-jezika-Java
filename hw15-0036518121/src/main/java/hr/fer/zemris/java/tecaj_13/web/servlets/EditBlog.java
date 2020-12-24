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

/**
 * Edits existing blog in database.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/servleti/author/editBlog/*")
public class EditBlog extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogEntry newBlogEntry = new BlogEntry();
		
		newBlogEntry.setLastModifiedAt(new Date());
		newBlogEntry.setTitle(req.getParameter("blogTitle"));
		newBlogEntry.setText(req.getParameter("blogText"));
		
		DAOProvider.getDAO().editBlog(Long.parseLong((String) req.getSession().getAttribute("blog.id")), newBlogEntry);
		
		resp.sendRedirect("/blog/servleti/author/" + req.getSession().getAttribute("current.user.nick"));
	}

}
