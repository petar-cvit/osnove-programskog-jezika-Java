package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface that has methods that are used to manipulate with documents.
 * 
 * @author Petar Cvitanović
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{

	/**
	 * Creates new blank document.
	 * 
	 * @return created document
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns currently opened document.
	 * 
	 * @return currently opened document
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Loads document from disc and opens it.
	 * 
	 * @param path
	 * @return loaded document
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves document to given path.
	 * 
	 * @param model
	 * @param newPath
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Closes document and removes it from list of documents.
	 * 
	 * @param model
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Adds {@link MultipleDocumentListener} to list of listeners.
	 * 
	 * @param listener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Removes {@link MultipleDocumentListener} from listener list.
	 * 
	 * @param l
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Returns number of opened documents.
	 * 
	 * @return number of opened documents.
	 */
	int getNumberOfDocuments();
	
	/**
	 * Returns document on given index.
	 * 
	 * @param index
	 * @return document
	 */
	SingleDocumentModel getDocument(int index);
	
}
