package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Provides {@link EntityManager} instances from {@link ThreadLocal}.
 * 
 * @author Petar Cvitanović
 *
 */
public class JPAEMProvider {

	/**
	 * {@link ThreadLocal} instance
	 */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Creates {@link EntityManager} instance if it does not exist and begins transaction. Returns
	 * {@link EntityManager} instance.
	 * 
	 * @return {@link EntityManager} instance
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if(em==null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Commits transaction, closes {@link EntityManager} and removes current thread value from {@link ThreadLocal}.
	 * 
	 * @throws DAOException
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if(em==null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
}