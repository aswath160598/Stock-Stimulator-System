package utilities;


import java.util.Collections;
import java.util.List;

/**
 * This class represents the graph to be plotted between a particular time range.
 */
public class GraphDrawImpl implements GraphDraw {

  @Override
  public StringBuilder drawGraph(List<Float> values, List<String> timeStamp) {
    StringBuilder str = new StringBuilder();
    float min = 0;
    float max;
    max = Collections.max(values);
    min = Collections.min(values);
    if (Math.max(min, max) != 0) {
      double x = max / 50;
      int l = 0;
      x = Math.ceil(x);
      l = (int) x;
      int m = 0;
      for (int i = 0; i < values.size(); i++) {
        str.append(timeStamp.get(i)).append("  ");
        m = values.get(i).intValue() / l;
        for (int j = 0; j < m; j++) {
          str.append("*");
        }
        str.append("\n");
      }
      str.append("\nScale: * = $").append(x);
    } else {
      throw new IllegalArgumentException("No transactions found in the given period.");
    }
    return str;
  }

  @Override
  public GUIBarChart drawGUIGraph(List<String> values, List<String> timeStamp) {
    String title = "Portfolio Performance";
    String xAxis = "";
    String source = "";
    GUIBarChart chart = new GUIBarChart(title, xAxis, source);

    for (int i = 0; i < values.size(); i++) {
      chart.add(timeStamp.get(i), (int) Float.parseFloat(values.get(i)), "");
    }

    //    chart.add("2020-04-05",       1000, "");
    //    chart.add("2020-05-05",       2000, "");
    //    chart.add("2020-06-05",       3000, "");

    StdDraw.setCanvasSize(1000, 700);
    StdDraw.setTitle("Portfolio Performance");
    StdDraw.enableDoubleBuffering();
    return chart;
  }
}


