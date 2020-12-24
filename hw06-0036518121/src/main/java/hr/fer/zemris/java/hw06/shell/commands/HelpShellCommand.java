package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.Parser;

/**
 * Implements ShellCommand interface. Lists all commands and it's descriptions.
 * 
 * @author Petar Cvitanović
 *
 */
public class HelpShellCommand implements ShellCommand{

	/**
	 * Lists all commands and writes it's descriptions. If help is given some other command
	 * name as argument, it prints it's name and description.
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

		if(argumentsList.size() != 0 && env.commands().containsKey(argumentsList.get(0))) {
			for(String s : env.commands().get(argumentsList.get(0)).getCommandDescription()) {
				env.writeln(s);
			}
		} else {

			for(Entry<String, ShellCommand> command : env.commands().entrySet()) {
				env.writeln(command.getValue().getCommandName() + "-");

				for(String s : command.getValue().getCommandDescription()) {
					env.writeln(s);
				}

				env.writeln("");
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();

		desc.add("Prints all commands and it's descrption.");
		desc.add("If you add some other command's name it prints it's description.");

		return desc;
	}

}
