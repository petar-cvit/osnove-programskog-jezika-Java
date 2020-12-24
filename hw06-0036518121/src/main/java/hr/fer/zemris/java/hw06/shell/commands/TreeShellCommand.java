package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.Parser;

/**
 * Implements ShellCommand interface. Prints tree of given directory.
 * 
 * @author Petar Cvitanović
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Prints directory tree of a given directory.
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
		
		if(!new File(argumentsList.get(0)).exists()) {
			env.writeln("Given doesn't exist as file or directory!");
			return ShellStatus.CONTINUE;
		}
		
		if(new File(argumentsList.get(0)).isFile()) {
			env.writeln("Given argument is a file!");
		}

		tree(new File(argumentsList.get(0)), 0, env);
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Recursively prints directory tree.
	 * 
	 * @param file
	 * @param level
	 * @param env
	 */
	private static void tree(File f, int level, Environment env) {
		for(int i = 0;i < level;i++) {
			env.write("  ");
		}

		env.writeln(f.getName());

		if(f.listFiles() == null) {
			return;
		}

		for(File child : f.listFiles()) {
			tree(child, level + 1, env);
		}
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		
		desc.add("Tree command prints tree with given directory as it's root.");
		desc.add("Every directory level is moved two characters to the right.");
		
		return desc;
	}

}
