package hr.fer.zemris.java.p11.servleti;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Returns image that contains a pie chart. That pie chart shows data about operating system's usage.
 * 
 * @author Petar Cvitanović
 *
 */
public class ReportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OutputStream out = resp.getOutputStream();
		try {
			DefaultPieDataset myServletPieChart = new DefaultPieDataset();
			myServletPieChart.setValue("Windows", 150);
			myServletPieChart.setValue("Linux", 40);
			myServletPieChart.setValue("Mac", 62);
			JFreeChart mychart = ChartFactory.createPieChart("OS usage",myServletPieChart,true,true,false);  
			resp.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out, mychart, 400, 300);
		}
		catch (Exception e) {
			System.err.println(e.toString());
		}
		finally {
			out.close();
		}

	}

}
