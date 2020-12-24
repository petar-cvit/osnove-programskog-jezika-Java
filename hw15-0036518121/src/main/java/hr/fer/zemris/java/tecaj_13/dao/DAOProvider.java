package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Provides {@link DAO} implementation as singleton.
 * 
 * @author Petar Cvitanović
 *
 */
public class DAOProvider {

	/**
	 * {@link DAO} implementation
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Returns {@link DAO} implementation.
	 * 
	 * @return {@link DAO} implementation
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}