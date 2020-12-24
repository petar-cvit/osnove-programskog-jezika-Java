package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * SkipCommand implements Command. Used to move turtle on the screen.
 * 
 * @author Petar Cvitanović
 *
 */
public class SkipCommand implements Command{
	
	/**
	 * length of step
	 */
	private double step;
	
	/**
	 * Constructor with step.
	 * 
	 * @param step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	/**
	 * Moves turtle for the given step but doesn't draw a line.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		double x0 = state.getPosition().getX();
		double y0 = state.getPosition().getY();
		
		Vector2D shift = state.getDirection().scaled(state.getStep() * step);
		
		painter.drawLine(x0, y0, shift.getX() + x0, shift.getY() + y0, state.getColor(), 0.f);
		
		ctx.popState();
		ctx.pushState(new TurtleState(state.getDirection(),
				new Vector2D(shift.getX() + x0, shift.getY() + y0),
				state.getColor(), state.getStep()));
	}

}
