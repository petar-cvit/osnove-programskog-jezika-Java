package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class that extends JFrame. When ran shows GUI with two lists and one button. Every time
 * this button is clicked, in each list there is a new prime number.
 * 
 * @author Petar Cvitanović
 *
 */
public class PrimDemo extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}
	
	/**
	 * GUI initialisation.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<Integer>(model);
		JList<Integer> list2 = new JList<Integer>(model);
		
		JPanel listPane = new JPanel(new GridLayout(1, 0));
		listPane.add(new JScrollPane(list1));
		listPane.add(new JScrollPane(list2));
		
		JButton button = new JButton("sljedeći");
		button.addActionListener(e -> {
			model.next();
		});		
		
		cp.add(listPane, BorderLayout.CENTER);
		cp.add(button, BorderLayout.PAGE_END);
	}

	/**
	 * Main method that is ran when program is called from command line.
	 * Calls new instance of PrimeDemo and shows GUI.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
}
