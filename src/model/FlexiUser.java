package model;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This represents the flexible user interface of our Model.
 */
public interface FlexiUser {

  /**
   * Function to add a new flexible Portfolio to the user.
   *
   * @param p    the flexible portfolio to be added to the user.
   * @param name the portfolio name to be added.
   */
  void addNewFlexiPortfolio(Map<String, List<List<String>>> p, String name);

  /**
   * Function to get the value of a Portfolio on a specified date.
   *
   * @param fName the flexible portfolio name
   * @param date  the date on which the flexible portfolio value must be returned
   * @return the values of a flexible portoflio on a given date
   * @throws ParseException the ParseException has to be thrown
   */
  float getFlexiPortfolioValue(String fName, String date)
          throws ParseException;

  /**
   * Function to get the number of flexible Portfolios.
   *
   * @return the number of portfolios
   */
  int getNoOfPortfolios();

  /**
   * Function to get the portfolio.
   *
   * @param portfolioName the flexible portfolio name.
   * @return the desired flexible portfolio
   */
  FlexiPortfolio getPortfolio(String portfolioName);

  /**
   * Function to check if a Portfolio exists.
   *
   * @param name the name of the portfolio
   * @return true or false is returned
   */
  Boolean checkPortfolioExists(String name);

  /**
   * Function to get the list of flexible portfolios.
   *
   * @return the list of all portfolios
   */
  List<String> getAllPortfolioNames();

  /**
   * Function to check if a Portfolio exists.
   *
   * @param name the Portfolio name
   * @return true of portfolio exists or false when it doesn't exist.
   */
  Boolean checkPortfolioFileExists(String name);

  /**
   * Function to add a stock.
   *
   * @param s             the flexible stock to be added
   * @param portfolioName the portfolio name
   */
  void addStockToExistingPortfolio(FlexiStocks s, String portfolioName);

  /**
   * Function to calculate cost basis of a flexible portfolio.
   *
   * @param portfolioName the portfolio name
   * @param date          the date on which cost basis has to be calculated
   * @param cache         a local cache
   * @return the cost basis is calculated and returned.
   * @throws ParseException the ParseException to be thrown.
   */
  float calculateCostBasis(String portfolioName, String date, Map<String, Map<String, Float>> cache)
          throws ParseException;

  /**
   * Function to save a flexible Portfolio.
   *
   * @param portfolioName the Portfolio name
   * @throws ParserConfigurationException the ParserConfigurationException to be thrown
   * @throws FileNotFoundException        the FileNotFoundException to be thrown
   * @throws TransformerException         the TransformerException to be thrown.
   */
  void saveFlexiPortfolio(String portfolioName) throws ParserConfigurationException,
          FileNotFoundException, TransformerException;

  /**
   * Function to load a flexible Portfolio.
   *
   * @param portfolioName the Portfolio name
   * @throws IllegalArgumentException     the IllegalArgumentException to be thrown
   * @throws ParserConfigurationException the ParserConfigurationException to be thrown
   * @throws IOException                  the IOException to be thrown
   * @throws SAXException                 the SAXException to be thrown
   * @throws TransformerException         the TransformerException to be thrown
   */
  void loadFlexiPortfolio(String portfolioName) throws IllegalArgumentException,
          ParserConfigurationException, IOException, SAXException, TransformerException;

  /**
   * Function to draw a graph between the given time range for a flexible Portfolio.
   *
   * @param fPortfolio the Portfolio name
   * @param startDate  the start date from when graph has to be drawn
   * @param endDate    the end date from when graph has to be drawn
   * @return stringbuilder of the graph
   * @throws ParseException the ParseException to be thrown
   */
  StringBuilder drawGraph(String fPortfolio, String startDate, String endDate)
          throws ParseException;

  /**
   * Function to draw a graph between the given time range for a flexible Portfolio.
   *
   * @param portfolioName the Portfolio name
   * @param startDate     the start date from when graph has to be drawn
   * @param endDate       the end date from when graph has to be drawn
   * @return the map of vales and timestamp for graph
   */
  Map<String, List<String>> graphComputation(String portfolioName, String startDate,
                                             String endDate);

  /**
   * Function to implement dollar cost averaging .
   *
   * @param portfolioName the Portfolio name
   * @param obj           the dollar cost averaging object
   * @throws FileNotFoundException        the File Not Found Exception to be thrown
   * @throws ParserConfigurationException the ParserConfigurationException Exception to be thrown
   * @throws TransformerException         the TransformerException Exception to be thrown
   */
  void dollarCostAveraging(String portfolioName, DollarCostAveraging obj) throws
          FileNotFoundException, ParserConfigurationException, TransformerException;

  /**
   * Function to implement dollar cost averaging .
   *
   * @param portfolioName the Portfolio name
   * @param obj           the dollar cost averaging object
   * @throws IllegalArgumentException     the File Not Found Exception to be thrown
   * @throws FileNotFoundException        the File Not Found Exception to be thrown
   * @throws ParserConfigurationException the ParserConfigurationException Exception to be thrown
   * @throws TransformerException         the TransformerException Exception to be thrown
   */
  void dollarCostAveragingSingle(String portfolioName, DollarCostAveragingSingle obj)
          throws IllegalArgumentException, FileNotFoundException, ParserConfigurationException,
          TransformerException;

  /**
   * Function to update dollar cost averaging with the current date.
   *
   * @throws FileNotFoundException        the File Not Found Exception to be thrown
   * @throws ParserConfigurationException the ParserConfigurationException Exception to be thrown
   * @throws TransformerException         the TransformerException Exception to be thrown
   */
  void updateDollarCostAvg() throws FileNotFoundException, ParserConfigurationException,
          TransformerException;

  /**
   * Function to add new dollar cost averaging.
   *
   * @param p    the map to add as dollar cost averaging
   * @param name the Portfolio name
   * @throws FileNotFoundException        the File Not Found Exception to be thrown
   * @throws ParserConfigurationException the ParserConfigurationException Exception to be thrown
   * @throws TransformerException         the TransformerException Exception to be thrown
   */
  void addNewDollarCostAvg(Map<String, List<String>> p, String name) throws FileNotFoundException,
          ParserConfigurationException, TransformerException;

  /**
   * Function to add new single dollar cost averaging.
   *
   * @param d             the map to add as dollar cost averaging
   * @param portfolioName the Portfolio name
   * @throws FileNotFoundException        the File Not Found Exception to be thrown
   * @throws ParserConfigurationException the ParserConfigurationException Exception to be thrown
   * @throws TransformerException         the TransformerException Exception to be thrown
   */
  void addNewDollarCostAvgSingle(Map<String, List<String>> d, String portfolioName)
          throws FileNotFoundException, ParserConfigurationException, TransformerException;

  /**
   * Function to calculate cost basis with dollar cost averaging.
   *
   * @param portfolioName the Portfolio name
   * @param date          the date
   * @param cache a local cache
   * @return the cost basis value
   * @throws ParseException the ParseException Exception to be thrown
   */
  float calculateDCAPortfolioCostBasis(String portfolioName, String date,
                                       Map<String, Map<String, Float>> cache)
          throws ParseException;
}
