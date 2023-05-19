package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents each flexible portfolio object and implements the flexible
 * Portfolio Interface.
 */
public class FlexiPortfolioImpl extends PortfolioImpl implements FlexiPortfolio {
  private List<FlexiStocks> portfolio;

  /**
   * A constructor to initialize a flexible Portfolio.
   */
  public FlexiPortfolioImpl() {
    this.portfolio = new ArrayList<>();
  }

  /**
   * A constructor to create a flexible portfolio object for our portfolio.
   *
   * @param portfolio the flexible portfolio whose object has to be created.
   */
  public FlexiPortfolioImpl(Map<String, List<List<String>>> portfolio) {
    List<FlexiStocks> p = new ArrayList<>();
    Map<String, Float> old = new HashMap<>();
    for (Map.Entry<String, List<List<String>>> mapElement : portfolio.entrySet()) {
      String key = mapElement.getKey();
      List<List<String>> val_string = mapElement.getValue();
      for (List<String> strings : val_string) {
        List<String> li;
        li = strings;
        String buyDateStr = li.get(0);
        String status = li.get(1);
        String noOfShares = li.get(2);
        String commissionFee = li.get(3);
        try {
          String[] buyDate = buyDateStr.split("-");
          LocalDate buyDateL = LocalDate.of(Integer.parseInt(buyDate[0]),
                  Integer.parseInt(buyDate[1]), Integer.parseInt(buyDate[2]));
          float noOfSharesValue = Float.parseFloat(noOfShares);
          float commissionFee1 = Float.parseFloat(commissionFee);
          p.add(FlexiStocksImpl.getBuilder1()
                  .noOfShares(noOfSharesValue)
                  .ticker(key)
                  .buyDate(buyDateL)
                  .status(status)
                  .commissionFee(commissionFee1)
                  .create());

          old.put(key, noOfSharesValue);
        } catch (ArrayIndexOutOfBoundsException e) {
          throw new ArrayIndexOutOfBoundsException("Invalid date format.");
        } catch (NumberFormatException e) {
          throw new NumberFormatException("Invalid date format.");
        }
      }
    }
    this.portfolio = p;
  }

  @Override
  public String toString() {
    return this.portfolio.toString();
  }


  @Override
  public Map<String, Float> getComposition(LocalDate date) {
    Map<String, Float> h = new HashMap<>();
    for (FlexiStocks fStocks : portfolio) {
      if ((fStocks.getStatus().equals("b"))
              && h.containsKey(fStocks.getTicker())
              && ((fStocks.getBuyDate().isBefore(date)) || (fStocks.getBuyDate().isEqual(date)))) {
        float x = h.get(fStocks.getTicker());
        x = x + fStocks.getNoOfShares();
        h.put(fStocks.getTicker(), x);
      } else if ((fStocks.getStatus().equals("b"))
              && ((fStocks.getBuyDate().isBefore(date)) || (fStocks.getBuyDate().isEqual(date)))
              && !h.containsKey(fStocks.getTicker())) {
        h.put(fStocks.getTicker(), fStocks.getNoOfShares());
      } else if ((fStocks.getStatus().equals("s"))
              && ((fStocks.getBuyDate().isBefore(date)) || (fStocks.getBuyDate().isEqual(date)))
              && h.containsKey(fStocks.getTicker())) {
        float x = h.get(fStocks.getTicker());
        x = x - fStocks.getNoOfShares();
        h.put(fStocks.getTicker(), x);
      }
    }
    return h;
  }

  @Override
  public Boolean validateSell(LocalDate sellDate, String ticker, Float sellQuantity) {
    Map<String, Float> comp = getComposition(sellDate);
    float nos = 0;

    for (FlexiStocks fs : portfolio) {
      if (ticker.equals(fs.getTicker())) {
        if (fs.getStatus().equals("b")
                && (fs.getBuyDate().isBefore(sellDate))
                || fs.getBuyDate().equals(sellDate)) {
          nos += fs.getNoOfShares();
        } else if (fs.getStatus().equals("s")) {
          nos -= fs.getNoOfShares();
        }
      }
    }
    if (nos - sellQuantity < 0) {
      return false;
    }
    return comp.containsKey(ticker) && (comp.get(ticker) - sellQuantity >= 0);
  }

  @Override
  public void addStock(FlexiStocks s) {
    portfolio.add(s);
  }

  @Override
  public float calculatePortfolioCostBasis(String date, Map<String, Map<String, Float>> cache) {
    float cb = 0;
    FlexiStocks fs;

    Map<String, Float> v;

    String[] sdArray = date.split("-");

    LocalDate d = LocalDate.of(Integer.parseInt(sdArray[0]),
            Integer.parseInt(sdArray[1]), Integer.parseInt(sdArray[2]));

    GetStockValueAPIImpl getVal = new GetStockValueAPIImpl();
    for (FlexiStocks flexiStocks : portfolio) {
      fs = flexiStocks;
      if (fs.getStatus().equals("b")
              && (fs.getBuyDate().isBefore(d) || fs.getBuyDate().equals(d))) {

        if (!cache.containsKey(fs.getTicker())) {
          cache.put(fs.getTicker(), getVal.getStockString(fs.getTicker()));
        }

        LocalDate incDate = fs.getBuyDate();
        v = cache.get(fs.getTicker());

        while (!v.containsKey(incDate.toString())) {
          incDate = incDate.plusDays(1);
        }


        cb += v.get(incDate.toString()) * fs.getNoOfShares();

        cb += fs.getCommissionFee();
      } else if (fs.getStatus().equals("s")
              && (fs.getBuyDate().isBefore(d) || fs.getBuyDate().equals(d))) {
        cb += fs.getCommissionFee();
      }
    }
    return cb;
  }

  @Override
  public int size() {
    return this.portfolio.size();
  }

  @Override
  public FlexiStocks getAt(int x) {
    return this.portfolio.get(x);
  }
}
