package model;

import java.time.LocalDate;
import java.util.Map;

/**
 * This Interface represents each Dollar Cost Averaging Single Strategy.
 */
public interface DollarCostAveragingSingle {
  /**
   * Function to return the strategy name.
   *
   * @return the strategy name
   */
  String getPlanName();

  /**
   * Function to return the start date.
   *
   * @return the start date
   */
  LocalDate getStartDate();

  /**
   * Function to return the proportion map.
   *
   * @return the proportion map
   */
  Map<String, Float> getProportionMap();

  /**
   * Function to return the total investment.
   *
   * @return the total investment
   */
  float getTotalInvestment();

  /**
   * Function to return the status.
   *
   * @return the status
   */
  char getStatus();

  /**
   * Function to change the status.
   *
   */
  void status();

  /**
   * Function to return commission fee.
   *
   * @return the commission fee
   */
  float getCommissionFee();
}
