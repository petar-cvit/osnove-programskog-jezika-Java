package hr.fer.zemris.java.hw11.jnotepadpp;


/**
 * Listens to {@link SingleDocumentModel} changes.
 * 
 * @author Petar Cvitanović
 *
 */
public interface SingleDocumentListener {

	/**
	 * Model's modify flag has been changed.
	 * 
	 * @param model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Model's path has been updated.
	 * 
	 * @param model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
	
}
