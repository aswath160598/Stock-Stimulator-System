package controller;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This class represents our Features Interface. So that controller does not have to directly
 * implement the features interface and instead can hva e a features object which it will
 * pass to the view instead of passing itself. Using this approach the view does not have access t
 * o all public methods of the controller.
 */
public class FeaturesImpl implements Features {

  private final SwingController c;

  /**
   * Default Constructor for features Implementation.
   *
   * @param controller the controller object
   */
  public FeaturesImpl(SwingController controller) {
    c = controller;
  }

  @Override
  public void addDCA(String dcaName,
                     String dcaPortfolioName,
                     String dcaStartDate,
                     String dcaEndDate,
                     String dcaInvestmentAmount,
                     String dcaInterval,
                     String dcaPropMap,
                     String dcaCommission) throws FileNotFoundException,
          ParserConfigurationException, TransformerException {
    if (dcaEndDate.equals("")) {
      dcaEndDate = "2099-12-31";
    }
    c.createDollarCostAveraging(dcaName, dcaPortfolioName, dcaStartDate, dcaEndDate,
            dcaInvestmentAmount, dcaInterval, dcaPropMap, dcaCommission);
  }

  @Override
  public void calculateCB(String cbPortfolioName, String cbDate) {
    c.calculateCB(cbPortfolioName, cbDate);
  }

  @Override
  public void addFixedAmountInvestment(String fixedAmountName,
                                       String fixedAmountPortfolioName,
                                       String fixedAmountDate,
                                       String fixedAmountInvestmentAmount,
                                       String fixedAmountPropMap,
                                       String fixedAmountCommission) {
    c.addFixedAmountInvestment(fixedAmountName,
            fixedAmountPortfolioName,
            fixedAmountDate,
            fixedAmountInvestmentAmount,
            fixedAmountPropMap,
            fixedAmountCommission);
  }

  @Override
  public void calculatePortfolioValue(String portfolioValuePortfolioName,
                                      String portfolioValueDate) {
    c.calculatePortfolioValue(portfolioValuePortfolioName, portfolioValueDate);
  }

  @Override
  public void plotGraph(String plotGraphPortfolioName,
                        String plotGraphStartDate,
                        String plotGraphEndDate) {
    c.plotGraph(plotGraphPortfolioName, plotGraphStartDate, plotGraphEndDate);
  }

  @Override
  public void viewSellMenu() {
    c.viewSellMenu();
  }

  @Override
  public void viewPortfolioMenu() {
    c.viewPortfolioMenu();
  }

  @Override
  public void viewSaveMenu() {
    c.viewSaveMenu();
  }

  @Override
  public void viewCostBasisMenu() {
    c.viewCostBasisMenu();
  }

  @Override
  public void viewPortfolioValueMenu() {
    c.viewPortfolioValueMenu();
  }

  @Override
  public void viewPlotGraphMenu() {
    c.viewPlotGraphMenu();
  }

  @Override
  public void viewFixedAmountMenu() {
    c.viewFixedAmountMenu();
  }

  @Override
  public void savePortfolio(String portfolioName) {
    try {
      c.savePortfolio(portfolioName);
    } catch (FileNotFoundException | ParserConfigurationException | TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void loadPortfolio(String portfolioName) {
    try {
      c.loadPortfolio(portfolioName);
    } catch (ParserConfigurationException | SAXException | TransformerException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void exitProgram() {
    c.saveAllPortfolios();
    System.exit(0);
  }

  @Override
  public void buyClicked(String portfolioName, String ticker,
                         String buyDate, String nos, String commission) {
    c.buyStock(portfolioName, ticker, buyDate, nos, commission);
  }

  @Override
  public void sellClicked(String portfolioName, String ticker, String sellDate,
                          String nos, String sellCommission) {
    c.sellStock(portfolioName, ticker, sellDate, nos, sellCommission);
  }

  @Override
  public void viewPortfolio(String portfolioName, String date) {
    c.viewPortfolio(portfolioName, date);
  }
}
