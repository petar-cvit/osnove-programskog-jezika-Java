package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

/**
 * Used to return floppy disc icons.
 * 
 * @author Petar Cvitanović
 *
 */
public class FloppyIcons {
	
	/**
	 * Retrieves icon image from file path given as argument, resizes it and returns as {@link ImageIcon} instance.
	 * 
	 * @param file
	 * @return
	 */
	private ImageIcon getIcon(String file) {
		try(InputStream is = this.getClass().getResourceAsStream(file)) {
			byte[] bytes = is.readAllBytes();
			ImageIcon icon = new ImageIcon(bytes);
			
			//resize
			Image image = icon.getImage();
			image = image.getScaledInstance(12, 12, Image.SCALE_SMOOTH);
			
			return new ImageIcon(image);
			
		} catch (IOException e) {
 			System.out.println("Unable to find icon!");
 			return null;
 		}
	}
	
	/**
	 * Returns blue icon as {@link ImageIcon}.
	 * 
	 * @return blue icon
	 */
	public ImageIcon getSavedIcon() {
		return getIcon("icons/saved.png");
	}
	
	/**
	 * Returns red icon as {@link ImageIcon}.
	 * 
	 * @return red icon
	 */
	public ImageIcon getUnsavedIcon() {
		return getIcon("icons/unsaved.png");
	}

}
