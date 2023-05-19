import controller.Controller;
import controller.ControllerImpl;
import model.Portfolio;
import model.PortfolioImpl;
import model.Stocks;
import model.User;
import model.UserImpl;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the UserImpl class.
 */
public class UserImplTest {
  private User u;
  private User u1;
  private Portfolio p;
  private Portfolio p1;
  private StringBuilder log;
  private ByteArrayInputStream in;

  @Before
  public void setUp() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
  }

  @Test
  public void addTwoPortf() {
    Map<String, Float> h1 = new HashMap<>();
    h1.put("GOOG", 10F);
    h1.put("MSFT", 20F);
    h1.put("AMZN", 30F);
    h1.put("APPL", 40F);

    Map<String, Float> h2 = new HashMap<>();
    h2.put("TSLA", 5F);
    h2.put("BRK.B", 1F);
    h2.put("V", 32F);
    h2.put("META", 4F);

    u = new UserImpl();
    u.addNewPortfolio(h1, "p1");
    u.addNewPortfolio(h2, "p2");
    assertEquals("{p1=[MSFT 20, GOOG 10, APPL 40, AMZN 30], p2=[META 4, TSLA 5, V 32, " +
            "BRK.B 1]}", u.toString());
  }

  @Test
  public void test2() {
    Map<String, Float> h1 = new HashMap<>();
    h1.put("GOOG", 10F);
    h1.put("MSFT", 20F);
    h1.put("AMZN", 30F);
    h1.put("AAPL", 40F);
    h1.put("TSLA", 5F);

    Map<String, Float> h2 = new HashMap<>();
    h2.put("TSLA", 5F);
    h2.put("BRK.B", 1F);
    h2.put("V", 32F);
    h2.put("META", 4F);

    u = new UserImpl();
    u.addNewPortfolio(h1, "p1");
    u.addNewPortfolio(h2, "p2");
    assertEquals(2, u.getNoOfPortfolios());
  }

  @Test
  public void test3() {
    Map<String, Float> h1 = new HashMap<>();
    h1.put("GOOG", 10F);
    h1.put("MSFT", 20F);
    h1.put("AMZN", 30F);
    h1.put("AAPL", 40F);
    h1.put("TSLA", 5F);

    Map<String, Float> h2 = new HashMap<>();
    h2.put("TSLA", 5F);
    h2.put("BRK.B", 1F);
    h2.put("V", 32F);
    h2.put("META", 4F);

    u = new UserImpl();
    u.addNewPortfolio(h1, "p1");
    u.addNewPortfolio(h2, "p2");
    p = u.getPortfolio("p1");
    System.out.println(p);
    assertEquals("[MSFT 20, GOOG 10, AAPL 40, TSLA 5, AMZN 30]", p.toString());
  }

  @Test
  public void test4() {
    Map<String, Float> h1 = new HashMap<>();
    h1.put("GOOG", 10F);
    h1.put("MSFT", 20F);
    h1.put("AMZN", 30F);
    h1.put("AAPL", 40F);
    h1.put("TSLA", 5F);

    Map<String, Float> h2 = new HashMap<>();
    h2.put("TSLA", 5F);
    h2.put("BRK.B", 1F);
    h2.put("V", 32F);
    h2.put("META", 4F);

    u = new UserImpl();
    u.addNewPortfolio(h1, "p1");
    u.addNewPortfolio(h2, "p2");
    p = u.getPortfolio("p1");
    System.out.println(p);
    assertEquals("[MSFT 20, GOOG 10, AAPL 40, TSLA 5, AMZN 30]", p.toString());
  }

  @Test
  public void test5() throws ParseException, IOException {
    Map<String, Float> h1 = new HashMap<>();
    Map<String, List<Float>> valTest;
    h1.put("GOOG", 10F);
    h1.put("MSFT", 20F);
    h1.put("AMZN", 30F);
    h1.put("AAPL", 40F);
    h1.put("TSLA", 5F);

    Map<String, Float> h2 = new HashMap<>();
    h2.put("TSLA", 5F);
    h2.put("BRK.B", 1F);
    h2.put("V", 32F);
    h2.put("META", 4F);
    p1 = new PortfolioImpl(h1);
    u = new UserImpl();
    u.addNewPortfolio(h1, "p1");
    u.addNewPortfolio(h2, "p2");
    valTest = u.getPortfolioValue(p1, "2022-10-25");
    assertEquals("{MSFT=[250.66, 20.0, 5013.2], "
            + "GOOG=[104.93, 10.0, 1049.3], "
            + "AAPL=[152.34, 40.0, 6093.5996], "
            + "TSLA=[222.41, 5.0, 1112.05], "
            + "AMZN=[120.6, 30.0, 3618.0]}", valTest.toString());
  }

  @Test
  public void testSaveAndLoadFile()
          throws IOException, ParserConfigurationException, TransformerException, SAXException {
    Map<String, Float> h1 = new HashMap<>();
    h1.put("GOOG", 10F);
    h1.put("MSFT", 20F);
    h1.put("AMZN", 30F);
    h1.put("AAPL", 40F);
    h1.put("TSLA", 5F);
    Map<String, Float> h2 = new HashMap<>();
    h2.put("TSLA", 5F);
    h2.put("V", 32F);
    h2.put("GOOG", 4F);
    p1 = new PortfolioImpl(h1);
    u = new UserImpl();
    u1 = new UserImpl();
    u.addNewPortfolio(h1, "p1");
    u.addNewPortfolio(h2, "p2");
    u.savePortfolio("p2");
    u1.loadPortfolio("p2");
    p = u1.getPortfolio("p2");
    p1 = u.getPortfolio("p2");
    assertEquals(p1.toString(), p.toString());
  }

  @Test
  public void testLoadFile()
          throws IOException, ParserConfigurationException, TransformerException, SAXException {
    Map<String, Float> h1 = new HashMap<>();
    h1.put("GOOG", 10F);
    h1.put("MSFT", 20F);
    h1.put("AMZN", 30F);
    h1.put("AAPL", 40F);
    h1.put("TSLA", 5F);
    Map<String, Float> h2 = new HashMap<>();
    h2.put("TSLA", 5F);
    h2.put("V", 32F);
    h2.put("GOOG", 4F);
    p1 = new PortfolioImpl(h1);
    u = new UserImpl();
    u1 = new UserImpl();
    u.addNewPortfolio(h1, "p1");
    u.addNewPortfolio(h2, "p2");
    u.savePortfolio("p2");
    u1.loadPortfolio("p2");
    p = u1.getPortfolio("p2");
    assertEquals("[GOOG 4, TSLA 5, V 32]", p.toString());
  }

  /**
   * This mock test checks control flow to all methods of the StocksImpl class and
   * the PortfolioImpl class.
   */
  @Test
  public void stocksMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 p1 goog 10 n 3 p1 2020-01-01 7").getBytes());
    ViewImplTest.MockView mv = new ViewImplTest.MockView(log);
    User mu = new MockUser(log);
    Controller c = new ControllerImpl(mu, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "askPortfolioName method called "
            + "checkPortfolioExists method called "
            + "with p1 enterTickerNOS method called "
            + "continueAddingStocks method called "
            + "addNewPortfolio method called with {goog=10.0} p1 "
            + "showMainMenu method called "
            + "askPortfolioName method called "
            + "enterDate method called "
            + "checkPortfolioExists method called with p1 "
            + "getPortfolio method called with p1 "
            + "getPortfolioValue method called with 2020-01-01 "
            + "getComposition method called "
            + "getTicker method called "
            + "getNoOfShares method called "
            + "showPortfolioValueHeading method called "
            + "printMapElement method called goog=[0.0, 0.0, 0.0]"
            + "printTotalValue method called with total 0.0"
            + "showMainMenu method called", log.toString().trim());
  }

  @Test
  public void stocksMockTest1() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 p1 goog 10 n 3 p1 2020-01--1 7").getBytes());
    ViewImplTest.MockView mv = new ViewImplTest.MockView(log);
    User mu = new MockUser(log);
    Controller c = new ControllerImpl(mu, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "askPortfolioName method called "
            + "checkPortfolioExists method called "
            + "with p1 enterTickerNOS method called "
            + "continueAddingStocks method called "
            + "addNewPortfolio method called with {goog=10.0} p1 "
            + "showMainMenu method called "
            + "askPortfolioName method called "
            + "enterDate method called "
            + "checkPortfolioExists method called with p1 "
            + "getPortfolio method called with p1 "
            + "getPortfolioValue method called with 2020-01--1 "
            + "getComposition method called "
            + "getTicker method called "
            + "getNoOfShares method called "
            + "showPortfolioValueHeading method called "
            + "printMapElement method called goog=[0.0, 0.0, 0.0]"
            + "printTotalValue method called with total 0.0"
            + "showMainMenu method called", log.toString().trim());
  }

  @Test
  public void userSavePortfolioMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 p1 goog 10 n 4 p1 7").getBytes());
    ViewImplTest.MockView mv = new ViewImplTest.MockView(log);
    User mu = new MockUser(log);
    Controller c = new ControllerImpl(mu, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "askPortfolioName method called "
            + "checkPortfolioExists method called "
            + "with p1 enterTickerNOS method called "
            + "continueAddingStocks method called "
            + "addNewPortfolio method called with {goog=10.0} p1 "
            + "showMainMenu method called "
            + "askPortfolioName method called "
            + "checkPortfolioExists method called with p1 "
            + "savePortfolio method called with portfolioName as p1 "
            + "savePortfolioSuccessful method called "
            + "showMainMenu method called", log.toString().trim());
  }

  @Test
  public void userSaveAllPortfolioMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 p1 goog 10 n 6").getBytes());
    ViewImplTest.MockView mv = new ViewImplTest.MockView(log);
    User mu = new MockUser(log);
    Controller c = new ControllerImpl(mu, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "askPortfolioName method called "
            + "checkPortfolioExists method called with p1 "
            + "enterTickerNOS method called "
            + "continueAddingStocks method called "
            + "addNewPortfolio method called with {goog=10.0} p1 "
            + "showMainMenu method called "
            + "getAllPortfolioNames method called "
            + "savePortfolio method called with portfolioName as p1 "
            + "savePortfolioSuccessful method called", log.toString().trim());
  }

  @Test
  public void userLoadPortfolioMockTest() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("5 p1 7").getBytes());
    ViewImplTest.MockView mv = new ViewImplTest.MockView(log);
    User mu = new MockUser(log);
    Controller c = new ControllerImpl(mu, mv, in);
    c.display2();
    assertEquals("showMainMenu method called "
            + "askPortfolioName method called "
            + "checkPortfolioFileExists method called with p1 "
            + "loadPortfolio method called with p1 "
            + "loadPortfolioSuccessful method called "
            + "showMainMenu method called", log.toString().trim());
  }


  /**
   * A MockUser class to mock test the Controller.
   */
  static class MockUser implements User {
    private StringBuilder log;
    Portfolio mp;

    public MockUser(StringBuilder log) {
      this.log = log;
      mp = new MockPortfolio(this.log);
    }

    @Override
    public void addNewPortfolio(Map<String, Float> p, String name) {
      log.append("addNewPortfolio method called with ")
              .append(p)
              .append(" ")
              .append(name)
              .append(" ");
    }

    @Override
    public int getNoOfPortfolios() {
      log.append("getNoOfPortfolios method called ");
      return 0;
    }

    @Override
    public Portfolio getPortfolio(String portfolioName) {
      log.append("getPortfolio method called with ")
              .append(portfolioName)
              .append(" ");
      Map<String, Integer> m = new HashMap<>();
      m.put("goog", 10);
      Portfolio p = new MockPortfolio(log);
      return p;
    }

    @Override
    public Map<String, List<Float>> getPortfolioValue(Portfolio portfolio, String date) {
      log.append("getPortfolioValue method called with ")
              .append(date)
              .append(" ");
      mp.getComposition();
      Map<String, List<Float>> m = new HashMap<>();
      List<Float> f = new ArrayList<>();
      f.add((float) 0);
      f.add((float) 0);
      f.add((float) 0);
      m.put("goog", f);
      return m;
    }

    @Override
    public void savePortfolio(String portfolioName) {
      log.append("savePortfolio method called with portfolioName as ")
              .append(portfolioName)
              .append(" ");
    }

    @Override
    public void loadPortfolio(String portfolioName) {
      log.append("loadPortfolio method called with ")
              .append(portfolioName)
              .append(" ");
    }

    @Override
    public Boolean checkPortfolioExists(String name) {
      log.append("checkPortfolioExists method called with ")
              .append(name)
              .append(" ");
      StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
      return !stackTraceElements[2].getMethodName().equals("addPortfolio");
    }

    @Override
    public Boolean checkPortfolioFileExists(String name) {
      log.append("checkPortfolioFileExists method called with ")
              .append(name)
              .append(" ");
      return true;
    }

    @Override
    public List<String> getAllPortfolioNames() {
      log.append("getAllPortfolioNames method called ");
      List<String> m = new ArrayList<>();
      m.add("p1");
      return m;
    }

    @Override
    public float getPortfolioSumValue(Map<String, List<Float>> val) {
      return 0;
    }
  }

  /**
   * A MockPortfolio class to mock test the Controller.
   */
  static class MockPortfolio implements Portfolio {
    private StringBuilder log;
    MockStock ms;

    public MockPortfolio(StringBuilder log) {
      this.log = log;
      this.ms = new MockStock(log);
    }

    @Override
    public Map<String, Float> getComposition() {
      log.append("getComposition method called ");
      ms.getTicker();
      ms.getNoOfShares();
      return null;
    }

    @Override
    public int getNoOfStocks() {
      log.append("getNoOfStocks method called ");
      return 0;
    }
  }

  /**
   * A MockStock class to mock test the Controller.
   */
  static class MockStock implements Stocks {

    private StringBuilder log;

    public MockStock(StringBuilder log) {
      this.log = log;
    }

    @Override
    public String getTicker() {
      log.append("getTicker method called ");
      return null;
    }

    @Override
    public float getNoOfShares() {
      log.append("getNoOfShares method called ");
      return 0;
    }

    @Override
    public float getCostPrice() {
      log.append("getCostPrice method called ");
      return 0;
    }
  }
}
