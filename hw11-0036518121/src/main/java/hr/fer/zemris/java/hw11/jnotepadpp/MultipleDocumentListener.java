package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Listener for {@link MultipleDocumentModel}. Listens on model's changes.
 * 
 * @author Petar Cvitanović
 *
 */
public interface MultipleDocumentListener{

	/**
	 * Change of opened document.
	 * 
	 * @param previousModel
	 * @param currentModel
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel,
			SingleDocumentModel currentModel);
	
	/**
	 * Another document has been added.
	 * 
	 * @param added document
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Document has been removed.
	 * 
	 * @param removed document
	 */
	void documentRemoved(SingleDocumentModel model);

}
