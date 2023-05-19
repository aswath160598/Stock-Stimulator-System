import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import controller.FlexiController;
import controller.FlexiControllerImpl;
import model.FlexiUser;
import model.FlexiUserImpl;
import view.FlexiView;
import view.FlexiViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the Flexible Portfolio Controller class.
 */
public class FlexiControllerImplTest {

  private FlexiController fc;
  private InputStream in;
  private ByteArrayOutputStream bytes;
  private FlexiUser user;
  private FlexiView view;

  @Before
  public void setUp() {

    bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new FlexiViewImpl(out);

    user = new FlexiUserImpl();
  }

  @Test
  public void buyStockTest1() throws IOException {
    in = new ByteArrayInputStream("GOOG 2020-01-01 10 y AMZN 2022-01-01 20 n".getBytes());
    fc = new FlexiControllerImpl(user, view, in);

    fc.buyStock("p1");
    assertEquals("{GOOG=10.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2021, 1, 2)).toString());
  }

  @Test
  public void buyStockTest2() throws IOException {
    in = new ByteArrayInputStream(("GOOG 2020-01-01 10 y GOOG 2020-01-02 10 y "
            + "AMZN 2022-01-01 20 y AMZN 2022-01-02 20 n").getBytes());
    fc = new FlexiControllerImpl(user, view, in);

    fc.buyStock("p1");
    assertEquals("{GOOG=20.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2021, 1, 2)).toString());

    assertEquals("{GOOG=20.0, AMZN=40.0}",
            user.getPortfolio("p1")
                    .getComposition(LocalDate.of(2022, 2, 2)).toString());
  }

  @Test
  public void buyStockTest3() throws IOException {
    in = new ByteArrayInputStream(("GOOG 2020-01-01 10 y GOOG 2020-01-01 10 y "
            + "AMZN 2022-01-01 20 y AMZN 2022-01-01 20 n").getBytes());
    fc = new FlexiControllerImpl(user, view, in);

    fc.buyStock("p1");
    assertEquals("{GOOG=20.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2021, 1, 2)).toString());

    assertEquals("{GOOG=20.0, AMZN=40.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2022, 2, 2)).toString());
  }

  @Test
  public void buyStockTest4() throws IOException {
    // addition to existing portfolio

    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2020-09-01");
    val_string.add("b");
    val_string.add("5");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2020-08-01");
    val_string.add("b");
    val_string.add("12");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2020-08-05");
    val_string.add("s");
    val_string.add("3");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);

    // creating portfolio p1 for the user
    user.addNewFlexiPortfolio(h, "p1");

    in = new ByteArrayInputStream("MSFT 2020-01-01 10 y AMZN 2022-01-01 20 n".getBytes());

    // user already has a portfolio called p1
    fc = new FlexiControllerImpl(user, view, in);

    fc.buyStock("p1");

    assertEquals("{MSFT=10.0, GOOG=14.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2021, 1, 2)).toString());

    assertEquals("{MSFT=10.0, GOOG=14.0, AMZN=20.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2022, 1, 2)).toString());
  }

  @Test
  public void sellStockTest1() throws IOException, ParseException,
          ParserConfigurationException, TransformerException, SAXException {
    // creation of new portfolio with 2 stocks and sell 2 of each stock

    in = new ByteArrayInputStream(("2 1 p1 GOOG 2021-03-01 10 y AMZN 2021-01-01 20 n "
            + " 2 p1 GOOG 2021-01-02 2 y AMZN 2021-01-02 2 n 0 0").getBytes());

    fc = new FlexiControllerImpl(user, view, in);
    fc.showPortfolioTypeSelection();
    assertEquals("Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker of stock to be sold\n" +
            "Enter the sell date in format yyyy-mm-dd.\n" +
            "Stock cannot be sold.\n" +
            "Do you want to sell another stock?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Invalid user input entered.\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Invalid user input entered.\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Invalid user input entered.\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Portfolio not found.\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Portfolio p1 saved.\n" +
            "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n", bytes.toString());
  }

  @Test
  public void sellStockTest2() throws IOException {
    // creation of new portfolio with 2 stocks and
    // sell 2 of each stock in two different transactions

    in = new ByteArrayInputStream(("GOOG 2020-01-01 10 y AMZN 2021-01-01 20 n "
            + "GOOG 2021-01-01 2 y GOOG 2021-01-01 2 y AMZN 2021-01-02 2 n").getBytes());
    fc = new FlexiControllerImpl(user, view, in);

    fc.buyStock("p1");
    assertEquals("{GOOG=10.0, AMZN=20.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2022, 1, 2)).toString());

    fc.sellStock("p1");

    assertEquals("{GOOG=6.0, AMZN=18.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2022, 1, 2)).toString());
  }

  @Test
  public void sellStockTest3() throws IOException {
    // creation of new portfolio with 2 stocks and sell on buy day

    in = new ByteArrayInputStream(("GOOG 2020-01-01 10 y AMZN 2021-01-01 20 n "
            + "GOOG 2020-01-01 2 y AMZN 2021-01-01 2 n").getBytes());
    fc = new FlexiControllerImpl(user, view, in);

    fc.buyStock("p1");
    assertEquals("{GOOG=10.0, AMZN=20.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2022, 1, 2)).toString());

    fc.sellStock("p1");

    assertEquals("{GOOG=8.0, AMZN=18.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2022, 1, 2)).toString());
  }

  @Test
  public void sellStockTest4() throws IOException {
    // creation of new portfolio with 2 stocks and sell 2 of each stock

    in = new ByteArrayInputStream(("GOOG 2021-03-01 10 y AMZN 2021-01-01 20 n "
            + "GOOG 2021-01-02 2 y AMZN 2021-01-02 12 y AMZN 2021-01-01 9 n").getBytes());
    fc = new FlexiControllerImpl(user, view, in);

    fc.buyStock("p1");
    assertEquals("{GOOG=10.0, AMZN=20.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2022, 1, 2)).toString());

    fc.sellStock("p1");

    assertEquals("{GOOG=10.0, AMZN=20.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2022, 1, 2)).toString());
  }

  @Test
  public void sellStockTest5() throws ParseException, IOException, ParserConfigurationException,
          TransformerException, SAXException {
    // creation of new portfolio with 2 stocks and sell 2 of each stock

    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 5 y goog 2020-02-02 7 n "
            + "2 p1 goog 2020-02-05 8 y goog 2020-02-04 5 n 3 p1 2021-1-1 0 0").getBytes());
    fc = new FlexiControllerImpl(user, view, in);

    String message = "Please select an option:\n" +
            "1. Operate on an inflexible portfolio\n" +
            "2. Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. View a Portfolio\n" +
            "4. Save a Portfolio\n" +
            "5. Load a Portfolio\n" +
            "6. Determine the cost basis\n" +
            "7. Determine the value\n" +
            "8. Plot portfolio performance\n" +
            "Enter Portfolio name\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. View a Portfolio\n" +
            "4. Save a Portfolio\n" +
            "5. Load a Portfolio\n" +
            "6. Determine the cost basis\n" +
            "7. Determine the value\n" +
            "8. Plot portfolio performance\n" +
            "Enter Portfolio name\n" +
            "Enter ticker of stock to be sold\n" +
            "Enter the sell date in format yyyy-mm-dd.\n" +
            "Enter no. of shares to be sold.\n" +
            "STOCK SOLD!\n" +
            "Do you want to sell another stock?\n" +
            "Enter ticker of stock to be sold\n" +
            "Enter the sell date in format yyyy-mm-dd.\n" +
            "Enter no. of shares to be sold.\n" +
            "Stock cannot be sold.\n" +
            "Do you want to sell another stock?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. View a Portfolio\n" +
            "4. Save a Portfolio\n" +
            "5. Load a Portfolio\n" +
            "6. Determine the cost basis\n" +
            "7. Determine the value\n" +
            "8. Plot portfolio performance\n" +
            "Enter Portfolio name\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "goog 4\n" +
            "\n" +
            "Please Enter select option\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. View a Portfolio\n" +
            "4. Save a Portfolio\n" +
            "5. Load a Portfolio\n" +
            "6. Determine the cost basis\n" +
            "7. Determine the value\n" +
            "8. Plot portfolio performance\n" +
            "Please select an option:\n" +
            "1. Operate on an inflexible portfolio\n" +
            "2. Operate on a flexible portfolio\n";

    fc.showPortfolioTypeSelection();

    assertEquals(message, bytes.toString());
  }

  @Test
  public void sellMoreStockThanAvailableTest1() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 5 n "
            + "2 p1 goog 2020-02-05 8 n 0 0").getBytes());
    fc = new FlexiControllerImpl(user, view, in);

    String message = "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker of stock to be sold\n" +
            "Enter the sell date in format yyyy-mm-dd.\n" +
            "Enter no. of shares to be sold.\n" +
            "Stock cannot be sold.\n" +
            "Do you want to sell another stock?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Portfolio p1 saved.\n" +
            "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n";

    fc.showPortfolioTypeSelection();

    assertEquals(message, bytes.toString());
  }

  @Test
  public void invalidDateTest1() throws ParseException, IOException, ParserConfigurationException,
          TransformerException, SAXException {
    in = new ByteArrayInputStream(("2 1 p1 goog 2020/01/01 5 n 0 0").getBytes());

    fc = new FlexiControllerImpl(user, view, in);

    fc.showPortfolioTypeSelection();
    assertEquals("Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "Invalid date format.\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n", bytes.toString());

  }

  @Test
  public void invalidSellDateTest1() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 5 n "
            + "2 p1 goog 2019-02-05 1 n 0 0").getBytes());

    fc = new FlexiControllerImpl(user, view, in);

    fc.showPortfolioTypeSelection();
    String message = "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker of stock to be sold\n" +
            "Enter the sell date in format yyyy-mm-dd.\n" +
            "Stock cannot be sold.\n" +
            "Do you want to sell another stock?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Invalid user input entered.\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Portfolio p1 saved.\n" +
            "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n";
    assertEquals(message, bytes.toString());
  }

  @Test
  public void invalidBuyDateTest1() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    in = new ByteArrayInputStream(("2 1 p1 goog 2030-01-01 5 m 0 0").getBytes());

    fc = new FlexiControllerImpl(user, view, in);

    fc.showPortfolioTypeSelection();
    String message = "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "Date cannot be in the future.\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n";
    assertEquals(message, bytes.toString());
  }

  @Test
  public void invalidBuyDateTestChristmas() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    in = new ByteArrayInputStream(("2 1 p1 goog 2021-12-25 5 n 0 0").getBytes());

    fc = new FlexiControllerImpl(user, view, in);

    fc.showPortfolioTypeSelection();
    String message = "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "Date cannot be in the future.\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n";
    assertEquals(message, bytes.toString());
  }

  @Test
  public void loadShowPortfolioTest1() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {

    in = new ByteArrayInputStream(("2 5 p1 3 p1 2022-01-01 0 0").getBytes());

    fc = new FlexiControllerImpl(user, view, in);
    fc.showPortfolioTypeSelection();

    assertEquals("Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Portfolio p1 successfully loaded.\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "GOOG 10\n" +
            "AMZN 20\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Portfolio p1 saved.\n" +
            "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n", bytes.toString());
  }

  @Test
  public void costBasis() throws ParseException, IOException, ParserConfigurationException,
          TransformerException, SAXException {
    // creation of new portfolio with 2 stocks and sell 2 of each stock

    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 5 y goog 2020-02-02 7 n "
            + "2 p1 goog 2020-02-05 8 n 6 p1 2021-1-1 1 0 0").getBytes());
    fc = new FlexiControllerImpl(user, view, in);

    String message = "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker of stock to be sold\n" +
            "Enter the sell date in format yyyy-mm-dd.\n" +
            "Enter no. of shares to be sold.\n" +
            "STOCK SOLD!\n" +
            "Do you want to sell another stock?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter the commission fee\n" +
            "Cost Basis of portfolio p1 is $16725.71\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Portfolio p1 saved.\n" +
            "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio";

    fc.showPortfolioTypeSelection();

    assertEquals(message, bytes.toString().trim());
  }

  @Test
  public void graphPlot() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-1-1 7 y goog 2020-1-2 12 y amzn " +
            "2020-1-3 3 n 8 p1 2020-1-1 2020-1-29 0 0").getBytes());
    fc = new FlexiControllerImpl(user, view, in);
    fc.showPortfolioTypeSelection();
    String output = bytes.toString();
    String message = "Enter Portfolio name\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter Portfolio name\n" +
            "Enter the start date and the end date between which you want the graph: \n" +
            "Performance of portfolio p1  2020-1-1  2020-1-12\n" +
            "2020-01-01  **************\n" +
            "2020-01-02  ***************************************\n" +
            "2020-01-03  ***********************************************\n" +
            "2020-01-04  ***********************************************\n" +
            "2020-01-05  ***********************************************\n" +
            "2020-01-06  *************************************************\n" +
            "2020-01-07  *************************************************\n" +
            "2020-01-08  *************************************************\n" +
            "2020-01-09  *************************************************\n" +
            "2020-01-10  *************************************************\n" +
            "2020-01-11  *************************************************\n" +
            "2020-01-12  *************************************************\n" +
            "Scale: * = $657.0";
    assertEquals(message, output);
  }

  @Test
  public void negativeCommissionTest1() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 7 n " +
            "6 p1 2021-01-01 -6 0 0").getBytes());
    fc = new FlexiControllerImpl(user, view, in);
    fc.showPortfolioTypeSelection();
    String output = bytes.toString();
    String message = "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Enter Portfolio name\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter the commission fee\n" +
            "Commission fees cannot be less than or equal to zero\n" +
            "\n" +
            "Please Enter select option\n" +
            "1.)  Buy a stock\n" +
            "2.)  Sell a stock\n" +
            "3.)  View a Portfolio\n" +
            "4.)  Save a Portfolio\n" +
            "5.)  Load a Portfolio\n" +
            "6.)  Determine the cost basis\n" +
            "7.)  Determine the value\n" +
            "8.)  Plot portfolio performance\n" +
            "9.)  Show all portfolios\n" +
            "0.)  Go to previous menu\n" +
            "Portfolio p1 saved.\n" +
            "Please select an option:\n" +
            "1.) Operate on an inflexible portfolio\n" +
            "2.) Operate on a flexible portfolio\n";
    assertEquals(message, output);
  }

  @Test
  public void graphPlot1() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-1-1 7 y goog 2020-1-2 12 y " +
            "amzn 2020-1-1 3 n 8 p1 2020-1-1 2020-2-5 0 0").getBytes());
    fc = new FlexiControllerImpl(user, view, in);
    fc.showPortfolioTypeSelection();
    String output = bytes.toString();
    String message = "Enter Portfolio name\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter Portfolio name\n" +
            "Enter the start date and the end date between which you want the graph: \n" +
            "Performance of portfolio p1  2020-1-1  2020-1-12\n" +
            "2020-01-01  **************\n" +
            "2020-01-02  ***************************************\n" +
            "2020-01-03  ***********************************************\n" +
            "2020-01-04  ***********************************************\n" +
            "2020-01-05  ***********************************************\n" +
            "2020-01-06  *************************************************\n" +
            "2020-01-07  *************************************************\n" +
            "2020-01-08  *************************************************\n" +
            "2020-01-09  *************************************************\n" +
            "2020-01-10  *************************************************\n" +
            "2020-01-11  *************************************************\n" +
            "2020-01-12  *************************************************\n" +
            "Scale: * = $657.0";
    assertEquals(message, output);
  }

  @Test
  public void plotBeforeFirstPurchaseWithGraph() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    in = new ByteArrayInputStream(("2 1 p1 msft 2020-1-1 10 y msft 2020-1-1 20 y " +
            "amzn 2020-1-1 30 n " +
            "8 p1 2019-12-30 2021-4-25 0 0").getBytes());
    fc = new FlexiControllerImpl(user, view, in);
    fc.showPortfolioTypeSelection();

    String message = "Please select an option:\n" +
            "1. Operate on an inflexible portfolio\n" +
            "2. Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. View a Portfolio\n" +
            "4. Save a Portfolio\n" +
            "5. Load a Portfolio\n" +
            "6. Determine the cost basis\n" +
            "7. Determine the value\n" +
            "8. Plot portfolio performance\n" +
            "Enter Portfolio name\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. View a Portfolio\n" +
            "4. Save a Portfolio\n" +
            "5. Load a Portfolio\n" +
            "6. Determine the cost basis\n" +
            "7. Determine the value\n" +
            "8. Plot portfolio performance\n" +
            "Enter Portfolio name\n" +
            "Enter the start date for the plot\n" +
            "Enter the end date for the plot\n" +
            "\n" +
            "Performance of portfolio p1 from 2019-12-30 to 2021-4-25\n" +
            "\n" +
            "DEC 2019  \n" +
            "JAN 2020  *****************************\n" +
            "FEB 2020  ***************************\n" +
            "MAR 2020  ****************************\n" +
            "APR 2020  ************************************\n" +
            "MAY 2020  ***********************************\n" +
            "JUN 2020  ****************************************\n" +
            "JUL 2020  *********************************************\n" +
            "AUG 2020  *************************************************\n" +
            "SEP 2020  *********************************************\n" +
            "OCT 2020  ********************************************\n" +
            "NOV 2020  *********************************************\n" +
            "DEC 2020  ***********************************************\n" +
            "JAN 2021  **********************************************\n" +
            "FEB 2021  *********************************************\n" +
            "MAR 2021  *********************************************\n" +
            "APR 2021  ************************************************\n" +
            "\n" +
            "Scale: * = $2206.0\n" +
            "\n" +
            "Please Enter select option\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. View a Portfolio\n" +
            "4. Save a Portfolio\n" +
            "5. Load a Portfolio\n" +
            "6. Determine the cost basis\n" +
            "7. Determine the value\n" +
            "8. Plot portfolio performance\n" +
            "Please select an option:\n" +
            "1. Operate on an inflexible portfolio\n" +
            "2. Operate on a flexible portfolio";
    String output = bytes.toString();

    assertEquals(message, output.trim());
  }

  @Test
  public void sellStockTest6() {
    // creation of new portfolio with 2 stocks and sell 2 of each stock

    in = new ByteArrayInputStream(("2 1 p1 aapl 2020-01-01 5 y aapl 2020-02-02 7 n "
            + "2 p1 aapl 2020-02-05 8 y aapl 2020-02-04 5 n 8 " +
            "p1 2012-12-30 2021-4-25 0 0").getBytes());
    fc = new FlexiControllerImpl(user, view, in);

    String message = "";

    String output = bytes.toString();

    assertEquals(message, output.trim());
  }

  @Test
  public void sellStockTest7() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    // creation of new portfolio with 2 stocks and sell 2 of each stock

    in = new ByteArrayInputStream(("2 1 p1 aapl 2020-01-01 10 y msft 2021-1-1 34 n "
            + "2 p1 msft 2021-6-6 30 n 8 p1 2010-1-1 2022-10-30 0 0").getBytes());
    fc = new FlexiControllerImpl(user, view, in);

    String message = "Please select an option:\n" +
            "1. Operate on an inflexible portfolio\n" +
            "2. Operate on a flexible portfolio\n" +
            "\n" +
            "Please Enter select option\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. View a Portfolio\n" +
            "4. Save a Portfolio\n" +
            "5. Load a Portfolio\n" +
            "6. Determine the cost basis\n" +
            "7. Determine the value\n" +
            "8. Plot portfolio performance\n" +
            "Enter Portfolio name\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "Enter ticker\n" +
            "Enter the date in format yyyy-mm-dd.\n" +
            "Enter number of shares\n" +
            "Do you want to continue adding Stocks?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. View a Portfolio\n" +
            "4. Save a Portfolio\n" +
            "5. Load a Portfolio\n" +
            "6. Determine the cost basis\n" +
            "7. Determine the value\n" +
            "8. Plot portfolio performance\n" +
            "Enter Portfolio name\n" +
            "Enter ticker of stock to be sold\n" +
            "Enter the sell date in format yyyy-mm-dd.\n" +
            "Enter no. of shares to be sold.\n" +
            "STOCK SOLD!\n" +
            "Do you want to sell another stock?\n" +
            "\n" +
            "Please Enter select option\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. View a Portfolio\n" +
            "4. Save a Portfolio\n" +
            "5. Load a Portfolio\n" +
            "6. Determine the cost basis\n" +
            "7. Determine the value\n" +
            "8. Plot portfolio performance\n" +
            "Enter Portfolio name\n" +
            "Enter the start date for the plot\n" +
            "Enter the end date for the plot\n" +
            "\n" +
            "Performance of portfolio p1 from 2010-1-1 to 2022-10-30\n" +
            "\n" +
            "2010-12-31  \n" +
            "2011-12-31  \n" +
            "2012-12-31  \n" +
            "2012-12-31  \n" +
            "2013-12-31  \n" +
            "2014-12-31  \n" +
            "2015-12-31  \n" +
            "2016-12-31  \n" +
            "2017-12-31  \n" +
            "2018-12-31  \n" +
            "2019-12-31  \n" +
            "2020-12-31  *********************\n" +
            "2021-12-31  *************************************************\n" +
            "2022-10-30  ***************************************\n" +
            "\n" +
            "Scale: * = $63.0\n" +
            "\n" +
            "Please Enter select option\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. View a Portfolio\n" +
            "4. Save a Portfolio\n" +
            "5. Load a Portfolio\n" +
            "6. Determine the cost basis\n" +
            "7. Determine the value\n" +
            "8. Plot portfolio performance\n" +
            "Please select an option:\n" +
            "1. Operate on an inflexible portfolio\n" +
            "2. Operate on a flexible portfolio";

    fc.showPortfolioTypeSelection();
    String output = bytes.toString();
    assertEquals(message, output.trim());
  }
}

