package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.Parser;

/**
 * Implements ShellCommand interface. Output of ls command consists of 4 columns. First
 * column indicates if current object is directory(d), readable(r), writable (w) and
 * executable (x). Second column contains object size in bytes that is right aligned and
 * occupies 10 characters. Follows file creation date/time and finally file name.
 * 
 * @author Petar Cvitanović
 *
 */
public class LsShellCommand implements ShellCommand{

	/**
	 * Output of ls command consists of 4 columns. First column indicates if current object
	 * is directory(d), readable(r), writable (w) and executable (x). Second column contains
	 * object size in bytes that is right aligned and occupies 10 characters. Follows file
	 * creation date/time and finally file name.
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

		File dir = new File(argumentsList.get(0));

		if(!dir.isDirectory()) {
			env.writeln("Argument isn't directory!");
			return ShellStatus.CONTINUE;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for(File f : dir.listFiles()) {
			try {
				formatFile(f, sdf, env);
			} catch (IOException e) {
				env.writeln("Invalid file path!");
			}
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Private method that formats data about file and prints it on console through
	 * environment.
	 * 
	 * @param f
	 * @param sdf
	 * @param env
	 * @throws IOException
	 */
	private static void formatFile(File f, SimpleDateFormat sdf, Environment env) throws IOException {
		StringBuilder sb = new StringBuilder();

		sb.append(f.isDirectory() ? "d" : "-");
		sb.append(f.canRead() ? "r" : "-");
		sb.append(f.canWrite() ? "w" : "-");
		sb.append(f.canExecute() ? "x" : "-");

		sb.append(" ");

		Long size = folderContent(f);

		sb.append(" ".repeat(10 - size.toString().length()) + size + " ");

		BasicFileAttributeView faView = Files.getFileAttributeView(
				f.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
				);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

		sb.append(formattedDateTime + " " + f.getName());

		env.writeln(sb.toString());
	}

	/**
	 * Sums size of all files in a folder.
	 * 
	 * @param file
	 * @return
	 */
	private static long folderContent(File file) {
		if(!file.isDirectory()) {
			return file.length();
		}

		long size = 0;

		for(File child : file.listFiles()) {
			size += folderContent(child);
		}

		return size;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();

		desc.add("Output of ls command consists of 4 columns. First column");
		desc.add("indicates if current object is directory(d), readable(r),");
		desc.add("writable (w) and executable (x). Second column contains object");
		desc.add("size in bytes that is right aligned and occupies 10 characters.");
		desc.add("Follows file creation date/time and finally file name.");

		return desc;
	}

}
