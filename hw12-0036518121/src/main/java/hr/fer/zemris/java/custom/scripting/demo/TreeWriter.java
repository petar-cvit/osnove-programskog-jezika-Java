package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class that prints file's content on console.
 * 
 * @author Petar Cvitanović
 *
 */
public class TreeWriter {

	/**
	 * Class that implements {@link INodeVisitor} and prints file's content on console.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	private static class WriterVisitor implements INodeVisitor{

		@Override
		public void visitTextNode(TextNode node) {
			System.out.println(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.println(String.join(" ",
					"{",
					"for",
					node.getVariable().asText(),
					node.getStartExpression().asText(),
					node.getEndExpression().asText(),
					node.getStepExpression().asText(),
					"}")
					);

			for(int i = 0;i < node.numberOfChildren();i++) {
				node.getChild(i).accept(this);
			}

			System.out.println("{ end }");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.printf("{%%= ");
			for(Element e : node.getElements()) {
				System.out.printf(e.asText());
			}
			System.out.printf("}");
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0;i < node.numberOfChildren();i++) {
				node.getChild(i).accept(this);
			}
		}

	}

	/**
	 * Main method that reads file's content, parses it and uses {@link INodeVisitor} implementation
	 * to write file's content on console.
	 * 
	 * @param file path
	 */
	public static void main(String[] args) {
		String filepath = args[0];

		String docBody = null;
		try {
			docBody = new String(
					Files.readAllBytes(Paths.get(filepath)),
					StandardCharsets.UTF_8
					);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		
		WriterVisitor visitor = new WriterVisitor();
		
		document.accept(visitor);
	}

}