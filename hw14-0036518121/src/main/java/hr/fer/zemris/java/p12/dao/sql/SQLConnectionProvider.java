package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * Holds connections to database in {@link ThreadLocal} object.
 * 
 * @author Petar Cvitanović
 *
 */
public class SQLConnectionProvider {

	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Set connection for current thread (or delete if map entry is <code>null</code>).
	 * 
	 * @param con database connection
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Get connection that current thread can use.
	 * 
	 * @return connection to database
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}