package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.Parser;

/**
 * Implements ShellCommand interface. On the right side prints only a standard subset of characters.
 * Prints hexadecimal representation of characters in given file.
 * 
 * @author Petar Cvitanović
 *
 */
public class HexDumpShellCommand implements ShellCommand{

	/**
	 * Prints hexadecimal values and and characters from given file.
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
		
		Integer counter = 0;
		
		if(!new File(argumentsList.get(0)).isFile()) {
			env.writeln("Argument is not file!");
			return ShellStatus.CONTINUE;
		}
		
		try(InputStream is = Files.newInputStream(Paths.get(argumentsList.get(0)))) {
			
			byte[] input = new byte[16];
			while(true) {
				StringBuilder sb = new StringBuilder();
				
				sb.append(String.format("%08X", counter));
				counter += 16;
				
				int r = is.read(input);
				if(r < 1) {
					break;
				}
				
				sb.append(":");
				
				int limit1 = r < 8 ? r : 8;
				int limit2 = r < 8 ? 8 : 16 - r;
				
				for(int i = 0;i < limit1;i++) {
					sb.append(" " + Util.bytetohex(new byte[] {input[i]}));
				}
				
				sb.append("   ".repeat(8 - limit1) + "|");
				
				for(int i = 0;i < r - 8;i++) {
					sb.append(Util.bytetohex(new byte[] {input[i]}) + " ");
				}
				
				sb.append("   ".repeat(limit2) + "|");
				
				for(int i = 0;i < r;i++) {
					if(input[i] < 32 || input[i] > 127) {
						sb.append(".");
					} else {
						sb.append((char) input[i]);
					}
				}
				
				env.writeln(sb.toString());
			}
		} catch (IOException e) {
			env.writeln("Invalid path!");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		
		desc.add("On the right side prints only a standard subset of characters.");
		desc.add("Prints hexadecimal representation of characters in given file.");
		
		return desc;
	}

}
