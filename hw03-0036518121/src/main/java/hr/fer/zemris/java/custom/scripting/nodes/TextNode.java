package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Extends Node class. Holds texts outside of tags.
 * Can't have children.
 * 
 * @author Petar Cvitanović
 *
 */
public class TextNode extends Node{

	/**
	 * text outside of tag
	 */
	private String text;
	
	/**
	 * Constructor with text
	 * 
	 * @throws NullPointerException
	 * 				if given text is null!
	 * 
	 * @param text
	 */
	public TextNode(String text) {
		if(text == null) {
			throw new NullPointerException("Text cannot be null!");
		}
		this.text = text;
	}
	
	/**
	 * Text getter
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof TextNode)) {
			return false;
		}
		
		TextNode node = (TextNode) o;
		
		return getText().equals(node.getText());
	}
}
