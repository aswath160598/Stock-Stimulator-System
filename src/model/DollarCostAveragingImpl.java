package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This Class implements the Dollar Cost Averaging Strategy.
 */
public class DollarCostAveragingImpl implements DollarCostAveraging {
  private String planName;
  private LocalDate startDate;
  private Map<String, Float> proportionMap = new HashMap<>();
  private LocalDate endDate;
  private float totalInvestment;
  private float commissionFee;
  private int interval;
  private List<LocalDate> dateList;

  /**
   * Default Constructor to create Dollar Cost Averaging Strategy Objects.
   *
   * @param planName        the portfolio name
   * @param startDate       the start date
   * @param endDate         the end date
   * @param totalInvestment the total Investment
   * @param interval        the interval
   * @param proportionMap   the proportion Map
   * @param commissionFee   the commission fee
   */
  public DollarCostAveragingImpl(String planName, LocalDate startDate, LocalDate endDate,
                                 float totalInvestment, int interval, Map<String, Float>
                                         proportionMap, float commissionFee) {

    this.planName = planName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.totalInvestment = totalInvestment;
    this.interval = interval;
    this.proportionMap = proportionMap;
    this.commissionFee = commissionFee;
    this.dateList = computeDateList(startDate, endDate, interval);
  }

  @Override
  public List<LocalDate> computeDateList(LocalDate startDate, LocalDate endDate, int interval) {
    List<LocalDate> listDates = new ArrayList<LocalDate>();
    LocalDate currDate = startDate;
    while (currDate.isBefore(endDate) || currDate.isEqual(endDate)) {
      listDates.add(currDate);
      currDate = currDate.plusDays(interval);
    }
    return listDates;
  }

  @Override
  public List<LocalDate> getDateList() {
    return this.dateList;
  }

  @Override
  public String getPlanName() {
    return this.planName;
  }

  @Override
  public LocalDate getStartDate() {
    return this.startDate;
  }

  @Override
  public Map<String, Float> getProportionMap() {
    return this.proportionMap;
  }

  @Override
  public LocalDate getEndDate() {
    return this.endDate;
  }

  @Override
  public float getTotalInvestment() {
    return this.totalInvestment;
  }

  @Override
  public int getInterval() {
    return this.interval;
  }

  @Override
  public float getCommissionFee() {
    return this.commissionFee;
  }

  @Override
  public String toString() {
    return this.planName + " " + this.startDate + " " + this.endDate + " " +
            this.totalInvestment + " " + this.interval + this.proportionMap;
  }
}
