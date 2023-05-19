package utilities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.DollarCostAveraging;
import model.FlexiPortfolio;
import model.FlexiPortfolioImpl;
import model.FlexiStocks;
import model.FlexiStocksImpl;
import model.TickerValidator;
import model.TickerValidatorImpl;

/**
 * This class implements all the methods of the file handler interface which consist of saving a
 * file, loading a file.
 */
public class XMLFileHandler implements FileHandler {

  @Override
  public void savePortfolio(FlexiPortfolio fp, String portfolioName) throws
          ParserConfigurationException, FileNotFoundException, TransformerException {
    FlexiStocks fStocks;
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("Portfolio");
    rootElement.setAttribute("type", "Flexible");
    doc.appendChild(rootElement);
    int x = 0;


    List<FlexiStocks> stockList = new ArrayList<>();
    for (int i = 0; i < fp.size(); i++) {
      stockList.add(fp.getAt(i));
    }

    Comparator<FlexiStocks> comparatorAsc = Comparator.comparing(FlexiStocks::getBuyDate);
    stockList.sort(comparatorAsc);


    for (int i = 0; i < fp.size(); i++) {
      fStocks = stockList.get(i);
      String tickerSymbol = fStocks.getTicker();
      float noOfShares = fStocks.getNoOfShares();
      String date = fStocks.getBuyDate().toString();
      String s = fStocks.getStatus();
      float commissionFee = fStocks.getCommissionFee();
      Element stock = doc.createElement("Stock");
      rootElement.appendChild(stock);
      stock.setAttribute("id", String.valueOf(x));
      x++;
      Element ticker = doc.createElement("Ticker");
      ticker.setTextContent(tickerSymbol);
      stock.appendChild(ticker);

      Element noOfStocks = doc.createElement("NoOfStocks");
      noOfStocks.setTextContent(String.valueOf(noOfShares));
      stock.appendChild(noOfStocks);

      Element stockDate = doc.createElement("TransactionDate");
      stockDate.setTextContent(date);
      stock.appendChild(stockDate);

      Element status = doc.createElement("Status");
      status.setTextContent(s);
      stock.appendChild(status);

      Element commissionFee1 = doc.createElement("CommissionFee");
      commissionFee1.setTextContent(String.valueOf(commissionFee));
      stock.appendChild(commissionFee1);
    }
    File theDir = new File(portfolioName);
    if (!theDir.exists()) {
      theDir.mkdirs();
    }
    FileOutputStream output = new FileOutputStream(portfolioName + "/" + portfolioName +
            "_flexible" + ".xml");
    writeXml(doc, output);
  }

  private void writeXml(Document doc, OutputStream output)
          throws TransformerException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);
    transformer.transform(source, result);
  }

  @Override
  public Map<String, List<List<String>>> loadPortfolio(String portfolioName) throws
          IllegalArgumentException, ParserConfigurationException, IOException, SAXException {
    TickerValidator tv = new TickerValidatorImpl("stock_master_file.csv");
    tv.readFile();
    Map<String, List<List<String>>> h = new HashMap<>();
    File file = new File(portfolioName + "/" + portfolioName + "_flexible" + ".xml");
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(file);
    doc.getDocumentElement().normalize();
    NodeList nodeList = doc.getElementsByTagName("Stock");

    FlexiPortfolio p = new FlexiPortfolioImpl();
    FlexiStocksImpl s;
    String[] bd;
    String[] fileList;
    for (int itr = 0; itr < nodeList.getLength(); itr++) {
      Node node = nodeList.item(itr);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element eElement = (Element) node;
        String ticker = eElement.getElementsByTagName("Ticker").item(0).getTextContent();
        String noOfStocks = eElement.getElementsByTagName("NoOfStocks")
                .item(0).getTextContent();
        String dateTransaction = eElement.getElementsByTagName("TransactionDate")
                .item(0).getTextContent();
        String status = eElement.getElementsByTagName("Status")
                .item(0).getTextContent();
        String commissionFee = eElement.getElementsByTagName("CommissionFee")
                .item(0).getTextContent();

        if (!(status.equals("b") || status.equals("s"))) {
          throw new IllegalArgumentException("Invalid transaction status in portfolio file.");
        }


        try {
          float nos = Float.parseFloat(noOfStocks);
        } catch (NumberFormatException e) {
          throw new ParserConfigurationException("Invalid no. of shares in file.");
        }


        List<String> val_string = new ArrayList<>();
        val_string.add(dateTransaction);
        val_string.add(status);
        val_string.add(noOfStocks);
        val_string.add(commissionFee);

        if (!tv.checkTicker(ticker)) {
          throw new IllegalArgumentException("Invalid ticker in file.");
        }

        bd = dateTransaction.split("-");
        LocalDate bdDate = LocalDate.of(Integer.parseInt(bd[0]),
                Integer.parseInt(bd[1]), Integer.parseInt(bd[2]));
        s = new FlexiStocksImpl(Float.parseFloat(noOfStocks), 0, ticker, bdDate, status,
                Float.parseFloat(commissionFee));

        if (status.equals("b")) {
          p.addStock(s);
        } else {
          if (!p.validateSell(bdDate, ticker, Float.parseFloat(noOfStocks))) {
            throw new IllegalArgumentException("Invalid transactions in portfolio "
                    + portfolioName + " file.");
          }
        }

        if (!h.containsKey(ticker)) {
          List<List<String>> s1 = new ArrayList<>();
          s1.add(val_string);
          h.put(ticker, s1);
        } else {
          h.get(ticker).add(val_string);
        }
      }
    }
    return h;
  }

  @Override
  public void saveDCA(Map<String, List<String>> d, String portfolioName) throws
          ParserConfigurationException, FileNotFoundException, TransformerException {
    FlexiStocks fStocks;
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    String planNameg = "";
    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("DollarCostAveraging");
    doc.appendChild(rootElement);

    Map<String, Float> proportionMap = new HashMap<>();
    List<DollarCostAveraging> listDollarCostAveraging = new ArrayList<>();
    for (Map.Entry<String, List<String>> mapElement : d.entrySet()) {
      String planName = mapElement.getKey();
      planNameg = mapElement.getKey();
      List<String> dollarList = mapElement.getValue();
      String sDate = dollarList.get(0);
      String eDate = dollarList.get(1);
      float totalInvestment = Float.parseFloat(dollarList.get(2));
      int interval = Integer.parseInt(dollarList.get(3));
      String prop = dollarList.get(4);
      String commissionFee = dollarList.get(5);
      Element plan = doc.createElement("PlanName");
      rootElement.appendChild(plan);
      plan.setAttribute("id", planName);

      Element starDate = doc.createElement("StartDate");
      starDate.setTextContent(sDate);
      plan.appendChild(starDate);

      Element endDate = doc.createElement("EndDate");
      endDate.setTextContent((eDate));
      plan.appendChild(endDate);

      Element totInvestment = doc.createElement("TotalInvestment");
      totInvestment.setTextContent(String.valueOf(totalInvestment));
      plan.appendChild(totInvestment);

      Element interv = doc.createElement("Interval");
      interv.setTextContent(String.valueOf(interval));
      plan.appendChild(interv);


      Element propor = doc.createElement("Proportion");
      propor.setTextContent(prop);
      plan.appendChild(propor);

      Element comFee = doc.createElement("CommissionFee");
      comFee.setTextContent(commissionFee);
      plan.appendChild(comFee);
    }
    File theDir = new File(portfolioName);
    if (!theDir.exists()) {
      theDir.mkdirs();
    }
    FileOutputStream output = new FileOutputStream(portfolioName + "/" + "DCA" +
            planNameg + ".xml");
    writeXml(doc, output);
  }

  @Override
  public Map<String, List<String>> loadDCA(String portfolioName, String dcaFileName)
          throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Map<String, List<String>> h = new HashMap<>();
    List<String> li = new ArrayList<String>();
    File file = new File(portfolioName + "/" + dcaFileName);
    Document doc = db.parse(file);
    doc.getDocumentElement().normalize();
    NodeList nodeList = doc.getElementsByTagName("PlanName");
    for (int itr = 0; itr < nodeList.getLength(); itr++) {
      Node node = nodeList.item(itr);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element eElement = (Element) node;
        String startDate = eElement.getElementsByTagName("StartDate")
                .item(0).getTextContent();
        String endDate = eElement.getElementsByTagName("EndDate")
                .item(0).getTextContent();
        String totalInvestment = eElement.getElementsByTagName("TotalInvestment")
                .item(0).getTextContent();
        String interval = eElement.getElementsByTagName("Interval")
                .item(0).getTextContent();
        String proportion = eElement.getElementsByTagName("Proportion")
                .item(0).getTextContent();
        String commissionFee = eElement.getElementsByTagName("CommissionFee")
                .item(0).getTextContent();
        li.add(startDate);
        li.add(endDate);
        li.add(totalInvestment);
        li.add(interval);
        li.add(proportion);
        li.add(commissionFee);
      }
      h.put(dcaFileName.substring(3, 5), li);
    }
    return h;
  }
}
