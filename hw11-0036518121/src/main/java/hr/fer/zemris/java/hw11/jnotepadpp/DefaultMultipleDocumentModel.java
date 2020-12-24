package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTabbedPane;

/**
 * Class that extends {@link JTabbedPane} class and represents text editor in our notepad.
 * It holds multiple {@link SingleDocumentModel} instances which represent tabs. This class also
 * holds instance of {@link SingleDocumentModel} that is currently opened.
 * 
 * @author Petar Cvitanović
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * list of single documents
	 */
	private List<SingleDocumentModel> singleDocuments;
	
	/**
	 * currently opened {@link SingleDocumentModel}
	 */
	private SingleDocumentModel currentSingle;
	
	/**
	 * list of {@link MultipleDocumentListener} listeners
	 */
	private List<MultipleDocumentListener> listeners;

	/**
	 * Constructor.
	 */
	public DefaultMultipleDocumentModel() {
		singleDocuments = new ArrayList<SingleDocumentModel>();
		
		listeners = new ArrayList<MultipleDocumentListener>();
		
		addChangeListener(e -> {
			try {
				currentSingle = singleDocuments.get(getSelectedIndex());
			
				for(MultipleDocumentListener l : listeners) {
					l.currentDocumentChanged(null, currentSingle);
				}
				
			} catch (IndexOutOfBoundsException ex) {
			}
		});
	}
	
	/**
	 * Returns index of given model in {@link SingleDocumentModel} list.
	 * 
	 * @param model
	 * @return index in list
	 */
	public int indexOfSingleDocument(SingleDocumentModel model) {
		return singleDocuments.indexOf(model);
	}	

	@Override
	public SingleDocumentModel createNewDocument() {
		DefaultSingleDocumentModel single = new DefaultSingleDocumentModel(null, "");
		singleDocuments.add(single);
		notifyListenersAdd(single);
		
		single.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(singleDocuments.indexOf(model),
						model.isModified() ? new FloppyIcons().getUnsavedIcon():
								new FloppyIcons().getSavedIcon());
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(singleDocuments.indexOf(model),
						model.getFilePath().getFileName().toString());
			}
		});
		
		single.setModified(false);
		
		return single;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentSingle;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		try {
			String text = String.join("\n", Files.readAllLines(path));
			
			DefaultSingleDocumentModel single = new DefaultSingleDocumentModel(path, text);

			if(alreadyOpened(single) != -1) {
				for(MultipleDocumentListener l : listeners) {
					l.currentDocumentChanged(currentSingle, singleDocuments.get(alreadyOpened(single)));
				}
				
				return single;
			}
			
			singleDocuments.add(single);
			notifyListenersAdd(single);
			
			single.addSingleDocumentListener(new SingleDocumentListener() {
				
				@Override
				public void documentModifyStatusUpdated(SingleDocumentModel model) {
					setIconAt(singleDocuments.indexOf(model),
							model.isModified() ? new FloppyIcons().getUnsavedIcon():
									new FloppyIcons().getSavedIcon());
				}
				
				@Override
				public void documentFilePathUpdated(SingleDocumentModel model) {
					setTitleAt(singleDocuments.indexOf(model),
							model.getFilePath().getFileName().toString());
				}
			});
			
			single.setModified(false);
			
			return single;

		} catch (IOException e) {
			System.out.println("Invalid file path!");
		}

		return null;
	}
	
	/**
	 * Checks if there is a {@link SingleDocumentModel} in document list with the same file path
	 * as given document.
	 * 
	 * @param model
	 * @return index of document in the list if there is some document with the same
	 * 			file path as given document, if there is not then -1.
	 */
	private int alreadyOpened(SingleDocumentModel model) {
		for(SingleDocumentModel s : singleDocuments) {
			if(s.getFilePath() != null && s.getFilePath().equals(model.getFilePath())) {
				return singleDocuments.indexOf(s);
			}
		}
		
		return -1;
	}
	
	/**
	 * Notifies all listeners that another document has been added, and that currently opened tab
	 * changed.
	 * 
	 * @param single
	 */
	private void notifyListenersAdd(SingleDocumentModel single) {
		for(MultipleDocumentListener l : listeners) {
			l.documentAdded(single);
			l.currentDocumentChanged(currentSingle, single);
		}
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		try(OutputStream os = new BufferedOutputStream(Files.newOutputStream(newPath))) {
			String text = model.getTextComponent().getText();
			byte[] textBytes = text.getBytes();
			
			os.write(textBytes);
			
			model.setFilePath(newPath);
			model.setModified(false);
		} catch (IOException e) {
			System.out.println("Invalid destination path!");
		}
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		for(MultipleDocumentListener l : listeners) {
			l.documentRemoved(model);
			singleDocuments.remove(model);
			try {
				l.currentDocumentChanged(currentSingle, singleDocuments.get(singleDocuments.size() - 1));
			} catch (IndexOutOfBoundsException e) {
				l.currentDocumentChanged(currentSingle, null);
			}
		}
		
		try {
			currentSingle = singleDocuments.get(singleDocuments.size() - 1);
		} catch (IndexOutOfBoundsException e) {
			currentSingle = null;
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return singleDocuments.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return singleDocuments.get(index);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return singleDocuments.iterator();
	}
}
