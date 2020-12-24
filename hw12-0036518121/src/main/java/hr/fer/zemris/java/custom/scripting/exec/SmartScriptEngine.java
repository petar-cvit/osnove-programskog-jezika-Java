package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Runs parsed smart script with implementation of {@link INodeVisitor} implementation.
 * 
 * @author Petar Cvitanović
 *
 */
public class SmartScriptEngine {
	
	/**
	 * document node of parsed document
	 */
	private DocumentNode documentNode;
	
	/**
	 * request context
	 */
	private RequestContext requestContext;
	
	/**
	 * {@link ObjectMultistack} data structure
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * implementation of {@link INodeVisitor}.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			multistack.push(node.getVariable().asText(), 
					new ValueWrapper(node.getStartExpression().asText()));

			ValueWrapper wrapper = multistack.peek(node.getVariable().asText());

			while(true) {
				if(multistack.peek(node.getVariable().asText())
						.numCompare(node.getEndExpression().asText()) == 1) {
					break;
				}

				for(int i = 0;i < node.numberOfChildren();i++) {
					node.getChild(i).accept(this);
				}

				wrapper.add(node.getStepExpression() == null ?
						"1" : node.getStepExpression().asText());

				multistack.push(node.getVariable().asText(), wrapper);
			}
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<ValueWrapper> stack = new Stack<ValueWrapper>();

			for(Element element : node.getElements()) {
				if(element instanceof ElementString ||
						element instanceof ElementConstantDouble ||
						element instanceof ElementConstantInteger) {
					stack.push(new ValueWrapper(element.asText()));
				}

				if(element instanceof ElementVariable) {
					Object value = multistack.peek(element.asText()).getValue();
					stack.push(new ValueWrapper(value));
				}

				if(element instanceof ElementOperator) {
					ValueWrapper second = stack.pop();
					ValueWrapper first = stack.pop();
					
					switch(element.asText()) {
					case "+":
						first.add(second.getValue()); 
						break;

					case "-":
						first.subtract(second.getValue());
						break;

					case "/":
						first.divide(second.getValue());
						break;

					case "*":
						first.multiply(second.getValue());
						break;
					}

					stack.push(first);
				}

				if(element instanceof ElementFunction) {
					switch (element.asText()) {
					case "sin":
						Double value = ((Number) stack.pop().getValue()).doubleValue();
						stack.push(new ValueWrapper(Math.sin(value * 2 * Math.PI / 360.)));
						break;
						
					case "decfmt":
						DecimalFormat formatter = new DecimalFormat((String) stack.pop().getValue());
						Double doubleValue = (Double) stack.pop().getValue();
						
						stack.push(new ValueWrapper(formatter.format(doubleValue)));
						
						break;

					case "dup":
						ValueWrapper duplicate = stack.pop();
						stack.push(duplicate);
						stack.push(new ValueWrapper(duplicate.getValue()));
						break;

					case "swap":
						ValueWrapper first = stack.pop();
						ValueWrapper second = stack.pop();

						stack .push(second);
						stack.push(first);
						break;

					case "setMimeType":
						ValueWrapper mimeType = stack.pop();
						requestContext.setMimeType((String) mimeType.getValue());

						break;

					case "paramGet":
						ValueWrapper defValue = stack.pop();
						ValueWrapper name = stack.pop();
						
						String parameter = requestContext.getParameter(
								(String) name.getValue());
						
						stack.push(parameter == null ?
								defValue : new ValueWrapper(parameter));
						break;
						
					case "pparamGet":
						ValueWrapper pDefValue = stack.pop();
						ValueWrapper pName = stack.pop();
						
						String pParameter = requestContext.getPersistentParameter(
								(String) pName.getValue());
						
						stack.push(pParameter == null ?
								pDefValue : new ValueWrapper(pParameter));
						break;
						
					case "pparamSet":
						requestContext.setPersistentParameter(
								stack.pop().getValue().toString(),
								stack.pop().getValue().toString());
						break;
						
					case "pparamDel":
						requestContext.removePersistentParameter(
								(String) stack.pop().getValue());
						break;
						
					case "tparamGet":
						ValueWrapper tDefValue = stack.pop();
						ValueWrapper tName = stack.pop();
						
						String tParameter = requestContext.getTemporaryParameter(
								(String) tName.getValue());
						
						stack.push(tParameter == null ?
								tDefValue : new ValueWrapper(tParameter));
						break;
						
					case "tparamSet":
						requestContext.setTemporaryParameter(
								stack.pop().getValue().toString(),
								stack.pop().getValue().toString());
						break;
						
					case "tparamDel":
						requestContext.removeTemporaryParameter(
								(String) stack.pop().getValue());
						break;
						
					default:
						throw new UnsupportedOperationException();
					}
				}
			}
			
			ValueWrapper[] valueArray = new ValueWrapper[stack.size()];
			
			for(int i = stack.size() - 1;i >= 0;i--) {
				valueArray[i] = stack.pop();
			}
			
			for(ValueWrapper v : valueArray) {
				try {
					requestContext.write(v.getValue().toString());
				} catch (IOException | RuntimeException e) {
				}
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0;i < node.numberOfChildren();i++) {
				node.getChild(i).accept(this);
			}
		}

	};

	/**
	 * Constructor with document node and context.
	 * 
	 * @param documentNode
	 * @param requestContext
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext
			requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Method that calls accept method on document node with visitor that has been implemented.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}


	/**
	 * Main method that runs initializes all needed data structures and calls execute method for
	 * constructed {@link SmartScriptEngine}.
	 * 
	 * @param file path that needs to be executed
	 */
	public static void main(String[] args) {
		String fileName = args[0];

		String documentBody = null;
		try {
			documentBody = new String(
					Files.readAllBytes(Paths.get(fileName)),
					StandardCharsets.UTF_8
					);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> tempparameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies, tempparameters, null, null)
				).execute();
	}

}
