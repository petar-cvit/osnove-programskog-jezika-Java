package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.model.Option;

/**
 * Servlet that gets poll results and creates an excel table containing names of bands in one column
 * and number of votes for bands in the other column.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class ExcelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		List<Option> options = (List<Option>) req.getSession().getAttribute("options");

		try{
			HSSFWorkbook hwb = new HSSFWorkbook();

			HSSFSheet sheet = hwb.createSheet("Votes");

			HSSFRow titleRow = sheet.createRow(0);
			titleRow.createCell(0).setCellValue("Poll Option");
			titleRow.createCell(1).setCellValue("Votes");
			
			for(Option option : options) {
				HSSFRow rowHead = sheet.createRow(options.indexOf(option) + 1);
				rowHead.createCell(0).setCellValue(option.getName());
				rowHead.createCell(1).setCellValue(option.getVotes());
			}

			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment;filename=glasanje.xls");
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
