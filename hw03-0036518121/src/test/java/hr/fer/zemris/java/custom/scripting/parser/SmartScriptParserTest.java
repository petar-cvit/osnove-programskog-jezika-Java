package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

class SmartScriptParserTest {
	
	@Test
	public void test() {
		SmartScriptParser parser = new SmartScriptParser("gluposti\\{ i dos\\\\ada. {$= @atan \"\\nmlijeko\" $}");

		DocumentNode document = parser.getDocumentNode();
		assertEquals(2, document.numberOfChildren());
		
		assertEquals("gluposti{ i dos\\ada. ", ((TextNode) document.getChild(0)).getText());
		assertEquals("atan", ((EchoNode) document.getChild(1)).getElements()[0].asText());
		assertEquals("\nmlijeko", ((EchoNode) document.getChild(1)).getElements()[1].asText());
	}
	
	@Test
	public void faliKrajForaTest() {
		try {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser("volim matematiku {$FOR i 4 4$}\n"
					+ "{$FOR i 5 4$}\n"
					+ "{$end$}");
			fail("Nema end");
		} catch (SmartScriptParserException ex) {
		}
	}
	
	@Test
	public void forSMaloElemenataTest() {
		try {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser("volim matematiku {$FOR i 4 4$}\n"
					+ "{$FOR i 5 $}\n"
					+ "{$end$}{$end$}");
			fail("Mora bacit iznimku na malo elemenata");
		} catch (SmartScriptParserException ex) {
		}
	}

	@Test
	public void loop1Test() {
		SmartScriptParser parser = new SmartScriptParser(
				"nekakva recenica. {$FOR e 2.5 -4.56$} ide echo {$= varijabla * \"želj\\nko\"$} @tekst {$END$}");

		DocumentNode document = parser.getDocumentNode();
		assertEquals(2, document.numberOfChildren());

		ForLoopNode loop = (ForLoopNode) document.getChild(1);
		assertEquals(3, loop.numberOfChildren());

		assertEquals(" ide echo ", ((TextNode) loop.getChild(0)).getText());
		
		assertEquals("varijabla", ((EchoNode) loop.getChild(1)).getElements()[0].asText());
		assertEquals("*", ((EchoNode) loop.getChild(1)).getElements()[1].asText());
		assertEquals("želj\nko", ((EchoNode) loop.getChild(1)).getElements()[2].asText());
		
		assertEquals(" @tekst ", ((TextNode) loop.getChild(2)).getText());

		assertEquals("e", loop.getVariable().asText());
		assertEquals("2.5", loop.getStartExpression().asText());
		assertEquals("-4.56", loop.getEndExpression().asText());
	}

	@Test
	public void loop2Test() {
		SmartScriptParser parser = new SmartScriptParser(
				"nekakva recenica. {$FOR e 2.5 -4.56 12.89$} ide echo {$= varijabla * \"želj\\nko\"$} @tekst {$END$}");

		DocumentNode document = parser.getDocumentNode();
		assertEquals(2, document.numberOfChildren());

		ForLoopNode loop = (ForLoopNode) document.getChild(1);
		assertEquals(3, loop.numberOfChildren());

		assertEquals(" ide echo ", ((TextNode) loop.getChild(0)).getText());
		
		assertEquals("varijabla", ((EchoNode) loop.getChild(1)).getElements()[0].asText());
		assertEquals("*", ((EchoNode) loop.getChild(1)).getElements()[1].asText());
		assertEquals("želj\nko", ((EchoNode) loop.getChild(1)).getElements()[2].asText());
		
		assertEquals(" @tekst ", ((TextNode) loop.getChild(2)).getText());

		assertEquals("e", loop.getVariable().asText());
		assertEquals("2.5", loop.getStartExpression().asText());
		assertEquals("-4.56", loop.getEndExpression().asText());
		assertEquals("12.89", loop.getStepExpression().asText());
	}
	
	@Test
	public void zadacaPrimjerTest() {
		SmartScriptParser parser = new SmartScriptParser(
				"This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				" This is {$=  i  $}-th time this message is generated.\r\n" + 
				"{$ END $}\r\n" + 
				"{$ FOR i 0 10 2 $}\r\n" + 
				" sin({$=  i  $}^2) = {$=  i  i  *  @sin  \"0.000\"  @decfmt  $}\r\n" + 
				"{$ END $}");
		
		DocumentNode document = parser.getDocumentNode();
		assertEquals(4, document.numberOfChildren());

		TextNode node = (TextNode) document.getChild(0);
		assertTrue(node.getText().equals("This is sample text.\r\n"));
		
		ForLoopNode loop1 = (ForLoopNode) document.getChild(1);

		assertEquals("i", loop1.getVariable().asText());
		assertEquals("1", loop1.getStartExpression().asText());
		assertEquals("10", loop1.getEndExpression().asText());
		assertEquals("1", loop1.getStepExpression().asText());
		
		TextNode node2 = (TextNode) loop1.getChild(0);
		assertTrue(node2.getText().equals("\r\n This is "));
		
		EchoNode echo1 = (EchoNode) loop1.getChild(1);
		assertEquals("i", echo1.getElements()[0].asText());
		
		TextNode node3 = (TextNode) loop1.getChild(2);
		assertTrue(node3.getText().equals("-th time this message is generated.\r\n"));
		
		TextNode node4 = (TextNode) document.getChild(2);
		assertTrue(node4.getText().equals("\r\n"));
		
		ForLoopNode loop2 = (ForLoopNode) document.getChild(3);

		assertEquals("i", loop2.getVariable().asText());
		assertEquals("0", loop2.getStartExpression().asText());
		assertEquals("10", loop2.getEndExpression().asText());
		assertEquals("2", loop2.getStepExpression().asText());
		
		TextNode node5 = (TextNode) loop2.getChild(0);
		assertTrue(node5.getText().equals("\r\n sin("));
		
		EchoNode echo2 = (EchoNode) loop2.getChild(1);
		assertEquals("i", echo2.getElements()[0].asText());
		
		TextNode node6 = (TextNode) loop2.getChild(2);
		assertTrue(node6.getText().equals("^2) = "));
		
		EchoNode echo3 = (EchoNode) loop2.getChild(3);
		assertEquals("i", echo3.getElements()[0].asText());
		assertEquals("i", echo3.getElements()[1].asText());
		assertEquals("*", echo3.getElements()[2].asText());
		assertEquals("sin", echo3.getElements()[3].asText());
		assertEquals("0.000", echo3.getElements()[4].asText());
		assertEquals("decfmt", echo3.getElements()[5].asText());
		
		TextNode node7 = (TextNode) loop2.getChild(4);
		assertTrue(node7.getText().equals("\r\n"));
		
	}
	
	@Test
	public void primjerTest() {
		String doc = loader("primjer3.txt");

		SmartScriptParser parser = new SmartScriptParser(doc);
		DocumentNode d1 = parser.getDocumentNode();
		SmartScriptParser parser2 = new SmartScriptParser(d1.toString());
		DocumentNode d2 = parser2.getDocumentNode();
		
		assertTrue(d1.equals(d2));
 	}
	
	@Test
	public void iznimkaPrimjerTest() {
		try {
			String doc = loader("primjer9.txt");
			SmartScriptParser parser = new SmartScriptParser(doc);
			fail("nije element");
		} catch (SmartScriptParserException ex) {
		}
	}
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(InputStream is =
			this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while(true) {
					int read = is.read(buffer);
					if(read<1) break;
					bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}

}
