package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * RotateCommand class implements Command interface. Used to rotate direction of the turtle.
 * 
 * @author Petar Cvitanović
 *
 */
public class RotateCommand implements Command{
	
	/**
	 * angle in radians
	 */
	private double angle;
	
	/**
	 * Constructor with angle in degrees.
	 * 
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle/360.0 * 2 * Math.PI;
	}

	/**
	 * Rotates direction of the last pushed turtle state on the context stack for the given
	 * angle.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		state.getDirection().rotate(angle);
	}

}
