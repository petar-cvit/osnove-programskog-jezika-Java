package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.Parser;

/**
 * Implements ShellCommand interface. Creates directory.
 * 
 * @author Petar Cvitanović
 *
 */
public class MkdirShellCommand implements ShellCommand{

	/**
	 * Creates directory and all it's parents.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Parser p = null;
		
		try {
		p = new Parser(arguments);
		} catch (Exception e) {
			env.writeln("Invalid arguments! (check quotation marks)");
			return ShellStatus.CONTINUE;
		}

		List<String> argumentsList = p.getArguments();
		
		if(argumentsList.size() != 1) {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}

		File file = new File(argumentsList.get(0));
		
		if(file.mkdirs()) {
			env.writeln("Directory created!");
		} else {
			env.writeln("Unable to create directory.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		
		desc.add("Creates a directory.");
		desc.add("mkdir creates any intermediate directories in the path, if needed.");
		desc.add("For example, assume \\a does not exist then");
		desc.add("mkdir \\a\\b\\c");
		desc.add("mkdir wil create folder c which is inside folder b which is inside folder a.");
		
		return desc;
	}

}
