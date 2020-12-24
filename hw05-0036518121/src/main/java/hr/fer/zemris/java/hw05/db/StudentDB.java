package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Loads database and performs queries on generated database.
 * 
 * @author Petar Cvitanović
 *
 */
public class StudentDB {
	
	/**
	 * Returns new database with records loaded from the file whose path was given as argument.
	 * 
	 * @param path
	 * @return database
	 */
	public static StudentDatabase load(String path) {
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					 Paths.get(path),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			System.out.println("Unable to read document!");
		}
		
		StudentDatabase database = new StudentDatabase(lines);
		
		return database;
	}
	
	/**
	 * Filters database with query given as argument.
	 * 
	 * @param query
	 * @param db
	 * @return list of records
	 */
	public static List<StudentRecord> filterDatabase(String query, StudentDatabase db) {
		List<StudentRecord> out = new ArrayList<StudentRecord>();
		
		if(query.length() < 7 || !query.substring(0, 6).equals("query ")) {
			throw new IllegalArgumentException("Illegal command!");
		}
		
		QueryParser parser = new QueryParser(query.substring(6, query.length()));
		if(parser.isDirectQuery()) {
			System.out.println("Using index for record retrival.");
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			if(r != null) {
				out.add(r);
			}
		} else {
			out.addAll(db.filter(new QueryFilter(parser.getQuery())));
		}
		
		return out;
	}
	
	/**
	 * Main method. Loads data to database and performs queries. User gives queries
	 * through command line. If query is invalid user is informed and allowed to
	 * give new query. User can give queries until he types "exit".
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		StudentDatabase db = load("src/main/resources/database.txt");
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("> ");
			String query = sc.nextLine();

			if(query.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			List<StudentRecord> records = null;
			
			try {
				records = filterDatabase(query, db);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			List<String> output = RecordFormatter.format(records);
			output.forEach(System.out::println);
		}
	}

}
