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
 * This represents the user interface of our Model.
 */
public interface User {
  /**
   * The function to add a new portfolio.
   *
   * @param p    portfolio object to be added.
   * @param name The portfolio name.
   */
  void addNewPortfolio(Map<String, Float> p, String name);

  /**
   * This returns the total number of Portfolios.
   *
   * @return the total number of portfolios.
   */
  int getNoOfPortfolios();

  /**
   * Gets the Portfolio object with the name of the Portfolio.
   *
   * @param portfolioName the Portfolio name.
   * @return the corresponding portfolio object.
   */
  Portfolio getPortfolio(String portfolioName);

  /**
   * The function to get the Portfolio values.
   *
   * @param portfolio the portfolio whose value is to be computed.
   * @param date      the date on which the value has to be calculated.
   * @return the calculated Portfolio values.
   * @throws ParseException This exception throws when errors in Parsing is found.
   * @throws IOException    throws the error if any IO error occurs.
   */
  Map<String, List<Float>> getPortfolioValue(Portfolio portfolio, String date)
          throws ParseException, IOException;

  /**
   * Function to save a Portfolio.
   *
   * @param portfolioName the Portfolio name.
   * @throws ParserConfigurationException This exception throws when errors in Parsing is found.
   * @throws FileNotFoundException        This exception throws when file is not found.
   * @throws TransformerException         This exception throws when errors in transformations.
   */
  void savePortfolio(String portfolioName)
          throws ParserConfigurationException, FileNotFoundException, TransformerException;

  /**
   * Function to load a Portfolio.
   *
   * @param portfolioName the Portfolio name.
   * @throws ParserConfigurationException This exception throws when errors in Parsing is found.
   * @throws IOException                  throws the error if any IO error occurs.
   * @throws SAXException                 throws the error if any SAX error occurs.
   */
  void loadPortfolio(String portfolioName)
          throws ParserConfigurationException, IOException, SAXException;

  /**
   * Function to check if a Portfolio exists.
   *
   * @param name the Portfolio name.
   * @return true if the portfolio exists else returns false.
   */
  Boolean checkPortfolioExists(String name);

  /**
   * Function to check if a Portfolio file exists.
   *
   * @param name the Portfolio name.
   * @return true if the files exists else returns false.
   */
  Boolean checkPortfolioFileExists(String name);

  /**
   * Function to get all Portfolio names.
   *
   * @return list of portfolio names.
   */
  List<String> getAllPortfolioNames();

  float getPortfolioSumValue(Map<String, List<Float>> val);
}
