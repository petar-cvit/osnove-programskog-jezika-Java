package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class that shows GUI with buttons and screen that simulates calculator operations.
 * Some buttons react to check box state and change their text and function.
 * This class uses instance of {@link CalcModelImpl} class and custom layout
 * {@link CalcLayout}.
 * 
 * @author Petar Cvitanović
 *
 */
public class Calculator extends JFrame{

	private static final long serialVersionUID = 1L;

	/**
	 * calculator model
	 */
	private CalcModelImpl calcModel;
	
	/**
	 * stack
	 */
	private Stack<Double> stack;
	
	/**
	 * list of buttons with two operations
	 */
	private List<DoubleCalcButton> twoOperations;

	/**
	 * default constructor
	 */
	public Calculator() {
		super();
		calcModel = new CalcModelImpl();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		setLocation(20, 20);
		setSize(700, 400);
		twoOperations = new ArrayList<DoubleCalcButton>();
		stack = new Stack<Double>();
		initGUI();
	}

	/**
	 * GUI initialization.
	 */
	private void initGUI() {

		initializeToggable();

		setLayout(new CalcLayout(5));
		JLabel label = new JLabel(calcModel.toString(), SwingConstants.RIGHT);
		label.setFont(label.getFont().deriveFont(40f));
		label.setBackground(Color.YELLOW);
		label.setBorder(new LineBorder(Color.BLACK));
		label.setOpaque(true);

		calcModel.addCalcValueListener(e -> {
			label.setText(calcModel.toString());
		});

		add(label, new RCPosition(1, 1));		
		add(new CalcButton("=", e -> {
			try {
				Double result = calcModel.
						getPendingBinaryOperation().applyAsDouble(
								calcModel.getActiveOperand(), calcModel.getValue());

				calcModel.setValue(result);
				calcModel.setPendingBinaryOperation(null);
				
			} catch (Exception ex) {
			}
		}), new RCPosition(1, 6));
		
		add(getButton("0", digitListener("0")), new RCPosition(5, 3));
		
		add(getButton("1", digitListener("1")), new RCPosition(4, 3));
		add(getButton("2", digitListener("2")), new RCPosition(4, 4));
		add(getButton("3", digitListener("3")), new RCPosition(4, 5));
		
		add(getButton("4", digitListener("4")), new RCPosition(3, 3));
		add(getButton("5", digitListener("5")), new RCPosition(3, 4));
		add(getButton("6", digitListener("6")), new RCPosition(3, 5));
		
		add(getButton("7", digitListener("7")), new RCPosition(2, 3));
		add(getButton("8", digitListener("8")), new RCPosition(2, 4));
		add(getButton("9", digitListener("9")), new RCPosition(2, 5));

		
		add(twoOperations.get(0), new RCPosition(2, 2));

		add(twoOperations.get(1), new RCPosition(3, 1));
		add(twoOperations.get(2), new RCPosition(3, 2));
		
		add(twoOperations.get(3), new RCPosition(4, 1));
		add(twoOperations.get(4), new RCPosition(4, 2));
		
		add(twoOperations.get(5), new RCPosition(5, 1));
		add(twoOperations.get(6), new RCPosition(5, 2));
		
	
		add(new CalcButton("clr", e -> calcModel.clear()), new RCPosition(1, 7));

		
		add(new CalcButton("*", binaryOperationListener(BiFunctions.MUL)), new RCPosition(3, 6));
		add(new CalcButton("1/x", e -> freezeCalcValue(Function.INVERT)), new RCPosition(2, 1));
		add(new CalcButton("/", binaryOperationListener(BiFunctions.DIV)), new RCPosition(2, 6));
		add(new CalcButton("-", binaryOperationListener(BiFunctions.SUB)), new RCPosition(4, 6));
		add(new CalcButton("+", binaryOperationListener(BiFunctions.ADD)), new RCPosition(5, 6));
		
		
		add(new CalcButton("reset", e -> calcModel.clearAll()), new RCPosition(2, 7));

		add(new CalcButton("push", e -> {
			stack.push(calcModel.getValue());
			calcModel.clear();
		}), new RCPosition(3, 7));


		add(new CalcButton("pop", e -> {
			try {
				calcModel.setValue(stack.pop());
			} catch (EmptyStackException ex) {
				JOptionPane.showMessageDialog(
						this, 
						"Stack is already empty!");
			}
		}), new RCPosition(4, 7));

		add(new CalcButton("+/-", e -> {
			try {
				calcModel.swapSign();
			} catch (Exception ex) {
			}
		}), new RCPosition(5, 4));

		add(new CalcButton(".", e -> {
			try {
				calcModel.insertDecimalPoint();
			} catch (Exception ex) {
			}
		}), new RCPosition(5, 5));
		
		JCheckBox ch = new JCheckBox("Inv");
		ch.addActionListener(e -> toggleFunctions());

		add(ch, new RCPosition(5, 7));
	}

	/**
	 * Returns action listener for digit buttons.
	 * 
	 * @param number
	 * @return digit button action listener
	 */
	private ActionListener digitListener(String number) {
		return e -> {
			try {
				calcModel.freezeValue(null);
				calcModel.insertDigit(Integer.parseInt(number));
			} catch (Exception ex) {
			}
		};
	}

	/**
	 * Returns action listener for buttons that have binary operations.
	 * 
	 * @param binary operation
	 * @return binary operation button action listener
	 */
	private ActionListener binaryOperationListener(DoubleBinaryOperator f) {
		return e -> {
			try{

				if(calcModel.getPendingBinaryOperation() != null) {
					Double result = calcModel.
							getPendingBinaryOperation().applyAsDouble(
									calcModel.getActiveOperand(), calcModel.getValue());

					calcModel.setActiveOperand(result);
				} else {
					calcModel.setActiveOperand(calcModel.getValue());
				}


				calcModel.freezeValue(Double.valueOf(calcModel.getValue()).toString());
				calcModel.setPendingBinaryOperation(f);
				calcModel.clear();
				
			} catch (Exception ex) {
			}
		};
	}

	/**
	 * Returns button with given text and action.
	 * 
	 * @param text
	 * @param listener
	 * @return button
	 */
	private JButton getButton(String text, ActionListener listener) {
		JButton button = new JButton(text);

		button.setFont(button.getFont().deriveFont(30f));

		button.setBackground(new Color(51, 204, 255));
		button.setOpaque(true);
		button.setBorder(new LineBorder(new Color(0, 0, 154)));
		button.addActionListener(listener);

		return button;
	}

	/**
	 * Initialises list of buttons whose action depends on check box state.
	 */
	private void initializeToggable() {
		DoubleCalcButton sin = new DoubleCalcButton("sin", e -> freezeCalcValue(Function.SIN), 
				"arcsin", e -> freezeCalcValue(Function.ARC_SIN));

		DoubleCalcButton cos = new DoubleCalcButton("cos", e -> freezeCalcValue(Function.COS), 
				"arccos", e -> freezeCalcValue(Function.ARC_COS));

		DoubleCalcButton tan = new DoubleCalcButton("tan", e -> freezeCalcValue(Function.TAN), 
				"arctan", e -> freezeCalcValue(Function.ARC_TAN));

		DoubleCalcButton ctg = new DoubleCalcButton("ctg", e -> freezeCalcValue(Function.ARC_CTG), 
				"arcctg", e -> freezeCalcValue(Function.ARC_CTG));

		DoubleCalcButton log = new DoubleCalcButton("log", e -> freezeCalcValue(Function.LOG), 
				"10^x", e -> freezeCalcValue(Function.POWER_10));

		DoubleCalcButton ln = new DoubleCalcButton("ln", e -> freezeCalcValue(Function.LN), 
				"e^x", e -> freezeCalcValue(Function.POWER_E));

		DoubleCalcButton pow = new DoubleCalcButton("x^n", binaryOperationListener(BiFunctions.POW), 
				"x^(1/n)", binaryOperationListener(BiFunctions.ROOT));

		twoOperations.add(sin);
		twoOperations.add(log);
		twoOperations.add(cos);
		twoOperations.add(ln);
		twoOperations.add(tan);
		twoOperations.add(pow);
		twoOperations.add(ctg);

	}

	/**
	 * Calculates result of given function on current model value, and then sets that
	 * value to model.
	 * 
	 * @param function
	 */
	private void freezeCalcValue(IFunction f) {
		calcModel.setValue(f.execute(calcModel.getValue()));
	}

	/**
	 * Changes text and action of buttons depending on check box state.
	 */
	private void toggleFunctions() {
		for(DoubleCalcButton b : twoOperations) {
			b.toggle();
		}
	}

	/**
	 * Main method that is ran when program is called from command line.
	 * 
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
