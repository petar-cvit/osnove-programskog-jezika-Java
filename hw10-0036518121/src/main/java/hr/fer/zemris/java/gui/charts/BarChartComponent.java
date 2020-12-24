package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Component that displays data given as {@link BarChart} class. There are an x and y axes with marks and names.
 * Every value is represented as one column in this graph.
 * 
 * @author Petar Cvitanović
 *
 */
public class BarChartComponent extends JComponent{

	private static final long serialVersionUID = 1L;

	/**
	 * bar chart values
	 */
	private BarChart barChart;

	/**
	 * Constructor with instance of {@link BarChart} class.
	 * 
	 * @param barChart
	 */
	public BarChartComponent(BarChart barChart) {
		super();
		this.barChart = barChart;
	}

	/**
	 * Paints bar chart component.
	 */
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		//calculating largest y divisible by y difference
		int largestY = 0;

		while(true) {
			if(largestY % barChart.getDiffY() == 0 && largestY + barChart.getMinY() >= barChart.getMaxY()) {
				break;
			}

			largestY++;
		}
		
		largestY += barChart.getMinY();

		int gap = 7;
		int fontHeight = g.getFontMetrics().getHeight();

		int longestString = g.getFontMetrics().stringWidth(
				Integer.valueOf(largestY).toString());

		Dimension tableOrigin = new Dimension(longestString + fontHeight + 4 * gap, 4 * gap);

		int tableWidth = getSize().width - getInsets().left -getInsets().right
				- tableOrigin.width - 8 * gap;
		int tableHeight = getSize().height - getInsets().bottom -getInsets().top -
				tableOrigin.height - 8 * gap;

		int columnWidth = tableWidth / barChart.getValues().size();

		//drawing background grid and axis marks
		double relativeDiff = tableHeight * Double.valueOf(barChart.getDiffY()) /
				Double.valueOf(largestY - barChart.getMinY());
		
		int currentYMark = barChart.getMinY();
		
		//y axis marks and horizontal grid lines
		for(double i = tableOrigin.height + tableHeight; Math.round(i) >= tableOrigin.height;i -= relativeDiff) {
			g2d.setColor(Color.ORANGE);
			g2d.drawLine(tableOrigin.width, (int) Math.round(i),
					tableOrigin.width + tableWidth + 5, (int) Math.round(i));

			g2d.setColor(Color.DARK_GRAY);	
			g2d.drawLine(tableOrigin.width, (int) Math.round(i),
					tableOrigin.width - 5, (int) Math.round(i));

			int marklenght = g.getFontMetrics().stringWidth(
					Integer.valueOf(currentYMark).toString());

			g2d.drawString(Integer.valueOf(currentYMark).toString(),
					tableOrigin.width - gap - marklenght,
					(int) Math.round(i + fontHeight / 4));

			currentYMark += barChart.getDiffY();
		}

		//x axis marks and vertical grid lines
		for(double i = tableOrigin.width; i < tableOrigin.width + tableWidth; i += columnWidth) {
			g2d.setColor(Color.ORANGE);
			g2d.drawLine((int) Math.round(i), tableOrigin.height + tableHeight,
					(int) Math.round(i), tableOrigin.height - 5);

			g2d.setColor(Color.DARK_GRAY);
			g2d.drawLine((int) Math.round(i), tableOrigin.height + tableHeight,
					(int) Math.round(i), tableOrigin.height + tableHeight + 5);
		}

		//y axis description
		AffineTransform oldAt = g2d.getTransform();
		
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);

		g2d.setTransform(at);

		int yAxisDescStart = tableOrigin.height + tableHeight -
				(tableHeight - g.getFontMetrics().stringWidth(barChart.getyAxisDesc())) / 2;

		g2d.drawString(barChart.getyAxisDesc(),
				-yAxisDescStart,
				tableOrigin.width - longestString - 2 * gap
				);
		
		g2d.setTransform(oldAt);
		
		// drawing lines with arrows
		g2d.setColor(Color.DARK_GRAY);

		double arrowDim1 = Math.round(getSize().getHeight() * 0.005);
		double arrowDim2 = Math.round(getSize().getWidth() * 0.005);

		g2d.drawLine(tableOrigin.width, tableOrigin.height - 20,
				tableOrigin.width, tableOrigin.height + tableHeight);
		
		g2d.drawLine(tableOrigin.width, tableOrigin.height + tableHeight,
				tableOrigin.width + tableWidth + 20, tableOrigin.height + tableHeight);

		//y axis arrow
		int[] yAxisArrowX = {
				tableOrigin.width,
				(int) (tableOrigin.width + arrowDim2),
				(int) (tableOrigin.width - arrowDim2)
		};

		int[] yAxisArrowY = {
				tableOrigin.height - 20,
				(int) (tableOrigin.height - 20 + arrowDim1 * 2),
				(int) (tableOrigin.height - 20 + arrowDim1 * 2)
		};

		g2d.fillPolygon(yAxisArrowX, yAxisArrowY, 3);

		//y axis arrow
		int[] xAxisArrowX = {
				tableOrigin.width + tableWidth + 20,
				(int) (tableOrigin.width + tableWidth + 20 - arrowDim2 * 2),
				(int) (tableOrigin.width + tableWidth + 20 - arrowDim2 * 2)
		};

		int[] xAxisArrowY = {
				tableOrigin.height + tableHeight,
				(int) (tableOrigin.height + tableHeight + arrowDim1),
				(int) (tableOrigin.height + tableHeight - arrowDim1)
		};

		g2d.fillPolygon(xAxisArrowX, xAxisArrowY, 3);

		//drawing columns
		int columnStart = tableOrigin.width;

		for(XYValue v : barChart.getValues()) {
			int columnHeight = (int) Math.round(tableHeight * (Double.valueOf(v.getY() - barChart.getMinY()) /
					Double.valueOf(largestY - barChart.getMinY())));

			g2d.setColor(Color.ORANGE);
			g2d.fillRect(columnStart, tableOrigin.height + (tableHeight - columnHeight), columnWidth, columnHeight);

			g2d.setColor(Color.BLACK);
			g2d.drawRect(columnStart, tableOrigin.height + (tableHeight - columnHeight), columnWidth, columnHeight);

			g2d.drawString(Integer.valueOf(v.getX()).toString(),
					stringStart(columnStart, columnWidth, Integer.valueOf(v.getX()).toString(), g.getFontMetrics()),
					tableOrigin.height + tableHeight + fontHeight + gap
					);

			columnStart += columnWidth;
		}

		//x axis description
		g2d.drawString(barChart.getxAxisDesc(),
				stringStart(tableOrigin.width, tableWidth, barChart.getxAxisDesc(), g.getFontMetrics()),
				tableOrigin.height + tableHeight + (fontHeight + gap) * 2
				);
	}

	/**
	 * Calculates where string needs to start depending on it's length.
	 * 
	 * @param x
	 * @param length
	 * @param text
	 * @param fm
	 * @return string start
	 */
	private int stringStart(int x, int length, String text, FontMetrics fm) {
		int textLength = fm.stringWidth(text);

		return x + (length - textLength) / 2;
	}



}
