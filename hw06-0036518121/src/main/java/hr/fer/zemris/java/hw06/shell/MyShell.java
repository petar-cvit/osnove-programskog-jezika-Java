package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexDumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * MyShell class imitates terminal. It can perform actions such as: make new directory,
 * print out directory tree, write file's content on console... There is a help command
 * that prints all commands and it's descriptions.
 * 
 * @author Petar Cvitanović
 *
 */
public class MyShell {

	/**
	 * Implements evnironment interface. User communicates with shell only through this class
	 * and it's methods.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	private static class EvnironmentImpl implements Environment {

		/**
		 * prompt symbol
		 */
		private char promptSymbol;
		
		/**
		 * more lines symbol
		 */
		private char moreLinesSymbol;
		
		/**
		 * multi line symbol
		 */
		private char multiLineSymbol;
		
		/**
		 * scanner
		 */
		private Scanner sc;

		/**
		 * shell commands
		 */
		private SortedMap<String, ShellCommand> commands;

		/**
		 * Default constructor.
		 */
		private EvnironmentImpl() {
			promptSymbol = '>';
			moreLinesSymbol = '\\';
			multiLineSymbol = '|';
			
			sc = new Scanner(System.in);

			SortedMap<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();

			commands.put("symbol", new SymbolShellCommand());
			commands.put("cat", new CatShellCommand());
			commands.put("charsets", new CharsetsShellCommand());
			commands.put("mkdir", new MkdirShellCommand());
			commands.put("ls", new LsShellCommand());
			commands.put("tree", new TreeShellCommand());
			commands.put("copy", new CopyShellCommand());
			commands.put("hexdump", new HexDumpShellCommand());
			commands.put("exit", new ExitShellCommand());
			commands.put("help", new HelpShellCommand());
			
			this.commands = Collections.unmodifiableSortedMap(commands);
		}

		@Override
		public String readLine() throws ShellIOException {
			String line = "";

			while(true) {
				String lineInput = sc.nextLine();
				
				if(lineInput.isBlank() && line.isBlank()) {
					return "";
				}

				if(lineInput.charAt(lineInput.length() - 1) != moreLinesSymbol) {
					line = line.concat(" " + lineInput);
					break;
				}
				line = line.concat(" " + lineInput.substring(0, lineInput.length() - 1));
				write(multiLineSymbol + " ");
			}

			return line;
		}

		@Override
		public void write(String text) throws ShellIOException {
			if(text == null) {
				throw new ShellIOException();
			}

			System.out.print(text);

		}

		@Override
		public void writeln(String text) throws ShellIOException {
			if(text == null) {
				throw new ShellIOException();
			}

			System.out.println(text);

		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return commands;
		}

		@Override
		public Character getMultilineSymbol() {
			return multiLineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			this.multiLineSymbol = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			this.promptSymbol = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return moreLinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			this.moreLinesSymbol = symbol;
		}

	}

	/**
	 * Uses nested EnvironmentImpl class to implement shell.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Environment env = new EvnironmentImpl();

		env.writeln("Welcome to MyShell v 1.0");

		while(true) {
			env.write(env.getPromptSymbol() + " ");

			String line = "";
			
			try {
				line = env.readLine();
			} catch (Exception e) {
				env.writeln("Invalid input!");
			}
			
			line = removeWhitespaces(line);
			
			if(line.isBlank()) {
				continue;
			}
			

			String commandName = line.split(" ")[0];

			if(!env.commands().containsKey(commandName)) {
				env.writeln("Invalid command!");
				continue;
			}

			ShellStatus status = env.commands().get(commandName).executeCommand(
					env, line.substring(commandName.length() , line.length()));
			
			if(status == ShellStatus.TERMINATE) {
				break;
			}
		}

	}

	/**
	 * Private static method that eliminates all multiple whitespaces in a row.
	 * For example for input string "   I     love     this course." method returns.
	 * "I love this course.".
	 * 
	 * @param string
	 * @return string
	 */
	public static String removeWhitespaces(String s) {
		String[] parts = s.split("\\s");

		StringBuilder sb = new StringBuilder();

		for(String part : parts) {
			if(!part.isBlank()) {
				if(sb.toString().isBlank()) {
					sb.append(part);
				} else {
					sb.append(" " + part);
				}
			}
		}
		
		return sb.toString();
	}

}
