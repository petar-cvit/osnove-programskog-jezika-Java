package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class that provides {@link EntityManagerFactory} instance.
 * 
 * @author Petar Cvitanović
 *
 */
public class JPAEMFProvider {

	/**
	 * {@link EntityManagerFactory} instance
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * {@link EntityManagerFactory} getter.
	 * 
	 * @return {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * {@link EntityManagerFactory} getter.
	 * 
	 * @param emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}