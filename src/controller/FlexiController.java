package controller;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This Interface represents the Flexible Controller Module of our Portfolio Project.
 */
public interface FlexiController extends Controller {

  /**
   * Function to show the type of Portfolio to be selected.
   */
  void showPortfolioTypeSelection();

  /**
   * Function to show flexible operations.
   *
   * @throws ParseException               the ParseException to be thrown.
   * @throws IOException                  the IOException to be thrown.
   * @throws ParserConfigurationException the ParserConfigurationException to be thrown.
   * @throws TransformerException         the TransformerException to be thrown.
   * @throws SAXException                 the SAXException to be thrown.
   */
  void flexibleOperations() throws ParseException, IOException, ParserConfigurationException,
          TransformerException, SAXException;

  /**
   * Function to buy stock in a flexible Portfolio.
   *
   * @param portfolioName the portfolio name
   * @throws FileNotFoundException the FileNotFoundException to be thrown.
   */
  void buyStock(String portfolioName) throws IOException;

  /**
   * Function to sell Stock in a flexible Portfolio.
   *
   * @param portfolioName the Portfolio Name
   */
  void sellStock(String portfolioName);
}
