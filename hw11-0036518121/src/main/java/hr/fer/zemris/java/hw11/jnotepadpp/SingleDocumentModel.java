package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface with methods to manipulate with documents content.
 * 
 * @author Petar Cvitanović
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * Text area getter.
	 * 
	 * @return text area
	 */
	JTextArea getTextComponent();

	/**
	 * File path getter.
	 * 
	 * @return file path
	 */
	Path getFilePath();
	
	/**
	 * File path setter.
	 * 
	 * @param path
	 */
	void setFilePath(Path path);
	
	/**
	 * Returns modify flag.
	 * 
	 * @return modify flag
	 */
	boolean isModified();
	
	/**
	 * Modify flag setter.
	 * 
	 * @param modified
	 */
	void setModified(boolean modified);
	
	/**
	 * Adds given listener to model's listeners.
	 * 
	 * @param listener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes given listener from model's listeners.
	 * 
	 * @param listener
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

}
