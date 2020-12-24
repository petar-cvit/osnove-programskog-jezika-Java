package hr.fer.zemris.java.p11.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Takes three parameters. First parameter is start value, second parameter is end value and the third parameter is
 * the number to which power we will raise numbers in the interval of start value to end value.
 * 
 * @author Petar Cvitanović
 *
 */
public class PowersServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = 0;
		Integer b = 0;
		Integer n = 0;

		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
		} catch (NumberFormatException e) {
			req.getRequestDispatcher("./WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}

		if(a < -100 || a > 100 ||
				b < -100 || b > 100 ||
				n < 1 || n > 5) {
			req.getRequestDispatcher("./WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}
		
		if(b < a) {
			int tmp = a;
			a = b;
			b = tmp;
		}

		try{
			HSSFWorkbook hwb = new HSSFWorkbook();
			
			for(int i = 1;i <= n;i++) {
				HSSFSheet sheet = hwb.createSheet(i + "-th power");
				
				for(int j = a;j <= b;j++) {
					HSSFRow rowHead = sheet.createRow(j - a);
					rowHead.createCell(0).setCellValue(j);
					rowHead.createCell(1).setCellValue(Math.pow(j, i));
				}
			}
			
			resp.setContentType("application/vnd.ms-excel");
			ServletOutputStream out = resp.getOutputStream();
		    hwb.write(out);
		    out.flush();
		    out.close();
		    hwb.close();
			
		} catch ( Exception ex ) {
			System.out.println(ex);

		}
	}

}
