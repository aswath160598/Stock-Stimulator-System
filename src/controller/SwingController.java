package controller;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import view.IView;

/**
 * This Interface represents the Swing Controller Module of our Portfolio Project.
 */
public interface SwingController {
  /**
   * Function to set view.
   *
   * @param v the view object
   */
  void setView(IView v);

  /**
   * Function to buy a stock to be added to a portfolio.
   *
   * @param portfolioName the portfolio name
   * @param ticker        the ticker symbol
   * @param buyDate       the buy date
   * @param nos           the number of shares
   * @param commission    the commission fee
   */
  void buyStock(String portfolioName, String ticker, String buyDate, String nos, String commission);

  /**
   * Function to sell a stock from the portfolio.
   *
   * @param portfolioName  the portfolio name
   * @param ticker         the ticker symbol
   * @param sellDate       the sell date
   * @param nos            the number of shares
   * @param sellCommission the commission fee
   */
  void sellStock(String portfolioName, String ticker, String sellDate,
                 String nos, String sellCommission);

  /**
   * Function to view a portfolio on a particular day.
   *
   * @param portfolioName the portfolio name
   * @param date          the date
   * @return the portfolio contents
   */
  Map<String, Float> viewPortfolio(String portfolioName, String date);

  /**
   * Function to save the portfolio.
   *
   * @param portfolioName the portfolio name
   * @throws FileNotFoundException        the FileNotFoundException to be thrown.
   * @throws ParserConfigurationException the ParserConfigurationException to be thrown.
   * @throws TransformerException         the TransformerException to be thrown.
   */
  void savePortfolio(String portfolioName) throws FileNotFoundException,
          ParserConfigurationException, TransformerException;

  /**
   * Function to load the portfolio.
   *
   * @param portfolioName the portfolio name
   * @throws ParserConfigurationException the ParserConfigurationException to be thrown.
   * @throws IOException                  the IOException to be thrown.
   * @throws SAXException                 the SAXException to be thrown.
   * @throws TransformerException         the TransformerException to be thrown.
   */
  void loadPortfolio(String portfolioName) throws ParserConfigurationException,
          IOException, SAXException, TransformerException;

  /**
   * Function to create dollar cost averaging.
   *
   * @param dcaName             the dollar cost averaging strategy name
   * @param dcaPortfolioName    the portfolio name
   * @param dcaStartDate        the start date
   * @param dcaEndDate          the end date
   * @param dcaInvestmentAmount the total investment amount
   * @param dcaInterval         the interval
   * @param dcaPropMap          the proportion map
   * @param dcaCommission       the commission fee
   * @throws FileNotFoundException        the FileNotFoundException to be thrown.
   * @throws ParserConfigurationException the ParserConfigurationException to be thrown.
   * @throws TransformerException         the TransformerException to be thrown.
   */
  void createDollarCostAveraging(String dcaName, String dcaPortfolioName, String dcaStartDate,
                                 String dcaEndDate, String dcaInvestmentAmount, String dcaInterval,
                                 String dcaPropMap, String dcaCommission)
          throws FileNotFoundException, ParserConfigurationException, TransformerException;

  /**
   * Function to create fixed amount strategy.
   *
   * @param fixedAmountName             the fixed amount strategy name
   * @param fixedAmountPortfolioName    the portfolio name
   * @param fixedAmountDate             the date
   * @param fixedAmountInvestmentAmount the total investment amount
   * @param fixedAmountPropMap          the proportion map
   * @param fixedAmountCommission       the commission fee
   */
  void addFixedAmountInvestment(String fixedAmountName, String fixedAmountPortfolioName,
                                String fixedAmountDate, String fixedAmountInvestmentAmount,
                                String fixedAmountPropMap, String fixedAmountCommission);

  /**
   * Function to calculate the portfolio value.
   *
   * @param portfolioValuePortfolioName the portfolio name
   * @param portfolioValueDate          the date
   */
  void calculatePortfolioValue(String portfolioValuePortfolioName, String portfolioValueDate);

  /**
   * Function to calculate the cost basis of a portfolio.
   *
   * @param cbPortfolioName the portfolio name
   * @param cbDate          the date
   */
  void calculateCB(String cbPortfolioName, String cbDate);

  /**
   * Function to plot the graph of a portfolio.
   *
   * @param plotGraphPortfolioName the portfolio name
   * @param plotGraphStartDate     the start date
   * @param plotGraphEndDate       the end date
   */
  void plotGraph(String plotGraphPortfolioName, String plotGraphStartDate, String plotGraphEndDate);

  /**
   * Function to save all portfolios.
   */
  void saveAllPortfolios();

  /**
   * Function to view sell menu.
   */
  void viewSellMenu();

  /**
   * Function to view portfolio menu.
   */
  void viewPortfolioMenu();

  /**
   * Function to view save menu.
   */
  void viewSaveMenu();

  /**
   * Function to view cost basis menu.
   */
  void viewCostBasisMenu();

  /**
   * Function to view portfolio value menu.
   */
  void viewPortfolioValueMenu();

  /**
   * Function to view plot graph menu.
   */
  void viewPlotGraphMenu();

  /**
   * Function to view fixed amount menu.
   */
  void viewFixedAmountMenu();
}
