package model;

import java.time.LocalDate;

/**
 * This class represents each stock in a flexible portfolio object and implements the stock.
 */
public class FlexiStocksImpl extends StocksImpl implements FlexiStocks {
  private final float noOfShares;
  private final float costPrice;
  private final String ticker;
  private final LocalDate buyDate;
  private final String status;
  private final float commissionFee;

  /**
   * Parameterized Constructor to initialize a Stock object.
   *
   * @param noOfShares    the number of shares
   * @param costPrice     the cost price
   * @param ticker        the ticker symbol
   * @param date          buy date of the stock
   * @param status        of the transaction (b/s)
   * @param commissionFee for the transaction
   */
  public FlexiStocksImpl(float noOfShares, float costPrice,
                         String ticker, LocalDate date, String status, float commissionFee) {
    this.noOfShares = noOfShares;
    this.costPrice = costPrice;
    this.ticker = ticker;
    this.buyDate = date;
    this.status = status;
    this.commissionFee = commissionFee;
  }

  /**
   * Constructor to initialize a Stock object.
   */
  public FlexiStocksImpl() {
    this.noOfShares = 0;
    this.costPrice = 0;
    this.ticker = null;
    this.buyDate = null;
    this.status = "";
    this.commissionFee = 0;
  }

  /**
   * Function to create a builder object.
   *
   * @return the builder object
   */
  public static FlexiStocksImplCreator getBuilder1() {
    return new FlexiStocksImplCreator();
  }

  @Override
  public String getTicker() {
    return this.ticker;
  }

  @Override
  public float getNoOfShares() {
    return this.noOfShares;
  }

  @Override
  public float getCostPrice() {
    return this.costPrice;
  }

  @Override
  public LocalDate getBuyDate() {
    return this.buyDate;
  }

  @Override
  public String getStatus() {
    return this.status;
  }

  @Override
  public float getCommissionFee() {
    return this.commissionFee;
  }

  @Override
  public String toString() {
    return this.ticker + " " + this.noOfShares + " " + this.status + " " + this.buyDate;

  }

  /**
   * Static Class  of the Stock Implementation Creator that uses the builder Design.
   */
  public static class FlexiStocksImplCreator {
    private float noOfShares;
    private float costPrice;
    private String ticker;
    private LocalDate buyDate;
    private String status;
    private float commissionFee;

    private FlexiStocksImplCreator() {
      this.noOfShares = 0;
      this.costPrice = 0;
      this.ticker = null;
      this.buyDate = null;
      this.commissionFee = 0;
    }

    /**
     * Created a flexible Stock.
     *
     * @return the stock object
     */
    public FlexiStocks create() {
      return new FlexiStocksImpl(noOfShares, costPrice, ticker, buyDate, status, commissionFee);
    }

    /**
     * Stock Creator to check valid number of stocks.
     *
     * @param ns number of shares
     * @return the number of shares returned
     */
    public FlexiStocksImplCreator noOfShares(float ns) {
      if (ns <= 0) {
        throw new IllegalArgumentException("Invalid number of shares entered");
      }
      this.noOfShares = ns;
      return this;
    }

    /**
     * Stock Creator to check valid number of shares.
     *
     * @param cp the cost price
     * @return the number of shares returned
     */
    public FlexiStocksImplCreator costPrice(float cp) {
      if (cp <= 0) {
        throw new IllegalArgumentException("Invalid number of shares entered");
      }
      this.costPrice = cp;
      return this;
    }

    /**
     * Stock Creator to check valid ticker or null.
     *
     * @param t ticker symbol
     * @return the builder object
     */
    public FlexiStocksImplCreator ticker(String t) {
      if (t == null || t.equals("")) {
        throw new IllegalArgumentException("Invalid ticker entered");
      }
      this.ticker = t;
      return this;
    }

    /**
     * Stock Creator to check valid buy date or in the future which is invalid.
     *
     * @param date the date as a parameter
     * @return the builder object
     */
    public FlexiStocksImplCreator buyDate(LocalDate date) {
      if (date.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Date cannot be in the future.");
      }
      this.buyDate = date;
      return this;
    }

    /**
     * Stock Creator to check the status of a particular transaction.
     *
     * @param status the status of a transaction
     * @return the builder object
     */
    public FlexiStocksImplCreator status(String status) {
      this.status = status;
      return this;
    }

    /**
     * Stock Creator to set the commission fee of a particular transaction.
     *
     * @param commissionFee the commission fee of the transaction
     * @return the builder object
     */
    public FlexiStocksImplCreator commissionFee(float commissionFee) {
      this.commissionFee = commissionFee;
      return this;
    }
  }
}
