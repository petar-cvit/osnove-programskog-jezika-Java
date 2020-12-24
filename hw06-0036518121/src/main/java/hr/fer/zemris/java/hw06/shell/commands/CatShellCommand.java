package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.Parser;

/**
 * Implements ShellCommand interface. Prints file content on console.
 * 
 * @author Petar Cvitanović
 *
 */
public class CatShellCommand implements ShellCommand{

	/**
	 * Prints file's content on console if file is file.
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
		
		if(!new File(argumentsList.get(0)).isFile()) {
			env.writeln("First argument needs to be file!");
			return ShellStatus.CONTINUE;
		}
		
		if(argumentsList.size() < 1 || argumentsList.size() > 2) {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}

		BufferedReader br = null;

		try {
			if(argumentsList.size() == 2) {
				br = Files.newBufferedReader(Paths.get(argumentsList.get(0)), Charset.forName(argumentsList.get(1)));

			} else {
				br = Files.newBufferedReader(Paths.get(argumentsList.get(0)));
			}
			
			String line = br.readLine();
			
			while(line != null) {
				env.writeln(line);
				line = br.readLine();
			}
			
		} catch (IOException e) {
			env.writeln("Invalid path!");
		} catch (UnsupportedCharsetException e) {
			env.writeln("Invalid charset!");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		
		desc.add("Displays the contents of a text file or files.");
		
		return desc;
	}

}
