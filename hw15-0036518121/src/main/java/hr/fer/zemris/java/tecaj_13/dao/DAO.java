package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface with methods that use HQL commands.
 * 
 * @author Petar Cvitanović
 *
 */
public interface DAO {

	/**
	 * Gets entry with given id. If no such entry exists returns null.
	 * 
	 * @param id entry id
	 * @return entry or <code>null</code> if entry does not exist
	 * @throws DAOException if error occurs while getting data from database
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Returns list of all registered blog users.
	 * 
	 * @return list of all blog users
	 * @throws DAOException if error occurs while getting data from database
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;
	
	/**
	 * Returns list of all registered blog users with given nickname.
	 * 
	 * @return list of all blog users with given nickname
	 * @throws DAOException if error occurs while getting data from database
	 */
	public List<BlogUser> getBlogUserByNick(String nick) throws DAOException;
	
	/**
	 * Inserts given {@link BlogUser} into database.
	 * 
	 * @throws DAOException if error occurs while performing HQL command
	 */
	public void insertNewUser(BlogUser user) throws DAOException;
	
	/**
	 * Returns list of all {@link BlogEntry} instances created by given {@link BlogUser}.
	 * 
	 * @return list of blog entries
	 * @throws DAOException if error occurs while getting data from database
	 */
	public List<BlogEntry> getEntriesByAuthor(BlogUser blogUser) throws DAOException;
	
	/**
	 * Inserts given {@link BlogEntry} into database.
	 * 
	 * @throws DAOException if error occurs while performing HQL command
	 */
	public void insertBlogEntry(BlogEntry blogEntry) throws DAOException;

	/**
	 * Returns list of all {@link BlogEntry} instances created by given {@link BlogUser}.
	 * 
	 * @return list of blog entries
	 * @throws DAOException if error occurs while getting data from database
	 */
	public BlogUser getBlogUser(Long id) throws DAOException;
	
	/**
	 * Inserts given {@link BlogComment} into database.
	 * 
	 * @throws DAOException if error occurs while performing HQL command
	 */
	public void insertComment(BlogComment comment) throws DAOException;
	
	/**
	 * Updates {@link BlogEntry} with given blogID with given blog entry attributes.
	 * 
	 * @throws DAOException if error occurs while performing HQL command
	 */
	public void editBlog(Long blogID, BlogEntry blogEntry) throws DAOException;
}