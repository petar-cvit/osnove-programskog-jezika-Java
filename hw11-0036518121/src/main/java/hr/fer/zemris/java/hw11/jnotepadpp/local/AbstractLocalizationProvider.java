package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that implements {@link ILocalizationProvider} interface.
 * Has list of listeners that listen for localization change, and notifies
 * them with fire method.
 * 
 * @author Petar Cvitanović
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	
	/**
	 * list of listeners
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * Constructor. 
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<ILocalizationListener>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Notifies all registered listeners.
	 */
	public void fire() {
		for(ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
	
}
