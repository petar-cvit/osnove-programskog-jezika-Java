package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 * Represents calculator button. Extends {@link JButton} class.
 * 
 * @author Petar Cvitanović
 *
 */
public class CalcButton extends JButton{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with button text and button listener.
	 * 
	 * @param text
	 * @param listener
	 */
	public CalcButton(String text, ActionListener listener) {
		super();
		
		setText(text);
		setBackground(new Color(51, 204, 255));
		setOpaque(true);
		setBorder(new LineBorder(new Color(0, 0, 154)));
		addActionListener(listener);
	}
}