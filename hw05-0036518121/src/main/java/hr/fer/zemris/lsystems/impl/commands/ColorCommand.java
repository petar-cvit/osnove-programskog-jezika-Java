package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * ColorCommand class implements Command interface. It's used to set color on the last turtle
 * state pushed on the context stack.
 * 
 * @author Petar Cvitanović
 *
 */
public class ColorCommand implements Command{
	
	/**
	 * new color
	 */
	private Color color;
	
	/**
	 * Constructor with new color.
	 * 
	 * @param color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	/**
	 * Sets color on the last turtle state pushed on context stack.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
