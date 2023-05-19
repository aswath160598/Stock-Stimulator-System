package model;

import java.util.Map;

/**
 * This Interface represents each portfolio and the functions of a portfolio object.
 */
public interface Portfolio {
  /**
   * Function to get the composition of a Portfolio.
   *
   * @return the resulting Portfolio Object.
   */
  Map<String, Float> getComposition();

  /**
   * Function to get the number of stocks of a Portfolio.
   *
   * @return the total number of stocks in the Portfolio.
   */
  int getNoOfStocks();
}
