package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Environment interface that offers methods to manipulate shell and it's commands.
 * 
 * @author Petar Cvitanović
 *
 */
public interface Environment {

	/**
	 * Reads line from console. If line ends with more lines symbol, method appends next line.
	 * Every new line is interpreted as whitespace so lines don't continue after new line.
	 * 
	 * @return next input line.
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes on console.
	 * 
	 * @param text
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes one line on console.
	 * 
	 * @param text
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns all shell commands mapped with commands names.
	 * 
	 * @return map of commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Multiline symbol getter.
	 * 
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Multiline symbol setter.
	 * 
	 * @param symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Prompt symbol getter.
	 * 
	 * @return prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Prompt symbol setter.
	 * 
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * More lines symbol getter.
	 * 
	 * @return more lines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * More lines symbol setter.
	 * 
	 * @param symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
