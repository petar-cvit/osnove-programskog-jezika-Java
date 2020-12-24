package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Implementation of {@link ILocalizationProvider}. Creates {@link ResourceBundle} and {@link Locale}
 * which are used to return translated string from files. This class is a singleton which means that
 * there can be only one instance of this class. This instance is saved in static final variable.
 * This instance can be retrieved with getInstance method.
 * 
 * @author Petar Cvitanović
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

	/**
	 * current language, initially Croatian
	 */
	private String language;
	
	/**
	 * resource bundle
	 */
	private ResourceBundle bundle;
	
	/**
	 * locale
	 */
	private Locale locale;

	/**
	 * unique class instance
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Constructor.
	 */
	private LocalizationProvider() {
		language = "hr";
		locale = Locale.forLanguageTag(this.language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
	}

	/**
	 * Returns unique instance of this class.
	 * 
	 * @return instance
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * Sets language, locale and bundle. Notifies all listeners about that actions.
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
		locale = Locale.forLanguageTag(this.language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
		fire();
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
