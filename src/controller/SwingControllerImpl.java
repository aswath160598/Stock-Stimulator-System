package controller;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.FlexiPortfolio;
import model.FlexiStocks;
import model.FlexiStocksImpl;
import model.FlexiUser;
import utilities.GUIBarChart;
import utilities.GraphDraw;
import utilities.GraphDrawImpl;
import view.IView;

/**
 * This class represents our Swing Controller. It implements all the swing controller functions
 * specific to flexible portfolio and interacts with the view and the model.
 */
public class SwingControllerImpl implements SwingController {
  private final FlexiUser user;
  private IView view;
  private final Features features;
  private final Map<String, Map<String, Float>> cache;

  /**
   * This represents the Flexible Controller Constructor.
   *
   * @param u the user object
   */
  public SwingControllerImpl(FlexiUser u) {
    user = u;
    features = new FeaturesImpl(this);

    cache = new HashMap<>();
  }

  @Override
  public void setView(IView v) {
    view = v;
    view.addFeatures(features);
  }

  @Override
  public void buyStock(String portfolioName, String ticker, String buyDate,
                       String nos, String commission) {
    FlexiStocks newStock;
    if (user.checkPortfolioExists(portfolioName)) {
      newStock = FlexiStocksImpl.getBuilder1()
              .ticker(ticker)
              .buyDate(LocalDate.parse(buyDate))
              .noOfShares(Float.parseFloat(nos))
              .status("b")
              .commissionFee(Float.parseFloat(commission))
              .create();
      user.addStockToExistingPortfolio(newStock, portfolioName);
    } else {
      Map<String, List<List<String>>> m = new HashMap<>();
      List<String> s = new ArrayList<>();
      s.add(buyDate);
      s.add("b");
      s.add(String.valueOf(nos));
      s.add(commission);

      List<List<String>> s1 = new ArrayList<>();
      s1.add(s);
      m.put(ticker, s1);

      user.addNewFlexiPortfolio(m, portfolioName);
    }
    view.showInformationMessage("Buy Successful");
    view.clearBuyPanelTextFields();
  }

  @Override
  public void sellStock(String portfolioName, String ticker, String sellDate,
                        String nos, String sellCommission) {
    if (user.checkPortfolioExists(portfolioName)) {
      // sell operation with validation
      FlexiPortfolio p = user.getPortfolio(portfolioName);
      FlexiStocks newStock;
      if (p.validateSell(LocalDate.parse(sellDate), ticker, Float.valueOf(nos))) {
        // sell is valid
        newStock = FlexiStocksImpl.getBuilder1()
                .ticker(ticker)
                .buyDate(LocalDate.parse(sellDate))
                .noOfShares(Float.parseFloat(nos))
                .status("s")
                .commissionFee(Float.parseFloat(sellCommission))
                .create();

        user.addStockToExistingPortfolio(newStock, portfolioName);
        view.showInformationMessage("Stock Sold!!!");
        view.clearSellPanelTextFields();
      } else {
        view.showWarningMessage("Invalid Sell!!!");
      }
    } else {
      view.showWarningMessage("Portfolio " + portfolioName + " does not exist!!!");
    }
  }

  @Override
  public Map<String, Float> viewPortfolio(String portfolioName, String date) {
    if (user.checkPortfolioExists(portfolioName)) {
      Map<String, Float> m = user.getPortfolio(portfolioName)
              .getComposition(LocalDate.parse(date));
      StringBuilder sb = new StringBuilder();
      for (Map.Entry<String, Float> mapElement : m.entrySet()) {
        sb.append(mapElement.getKey());
        sb.append(" ");
        sb.append(mapElement.getValue());
        sb.append("\n");
      }

      if (sb.toString().equals("")) {
        view.showWarningMessage("No stocks found for portfolio "
                + portfolioName + " on date " + date);
      } else {
        StringBuilder sb1 = new StringBuilder("Portfolio "
                + portfolioName
                + " had the following stocks on " + date + ":\n");
        sb1.append(sb);
        view.showInformationMessage(sb1.toString().trim());
      }

    } else {
      // throw a message portfolio does not exist
      view.showWarningMessage("Portfolio " + portfolioName + " does not exist!!!");
    }
    return null;
  }

  @Override
  public void savePortfolio(String portfolioName) throws FileNotFoundException,
          ParserConfigurationException, TransformerException {
    if (user.checkPortfolioExists(portfolioName)) {
      // save the portfolio
      user.saveFlexiPortfolio(portfolioName);
      view.showInformationMessage("Saved portfolio " + portfolioName);
    } else {
      // throw a message portfolio does not exist
      view.showWarningMessage("Portfolio " + portfolioName + " does not exist!!!");
    }
  }

  @Override
  public void loadPortfolio(String portfolioName) throws ParserConfigurationException,
          IOException, SAXException, TransformerException {
    try {
      user.loadFlexiPortfolio(portfolioName);
      view.showInformationMessage("Portfolio " + portfolioName + " successfully loaded.");
    } catch (IOException e) {
      view.showWarningMessage("Portfolio file " + portfolioName + " not found!!!");
    }
  }

  @Override
  public void createDollarCostAveraging(String dcaName, String dcaPortfolioName,
                                        String dcaStartDate, String dcaEndDate,
                                        String dcaInvestmentAmount, String dcaInterval,
                                        String dcaPropMap, String dcaCommission)
          throws FileNotFoundException, ParserConfigurationException, TransformerException {
    if (LocalDate.parse(dcaEndDate).isAfter(LocalDate.parse(dcaStartDate))) {
      Map<String, List<String>> dca = new HashMap<>();
      List<String> p = new ArrayList<>();
      p.add(dcaStartDate);
      p.add(dcaEndDate);
      p.add(dcaInvestmentAmount);
      p.add(dcaInterval);
      p.add(dcaPropMap);
      p.add(dcaCommission);
      dca.put(dcaName, p);
      user.addNewDollarCostAvg(dca, dcaPortfolioName);
      user.updateDollarCostAvg();
      view.showInformationMessage("Dollar Cost Averaging Plan created successfully.");
    } else {
      view.showWarningMessage("End date has to be after start date.");
    }
  }

  @Override
  public void addFixedAmountInvestment(String fixedAmountName, String fixedAmountPortfolioName,
                                       String fixedAmountDate, String fixedAmountInvestmentAmount,
                                       String fixedAmountPropMap, String fixedAmountCommission) {
    Map<String, List<String>> fixedAmt = new HashMap<>();
    List<String> p = new ArrayList<>();
    p.add(fixedAmountDate);
    p.add(fixedAmountInvestmentAmount);
    p.add(fixedAmountPropMap);
    p.add(fixedAmountCommission);
    fixedAmt.put(fixedAmountName, p);
    try {
      user.addNewDollarCostAvgSingle(fixedAmt, fixedAmountPortfolioName);
      user.updateDollarCostAvg();
      view.showInformationMessage("Fixed Amount Invested Successfully.");
    } catch (IllegalArgumentException e) {
      view.showWarningMessage(e.getMessage());
    } catch (FileNotFoundException | ParserConfigurationException | TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void calculatePortfolioValue(String portfolioValuePortfolioName,
                                      String portfolioValueDate) {
    if (user.checkPortfolioExists(portfolioValuePortfolioName)) {
      try {
        float value = user.getFlexiPortfolioValue(portfolioValuePortfolioName, portfolioValueDate);
        view.showInformationMessage("Value of portfolio "
                + portfolioValuePortfolioName + " on "
                + portfolioValueDate + " was $" + String.format("%.2f", value));
      } catch (ParseException e) {
        view.showWarningMessage("Portfolio error");
      }
    } else {
      view.showWarningMessage("Portfolio " + portfolioValuePortfolioName + " not found!!!");
    }
  }

  @Override
  public void calculateCB(String cbPortfolioName, String cbDate) {
    if (user.checkPortfolioExists(cbPortfolioName)) {
      try {
        float costBasis = user.calculateDCAPortfolioCostBasis(cbPortfolioName, cbDate, cache);
        view.showInformationMessage("Cost Basis of portfolio "
                + cbPortfolioName + " on "
                + cbDate + " was $" + String.format("%.2f", costBasis));
      } catch (ParseException e) {
        view.showWarningMessage("Portfolio error");
      }
    } else {
      view.showWarningMessage("Portfolio " + cbPortfolioName + " not found!!!");
    }
  }

  @Override
  public void plotGraph(String plotGraphPortfolioName, String plotGraphStartDate,
                        String plotGraphEndDate) {
    try {
      if (user.checkPortfolioExists(plotGraphPortfolioName)) {
        Map<String, List<String>> data = user.graphComputation(plotGraphPortfolioName,
                plotGraphStartDate, plotGraphEndDate);
        GraphDraw g = new GraphDrawImpl();
        GUIBarChart chart = g.drawGUIGraph(data.get("values"), data.get("timeStamp"));
        view.showChart(chart);
      } else {
        view.showWarningMessage("Portfolio " + plotGraphPortfolioName + " not found!!!");
      }
    } catch (IllegalArgumentException e) {
      view.showWarningMessage(e.getMessage());
    }
  }

  @Override
  public void saveAllPortfolios() {
    List<String> portfolioNames = user.getAllPortfolioNames();
    for (String portfolioName : portfolioNames) {
      try {
        user.saveFlexiPortfolio(portfolioName);
      } catch (FileNotFoundException | ParserConfigurationException | TransformerException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void viewSellMenu() {
    List<String> portfolios = user.getAllPortfolioNames();
    view.viewSellMenu(portfolios);
  }

  @Override
  public void viewPortfolioMenu() {
    List<String> portfolios = user.getAllPortfolioNames();
    view.viewPortfolioMenu(portfolios);
  }

  @Override
  public void viewSaveMenu() {
    List<String> portfolios = user.getAllPortfolioNames();
    view.viewSaveMenu(portfolios);
  }

  @Override
  public void viewCostBasisMenu() {
    List<String> portfolios = user.getAllPortfolioNames();
    view.viewCostBasisMenu(portfolios);
  }

  @Override
  public void viewPortfolioValueMenu() {
    List<String> portfolios = user.getAllPortfolioNames();
    view.viewPortfolioValueMenu(portfolios);
  }

  @Override
  public void viewPlotGraphMenu() {
    List<String> portfolios = user.getAllPortfolioNames();
    view.viewPlotGraphMenu(portfolios);
  }

  @Override
  public void viewFixedAmountMenu() {
    List<String> portfolios = user.getAllPortfolioNames();
    view.viewFixedAmountMenu(portfolios);
  }
}