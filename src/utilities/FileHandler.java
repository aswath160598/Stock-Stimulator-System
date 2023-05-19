package utilities;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.FlexiPortfolio;

/**
 * This Interface represents the file methods with respect to the Flexible Portfolio.
 */
public interface FileHandler {
  /**
   * Function is used to save a flexible portfolio as an xml file.
   *
   * @param fp            the flexible portfolio
   * @param portfolioName the portfolio name
   * @throws ParserConfigurationException the ParserConfigurationException to be thrown
   * @throws FileNotFoundException        the FileNotFoundException to be thrown
   * @throws TransformerException         the TransformerException to be thrown
   */
  void savePortfolio(FlexiPortfolio fp, String portfolioName) throws ParserConfigurationException,
          FileNotFoundException, TransformerException;

  /**
   * The function is used to load a Portfolio.
   *
   * @param portfolioName the flexible portfolio name
   * @return the flexible portfolio object after loading
   * @throws IllegalArgumentException     the IllegalArgumentException to be thrown
   * @throws ParserConfigurationException the ParserConfigurationException to be thrown
   * @throws IOException                  the IOException to be thrown
   * @throws SAXException                 the SAXException to be thrown
   */
  Map<String, List<List<String>>> loadPortfolio(String portfolioName) throws
          IllegalArgumentException, ParserConfigurationException, IOException, SAXException;

  void saveDCA(Map<String, List<String>> d, String portfolioName) throws
          ParserConfigurationException, FileNotFoundException, TransformerException;

  Map<String, List<String>> loadDCA(String portfolioName, String dcaFileName) throws
          ParserConfigurationException, IOException, SAXException;
}
