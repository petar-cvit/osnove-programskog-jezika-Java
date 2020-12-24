package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface with methods for getting translated string and manipulate listeners.
 * 
 * @author Petar Cvitanović
 *
 */
public interface ILocalizationProvider {
	
	/**
	 * Adds {@link ILocalizationListener}.
	 * 
	 * @param listener
	 */
	public void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Removes {@link ILocalizationListener}.
	 * 
	 * @param listener
	 */
	public void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Gets string depending on given key.
	 * 
	 * @param key
	 * @return translated string
	 */
	public String getString(String key);
	
	/**
	 * Returns current language.
	 * 
	 * @return current language abbreviation.
	 */
	public String getCurrentLanguage();
}
