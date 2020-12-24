package hr.fer.zemris.java.p11.servleti;

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

/**
 * Servlet that gets poll results and creates an excel table containing names of bands in one column
 * and number of votes for bands in the other column.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/glasanje-xls")
public class ExcelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		List<Quadraple> bands = (List<Quadraple>) req.getSession().getAttribute("bandsResults");

		try{
			HSSFWorkbook hwb = new HSSFWorkbook();

			HSSFSheet sheet = hwb.createSheet("Top chart");

			HSSFRow titleRow = sheet.createRow(0);
			titleRow.createCell(0).setCellValue("Band name");
			titleRow.createCell(1).setCellValue("Votes");
			
			for(Quadraple q : bands) {
				HSSFRow rowHead = sheet.createRow(bands.indexOf(q) + 1);
				rowHead.createCell(0).setCellValue(q.getName());
				rowHead.createCell(1).setCellValue(q.getVotes());
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
