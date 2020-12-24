package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * Document node class represents tree root. It has children of class ForLoopNode, EchoNode
 * and TextNode. Extends Node class.
 * 
 * @author Petar Cvitanović
 *
 */
public class DocumentNode extends Node{

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		allNodes(this, sb);
		
		return sb.toString();
	}
	
	/**
	 * Recursively going down the tree and appending current node's contenet to stringbuilder.
	 * 
	 * @param node
	 * @param stringbuilder
	 */
	public void allNodes(Node node, StringBuilder sb) {

		if(node instanceof TextNode) {

			char[] text = ((TextNode) node).getText().toCharArray();
			for(char c : text) {
				if(c == '\\' || c == '{') {
					sb.append('\\');
				}
				sb.append(c);
			}
		} else if(node instanceof ForLoopNode) {
			sb.append("{$ FOR ");
			
			sb.append(((ForLoopNode) node).getVariable().asText());
			sb.append(" ");
			sb.append(((ForLoopNode) node).getStartExpression().asText());
			sb.append(" ");
			sb.append(((ForLoopNode) node).getEndExpression().asText());
			sb.append(" ");
			
			if(((ForLoopNode) node).getStepExpression() != null) {
				sb.append(((ForLoopNode) node).getStepExpression().asText());
			}
			
			sb.append(" $}");
			
			for(int i = 0;i < node.numberOfChildren();i++) {
				allNodes(node.getChild(i), sb);
			}

			sb.append("{$ END $}");
			
		} else if(node instanceof EchoNode){
			sb.append("{$= ");
			
			int numberOfElements = ((EchoNode) node).getElements().length;
			Element[] elements = ((EchoNode) node).getElements();
			
			for(int i = 0;i < numberOfElements;i++) {
				Element element = elements[i];
				
				if(element instanceof ElementString) {
					
					sb.append(" \"");
					char[] text = element.asText().toCharArray();
					
					for(char c : text) {
						if(c == '\\' || c == '\"') {
							sb.append('\\');
						}
						sb.append(c);
					}
					
					sb.append("\" ");
				} else if (element instanceof ElementFunction) {
					sb.append(" @" + element.asText() + " ");
				} else {
					sb.append(" " + element.asText() + " ");
				}
			}
			
			sb.append(" $}");
		} else {
			for(int i = 0;i < node.numberOfChildren();i++) {
				allNodes(node.getChild(i), sb);
			}
			
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof DocumentNode)) {
			return false;
		}
		
		DocumentNode node = (DocumentNode) o;
		
		for(int i = 0;i < numberOfChildren();i++) {
			if(!getChild(i).equals(node.getChild(i))) {
				return false;
			}
		}
		
		return true;
	}
}
























