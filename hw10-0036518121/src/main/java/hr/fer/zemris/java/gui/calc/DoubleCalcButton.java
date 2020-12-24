package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 * Calculator buttons that change function depending on check box state.
 * 
 * @author Petar Cvitanović
 *
 */
public class DoubleCalcButton extends JButton{

	private static final long serialVersionUID = 1L;
	
	/**
	 * current button text
	 */
	private String text;
	
	/**
	 * current button listener
	 */
	private ActionListener listener;
	
	/**
	 * first button text
	 */
	private String text1;
	
	/**
	 * first button listener
	 */
	private ActionListener listener1;

	/**
	 * alternative button text
	 */
	private String text2;
	
	/**
	 * alternative button listener
	 */
	private ActionListener listener2;

	/**
	 * Constructor with both button texts and listeners.
	 * 
	 * @param text1
	 * @param listener1
	 * @param text2
	 * @param listener2
	 */
	public DoubleCalcButton(String text1, ActionListener listener1, String text2,
			ActionListener listener2) {
		super();
		this.text = text1;
		this.listener = listener1;
		this.text1 = text1;
		this.listener1 = listener1;
		this.text2 = text2;
		this.listener2 = listener2;

		setText(text);
		setBackground(new Color(51, 204, 255));
		setOpaque(true);
		setBorder(new LineBorder(new Color(0, 0, 154)));
		addActionListener(listener);
	}

	/**
	 * Changes current button text and function to the other text and listener
	 */
	public void toggle() {
		removeActionListener(listener);

		if(text.equals(text1)) {
			listener = listener2;
			text = text2;
		} else {
			listener = listener1;
			text = text1;
		}
		
		addActionListener(listener);
		setText(text);
	}
}



