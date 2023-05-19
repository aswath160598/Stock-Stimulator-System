package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents each portfolio object and implements the Portfolio Interface.
 */
public class PortfolioImpl implements Portfolio {
  private final List<Stocks> portfolio;

  /**
   * A default constructor to initialize a Portfolio.
   */
  public PortfolioImpl() {
    this.portfolio = new ArrayList<>();
  }

  /**
   * A parameterized constructor to initialize a Portfolio.
   *
   * @param portfolio the portfolio object to be added.
   */
  public PortfolioImpl(Map<String, Float> portfolio) {
    List<Stocks> p = new ArrayList<>();
    for (Map.Entry<String, Float> mapElement : portfolio.entrySet()) {
      String key = mapElement.getKey();
      float value = mapElement.getValue();
      p.add(StocksImpl.getBuilder().noOfShares(value).ticker(key).create());
    }
    this.portfolio = p;
  }

  @Override
  public Map<String, Float> getComposition() {
    Map<String, Float> h = new HashMap<>();
    for (Stocks stocks : portfolio) {
      h.put(stocks.getTicker(), stocks.getNoOfShares());
    }
    return h;
  }

  @Override
  public int getNoOfStocks() {
    return portfolio.size();
  }

  @Override
  public String toString() {
    return this.portfolio.toString();
  }
}
