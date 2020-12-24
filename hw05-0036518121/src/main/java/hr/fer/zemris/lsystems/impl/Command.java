package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Command interface interacts with painter and context through execute method.
 * 
 * @author Petar Cvitanović
 *
 */
public interface Command {
	
	/**
	 * Changes context content or uses painters method draw line.
	 * 
	 * @param ctx
	 * @param painter
	 */
	void execute(Context ctx, Painter painter);

}
