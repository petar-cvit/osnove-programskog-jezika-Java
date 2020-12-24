package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * PushCommand class implements Command interface. It's used to push one state to context stack.
 * 
 * @author Petar Cvitanović
 *
 */
public class PushCommand implements Command{

	/**
	 * To context stack pushes state from the top of the stack.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
