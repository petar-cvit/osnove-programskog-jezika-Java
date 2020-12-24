package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This class is s decorator for some {@link ILocalizationProvider}. Offers connect and disconnect methods
 * which manage connection status. When asked to resolve a key delegates this request to wrapped
 * {@link ILocalizationProvider} object.  When user calls connect() on it, the method will register an
 * instance of anonimous ILocalizationListener on the decorated object. When user calls disconnect(),
 * this object will be deregistered from decorated object.
 * 
 * Listens for localization changes and notifies all register listeners.
 * 
 * @author Petar Cvitanović
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	
	/**
	 * connection flag
	 */
	private boolean connected;
	
	/**
	 * {@link ILocalizationProvider} instance
	 */
	private ILocalizationProvider ilp;
	
	/**
	 * Constructor with {@link ILocalizationProvider} instance.
	 * 
	 * @param ilp
	 */
	public LocalizationProviderBridge(ILocalizationProvider ilp) {
		this.ilp = ilp;
	}
	
	/**
	 * Connect method that adds listener and sets connected flag to true.
	 */
	public void connect() {
		if(connected) {
			return;
		}
		
		ilp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		});
		
		connected = true;
	}
	
	/**
	 * Disconnect method that removes listener and sets connected flag to false.
	 */
	public void disconnect() {
		connected = false;
		
		ilp.removeLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		});
	}
	
	@Override
	public String getString(String key) {
		return ilp.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return ilp.getCurrentLanguage();
	}
}
