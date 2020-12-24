package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.Hashtable;

/**
 * Custom layout that implements LayoutManager2 interface.
 * 
 * @author Petar Cvitanović
 *
 */
public class CalcLayout implements LayoutManager2{

	/**
	 * gap between components
	 */
	private int gap;

	/**
	 * number of component rows
	 */
	private final int rows = 5;

	/**
	 * number of component columns
	 */
	private final int columns = 7;

	/**
	 * components mapped to it's positions determined by RCPosition intenaces.
	 */
	private Hashtable<Component, RCPosition> compTable;

	/**
	 * Default constructor. Sets gap to zero.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor with given gap.
	 * 
	 * @param gap
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		compTable = new Hashtable<Component, RCPosition>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		compTable.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getDimension(parent, "preferred");
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getDimension(parent, "minimum");
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getDimension(target, "maximum");
	}

	/**
	 * Returns dimension of main component based on given string(minimum, preferred, maximum).
	 * 
	 * @param parent
	 * @param s
	 * @return dimension
	 */
	private Dimension getDimension(Container parent, String s) {
		Dimension dim = getLargestComponent(parent, s);

		dim.setSize(columns * (dim.width + gap) - gap, rows * (dim.height + gap) - gap);

		return dim;
	}

	/**
	 * Iterates through components and finds the largest component.
	 * 
	 * @param parent
	 * @param s
	 * @return largest component
	 */
	private Dimension getLargestComponent(Container parent, String s) {
		Dimension dim = new Dimension(0, 0);
		for(Component c : parent.getComponents()) {
			int height = 0, width = 0;

			RCPosition rc = compTable.get(c);

			switch (s) {
			case "minimum":
				height = (int) c.getMinimumSize().getHeight();

				width = rc.getColumn() == 1 && rc.getRow() == 1 ?
						(int) (c.getMinimumSize().getWidth() - 4 * gap) / 5 : 
							(int) c.getMinimumSize().getWidth();
						break;

			case "preferred":
				height = (int) c.getPreferredSize().getHeight();

				width = rc.getColumn() == 1 && rc.getRow() == 1 ?
						(int) (c.getPreferredSize().getWidth() - 4 * gap) / 5 : 
							(int) c.getPreferredSize().getWidth();
						break;

			case "maximum":
				height = (int) c.getMaximumSize().getHeight();

				width = rc.getColumn() == 1 && rc.getRow() == 1 ?
						(int) (c.getMaximumSize().getWidth() - 4 * gap) / 5 : 
							(int) c.getMaximumSize().getWidth();
						break;

			default:
				throw new IllegalArgumentException("Invalid size attribute!");
			}

			dim.height = Math.max(height, dim.height);
			dim.width = Math.max(width, dim.width);
		}

		return dim;
	}

	@Override
	public void layoutContainer(Container parent) {

		if(parent.getComponentCount() == 0) {
			return;
		}

		Dimension size = parent.getSize();

		double width = size.width - parent.getInsets().left -parent.getInsets().right;
		double height = size.height  - parent.getInsets().top -parent.getInsets().bottom;

		double cellWidth = (width - gap * (columns - 1)) / columns;
		double cellHeight = (height - gap * (rows - 1)) / rows;

		for(Component c : parent.getComponents()) {
			RCPosition rc = compTable.get(c);

			if(rc.getColumn() < 6 && rc.getRow() == 1) {
				c.setBounds(parent.getInsets().left, parent.getInsets().top, (int) Math.round(5 * (cellWidth + gap) - gap),
						(int) Math.round(cellHeight));
			} else {
				c.setBounds((int) Math.round((rc.getColumn() - 1) * (cellWidth + gap) + parent.getInsets().left), 
						(int) Math.round((rc.getRow() - 1) * (cellHeight + gap) + parent.getInsets().top),
						(int) Math.round((rc.getColumn()) * (cellWidth + gap) - gap - (rc.getColumn() - 1) * (cellWidth + gap)),
						(int) Math.round((rc.getRow()) * (cellHeight + gap) - gap - (rc.getRow() - 1) * (cellHeight + gap)));
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(!(constraints instanceof RCPosition) && !(constraints instanceof String)) {
			throw new IllegalArgumentException("Constraint must be instance of RCPosition class!");
		}

		RCPosition rc = null;

		if(constraints instanceof RCPosition) {
			rc = (RCPosition) constraints;
		} else {
			try {
				rc = RCPosition.parse((String) constraints);
			} catch(Exception e) {
				throw new IllegalArgumentException("Unable to parse given string!");
			}
		}

		if(rc.getRow() < 1 || rc.getRow() > 5 ||
				rc.getColumn() < 1 || rc.getRow() > 7 ||
				rc.getColumn() < 6 && rc.getColumn() != 1 && rc.getRow() == 1) {
			throw new NullPointerException("Invalid row or column index!");
		}

		if(compTable.containsKey(comp)) {
			throw new NullPointerException("Slot is taken!");
		}

		compTable.put(comp, rc);

	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {

	}

}
