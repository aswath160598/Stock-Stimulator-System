package model;

import java.time.LocalDate;

/**
 * This Interface represents each stock in a flexible portfolio and the functions of a
 * portfolio object.
 */
public interface FlexiStocks extends Stocks {
  /**
   * Function that gets the buy date of a Stock.
   *
   * @return the buy date of a particular stock in a specific transaction
   */
  LocalDate getBuyDate();

  /**
   * Function to get the status of a Stock.
   *
   * @return the status of a stock if it is a buy or a sell
   */
  String getStatus();

  /**
   * Function to return the commission fee of a transaction.
   *
   * @return commission fee of a transaction
   */
  float getCommissionFee();
}
