package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.p12.model.Option;

/**
 * Creates a pie chart based on poll data. That image is returned in response.
 * 
 * @author Petar Cvitanović
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class StatisticsServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		List<Option> options = (List<Option>) req.getSession().getAttribute("options");
		
		OutputStream out = resp.getOutputStream();
		try {
			DefaultPieDataset myServletPieChart = new DefaultPieDataset();
			
			for(Option o : options) {
				myServletPieChart.setValue(o.getName(), o.getVotes());
			}
			
			JFreeChart mychart = ChartFactory.createPieChart("Statistics",myServletPieChart,true,true,false);  
			resp.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out, mychart, 400, 400);
		}
		catch (Exception e) {
			System.err.println(e.toString());
		}
		finally {
			out.close();
		}
	}

}
