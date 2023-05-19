package model;

/**
 * This Interface represents each portfolio and the functions of a portfolio objeect.
 */
public interface Stocks {
  /**
   * Get the ticker symbol of a particular Stock.
   *
   * @return the ticker symbol
   */
  String getTicker();

  /**
   * Get the number of shares of a particular stock.
   *
   * @return the number of shares
   */
  float getNoOfShares();

  /**
   * Get the cost price of a stock.
   *
   * @return the cost price.
   */
  float getCostPrice();
}
