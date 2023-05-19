package utilities;

import java.text.ParseException;
import java.util.List;

/**
 * This represents the interface to draw a graph.
 */
public interface GraphDraw {

  /**
   * This function is used to draw the graph between a particular function.
   *
   * @param values    the list of values of the flexible portfolio
   * @param timeStamp the list of timestamps of the flexible portfolio
   * @return stringbuilder of the graph
   * @throws ParseException the ParseException to be thrown
   */
  StringBuilder drawGraph(List<Float> values, List<String> timeStamp) throws ParseException;

  GUIBarChart drawGUIGraph(List<String> values, List<String> timeStamp);
}
