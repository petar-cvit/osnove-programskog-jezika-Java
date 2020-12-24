package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Represents bar chart data. Contains bar chart data, maximum and minimum y value, difference between two y values
 * and axis descriptions.
 * 
 * @author Petar Cvitanović
 *
 */
public class BarChart {
	
	/**
	 * x axis description
	 */
	private String xAxisDesc;
	
	/**
	 * y axis description
	 */
	private String yAxisDesc;
	
	/**
	 * list of all chart values
	 */
	private List<XYValue> values;
	
	/**
	 * minimum y value
	 */
	private int minY;
	
	/**
	 * maximum y value
	 */
	private int maxY;
	
	/**
	 * y value difference
	 */
	private int diffY;
	
	/**
	 * Constructor with all needed data.
	 * 
	 * @param values
	 * @param xAxisDesc
	 * @param yAxisDesc
	 * @param minY
	 * @param maxY
	 * @param diffY
	 */
	public BarChart(List<XYValue> values, String xAxisDesc, String yAxisDesc, int minY, int maxY, int diffY) {
		super();
		
		if(minY < 0 || minY >= maxY) {
			throw new IllegalArgumentException();
		}
		
		for(XYValue v : values) {
			if(v.getY() < minY) {
				throw new IllegalArgumentException();
			}
		}
		
		this.xAxisDesc = xAxisDesc;
		this.yAxisDesc = yAxisDesc;
		this.values = values;
		this.minY = minY;
		this.maxY = maxY;
		this.diffY = diffY;
	}

	/**
	 * X axis description getter.
	 * 
	 * @return x axis description
	 */
	public String getxAxisDesc() {
		return xAxisDesc;
	}

	/**
	 * Y axis description getter.
	 * 
	 * @return y axis description
	 */
	public String getyAxisDesc() {
		return yAxisDesc;
	}

	/**
	 * Values getter.
	 * 
	 * @return values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Minimum y value getter.
	 * 
	 * @return minimum y value
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Maximum y value getter.
	 * 
	 * @return maximum y value
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Y value difference.
	 * 
	 * @return y value difference
	 */
	public int getDiffY() {
		return diffY;
	}
}
