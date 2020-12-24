package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * ScaleCommand implements Command interface. Used to scale length of turtle's step.
 * 
 * @author Petar Cvitanović
 *
 */
public class ScaleCommand implements Command{
	
	/**
	 * used to multiply turtle's step
	 */
	private double factor;
	
	/**
	 * Constructor with factor to scale with.
	 * 
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Scales step of the last turtle state pushed on the context stack.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().scaleShift(factor);
	}

}
