package model;

/**
 * This class represents each Stock. It implements the Stock Interface methods.
 */
public class StocksImpl implements Stocks {

  private final float noOfShares;
  private final float costPrice;
  private final String ticker;

  /**
   * Constructor to initialize a Stock object.
   *
   * @param noOfShares the number of shares
   * @param costPrice  the cost price
   * @param ticker     the ticker symbol
   */
  public StocksImpl(float noOfShares, float costPrice, String ticker) {
    this.noOfShares = noOfShares;
    this.costPrice = costPrice;
    this.ticker = ticker;
  }

  /**
   * Default Constructor to initialize a Stock object.
   */
  public StocksImpl() {
    this.noOfShares = 0;
    this.costPrice = 0;
    this.ticker = null;
  }

  public static StocksImplCreator getBuilder() {
    return new StocksImplCreator();
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
  public String toString() {
    return this.ticker + " " + (int) this.noOfShares;
  }

  /**
   * Static Class  of the Stock Implementation Creator that uses the builder Design.
   */
  public static class StocksImplCreator {
    private float noOfShares;
    private float costPrice;
    private String ticker;

    private StocksImplCreator() {
      this.noOfShares = 0;
      this.costPrice = 0;
      this.ticker = null;
    }

    /**
     * Created a Stock.
     *
     * @return the stock object
     */
    public Stocks create() {
      return new StocksImpl(noOfShares, costPrice, ticker);
    }

    /**
     * Stock Creator to check valid number of stocks.
     *
     * @param ns number of shares
     * @return the number of shares returned
     */
    public StocksImplCreator noOfShares(float ns) {
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
    public StocksImplCreator costPrice(float cp) {
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
     * @return the ticker symbol
     */
    public StocksImplCreator ticker(String t) {
      if (t == null || t.equals("")) {
        throw new IllegalArgumentException("Invalid ticker entered");
      }
      this.ticker = t;
      return this;
    }
  }
}