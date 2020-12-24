package hr.fer.zemris.java.gui.layouts;

/**
 * Class with two read only properties.
 * 
 * @author Petar Cvitanović
 *
 */
public class RCPosition {
	
	/**
	 * row
	 */
	private final int row;
	
	/**
	 * column
	 */
	private final int column;
	
	/**
	 * Constructor with row and column.
	 * 
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns instance of RCPosition class determined with string. This string needs to be in form
	 * a, b where a is row, and b is column of returned RCPosition instance.
	 * 
	 * @param text
	 * @return RCPosition instance.
	 */
	public static RCPosition parse(String text) {
		String[] parts = text.split(",");
		
		return new RCPosition(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
	}

	/**
	 * Row getter.
	 * 
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Column getter.
	 * 
	 * @return getter.
	 */
	public int getColumn() {
		return column;
	}
	
}
