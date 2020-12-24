package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * DrawCommand class implements Command interface. It's used to draw lines and move turtle around.
 * 
 * @author Petar Cvitanović
 *
 */
public class DrawCommand implements Command{

	/**
	 * length of turtle step
	 */
	private double step;
	
	/**
	 * Constructor with length of step.
	 * 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * Uses painter drawLine method to draw and move turtle.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		double x0 = state.getPosition().getX();
		double y0 = state.getPosition().getY();
		
		Vector2D shift = state.getDirection().scaled(state.getStep() * step);
		
		painter.drawLine(x0, y0, shift.getX() + x0, shift.getY() + y0, state.getColor(), 1.f);
		
		ctx.popState();
		ctx.pushState(new TurtleState(state.getDirection(),
				new Vector2D(shift.getX() + x0, shift.getY() + y0),
				state.getColor(), state.getStep()));
	}
}
