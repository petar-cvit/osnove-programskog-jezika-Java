package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet that adds new comment to database.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/servleti/comment")
public class CommentServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser commentAuthor = DAOProvider.getDAO().getBlogUser((Long) req.getSession().getAttribute("current.user.id"));
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong((String) req.getSession().getAttribute("blog.id")));
		
		BlogComment blogComment = new BlogComment();
		
		blogComment.setBlogEntry(blogEntry);
		blogComment.setMessage(req.getParameter("comment"));
		blogComment.setPostedOn(new Date());
		blogComment.setUsersEMail(commentAuthor.getEmail());
		
		DAOProvider.getDAO().insertComment(blogComment);
		
		blogEntry.getComments().add(blogComment);
		
		resp.sendRedirect("/blog/servleti/author/" + req.getSession().getAttribute("authorsNick") + "/" +
								req.getSession().getAttribute("blog.id"));
	}
	
}
