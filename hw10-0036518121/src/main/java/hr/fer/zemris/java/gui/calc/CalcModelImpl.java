package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Implementation of {@link CalcModel} interface.
 * 
 * @author Petar Cvitanović
 *
 */
public class CalcModelImpl implements CalcModel{

	/**
	 * flag that determines whether or not calculator is editable
	 */
	private boolean editable;
	
	/**
	 * flag that determines whether or not current value is positive or not
	 */
	private boolean positive;
	
	/**
	 * current value represented as string
	 */
	private String digitsString;
	
	/**
	 * current value represented as double
	 */
	private double digitsNumber;
	
	/**
	 * frozen value
	 */
	private String frozenValue;
	
	/**
	 * operand that waits for 
	 */
	private Double activeOperand;
	
	/**
	 * operation that was last set
	 */
	private DoubleBinaryOperator pendingOperation;
	
	/**
	 * calculator listeners
	 */
	private List<CalcValueListener> listeners;

	/**
	 * Default constructor.
	 */
	public CalcModelImpl() {
		frozenValue = null;
		digitsString = "";
		digitsNumber = 0;
		editable = true;
		positive = true;
		activeOperand = null;
		listeners = new ArrayList<CalcValueListener>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Notifies all calculator listeners.
	 */
	private void notifyListeners() {
		for(CalcValueListener c : listeners) {
			c.valueChanged(this);
		}
	}

	@Override
	public double getValue() {
		return digitsNumber;
	}

	@Override
	public void setValue(double value) {
		digitsNumber = value;
		digitsString = Double.valueOf(digitsNumber).toString();
		editable = false;
		
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		digitsString = "";
		digitsNumber = 0.0;
		editable = true;
		
		notifyListeners();
	}

	@Override
	public void clearAll() {
		frozenValue = null;
		digitsString = "";
		digitsNumber = 0;
		editable = true;
		positive = true;
		activeOperand = null;
		
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editable) {
			throw new CalculatorInputException();
		}

		digitsNumber = -digitsNumber;

		if(!digitsString.isBlank()) {
			if(digitsString.indexOf('-') == 0) {
				digitsString =digitsString.substring(1);
			} else {
				digitsString = "-".concat(digitsString);
			}
		}

		positive = !positive;

		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editable || digitsString.isBlank()) {
			throw new CalculatorInputException();
		}

		if(digitsString.indexOf(".") != -1 || digitsString.equals("-")) {
			throw new CalculatorInputException("Multiple decimal points!");
		}

		digitsString = digitsString.concat(".");
		digitsNumber = Double.parseDouble(digitsString);

		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		frozenValue = null;
		
		if(!editable) {
			throw new CalculatorInputException();
		}

		if(digitsString.equals("0") && digit == 0) {
			return;
		}

		if(digitsString.equals("0") || digitsString.equals("-0")) {
			digitsString = digitsString.replace("0", "");
		}

		try {
			Double.parseDouble(
					digitsString.concat(
							Integer.valueOf(digit).toString()));

			digitsNumber = Double.parseDouble(
					digitsString.concat(
							Integer.valueOf(digit).toString()));

			digitsString = digitsString.concat(Integer.valueOf(digit).toString());

			if(digitsNumber == Double.POSITIVE_INFINITY || digitsNumber == Double.NEGATIVE_INFINITY) {
				throw new CalculatorInputException();
			}

		} catch (NumberFormatException e){
			throw new CalculatorInputException("Invalid number!");
		}
		
		notifyListeners();

	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(activeOperand == null) {
			throw new IllegalStateException();
		}

		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

	/**
	 * Freezes given value.
	 * 
	 * @param value
	 */
	public void freezeValue(String value) {
		frozenValue = value;
	}

	/**
	 * Returns true if there is some frozen value, false otherwise.
	 * 
	 * @return whether or not there is some frozen value
	 */
	public boolean hasFrozenValue() {
		return frozenValue != null;
	}

	@Override
	public String toString() {
		if(frozenValue != null) {
			return frozenValue;
		}

		if(digitsString.isBlank()) {
			return (positive ? "" : "-") + "0";
		}

		return digitsString;
	}



}
