package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Class derived from {@link LocalizationProviderBridge} class.
 * Registers itself as {@link WindowListener} for frame given in contructor.
 * 
 * @author Petar Cvitanović
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{
	
	/**
	 * Registers itself as window listener for frame given in constructor.
	 * When window is opened, connect method is called, when window is closed,
	 * disconnect method is called.
	 * 
	 * @param lp
	 * @param frame
	 */
	public FormLocalizationProvider(ILocalizationProvider lp, JFrame frame) {
		super(lp);
		
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
