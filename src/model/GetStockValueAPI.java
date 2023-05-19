package model;

import java.text.ParseException;
import java.util.Map;

/**
 * The Interface represents the method to get each Stock value inside a Portfolio.
 */
public interface GetStockValueAPI {
  /**
   * Function get the stock values from the API.
   *
   * @param ticker ticker symbol of each Stock.
   * @param date   date on which the Stock value should be computed.
   * @return the Stock price on that day.
   * @throws ParseException throws this exception if there are issues with parsing.
   */
  float getStockPrice(String ticker, String date) throws ParseException;

  /**
   * Function to return stock string with a map of values to be returned.
   *
   * @param ticker ticker symbol of each Stock.
   * @return a map of values with date and cost on a particular dates.
   */
  Map<String, Float> getStockString(String ticker);
}
