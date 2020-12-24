package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * ShellCommand interface offers three methods. This interface is used to implement shell
 * commands.
 * 
 * @author Petar Cvitanović
 *
 */
public interface ShellCommand {
	
	/**
	 * 
	 * @param env
	 * @param arguments
	 * @return
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Returns command's name.
	 * 
	 * @return name
	 */
	String getCommandName();
	
	/**
	 * Returns command's description.
	 * 
	 * @return description
	 */
	List<String> getCommandDescription();

}
