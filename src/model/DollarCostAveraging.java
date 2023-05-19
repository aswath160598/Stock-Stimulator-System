package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * This Interface represents each Dollar Cost Averaging Strategy.
 */
public interface DollarCostAveraging {
  /**
   * Function to compute list of dates.
   *
   * @param startDate the start date
   * @param endDate   the end date
   * @param interval  the interval
   * @return a list of local dates
   */
  List<LocalDate> computeDateList(LocalDate startDate, LocalDate endDate, int interval);

  /**
   * Function to return the list of remaining dates.
   *
   * @return the date list
   */
  List<LocalDate> getDateList();

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
   * Function to return the end date.
   *
   * @return the end date
   */
  LocalDate getEndDate();

  /**
   * Function to return the total investment.
   *
   * @return the total investment
   */
  float getTotalInvestment();

  /**
   * Function to return the interval.
   *
   * @return the interval
   */
  int getInterval();

  /**
   * Function to return the commission fee.
   *
   * @return the commission fee
   */
  float getCommissionFee();
}
