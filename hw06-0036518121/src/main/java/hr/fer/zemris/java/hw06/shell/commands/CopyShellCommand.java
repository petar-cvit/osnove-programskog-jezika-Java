package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.Parser;

/**
 * Copies content from first given file to another. If destination (second) file
 * already exists, user is asked whether or not he wants to overwrite it. If
 * destination is directory file is copied to this directory.
 * 
 * @author Petar Cvitanović
 *
 */
public class CopyShellCommand implements ShellCommand{

	/**
	 * Copies content from one file to another. If destination file is a directory,
	 * file is copied to this directory. If destination file already exists user is
	 * asked whether or not he wants to overwrite it.
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
		
		if(argumentsList.size() != 2) {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}
		
		if(!new File(argumentsList.get(0)).isFile()) {
			env.writeln("First argument needs to be file!");
			return ShellStatus.CONTINUE;
		}
		
		File destination = new File(argumentsList.get(1));

		if(destination.exists() && destination.isFile()) {
			env.writeln("Are you sure you want to overwrite " +
					argumentsList.get(1) + " with " + argumentsList.get(0) + "? (y/n)");
			String s = env.readLine();
			
			while((!s.trim().toLowerCase().equals("y") && !s.trim().toLowerCase().equals("n")) || s.isBlank()) {
				env.writeln("Select y for yes or n for no.");
				s = env.readLine();
			}

			if(s.toLowerCase().trim().equals("n")) {
				return ShellStatus.CONTINUE;
			}
		}

		OutputStream os = null;
		InputStream is = null;

		try {
			is = Files.newInputStream(Paths.get(argumentsList.get(0)));

			if(destination.isDirectory()) {
				File newFile = new File(new File(argumentsList.get(1)), argumentsList.get(0));
				
				os = Files.newOutputStream(newFile.toPath());
			} else {
				os = Files.newOutputStream(Paths.get(argumentsList.get(1)));
			}
		} catch (Exception e) {
			env.writeln("Invalid file paths!");
			return ShellStatus.CONTINUE;
		}

		try {
			byte[] buff = new byte[4096];
			while(true) {
				int r = is.read(buff);
				if(r < 1) {
					break;
				}

				os.write(buff);
				os.flush();
			}
		} catch(IOException ex) {
			env.writeln("Invalid file paths!");
			return ShellStatus.CONTINUE;
		}

		env.writeln("Content of " + argumentsList.get(0) + " has been copied to " + 
				argumentsList.get(1) + " file.");

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		
		desc.add("Copies content from first given file to another.");
		desc.add("If destination (second) file already exists, user is asked");
		desc.add("whether or not he wants to overwrite it.");
		desc.add("If destination is directory file is copied to this directory.");
		
		return desc;
	}

}
