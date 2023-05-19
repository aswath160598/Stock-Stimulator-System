package model;

import java.time.LocalDate;
import java.util.Map;

/**
 * This Interface represents each flexible portfolio and the functions of a portfolio object.
 */
public interface FlexiPortfolio extends Portfolio {
  /**
   * Function to get the composition of a flexible Portfolio.
   *
   * @param date the date on which the composition has to be calculated.
   * @return the composition of the portfolio on a particular date.
   */
  Map<String, Float> getComposition(LocalDate date);

  /**
   * Function to validate the selling of a stock.
   *
   * @param sellDate     the date on which we can sell.
   * @param ticker       the ticker symbol to be validated.
   * @param sellQuantity the quantity of shares to be sold.
   * @return map containing composition of the portfolio
   */
  Boolean validateSell(LocalDate sellDate, String ticker, Float sellQuantity);

  /**
   * Function to add a stock to a flexible Portfolio.
   *
   * @param s the stock to be added to a flexible Portfolio.
   */
  void addStock(FlexiStocks s);

  /**
   * Function to calculate the cost basis of the Portfolio.
   *
   * @param date  the date on which the cost basis of a Flexible Portfolio has to be
   *              calculated.
   * @param cache a local cache
   * @return the calculated cost basis value of a Portfolio.
   */
  float calculatePortfolioCostBasis(String date, Map<String, Map<String, Float>> cache);

  /**
   * Function to calculate the size of a Flexible Portfolio.
   *
   * @return the size of the Flexible Portfolio.
   */
  int size();

  /**
   * Function to get the stock at specific index of a Portfolio.
   *
   * @param x the index at which the stock has to be found
   * @return the stock at the specified index.
   */
  FlexiStocks getAt(int x);
}
