package hr.fer.zemris.java.fractals;

import java.util.concurrent.ThreadFactory;

/**
 * Has factory method that returns new daemon thread.
 * 
 * @author Petar Cvitanović
 *
 */
public class DaemonicThreadFactory implements ThreadFactory{

	/**
	 * Creates new daemon thread.
	 */
	@Override
	public Thread newThread(Runnable r) {
		Thread daemon = new Thread(r);
		daemon.setDaemon(true);
		return daemon;
	}
	
}
