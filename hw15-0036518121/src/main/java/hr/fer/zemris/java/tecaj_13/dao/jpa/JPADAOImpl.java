package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * {@link DAO} interface implementation.
 * 
 * @author Petar Cvitanović
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}
	
	@Override
	public List<BlogUser> getBlogUserByNick(String nick) throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> blogUsers = 
				(List<BlogUser>) JPAEMProvider.getEntityManager().createQuery("select b from BlogUser as b where b.nick=:be")
					.setParameter("be", nick)
					.getResultList();
		
		return blogUsers;
	}
	
	@Override
	public void insertNewUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);
	}
	
	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> blogUsers = 
				(List<BlogUser>) JPAEMProvider.getEntityManager().createQuery("select b from BlogUser as b")
					.getResultList();
		
		return blogUsers;
	}
	
	@Override
	public List<BlogEntry> getEntriesByAuthor(BlogUser blogUser) throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogEntry> blogEntries = 
				(List<BlogEntry>) JPAEMProvider.getEntityManager().createQuery("select b from BlogEntry as b where b.creator=:be")
					.setParameter("be", blogUser)
					.getResultList();
		
		return blogEntries;
	}
	
	@Override
	public void insertBlogEntry(BlogEntry blogEntry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(blogEntry);
	}
	
	@Override
	public BlogUser getBlogUser(Long id) throws DAOException {
		BlogUser blogUser = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		return blogUser;
	}
	
	@Override
	public void insertComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
	}
	
	@Override
	public void editBlog(Long blogID, BlogEntry blogEntry) throws DAOException {
		JPAEMProvider.getEntityManager()
					.createQuery("update BlogEntry set text=:btext, title=:btitle, lastModifiedAt=:btime where id=:be")
					.setParameter("btext", blogEntry.getText())
					.setParameter("btitle", blogEntry.getTitle())
					.setParameter("btime", blogEntry.getLastModifiedAt())
					.setParameter("be", blogID)
					.executeUpdate();
	}
}