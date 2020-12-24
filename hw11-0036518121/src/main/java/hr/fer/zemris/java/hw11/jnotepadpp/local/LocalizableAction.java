package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Class that extends {@link AbstractAction} class.
 * Used for changing action's name depending on localization.
 * 
 * @author Petar Cvitanović
 *
 */
public class LocalizableAction extends AbstractAction{

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
	public LocalizableAction(String key, ILocalizationProvider lp) {
		putValue(NAME, lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, lp.getString(key));
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
