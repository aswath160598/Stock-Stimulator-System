package controller;

import java.io.FileNotFoundException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This Interface represents the Features Interface of our Graphical User Interface.
 */
public interface Features {

  /**
   * Function to exit a Program.
   */
  void exitProgram();

  /**
   * Function to process buy Stock button.
   *
   * @param portfolioName the portfolio name
   * @param ticker        the ticker
   * @param buyDate       the buy date
   * @param nos           the number of shares
   * @param commission    the commission fee
   */
  void buyClicked(String portfolioName, String ticker, String buyDate,
                  String nos, String commission);

  /**
   * Function to process sell Stock button.
   *
   * @param portfolioName  the portfolio name
   * @param ticker         the ticker
   * @param sellDate       the sell date
   * @param nos            the number of shares
   * @param sellCommission the commission fee.
   */
  void sellClicked(String portfolioName, String ticker, String sellDate,
                   String nos, String sellCommission);

  /**
   * Function to process view Portfolio button.
   *
   * @param portfolioName the portfolio name
   * @param date          the date on which we have to view the portfolio
   */
  void viewPortfolio(String portfolioName, String date);

  /**
   * Function to process save Portfolio button.
   *
   * @param portfolioName the portfolio name
   */
  void savePortfolio(String portfolioName);

  /**
   * Function to process load Portfolio button.
   *
   * @param portfolioName the portfolio name
   */
  void loadPortfolio(String portfolioName);

  /**
   * Function to add dollar cost averaging strategy to a portfolio.
   *
   * @param dcaName             the dca strategy name
   * @param dcaPortfolioName    the portfolio name
   * @param dcaStartDate        the start date
   * @param dcaEndDate          the end date
   * @param dcaInvestmentAmount the total investment amount
   * @param dcaInterval         the interval for dollar cost averaging
   * @param dcaPropMap          the proportionality of stocks to be invested
   * @param dcaCommission       the commission fee for dollar cost averaging
   * @throws FileNotFoundException        the FileNotFoundException to be thrown
   * @throws ParserConfigurationException the ParserConfigurationException to be thrown
   * @throws TransformerException         the TransformerException to be thrown
   */
  void addDCA(String dcaName,
              String dcaPortfolioName,
              String dcaStartDate,
              String dcaEndDate,
              String dcaInvestmentAmount,
              String dcaInterval,
              String dcaPropMap,
              String dcaCommission) throws FileNotFoundException,
          ParserConfigurationException, TransformerException;

  /**
   * Function to calculate cost basis of a portfolio on a particular day.
   *
   * @param cbPortfolioName the portfolio name
   * @param cbDate          the date on which cost basis of a portfolio has to be calculated
   */
  void calculateCB(String cbPortfolioName, String cbDate);

  /**
   * Function to add Fixed Amount Investment strategy to a portfolio.
   *
   * @param fixedAmountName             the strategy name
   * @param fixedAmountPortfolioName    the portfolio name
   * @param fixedAmountDate             the start date
   * @param fixedAmountInvestmentAmount the total investment amount
   * @param fixedAmountPropMap          the proportionality of stocks to be invested
   * @param fixedAmountCommission       the commission fee for filled amount strategy
   */
  void addFixedAmountInvestment(String fixedAmountName,
                                String fixedAmountPortfolioName,
                                String fixedAmountDate,
                                String fixedAmountInvestmentAmount,
                                String fixedAmountPropMap,
                                String fixedAmountCommission);

  /**
   * Function to calculate a portfolio value.
   *
   * @param portfolioValuePortfolioName the Portfolio name
   * @param portfolioValueDate          the Portfolio date
   */
  void calculatePortfolioValue(String portfolioValuePortfolioName,
                               String portfolioValueDate);

  /**
   * Function to plot the bar graph.
   *
   * @param plotGraphPortfolioName the Portfolio name
   * @param plotGraphStartDate     the start date
   * @param plotGraphEndDate       the end date
   */
  void plotGraph(String plotGraphPortfolioName, String plotGraphStartDate, String plotGraphEndDate);

  /**
   * Function to display sell menu.
   */
  void viewSellMenu();

  /**
   * Function to display portfolio menu.
   */
  void viewPortfolioMenu();

  /**
   * Function to display save menu.
   */
  void viewSaveMenu();

  /**
   * Function to display cost basis menu.
   */
  void viewCostBasisMenu();

  /**
   * Function to display value of a portfolio menu.
   */
  void viewPortfolioValueMenu();

  /**
   * Function to display graph plotting menu.
   */
  void viewPlotGraphMenu();

  /**
   * Function to display single investment strategy menu.
   */
  void viewFixedAmountMenu();
}
