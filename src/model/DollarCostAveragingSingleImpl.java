package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * This Class implements the Dollar Cost Averaging Single Strategy.
 */
public class DollarCostAveragingSingleImpl implements DollarCostAveragingSingle {
  private String planName;
  private LocalDate startDate;
  private Map<String, Float> proportionMap = new HashMap<>();
  private float totalInvestment;
  private float commissionFee;
  private char status;

  /**
   * Default Constructor to create Dollar Cost Averaging Strategy Objects.
   *
   * @param planName        the portfolio name
   * @param startDate       the start date
   * @param totalInvestment the total Investment
   * @param proportionMap   the proportion Map
   * @param commissionFee   the commission fee
   */
  public DollarCostAveragingSingleImpl(String planName, LocalDate startDate, float totalInvestment,
                                       Map<String, Float> proportionMap, float commissionFee) {

    this.planName = planName;
    this.startDate = startDate;
    this.totalInvestment = totalInvestment;
    this.proportionMap = proportionMap;
    this.status = 'x';
    this.commissionFee = commissionFee;
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
  public float getTotalInvestment() {
    return this.totalInvestment;
  }

  @Override
  public char getStatus() {
    return this.status;
  }

  @Override
  public void status() {
    this.status = 'a';
  }

  @Override
  public float getCommissionFee() {
    return this.commissionFee;
  }

  @Override
  public String toString() {
    return this.planName + " " + this.startDate + " " +
            this.totalInvestment + " " + this.proportionMap;
  }

}
