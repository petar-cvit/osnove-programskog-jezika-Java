package hr.fer.zemris.java.gui.charts;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class that extends JFrame class. When called from command line with file path as argument, 
 * shows GUI with file path name and chart that was parsed from file text.
 * 
 * @author Petar Cvitanović
 *
 */
public class BarChartDemo extends JFrame{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor with bar chart that needs to be shown on GUI and file name.
	 * 
	 * @param bc
	 * @param file
	 */
	public BarChartDemo(BarChart bc, String file) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar chart");
		setLocation(20, 20);
		setSize(700, 500);
		
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		
		JLabel fileName = new JLabel(file);
		fileName.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		pane.add(fileName);
		pane.add(new BarChartComponent(bc));
		
		add(pane);
	}
	
	/**
	 * Main method that is ran when program is called from command line. Opens file given as
	 * argument in command line. Calls parse method that parses text, then calls new instance
	 * of BarChartDemo and shows GUI.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("Invalid number of arguments!");
			return;
		}
		
		SwingUtilities.invokeLater(() -> {
			BarChart model = null;
			
			try(BufferedReader br = Files.newBufferedReader(Paths.get(args[0]))) {
				model = parse(br);
				BarChartDemo bcd = new BarChartDemo(model, args[0]);
				bcd.setVisible(true);
			} catch (IOException e1) {
				System.out.println("Invalid file path!");
			} catch (Exception e2) {
				System.out.println("Invalid file!");
			}
		});
	}
	
	/**
	 * Reads lines from file and parses them to instance of BarChart class.
	 * 
	 * @throws IOException
	 * 
	 * @param br
	 * @return
	 * @throws IOException
	 */
	private static BarChart parse(BufferedReader br) throws IOException {
		String xAxisDesc = br.readLine();
		String yAxisDesc = br.readLine();
		
		String[] values = br.readLine().split("\\s");
		List<XYValue> valuesList = new ArrayList<XYValue>();
		
		for(String v : values) {
			if(v.isBlank()) {
				continue;
			}
			
			String[] parts = v.split(",");
			if(parts.length != 2) {
				throw new IllegalArgumentException();
			}
			
			valuesList.add(new XYValue(Integer.parseInt(parts[0]),
					Integer.parseInt(parts[1])));
		}
		
		int minY = Integer.parseInt(br.readLine());
		int maxY = Integer.parseInt(br.readLine());
		int diffY = Integer.parseInt(br.readLine());

		return new BarChart(valuesList, xAxisDesc, yAxisDesc, minY, maxY, diffY);
	}

}