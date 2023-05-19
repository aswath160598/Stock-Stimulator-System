package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * This class implements the methods of the User interface where all User operations are performed
 * on a Portfolio.
 */
public class UserImpl implements User {
  private final Map<String, Portfolio> portfolios;

  public UserImpl() {
    this.portfolios = new HashMap<>();
  }

  @Override
  public void addNewPortfolio(Map<String, Float> p, String name) {
    portfolios.put(name, new PortfolioImpl(p));
  }

  @Override
  public int getNoOfPortfolios() {
    return portfolios.size();
  }

  @Override
  public Portfolio getPortfolio(String portfolioName) {
    return this.portfolios.get(portfolioName);
  }

  @Override
  public String toString() {
    return this.portfolios.toString();
  }

  @Override
  public Map<String, List<Float>> getPortfolioValue(Portfolio portfolio, String date)
          throws ParseException, IOException {
    Map<String, List<Float>> val = new HashMap<>();
    GetStockValueAPIImpl getVal = new GetStockValueAPIImpl();
    Map<String, Float> composition = portfolio.getComposition();
    float stockValue;
    for (Map.Entry<String, Float> mapElement : composition.entrySet()) {
      List<Float> fArray = new ArrayList<>();
      String key = mapElement.getKey();
      float noOfShares = mapElement.getValue();
      stockValue = getVal.getStockPrice(key, date);
      fArray.add(stockValue);
      fArray.add(noOfShares);
      fArray.add(stockValue * noOfShares);
      val.put(key, fArray);
    }
    return val;
  }

  @Override
  public float getPortfolioSumValue(Map<String, List<Float>> val) {
    List<Float> f;
    float sum = 0;
    for (Map.Entry<String, List<Float>> mapElement : val.entrySet()) {
      f = mapElement.getValue();
      sum = sum + f.get(2);
    }
    return sum;
  }

  @Override
  public void savePortfolio(String portfolioName) throws ParserConfigurationException,
          FileNotFoundException, TransformerException {
    Portfolio p = portfolios.get(portfolioName);
    Map<String, Float> h = p.getComposition();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("Portfolio");
    doc.appendChild(rootElement);
    int x = 0;
    for (Map.Entry<String, Float> mapElement : h.entrySet()) {
      String tickerSymbol = mapElement.getKey();
      float noOfShares = mapElement.getValue();
      Element stock = doc.createElement("Stock");
      rootElement.appendChild(stock);
      stock.setAttribute("id", String.valueOf(x));
      x++;
      Element ticker = doc.createElement("Ticker");
      ticker.setTextContent(tickerSymbol);
      stock.appendChild(ticker);

      Element noOfStocks = doc.createElement("NoOfStocks");
      noOfStocks.setTextContent(String.valueOf((int) noOfShares));
      stock.appendChild(noOfStocks);
    }
    FileOutputStream output = new FileOutputStream(portfolioName + ".xml");
    writeXml(doc, output);
  }

  private static void writeXml(Document doc, OutputStream output)
          throws TransformerException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);
    transformer.transform(source, result);
  }

  @Override
  public void loadPortfolio(String portfolioName) throws IllegalArgumentException,
          ParserConfigurationException, IOException, SAXException {
    TickerValidator tv = new TickerValidatorImpl("stock_master_file.csv");
    tv.readFile();
    Map<String, Float> h = new HashMap<>();
    File file = new File(portfolioName + ".xml");
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(file);
    doc.getDocumentElement().normalize();
    NodeList nodeList = doc.getElementsByTagName("Stock");
    for (int itr = 0; itr < nodeList.getLength(); itr++) {
      Node node = nodeList.item(itr);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element eElement = (Element) node;
        String ticker = eElement.getElementsByTagName("Ticker").item(0).getTextContent();
        String noOfStocks = eElement.getElementsByTagName("NoOfStocks")
                .item(0).getTextContent();
        if (tv.checkTicker(ticker)) {
          if (h.containsKey(ticker)) {
            try {
              h.put(ticker, h.get(ticker) + Integer.parseInt(noOfStocks));
            } catch (NumberFormatException e) {
              throw new ParserConfigurationException("Invalid no. of shares");
            }
          } else {
            try {
              h.put(ticker, (float) Integer.parseInt(noOfStocks));
            } catch (NumberFormatException e) {
              throw new ParserConfigurationException("Invalid no. of shares");
            }
          }
        } else {
          throw new IllegalArgumentException("Invalid ticker in file.");
        }
      }
    }
    addNewPortfolio(h, portfolioName);
  }

  @Override
  public Boolean checkPortfolioExists(String name) {
    return portfolios.containsKey(name);
  }

  @Override
  public Boolean checkPortfolioFileExists(String name) {
    Path path = Paths.get(name + ".xml");
    return Files.exists(path);
  }

  @Override
  public List<String> getAllPortfolioNames() {
    List<String> s = new ArrayList<>();
    for (Map.Entry<String, Portfolio> mapElement : portfolios.entrySet()) {
      s.add(mapElement.getKey());
    }
    return s;
  }
}
