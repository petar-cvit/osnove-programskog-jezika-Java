package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * PopCommand implements Command interface. Pops one state from contxt. 
 * 
 * @author Petar Cvitanović
 *
 */
public class PopCommand implements Command{

	/**
	 * Pops one state from context.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
