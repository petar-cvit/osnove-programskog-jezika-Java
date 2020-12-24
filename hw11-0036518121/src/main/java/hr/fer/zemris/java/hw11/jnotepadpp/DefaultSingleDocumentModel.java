package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * Represents one open document in notepad, and also one tab in {@link MultipleDocumentModel}.
 * 
 * @author Petar Cvitanović
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel{

	/**
	 * text area of the document
	 */
	private JTextArea textArea;
	
	/**
	 * document file path
	 */
	private Path filePath;
	
	/**
	 * modified flag, true when there are some unsaved changes, false otherwise
	 */
	private boolean modified;
	
	/**
	 * list of listeners
	 */
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Constructor with file path and text. Creates new {@link JTextArea}.
	 * 
	 * @param path
	 * @param text
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		
		this.modified = true;
		this.filePath = path;
		this.listeners = new ArrayList<SingleDocumentListener>();
		textArea = new JTextArea(text);
		
		Document doc = textArea.getDocument();
		doc.addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		this.filePath = path;
	
		for(SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		
		for(SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
}
