package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Gets operands from context parameters, adds them and saves result in parameter map.
 * Default value for first operand is 1, and for the other is 2.
 * 
 * @author Petar Cvitanović
 *
 */
public class SumWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Integer a = getOperand("a", context, 1);
		Integer b = getOperand("b", context, 2);
		
		String result = Integer.valueOf(a + b).toString();
		
		context.setTemporaryParameter("zbroj", result);
		context.setTemporaryParameter("varA", a.toString());
		context.setTemporaryParameter("varB", b.toString());
		
		String image;
		if((a + b) % 2 == 0) {
			image = "../images/slika1.jpg";
		} else {
			image = "../images/slika2.jpg";
		}
		
		context.setTemporaryParameter("imgName", image);
		
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

	/**
	 * Tries to parse parameters from context to integers. If that can't be done returns default value that was given.
	 * Otherwise returns parsed parameters.
	 * 
	 * @param arg
	 * @param context
	 * @param dValue
	 * @return
	 */
	private Integer getOperand(String arg, RequestContext context, Integer dValue) {
		Integer out;
		
		if(arg.isBlank()) {
			return dValue;
		}
		
		try {
			out = Integer.parseInt(context.getParameter(arg));
		} catch(NumberFormatException ex) {
			out = dValue;
		}
		
		if(out == null) {
			out = dValue;
		}
		
		return out;
	}

}
