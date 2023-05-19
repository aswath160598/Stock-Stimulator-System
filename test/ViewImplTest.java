import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import controller.Controller;
import controller.ControllerImpl;
import model.Portfolio;
import model.PortfolioImpl;
import model.User;
import model.UserImpl;
import view.View;
import view.ViewImpl;

/**
 * A JUnit test class for the ViewImpl class.
 */
public class ViewImplTest {

  private View view;
  private ByteArrayOutputStream bytes;
  private ByteArrayInputStream in;
  private StringBuilder log;

  @Before
  public void setUp() {
    bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new ViewImpl(out);
  }

  @Test
  public void welcomeMessageTest() {
    String message = "Hi, Welcome to your portfolio\n";
    view.welcomeMessage();
    String output = bytes.toString();
    assertEquals(message, output);
  }

  @Test
  public void showMainMenuTest() {
    String message = "\nPlease Enter your option\n" +
            "1.) Add a new Portfolio\n" +
            "2.) View a Portfolio\n" +
            "3.) Show the total value of a Portfolio\n" +
            "4.) Save a Portfolio\n" +
            "5.) Load a Portfolio\n" +
            "6.) Exit the program\n";
    view.showMainMenu();
    assertEquals(message, bytes.toString());
  }

  @Test
  public void askPortfolioNameTest() {
    String message = "Enter Portfolio name\n";
    view.askPortfolioName();
    assertEquals(message, bytes.toString());
  }

  @Test
  public void portfolioExistsTest() {
    String message = "Portfolio already exists. Please enter a new name.\n";
    view.portfolioExists();
    assertEquals(message, bytes.toString());
  }

  @Test
  public void invalidPortfolioNameTest() {
    String message = "Enter a valid Portfolio Name\n";
    view.invalidPortfolioName();
    assertEquals(message, bytes.toString());
  }

  @Test
  public void invalidPortfolioNameTest1() {
    String message = "Enter a valid Portfolio Name\n";
    view.invalidPortfolioName();
    assertEquals(message, bytes.toString());
  }

  @Test
  public void enterTickerNOSTest() {
    String message = "Enter Stock ticker and number of shares\n";
    view.enterTickerNOS();
    assertEquals(message, bytes.toString());
  }

  @Test
  public void continueAddingStocksTest() {
    String message = "Do you want to continue adding Stocks?\n";
    view.continueAddingStocks();
    assertEquals(message, bytes.toString());
  }

  @Test
  public void invalidNOSTest() {
    String message = "Enter valid number of shares\n";
    view.invalidNOS();
    assertEquals(message, bytes.toString());
  }

  @Test
  public void invalidTickerTest() {
    String message = "Invalid ticker entered\n";
    view.invalidTicker();
    assertEquals(message, bytes.toString());
  }

  @Test
  public void printPortfolioTest1() {
    Map<String, Float> portfolio = new HashMap<>();
    portfolio.put("GOOG", 10F);
    portfolio.put("AMZN", 20F);
    Portfolio p = new PortfolioImpl(portfolio);
    String message = p.toString() + "\n";
    view.printPortfolio(p);
    assertEquals(message, bytes.toString());
  }

  @Test
  public void printPortfolioTest2() {
    Map<String, Float> portfolio = new HashMap<>();
    Portfolio p = new PortfolioImpl(portfolio);
    String message = p.toString() + "\n";
    view.printPortfolio(p);
    assertEquals(message, bytes.toString());
  }

  @Test
  public void printPortfolioTest3() {
    Map<String, Float> portfolio = new HashMap<>();
    portfolio.put("GOOG", 10F);
    Portfolio p = new PortfolioImpl(portfolio);
    String message = p.toString() + "\n";
    view.printPortfolio(p);
    assertEquals(message, bytes.toString());
  }

  @Test
  public void enterDateTest() {
    String message = "Enter the date in format yyyy-mm-dd.\n";
    view.enterDate();
    assertEquals(message, bytes.toString());
  }

  @Test
  public void printTotalValueTest() {
    String message = "The Total Value is : 100.0\n";
    view.printTotalValue(100);
    assertEquals(message, bytes.toString());
  }

  @Test
  public void printMapElementTest1() {
    Map<String, List<Float>> map = new HashMap<>();
    List<Float> f = new ArrayList<>();
    f.add(10.0F);
    f.add(20.0F);
    map.put("a", f);

    for (Map.Entry<String, List<Float>> m : map.entrySet()) {
      String message = m.getKey() + " " + m.getValue() + "\n";
      view.printMapElement(m);
      assertEquals(message, bytes.toString());
    }
  }

  @Test
  public void printMapElementTest2() {
    Map<String, List<Float>> map = new HashMap<>();
    List<Float> f = new ArrayList<>();
    f.add(10.0F);
    f.add(20.0F);
    map.put("a", f);

    String message = "";

    for (Map.Entry<String, List<Float>> m : map.entrySet()) {
      message += m.getKey() + " " + m.getValue() + "\n";
      view.printMapElement(m);
      assertEquals(message, bytes.toString());
    }

    f.add(30.0F);
    f.add(40.0F);
    map.put("b", f);

    for (Map.Entry<String, List<Float>> m : map.entrySet()) {
      message += m.getKey() + " " + m.getValue() + "\n";
      view.printMapElement(m);
      assertEquals(message, bytes.toString());
    }
  }

  @Test
  public void showPortfolioValueHeadingTest() {
    String message = "\nTicker [unit_Stock_Price, no_Of_Shares, stock_Value]\n";
    view.showPortfolioValueHeading();
    assertEquals(message, bytes.toString());
  }

  @Test
  public void welcomeMessageMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.display1();
    assertEquals("welcomeMessage method called", log.toString().trim());
  }

  @Test
  public void showMainMenuMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("7").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.display2();
    assertEquals("showMainMenu method called", log.toString().trim());
  }

  @Test
  public void askPortfolioNameMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("2 p1 7").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.displayPortfolio();
    assertEquals("askPortfolioName method called "
            + "invalidPortfolioName method called", log.toString().trim());
  }

  @Test
  public void portfolioExistsMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("p1 goog 10 n 1 p1 p2 msft 20 n 7").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.addPortfolio();
    assertEquals("askPortfolioName method called "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called", log.toString().trim());
  }

  @Test
  public void invalidPortfolioNameMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("p1").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.displayPortfolio();
    assertEquals("askPortfolioName method called "
            + "invalidPortfolioName method called", log.toString().trim());
  }

  @Test
  public void enterTickerNOSMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("p1 goog 10 n").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.addPortfolio();
    assertEquals("askPortfolioName method called "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called", log.toString().trim());
  }

  @Test
  public void continueAddingStocksMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("p1 goog 10 n").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.addPortfolio();
    assertEquals("askPortfolioName method called "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called", log.toString().trim());
  }

  @Test
  public void invalidNOSMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("p1 goog -10 goog 10 n").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.addPortfolio();
    assertEquals("askPortfolioName method called "
            + "enterTickerNOS method called "
            + "invalidNOS method called "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called", log.toString().trim());
  }


  @Test
  public void invalidTickerMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("p1 googijedoiqje -10 goog 10 n").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.addPortfolio();
    assertEquals("askPortfolioName method called "
            + "enterTickerNOS method called "
            + "invalidTicker method called "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called", log.toString().trim());
  }

  @Test
  public void printPortfolioMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 p1 goog 10 n 2 p1 7").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "askPortfolioName method called "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called "
            + "showMainMenu method called "
            + "askPortfolioName method called "
            + "printPortfolio method called "
            + "with portfolio [goog 10]"
            + "showMainMenu method called", log.toString().trim());
  }

  @Test
  public void enterDateMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("p1 2020-01-01").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.getTotalPortfolioValue();
    assertEquals("askPortfolioName method called "
            + "enterDate method called "
            + "invalidPortfolioName method called", log.toString().trim());
  }

  @Test
  public void printTotalValueMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 p1 goog 10 n 3 p1 2020-01-01 7").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "askPortfolioName method called "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called "
            + "showMainMenu method called "
            + "askPortfolioName method called "
            + "enterDate method called "
            + "showPortfolioValueHeading method called "
            + "printMapElement method called "
            + "goog=[1337.02, 10.0, 13370.2]"
            + "printTotalValue method called "
            + "with total 13370.2"
            + "showMainMenu method called", log.toString().trim());
  }

  @Test
  public void printMapElementMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 p1 goog 10 n 3 p1 2020-01-01 7").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "askPortfolioName method called "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called "
            + "showMainMenu method called "
            + "askPortfolioName method called "
            + "enterDate method called "
            + "showPortfolioValueHeading method called "
            + "printMapElement method called "
            + "goog=[1337.02, 10.0, 13370.2]"
            + "printTotalValue method called "
            + "with total 13370.2"
            + "showMainMenu method called", log.toString().trim());
  }

  @Test

  public void showPortfolioValueHeadingMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 p1 goog 10 n 3 p1 2020-01-01 7").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "askPortfolioName method called "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called "
            + "showMainMenu method called "
            + "askPortfolioName method called "
            + "enterDate method called "
            + "showPortfolioValueHeading method called "
            + "printMapElement method called "
            + "goog=[1337.02, 10.0, 13370.2]"
            + "printTotalValue method called with total 13370.2"
            + "showMainMenu method called", log.toString().trim());
  }

  @Test
  public void invalidDateFormatMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 p1 goog 10 n 3 p1 2020/01/01 7").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "askPortfolioName method called "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called "
            + "showMainMenu method called "
            + "askPortfolioName method called "
            + "enterDate method called "
            + "invalidDateFormat method called "
            + "showMainMenu method called", log.toString().trim());
  }

  @Test
  public void invalidUserInputMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("a 7").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "invalidUserInput method called "
            + "showMainMenu method called", log.toString().trim());
  }

  @Test
  public void invalidDateMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 p1 goog 10 n 3 p1 1234-01-01 7").getBytes());
    MockView mv = new MockView(log);
    User u = new UserImpl();
    Controller c = new ControllerImpl(u, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "askPortfolioName method called "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called "
            + "showMainMenu method called "
            + "askPortfolioName method called "
            + "enterDate method called "
            + "invalidDate method called "
            + "showMainMenu method called", log.toString().trim());
  }


  /**
   * A MockView class to mock test the Controller.
   */
  static class MockView implements View {

    private StringBuilder log;

    public MockView(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void welcomeMessage() {
      log.append("welcomeMessage method called ");
    }

    @Override
    public void showMainMenu() {
      log.append("showMainMenu method called ");
    }

    @Override
    public void askPortfolioName() {
      log.append("askPortfolioName method called ");
    }

    @Override
    public void portfolioExists() {
      log.append("portfolioExists method called ");
    }

    @Override
    public void invalidPortfolioName() {
      log.append("invalidPortfolioName method called ");
    }

    @Override
    public void enterTickerNOS() {
      log.append("enterTickerNOS method called ");
    }

    @Override
    public void continueAddingStocks() {
      log.append("continueAddingStocks method called ");
    }

    @Override
    public void invalidNOS() {
      log.append("invalidNOS method called ");
    }

    @Override
    public void invalidTicker() {
      log.append("invalidTicker method called ");
    }

    @Override
    public void printPortfolio(Portfolio p) {
      log.append("printPortfolio method called with portfolio ");
      log.append(p.toString());
    }

    @Override
    public void enterDate() {
      log.append("enterDate method called ");
    }

    @Override
    public void printTotalValue(float total) {
      log.append("printTotalValue method called with total ");
      log.append(total);
    }

    @Override
    public void printMapElement(Map.Entry<String, List<Float>> mapElement) {
      log.append("printMapElement method called ");
      log.append(mapElement);
    }

    @Override
    public void showPortfolioValueHeading() {
      log.append("showPortfolioValueHeading method called ");
    }

    @Override
    public void invalidDateFormat() {
      log.append("invalidDateFormat method called ");
    }

    @Override
    public void invalidUserInput() {
      log.append("invalidUserInput method called ");
    }

    @Override
    public void invalidDate() {
      log.append("invalidDate method called ");
    }

    @Override
    public void tickerValidatorFileMissing() {
      log.append("tickerValidatorFileMissing method called ");
    }

    @Override
    public void portfolioFileMissing() {
      log.append("portfolioFileMissing method called ");
    }

    @Override
    public void invalidXMLFormat() {
      log.append("invalidXMLFormat method called ");
    }

    @Override
    public void csvStockPriceNotFound() {
      log.append("csvStockPriceNotFound method called ");
    }

    @Override
    public void loadPortfolioSuccessful(String portfolioName) {
      log.append("loadPortfolioSuccessful method called ");
    }

    @Override
    public void savePortfolioSuccessful(String portfolioName) {
      log.append("savePortfolioSuccessful method called ");
    }
  }
}
