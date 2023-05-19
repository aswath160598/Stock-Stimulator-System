package controller;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.FlexiPortfolio;
import model.FlexiStocks;
import model.FlexiStocksImpl;
import model.FlexiUser;
import model.GetStockValueAPI;
import model.GetStockValueAPIImpl;
import model.TickerValidator;
import model.TickerValidatorImpl;
import model.User;
import model.UserImpl;
import view.FlexiView;

/**
 * This class represents our Controller. It implements all the controller functions specific to
 * flexible portfolio and interacts with the view and the model.
 */
public class FlexiControllerImpl extends ControllerImpl implements FlexiController {

  private final FlexiUser user;
  private final FlexiView view;
  private final Scanner sc;
  private final Controller c;
  private final Map<String, Map<String, Float>> cache;

  /**
   * This represents the Flexible Controller Constructor.
   *
   * @param user  user object
   * @param view  view object
   * @param input input object
   */
  public FlexiControllerImpl(FlexiUser user, FlexiView view, InputStream input) {
    this.user = user;
    this.view = view;
    sc = new Scanner(input);
    User u = new UserImpl();
    c = new ControllerImpl(u, view, sc);

    cache = new HashMap<>();
  }

  @Override
  public void showPortfolioTypeSelection() {
    view.selectPortfolioType();
    try {
      int portfolioType = Integer.parseInt(sc.next());
      while (portfolioType == 1 || portfolioType == 2) {
        if (portfolioType == 1) {
          c.display2();
          view.selectPortfolioType();
          portfolioType = Integer.parseInt(sc.next());
        } else if (portfolioType == 2) {
          flexibleOperations();
          view.selectPortfolioType();
          portfolioType = Integer.parseInt(sc.next());
        }
      }
    } catch (Exception e) {
      view.invalidUserInput();
      showPortfolioTypeSelection();
    }
  }

  @Override
  public void flexibleOperations() throws ParseException, IOException,
          ParserConfigurationException, TransformerException {
    view.showFlexibleMenu();

    try {

      int flexiPortfolioOperation = Integer.parseInt(sc.next());

      while (flexiPortfolioOperation >= 1 && flexiPortfolioOperation <= 9) {
        String portfolioName;
        switch (flexiPortfolioOperation) {
          case 1:
            view.askPortfolioName();
            portfolioName = sc.next();
            try {
              buyStock(portfolioName);
            } catch (IllegalArgumentException e) {
              view.showError(e.getMessage());
            }
            break;
          case 2:
            view.askPortfolioName();
            portfolioName = sc.next();
            if (user.checkPortfolioExists(portfolioName)) {
              try {
                sellStock(portfolioName);
              } catch (IndexOutOfBoundsException e) {
                view.invalidDate();
              }
            } else {
              view.showError("Portfolio not found.");
            }
            break;
          case 3:
            view.askPortfolioName();
            portfolioName = sc.next();
            try {

              view.enterDate();
              String d = sc.next();

              String[] sdArray = d.split("-");

              LocalDate date = LocalDate.of(Integer.parseInt(sdArray[0]),
                      Integer.parseInt(sdArray[1]), Integer.parseInt(sdArray[2]));

              Map<String, Float> m = user.getPortfolio(portfolioName)
                      .getComposition(date);
              for (Map.Entry<String, Float> mapElement : m.entrySet()) {
                view.showPortfolio(mapElement.getKey(), mapElement.getValue().intValue());
              }
            } catch (NullPointerException e) {
              view.invalidPortfolioName();
            } catch (DateTimeException e) {
              view.showError(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
              view.invalidDate();
            }
            break;
          case 4:
            view.askPortfolioName();
            portfolioName = sc.next();
            if (user.checkPortfolioExists(portfolioName)) {
              user.saveFlexiPortfolio(portfolioName);
              view.savePortfolioSuccessful(portfolioName);
            } else {
              view.showError("Portfolio not found.");
            }
            break;
          case 5:
            view.askPortfolioName();
            portfolioName = sc.next();
            try {
              user.loadFlexiPortfolio(portfolioName);
              view.loadPortfolioSuccessful(portfolioName);
            } catch (SAXException e) {
              view.invalidXMLFormat();
            } catch (IllegalArgumentException
                     | ParserConfigurationException
                     | DateTimeException
                     | FileNotFoundException
                     | ArrayIndexOutOfBoundsException e) {
              view.showError(e.getMessage());
            }
            break;
          case 6:
            view.askPortfolioName();
            portfolioName = sc.next();
            if (user.checkPortfolioExists(portfolioName)) {
              while (!user.checkPortfolioExists(portfolioName)) {
                view.invalidPortfolioName();
                portfolioName = sc.next();
              }

              view.enterDate();
              String d = sc.next();
              try {
                float cb = user.calculateCostBasis(portfolioName, d, cache);
                view.displayCostBasis(cb, portfolioName);
              } catch (DateTimeException | IllegalArgumentException e) {
                view.showError(e.getMessage());
              } catch (ArrayIndexOutOfBoundsException e) {
                view.invalidDate();
              }
            } else {
              view.showError("Portfolio not found.");
            }
            break;
          case 7:
            view.askPortfolioName();
            portfolioName = sc.next();
            view.enterDate();
            String date = sc.next();
            try {
              float value = user.getFlexiPortfolioValue(portfolioName, date);
              view.printTotalValue(value);
            } catch (IndexOutOfBoundsException e) {
              view.invalidDate();
            } catch (NullPointerException e) {
              view.invalidPortfolioName();
            } catch (NumberFormatException e) {
              view.invalidUserInput();
            } catch (DateTimeException | IllegalArgumentException e) {
              view.showError(e.getMessage());
            }
            break;
          case 8:
            view.askPortfolioName();
            portfolioName = sc.next();
            view.enterPlotStartDate();
            String sDate = sc.next();
            view.enterPlotEndDate();
            String eDate = sc.next();

            try {
              StringBuilder sb = user.drawGraph(portfolioName, sDate, eDate);
              view.show(sb.toString());
            } catch (DateTimeException | IllegalArgumentException e) {
              view.showError(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
              view.invalidDate();
            }

            break;

          case 9:
            List<String> pNames = c.getPortfolioNames();
            view.show("\nInflexible Portfolios:");
            view.show(pNames.toString());
            pNames = user.getAllPortfolioNames();
            view.show("\nFlexible Portfolios:");
            view.show(pNames.toString());
            break;

          default:
            break;
        }
        view.showFlexibleMenu();
        flexiPortfolioOperation = Integer.parseInt(sc.next());
      }
      // save all flexible portfolios
      saveAllPortfolios();
    } catch (NumberFormatException e) {
      view.invalidUserInput();
      flexibleOperations();
    }
  }


  @Override
  public void buyStock(String portfolioName) throws IOException {

    TickerValidator tv = new TickerValidatorImpl("stock_master_file.csv");
    tv.readFile();


    try {

      if (user.checkPortfolioExists(portfolioName)) {
        String addAnotherStock = "y";

        while (addAnotherStock.equals("y")) {
          String ticker;
          String buyDate;
          FlexiStocks newStock;

          view.enterTicker();
          ticker = sc.next();

          if (!tv.checkTicker(ticker)) {
            view.invalidTicker();
            continue;
          }

          view.enterBuyDate();
          buyDate = sc.next();
          String[] sdArray = buyDate.split("-");
          LocalDate d1 = LocalDate.of(Integer.parseInt(sdArray[0]),
                  Integer.parseInt(sdArray[1]), Integer.parseInt(sdArray[2]));

          if (!dateCheckerHelper(d1, ticker, cache)) {
            throw new IllegalArgumentException("Invalid buy date. Markets are not operational.");
          }
          view.enterNOS();

          try {
            float noOfShares = Float.parseFloat(sc.next());
            if (noOfShares - Math.floor(noOfShares) > 0) {
              view.invalidUserInput();
              continue;
            }

            view.show("Enter the commission fee for this transaction");
            String commissionFee = sc.next();
            float c = Float.parseFloat(commissionFee);
            while (c <= 0) {
              view.showError("Commission cannot be zero or negative. "
                      + "Please enter valid commission fee.");
              view.show("Enter the commission fee for this transaction");
              commissionFee = sc.next();
              c = Float.parseFloat(commissionFee);
            }


            String[] d = buyDate.split("-");

            try {
              LocalDate bd = LocalDate.of(Integer.parseInt(d[0]),
                      Integer.parseInt(d[1]), Integer.parseInt(d[2]));

              newStock = FlexiStocksImpl.getBuilder1()
                      .ticker(ticker)
                      .buyDate(bd)
                      .noOfShares(noOfShares)
                      .status("b")
                      .commissionFee(c)
                      .create();

              user.addStockToExistingPortfolio(newStock, portfolioName);
            } catch (DateTimeException e) {
              view.showError(e.getMessage());
            }
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid number of shares.");
          }

          view.continueAddingStocks();
          addAnotherStock = sc.next();
        }
      } else {
        String addAnotherStock = "y";
        Map<String, List<List<String>>> m = new HashMap<>();
        String ticker;
        String buyDate;
        String commissionFee;

        while (addAnotherStock.equals("y")) {
          view.enterTicker();
          ticker = sc.next();

          if (!tv.checkTicker(ticker)) {
            view.invalidTicker();
            continue;
          }

          view.enterDate();
          buyDate = sc.next();
          String[] sdArray = buyDate.split("-");
          LocalDate d1 = LocalDate.of(Integer.parseInt(sdArray[0]),
                  Integer.parseInt(sdArray[1]), Integer.parseInt(sdArray[2]));

          if (!dateCheckerHelper(d1, ticker, cache)) {
            throw new IllegalArgumentException("Invalid buy date. Markets are not operational.");
          }
          view.enterNOS();

          try {
            float noOfShares = Float.parseFloat(sc.next());
            if ((noOfShares - Math.floor(noOfShares) > 0) || noOfShares <= 0) {
              view.invalidUserInput();
              continue;
            }

            view.show("Enter the commission fee for this transaction");
            commissionFee = sc.next();
            float c = Float.parseFloat(commissionFee);
            while (c <= 0) {
              view.showError("Commission cannot be zero or negative. "
                      + "Please enter valid commission fee.");
              view.show("Enter the commission fee for this transaction");
              commissionFee = sc.next();
              c = Float.parseFloat(commissionFee);
            }

            List<String> s = new ArrayList<>();
            s.add(buyDate);
            s.add("b");
            s.add(String.valueOf(noOfShares));
            s.add(String.valueOf(c));

            if (m.containsKey(ticker)) {
              m.get(ticker).add(s);
            } else {
              List<List<String>> s1 = new ArrayList<>();
              s1.add(s);
              m.put(ticker, s1);
            }
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid number of shares.");
          }

          view.continueAddingStocks();
          addAnotherStock = sc.next();
        }
        try {
          user.addNewFlexiPortfolio(m, portfolioName);
        } catch (DateTimeException e) {
          view.showError(e.getMessage());
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      view.invalidDateFormat();
    }
  }

  @Override
  public void sellStock(String portfolioName) {
    String sellAnother = "y";
    while (sellAnother.equals("y")) {
      if (user.checkPortfolioExists(portfolioName)) {
        view.enterSellTicker();
        String ticker = sc.next();
        FlexiPortfolio p = user.getPortfolio(portfolioName);

        view.enterSellDate();
        String sd = sc.next();

        String[] sdArray = sd.split("-");

        LocalDate sellDate = LocalDate.of(Integer.parseInt(sdArray[0]),
                Integer.parseInt(sdArray[1]), Integer.parseInt(sdArray[2]));

        if (!dateCheckerHelper(sellDate, ticker, cache)) {
          throw new IllegalArgumentException("Invalid sell date. Markets are not operational.");
        }

        if (p.getComposition(sellDate).containsKey(ticker)) {
          view.enterSellQuantity();
          String sq = sc.next();

          if (Float.parseFloat(sq) - Math.floor(Float.parseFloat(sq)) > 0) {
            view.invalidUserInput();
            continue;
          }

          if (p.validateSell(sellDate, ticker, Float.valueOf(sq))) {
            FlexiStocks newStock;

            try {
              newStock = FlexiStocksImpl.getBuilder1()
                      .ticker(ticker)
                      .buyDate(sellDate)
                      .noOfShares(Float.parseFloat(sq))
                      .status("s")
                      .create();

              user.addStockToExistingPortfolio(newStock, portfolioName);
              view.stockSold();
            } catch (IllegalArgumentException | DateTimeException e) {
              view.showError(e.getMessage());
            }


          } else {
            view.stockNotSold();
          }
        } else {
          view.stockNotSold();
        }
      } else {
        view.invalidPortfolioName();
        portfolioName = sc.next();
        sellStock(portfolioName);
      }
      view.sellAnotherStock();
      sellAnother = sc.next();
    }

  }

  @Override
  public void saveAllPortfolios() {
    List<String> portfolioNames = user.getAllPortfolioNames();
    for (String portfolioName : portfolioNames) {
      try {
        user.saveFlexiPortfolio(portfolioName);
        view.savePortfolioSuccessful(portfolioName);
      } catch (FileNotFoundException e) {
        view.portfolioFileMissing();
      } catch (ParserConfigurationException | TransformerException e) {
        view.invalidXMLFormat();
      }
    }
  }

  private boolean dateCheckerHelper(LocalDate date, String ticker,
                                    Map<String, Map<String, Float>> c) {

    GetStockValueAPI getVal = new GetStockValueAPIImpl();

    if (!c.containsKey(ticker)) {
      c.put(ticker, getVal.getStockString(ticker));
    }

    Map<String, Float> v = c.get(ticker);

    return v.containsKey(date.toString());
  }
}
