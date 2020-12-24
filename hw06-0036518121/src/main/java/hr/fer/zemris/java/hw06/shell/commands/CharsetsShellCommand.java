package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implements ShellCommand interface. List all available charsets on console.
 * 
 * @author Petar Cvitanović
 *
 */
public class CharsetsShellCommand implements ShellCommand{

	/**
	 * Lists all available charsets on console.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}

		for(String charset : Charset.availableCharsets().keySet()) {
			env.writeln(charset);
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		
		desc.add("Lists all available charsets.");
		
		return desc;
	}

	

}
