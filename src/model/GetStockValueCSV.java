package model;

import java.io.IOException;
import java.text.ParseException;

/**
 * The Interface represents the method to get each Stock value inside a Portfolio.
 */
public interface GetStockValueCSV {
  /**
   * Function get the stock values from csv files.
   *
   * @param ticker ticker symbol of each Stock.
   * @param date   date on which the Stock value should be computed.
   * @return the Stock price on that day.
   * @throws ParseException This exception throws when errors in Parsing is found.
   * @throws IOException    This exception throws when IO errors are found.
   */
  float getStockPrice(String ticker, String date) throws ParseException, IOException;
}
