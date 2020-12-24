package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.Parser;

/**
 * Implements ShellCommand interface. Prints and sets shell's symbols for prompt, 
 * multiple lines, and more lines.
 * 
 * @author Petar Cvitanović
 *
 */
public class SymbolShellCommand implements ShellCommand{

	/**
	 * Symbol command can change or return current environment symbols (prompt, multiline, morelines).
	 * If given one arguments if will print current symbol depending on second argument.
	 * If given two arguments it will replace current symbol with second given argument.
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

		if(argumentsList.size() != 1 && argumentsList.size() != 2) {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}

		if(argumentsList.size() == 2) {
			switch (argumentsList.get(0)) {
			case "PROMPT":
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() +
						"' to '" + argumentsList.get(1).charAt(0) + "'");
				env.setPromptSymbol(argumentsList.get(1).charAt(0));
				break;

			case "MORELINES":
				env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() +
						"' to '" + argumentsList.get(1).charAt(0) + "'");
				env.setMorelinesSymbol(argumentsList.get(1).charAt(0));
				break;

			case "MULTILINE":
				env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() +
						"' to '" + argumentsList.get(1).charAt(0) + "'");
				env.setMultilineSymbol(argumentsList.get(1).charAt(0));
				break;

			default:
				env.writeln("Invalid command!");
			}

		} else if(argumentsList.size() == 1) {
			switch (argumentsList.get(0)) {
			case "PROMPT":
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
				break;

			case "MORELINES":
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
				break;

			case "MULTILINE":
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
				break;

			default:
				env.writeln("Invalid command!");
			}
		} else {
			env.writeln("Invalid command!");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();

		desc.add("Symbol command can change or return current environment");
		desc.add("symbols (prompt, multiline, morelines). If given one");
		desc.add("arguments if will print current symbol depending on second");
		desc.add("argument");
		desc.add("If given two arguments it will replace current symbol with");
		desc.add("second given argument.");

		return desc;
	}

}
