package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Main program of our notepad. Has instance of {@link DefaultMultipleDocumentModel}.
 * Notepad has functions such as: creating new blank documents, opening existing document,
 * saving document, closing tabs, cut/copy/paste, showing statistical info, exiting application,
 * changing application language, control letter case, sort document lines, and erase duplicate lines
 * from document. Application title changes when displayed tab is changed. It also
 * has status bar on the bottom that shows length of currently opened document, caret position
 * and time.
 * 
 * @author Petar Cvitanović
 *
 */
public class JNotepadPP extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * instance of {@link DefaultMultipleDocumentModel}
	 */
	private final DefaultMultipleDocumentModel multipleModel;

	/**
	 * instance of {@link FormLocalizationProvider}
	 */
	private FormLocalizationProvider flp =
			new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	/**
	 * status bar component that shows document length
	 */
	private JLabel fileLength = new JLabel();

	/**
	 * status bar component that shows caret position
	 */
	private JLabel caretInfo = new JLabel();

	/**
	 * status bar component that shows time
	 */
	private JLabel dateTime = new JLabel();

	/**
	 * caret listener
	 */
	private CaretListener caretListener;

	/**
	 * timer
	 */
	private Timer t;

	/**
	 * Constructor.
	 */
	public JNotepadPP() {
		super();

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(20, 20);
		setSize(700, 700);

		multipleModel = new DefaultMultipleDocumentModel();
		addModelListener();

		createCaretListener();
		multipleModel.createNewDocument();

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});

		initGUI();
	}

	/**
	 * Creates caret listener that is added to currently opened tab, and removed from tab when it is closed.
	 */
	private void createCaretListener() {
		caretListener = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				int caretPosition = multipleModel.getCurrentDocument().getTextComponent().getCaretPosition();
				JTextArea textArea = multipleModel.getCurrentDocument().getTextComponent();

				try {
					int line = textArea.getLineOfOffset(caretPosition);
					int column = caretPosition - textArea.getLineStartOffset(line);
					int selected = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

					if(selected == 0) {
						toggle.setEnabled(false);
						lower.setEnabled(false);
						upper.setEnabled(false);
					} else {
						toggle.setEnabled(true);
						lower.setEnabled(true);
						upper.setEnabled(true);
					}

					fileLength.setText(String.format("length:%d", textArea.getText().length()));

					caretInfo.setText(String.format("Ln:%d Col:%d Sel:%d", line + 1, column + 1, selected));

				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	/**
	 * Adds listener to model instance. Controls application title.
	 */
	private void addModelListener() {
		multipleModel.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				multipleModel.removeTabAt(multipleModel.indexOfSingleDocument(model));
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				String fileName = "";

				if(model.getFilePath() == null) {
					fileName = "unnamed";
				} else {
					fileName = model.getFilePath().getFileName().toString();
				}

				multipleModel.addTab(fileName, new JScrollPane(model.getTextComponent()));
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				multipleModel.setSelectedIndex(multipleModel.indexOfSingleDocument(currentModel));

				if(currentModel == null) {
					return;
				}
				setTitle((currentModel.getFilePath() == null ? "(unnamed)" :
					currentModel.getFilePath()) +
						" - JNotepad++");

				currentModel.getTextComponent().addCaretListener(caretListener);
				for(CaretListener l : currentModel.getTextComponent().getCaretListeners()) {
					l.caretUpdate(null);
				}

				if(previousModel != null) {
					previousModel.getTextComponent().removeCaretListener(caretListener);
				}
			}
		});
	}

	/**
	 * Called when user tries to exit application. If there is some tabs with unsaved
	 * changes, calls function that tries to save them.
	 */
	private void exit() {
		Iterator<SingleDocumentModel> iter = multipleModel.iterator();
		boolean dispose = true;

		while(iter.hasNext()) {
			if(iter.next().isModified()) {
				dispose = unsavedChanges();
				break;
			}
		}

		if(dispose) {
			dispose();
			t.stop();
		}
	}

	/**
	 * Opens {@link JOptionPane} and if user wants to save them, iterates over unsaved documents and
	 * saves them. User can also say he doesn't want to save changes and close the program, or cancel
	 * exiting.
	 * 
	 * @return true if user wants to close the program, false otherwise.
	 */
	private boolean unsavedChanges() {
		int input = JOptionPane.showConfirmDialog(
				this,
				"Save changes in unsaved tabs?"
				);

		if(input == JOptionPane.YES_OPTION) {
			saveAll();
			return true;
		} else if(input == JOptionPane.NO_OPTION){
			return true;
		}

		return false;
	}

	/**
	 * Saves all unsaved files.
	 */
	private void saveAll() {
		Iterator<SingleDocumentModel> iter = multipleModel.iterator();

		while(iter.hasNext()) {
			SingleDocumentModel single = iter.next();

			if(single.getFilePath() != null) {
				multipleModel.saveDocument(single,
						single.getFilePath());
				continue;
			}

			JFileChooser jfc = new JFileChooser(
					FileSystemView.getFileSystemView().getHomeDirectory());

			int returnValue = jfc.showOpenDialog(null);

			if(returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();

				if(selectedFile.exists()) {
					int input = JOptionPane.showConfirmDialog(
							this,
							"File already exists on disc!",
							"Want to owerwrite it?",
							JOptionPane.YES_NO_OPTION);

					if(input == JOptionPane.YES_OPTION) {
						multipleModel.saveDocument(single,
								Paths.get(selectedFile.getAbsolutePath()));
					}
				} else {
					multipleModel.saveDocument(single,
							Paths.get(selectedFile.getAbsolutePath()));
				}
			}
		}
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(multipleModel, BorderLayout.CENTER);

		createActions();
		createMenus();
		createToolbars();

		createStatusBar();
	}

	/**
	 * Opens file. 
	 */
	private Action openFile = new LocalizableAction("open", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			open();
		}
	};

	/**
	 * Method that opens {@link JFileChooser} and opens selected file.
	 */
	private void open() {
		JFileChooser jfc = new JFileChooser(
				FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(this);

		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			multipleModel.loadDocument(Paths.get(selectedFile.getAbsolutePath()));
		}
	}

	/**
	 * Saves document in selected file.
	 */
	private Action saveFileAs = new LocalizableAction("saveAs", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			save();
		}
	};

	/**
	 * Saves document on his current file path. If file hasn't been saved yet, chooses
	 * new file to save it.
	 */
	private Action saveFile = new LocalizableAction("save", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(multipleModel.getCurrentDocument().getFilePath() == null) {
				save();
			} else {
				multipleModel.saveDocument(multipleModel.getCurrentDocument(),
						multipleModel.getCurrentDocument().getFilePath());
			}
		}
	};

	/**
	 * Method that opens {@link JFileChooser} and saves document on selected file.
	 */
	private void save() {
		JFileChooser jfc = new JFileChooser(
				FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(this);

		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			
			if(selectedFile.exists()) {
				int input = JOptionPane.showConfirmDialog(
						this,
						"Want to owerwrite it?",
						"File already exists on disc!",
						JOptionPane.YES_NO_OPTION);

				if(input == JOptionPane.YES_OPTION) {
					multipleModel.saveDocument(multipleModel.getCurrentDocument(),
							Paths.get(selectedFile.getAbsolutePath()));
					
					setTitle(selectedFile.getPath().toString() + " - JNotepad++");
				}
			} else {
				multipleModel.saveDocument(multipleModel.getCurrentDocument(),
						Paths.get(selectedFile.getAbsolutePath()));
			
				setTitle(selectedFile.getPath().toString() + " - JNotepad++");
			}
		}
	}

	/**
	 * Creates new tab with blank text area.
	 */
	private Action newDocument = new LocalizableAction("newDocument", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multipleModel.createNewDocument();
		}
	};

	/**
	 * Removes tab from notepad, and from {@link DefaultMultipleDocumentModel} instance.
	 */
	private Action deleteDocument = new LocalizableAction("close", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(saveTab()) {
				multipleModel.closeDocument(multipleModel.getCurrentDocument());
			}
		}
	};

	private boolean saveTab() {
		if(!multipleModel.getCurrentDocument().isModified()) {
			return true;
		}

		int input = JOptionPane.showConfirmDialog(
				this,
				"Save changes in unsaved tab?"
				);

		if(input == JOptionPane.YES_OPTION) {
			if(multipleModel.getCurrentDocument().getFilePath() != null) {
				multipleModel.saveDocument(multipleModel.getCurrentDocument(),
						multipleModel.getCurrentDocument().getFilePath());
			} else {
				JFileChooser jfc = new JFileChooser(
						FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(this);

				if(returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					
					if(selectedFile.exists()) {
						int input2 = JOptionPane.showConfirmDialog(
								this,
								"Want to owerwrite it?",
								"File already exists on disc!",
								JOptionPane.YES_NO_OPTION);

						if(input2 == JOptionPane.YES_OPTION) {
							multipleModel.saveDocument(multipleModel.getCurrentDocument(),
									Paths.get(selectedFile.getAbsolutePath()));
						}
					} else {
						multipleModel.saveDocument(multipleModel.getCurrentDocument(),
								Paths.get(selectedFile.getAbsolutePath()));
					}
				}
			}

			return true;
		} else if(input == JOptionPane.NO_OPTION){
			return true;
		}

		return false;
	}

	/**
	 * Deletes selected text.
	 */
	private Action deleteText = new LocalizableAction("deleteText", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea text = multipleModel.getCurrentDocument().getTextComponent();

			Document doc = text.getDocument();
			int len = Math.abs(text.getCaret().getDot() - text.getCaret().getMark());

			if(len == 0) {
				return;
			}

			int offset = Math.min(text.getCaret().getDot(), text.getCaret().getMark());

			try {
				doc.remove(offset, len);
			} catch (BadLocationException ex) {
			}
		}
	};

	/**
	 * Copies selected text to clipboard.
	 */
	private Action copyText = new LocalizableAction("copy", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea text = multipleModel.getCurrentDocument().getTextComponent();

			Document doc = text.getDocument();
			int len = Math.abs(text.getCaret().getDot() - text.getCaret().getMark());

			if(len == 0) {
				return;
			}

			int offset = Math.min(text.getCaret().getDot(), text.getCaret().getMark());

			try {
				StringSelection selection = new StringSelection(doc.getText(offset, len));

				Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
				c.setContents(selection, selection);
			} catch (BadLocationException ex) {
			}
		}
	};

	/**
	 * Inserts text from clipboard to caret position. If there is some selected text,
	 * it is erased.
	 */
	private Action pasteText = new LocalizableAction("paste", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea text = multipleModel.getCurrentDocument().getTextComponent();

			Document doc = text.getDocument();
			int len = Math.abs(text.getCaret().getDot() - text.getCaret().getMark());
			int offset = Math.min(text.getCaret().getDot(), text.getCaret().getMark());

			try {
				String clipboardText = (String) Toolkit.getDefaultToolkit()
						.getSystemClipboard().getData(DataFlavor.stringFlavor);

				doc.remove(offset, len);

				doc.insertString(offset, clipboardText, null);
			} catch ( BadLocationException ex) {
				ex.printStackTrace();

			} catch (HeadlessException ex) {
				ex.printStackTrace();

			} catch (UnsupportedFlavorException ex) {
				ex.printStackTrace();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	};

	/**
	 * Displays statistical info about currently opened document.
	 */
	private Action info = new LocalizableAction("info", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			showInfo();
		}
	};

	private void showInfo() {
		JOptionPane.showMessageDialog(
				this,
				String.format("Number of characters: %d\n"
						+ "Number of non-blank characters: %d\n"
						+ "Number of lines: %d",
						multipleModel.getCurrentDocument().getTextComponent().getText().length(),
						countCharacters(multipleModel.getCurrentDocument()),
						multipleModel.getCurrentDocument().getTextComponent().getLineCount()),
				"Document info",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Counts non-whitespace characters in given document.
	 * 
	 * @param single
	 * @return number of non-whitespace characters in given document
	 */
	private int countCharacters(SingleDocumentModel single) {
		int out = 0;

		for(char c : single.getTextComponent().getText().toCharArray()) {
			if(!Character.isWhitespace(c)) {
				out++;
			}
		}

		return out;
	}

	/**
	 * Exits application
	 */
	private Action exit = new LocalizableAction("exit", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			exit();
		}
	};

	/**
	 * Changes selected text to upper case.
	 */
	private Action upper = new LocalizableAction("upper", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeText("upper");
		}
	};

	/**
	 * Changes selected text to lower case.
	 */
	private Action lower = new LocalizableAction("lower", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeText("lower");
		}
	};

	/**
	 * Toggles case for every letter that has been selected.
	 */
	private Action toggle = new LocalizableAction("toggle", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeText("toggle");
		}
	};

	/**
	 * Changes case of letters in currently opened document depending on given string(upper, lower, toggle).
	 * 
	 * @param upper, lower or toggle
	 */
	private void changeText(String s) {
		JTextComponent textComponent = multipleModel.getCurrentDocument().getTextComponent();

		Document doc = textComponent.getDocument();
		int len = Math.abs(textComponent.getCaret().getDot() - textComponent.getCaret().getMark());

		if(len == 0) {
			return;
		}

		int offset = Math.min(textComponent.getCaret().getDot(), textComponent.getCaret().getMark());

		try {
			String text = doc.getText(offset, len);
			text = changeCase(text, s);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ex) {
		}
	}

	/**
	 * Changes case of every letter in case depending on given string.
	 * 
	 * @param text
	 * @param upper, lower or toggle
	 * @return changed text
	 */
	private String changeCase(String text, String s) {
		char[] znakovi = text.toCharArray();
		for(int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			if(s.equals("upper")) {
				znakovi[i] = Character.toUpperCase(c);
			} else if(s.equals("lower")) {
				znakovi[i] = Character.toLowerCase(c);				
			} else {
				if(Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if(Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
		}
		return new String(znakovi);
	}

	/**
	 * Removes duplicate lines in selected lines.
	 */
	private Action unique = new LocalizableAction("unique", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortLines("unique");
		}
	};

	/**
	 * Sorts selected lines ascending.
	 */
	private Action ascending = new LocalizableAction("ascending", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortLines("ascending");
		}
	};

	/**
	 * Sorts selected lines descending.
	 */
	private Action descending = new LocalizableAction("descending", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortLines("descending");
		}
	};

	/**
	 * Sorts or removes lines depending on given string.
	 * 
	 * @param ascending, descending or unique
	 */
	private void sortLines(String s) {
		JTextComponent textComponent = multipleModel.getCurrentDocument().getTextComponent();
		Document doc = textComponent.getDocument();

		int pos1 = textComponent.getCaret().getMark();
		int pos2 = textComponent.getCaret().getDot();

		Element root = doc.getDefaultRootElement();
		int startRow = root.getElementIndex(Math.min(pos1, pos2));
		int endRow = root.getElementIndex(Math.max(pos1, pos2));

		List<String> readLines = new ArrayList<String>();
		String line = "";

		for(int i = startRow;i <= endRow;i++) {
			try {
				line = doc.getText(root.getElement(i).getStartOffset(),
						root.getElement(i).getEndOffset() - root.getElement(i).getStartOffset());
			} catch (BadLocationException ex) {
			}
			readLines.add(line);
		}

		Locale l = new Locale(LocalizationProvider.getInstance().getCurrentLanguage());
		Collator collator = Collator.getInstance(l);

		if(s.equals("ascending")) {
			readLines.sort(collator);
		} else if(s.equals("descending")) {
			readLines.sort(collator.reversed());
		} else {
			Set<String> tmp = new LinkedHashSet<String>(readLines);
			readLines.clear();
			readLines.addAll(tmp);
		}

		String text = String.join("", readLines);

		if(text.charAt(text.length() - 1) == '\n') {
			text = text.substring(0, text.length() - 1);
		}

		try {
			doc.remove(root.getElement(startRow).getStartOffset(),
					root.getElement(endRow).getEndOffset() - root.getElement(startRow).getStartOffset());

			doc.insertString(root.getElement(startRow).getStartOffset(), text, null);
		} catch (BadLocationException ex) {
			try {
				doc.remove(root.getElement(startRow).getStartOffset(),
						root.getElement(endRow).getEndOffset() - root.getElement(startRow).getStartOffset() - 1);

				doc.insertString(root.getElement(startRow).getStartOffset(), text, null);
			} catch (BadLocationException e) {
			}
		}
	}

	/**
	 * Adds key bindings, mnemonics and descriptions to actions.
	 */
	private void createActions() {
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocument.putValue(Action.SHORT_DESCRIPTION, "Used to create new document.");

		deleteDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		deleteDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		deleteDocument.putValue(Action.SHORT_DESCRIPTION, "Used to remove currently selected tab.");

		openFile.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openFile.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openFile.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disc.");

		saveFile.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveFile.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveFile.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disc.");

		saveFileAs.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveFileAs.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveFileAs.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disc to some file.");

		deleteText.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
		deleteText.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		deleteText.putValue(Action.SHORT_DESCRIPTION, "Deletes the highlighted text.");

		copyText.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyText.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyText.putValue(Action.SHORT_DESCRIPTION, "Copies the highlighted text to clipboard.");

		pasteText.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		pasteText.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		pasteText.putValue(Action.SHORT_DESCRIPTION, "Pastes text from clipboard into tab text.");

		info.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		info.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		info.putValue(Action.SHORT_DESCRIPTION, "Shows information about document.");

		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exit.putValue(Action.SHORT_DESCRIPTION, "Exit application.");

		upper.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		upper.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		upper.putValue(Action.SHORT_DESCRIPTION, "Upper case.");

		lower.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		lower.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		lower.putValue(Action.SHORT_DESCRIPTION, "Lower case.");

		toggle.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		toggle.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggle.putValue(Action.SHORT_DESCRIPTION, "Toggle case.");

		unique.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift U"));
		unique.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		unique.putValue(Action.SHORT_DESCRIPTION, "Removes duplicate lines.");

		ascending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));
		ascending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		ascending.putValue(Action.SHORT_DESCRIPTION, "Sorts lines ascending.");

		descending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift D"));
		descending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		descending.putValue(Action.SHORT_DESCRIPTION, "Sorts lines descending.");
	}

	/**
	 * Creates menu bar and adds all needed items.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(new LocalizableAction("fileMenu", flp));

		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocument));
		fileMenu.add(new JMenuItem(deleteDocument));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(openFile));
		fileMenu.add(new JMenuItem(saveFile));
		fileMenu.add(new JMenuItem(saveFileAs));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(info));
		fileMenu.add(new JMenuItem(exit));

		JMenu editMenu = new JMenu(new LocalizableAction("editMenu", flp));

		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(copyText));
		editMenu.add(new JMenuItem(pasteText));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(deleteText));

		JMenu languages = new JMenu(new LocalizableAction("language", flp));

		menuBar.add(languages);

		JMenuItem en = new JMenuItem("en");
		en.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("en"));

		JMenuItem de = new JMenuItem("de");
		de.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("de"));

		JMenuItem hr = new JMenuItem("hr");
		hr.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("hr"));

		languages.add(hr);
		languages.add(de);
		languages.add(en);

		JMenu tools = new JMenu(new LocalizableAction("tools", flp));

		menuBar.add(tools);

		JMenu changeCase = new JMenu(new LocalizableAction("changeCase", flp));

		tools.add(changeCase);

		changeCase.add(new JMenuItem(upper));
		changeCase.add(new JMenuItem(lower));
		changeCase.add(new JMenuItem(toggle));

		tools.add(new JMenuItem(unique));

		JMenu sort = new JMenu(new LocalizableAction("sort", flp));

		tools.add(sort);

		sort.add(new JMenuItem(ascending));
		sort.add(new JMenuItem(descending));

		setJMenuBar(menuBar);

	}

	/**
	 * Creates toolbar and adds all needed buttons to it.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(newDocument));
		toolBar.add(new JButton(deleteDocument));
		toolBar.addSeparator();

		toolBar.add(new JButton(openFile));
		toolBar.add(new JButton(saveFile));
		toolBar.add(new JButton(saveFileAs));
		toolBar.addSeparator();

		toolBar.add(new JButton(info));

		toolBar.add(new JSeparator(SwingConstants.VERTICAL));

		toolBar.add(new JButton(deleteText));
		toolBar.addSeparator();

		toolBar.add(new JButton(copyText));
		toolBar.add(new JButton(pasteText));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Creates status bar with document length, caret info and current time.
	 */
	private void createStatusBar() {
		JPanel statusBar = new JPanel(new GridLayout(1, 0));

		statusBar.add(fileLength);
		statusBar.add(caretInfo);
		statusBar.add(dateTime);

		setTime();

		getContentPane().add(statusBar, BorderLayout.SOUTH);
	}

	/**
	 * Starts timer and updates time on status bar.
	 */
	private void setTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		dateTime.setHorizontalAlignment(SwingConstants.RIGHT);

		ActionListener updateClockAction = e -> {
			dateTime.setText(LocalDateTime.now().format(dtf));
		};

		t = new Timer(100, updateClockAction);
		t.start();
	}

	/**
	 * Main method that creates JNotepadd instance and sets it visible.
	 * 
	 * @param no arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}