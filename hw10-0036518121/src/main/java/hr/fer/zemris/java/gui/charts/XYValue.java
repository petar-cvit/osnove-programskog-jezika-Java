package hr.fer.zemris.java.gui.charts;

/**
 * Class with two read only properties. Class represents one entry in bar chart.
 * 
 * @author Petar Cvitanović
 *
 */
public class XYValue {
	
	/**
	 * x value
	 */
	private final int x;
	
	/**
	 * y value
	 */
	private final int y;
	
	/**
	 * Constructor with x and y values.
	 * 
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * X value getter.
	 * 
	 * @return X value getter
	 */
	public int getX() {
		return x;
	}

	/**
	 * Y value getter.
	 * 
	 * @return Y value getter
	 */
	public int getY() {
		return y;
	}
}
