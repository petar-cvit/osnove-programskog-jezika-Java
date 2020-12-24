package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * Extends {@link JLabel} class. Used for changing text in labels.
 * 
 * @author Petar Cvitanović
 *
 */
public class LJLabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor with key and {@link ILocalizationProvider} instance.
	 * 
	 * @param key
	 * @param lp
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		setText(lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(lp.getString(key));
			}
		});
	}

}