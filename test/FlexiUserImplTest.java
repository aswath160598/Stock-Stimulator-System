import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import model.DollarCostAveraging;
import model.DollarCostAveragingSingle;
import model.FlexiPortfolio;
import model.FlexiPortfolioImpl;
import model.FlexiStocks;
import model.FlexiStocksImpl;
import model.FlexiUser;
import model.FlexiUserImpl;
import model.Portfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * A JUnit test class for the Flexible UserImpl class.
 */
public class FlexiUserImplTest {

  private FlexiUser user;
  private FlexiUser u1;
  private FlexiPortfolio p;
  private FlexiPortfolio p1;
  private StringBuilder log;
  private ByteArrayInputStream in;

  @Before
  public void setUp() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
  }

  @Test
  public void test5() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    float x = 0;
    x = user.getFlexiPortfolioValue("p1", "2022-11-09");
    assertEquals(348.34002685546875, x, 0);
  }

  @Test
  public void costBasisTest1() throws ParseException {
    Map<String, Map<String, Float>> cache = new HashMap<>();
    user = new FlexiUserImpl();
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2022-11-09");
    val_string.add("b");
    val_string.add("3");
    val_string.add("3");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-09");
    val_string.add("s");
    val_string.add("2");
    val_string.add("3");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-09");
    val_string.add("s");
    val_string.add("1");
    val_string.add("3");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);

    // creating portfolio p1 for the user
    user.addNewFlexiPortfolio(h, "p1");

    assertEquals("{GOOG=0.0}", user.getPortfolio("p1")
            .getComposition(LocalDate.of(2022, 11, 10)).toString());
    assertEquals(264.2,
            user.calculateCostBasis("p1", "2022-11-10", cache), 0.1);
  }

  @Test
  public void saveLoadTest1() throws IOException, ParserConfigurationException,
          TransformerException, SAXException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    user.saveFlexiPortfolio("p1");
    String p1_original = user.getPortfolio("p1").toString();
    user.loadFlexiPortfolio("p1");
    String p1_loaded = user.getPortfolio("p1").toString();
    assertEquals(p1_original, p1_loaded);
  }

  @Test
  public void test7() throws IOException, ParserConfigurationException, SAXException,
          TransformerException {
    user = new FlexiUserImpl();
    assertNull(user.getPortfolio("p1"));
    user.loadFlexiPortfolio("p1");
    user.getPortfolio("p1");
    assertEquals("[GOOG 1 b 2022-11-08, GOOG 1 b 2022-11-08, GOOG 1 b " +
            "2022-11-08, AMZN 1 b 2022-11-08, AMZN 1 b " +
            "2022-11-08, AMZN 1 s 2022-11-08]", user.getPortfolio("p1").toString());
  }

  @Test
  public void test8() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    StringBuilder str;
    List<String> val_string = new ArrayList<>();
    val_string.add("2021-11-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2021-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2021-11-08");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2021-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2021-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2021-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AAPL", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    str = user.drawGraph("p1", "2018-01-15", "2022-10-15");
    assertEquals("\nPerformance of portfolio p1 from 2018-01-15 to 2022-10-15\n" +
            "\n" +
            "JAN 2018  \n" +
            "APR 2018  \n" +
            "JUL 2018  \n" +
            "OCT 2018  \n" +
            "JAN 2019  \n" +
            "APR 2019  \n" +
            "JUL 2019  \n" +
            "OCT 2019  \n" +
            "JAN 2020  \n" +
            "APR 2020  \n" +
            "JUL 2020  \n" +
            "OCT 2020  \n" +
            "DEC 2020  \n" +
            "MAR 2021  \n" +
            "JUN 2021  \n" +
            "SEP 2021  \n" +
            "DEC 2021  *************************************************\n" +
            "MAR 2022  ************************************************\n" +
            "JUN 2022  *************************************\n" +
            "SEP 2022  *******\n" +
            "OCT 2022  *******\n" +
            "\n" +
            "Scale: * = $69.0", str.toString());
  }

  @Test
  public void testForMonths() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2016-11-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2016-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2017-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2017-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2016-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2017-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AAPL", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    StringBuilder sb = user.drawGraph("p1", "2016-10-15", "2017-12-15");
    assertEquals("\nPerformance of portfolio p1 from 2016-10-15 to 2017-12-15\n" +
            "\n" +
            "OCT 2016  \n" +
            "NOV 2016  *********************\n" +
            "DEC 2016  **********************\n" +
            "JAN 2017  **********************\n" +
            "FEB 2017  ***********************\n" +
            "MAR 2017  ************************\n" +
            "APR 2017  **************************\n" +
            "MAY 2017  ***************************\n" +
            "JUN 2017  **************************\n" +
            "JUL 2017  **************************\n" +
            "AUG 2017  ***************************\n" +
            "SEP 2017  ***************************\n" +
            "OCT 2017  *****************************\n" +
            "NOV 2017  ***********************************************\n" +
            "DEC 2017  *************************************************\n" +
            "DEC 2018  *************************************************\n" +
            "\n" +
            "Scale: * = $75.0", sb.toString());
  }

  @Test
  public void testForQuarters() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2014-11-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-03-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-07-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2016-09-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2016-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2019-11-08");
    val_string.add("s");
    val_string.add("3");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2017-03-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AAPL", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2018-09-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2018-05-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2018-03-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("V", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    StringBuilder sb = user.drawGraph("p1", "2014-10-15", "2020-05-15");
    assertEquals("\nPerformance of portfolio p1 from 2014-10-15 to 2020-05-15\n" +
            "\n" +
            "OCT 2014  \n" +
            "JAN 2015  *****\n" +
            "APR 2015  ***********\n" +
            "JUL 2015  *******************\n" +
            "OCT 2015  *********************\n" +
            "JAN 2016  **********************\n" +
            "APR 2016  *********************\n" +
            "JUL 2016  ***********************\n" +
            "OCT 2016  *************************\n" +
            "JAN 2017  ***************************\n" +
            "APR 2017  ********************************\n" +
            "JUL 2017  *********************************\n" +
            "SEP 2017  **********************************\n" +
            "DEC 2017  *************************************\n" +
            "MAR 2018  **************************************\n" +
            "JUN 2018  ******************************************\n" +
            "SEP 2018  ************************************************\n" +
            "DEC 2018  ****************************************\n" +
            "MAR 2019  **********************************************\n" +
            "JUN 2019  ********************************************\n" +
            "SEP 2019  *************************************************\n" +
            "DEC 2019  ***********************************************\n" +
            "MAR 2020  ****************************************\n" +
            "MAY 2020  ************************************************\n" +
            "\n" +
            "Scale: * = $97.0", sb.toString());
  }

  @Test
  public void testForQuartersLargeValues() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2014-11-08");
    val_string.add("b");
    val_string.add("97");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-03-08");
    val_string.add("b");
    val_string.add("104");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-07-08");
    val_string.add("b");
    val_string.add("65");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2016-09-08");
    val_string.add("b");
    val_string.add("12");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2016-11-08");
    val_string.add("b");
    val_string.add("18");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2019-11-08");
    val_string.add("s");
    val_string.add("21");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2017-03-08");
    val_string.add("b");
    val_string.add("143");
    val_string1.add(val_string);
    h.put("AAPL", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2018-09-08");
    val_string.add("b");
    val_string.add("104");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2018-05-08");
    val_string.add("b");
    val_string.add("124");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2018-03-08");
    val_string.add("b");
    val_string.add("199");
    val_string1.add(val_string);
    h.put("V", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    StringBuilder sb = user.drawGraph("p1", "2014-10-15", "2022-05-15");
    assertEquals("\nPerformance of portfolio p1 from 2014-10-15 to 2022-05-15\n" +
            "\n" +
            "2014-12-31  **\n" +
            "2015-12-31  ***********\n" +
            "2016-12-31  ***********\n" +
            "2017-12-31  *****************\n" +
            "2018-12-31  ********************\n" +
            "2019-12-31  ***************************\n" +
            "2020-12-31  ********************************\n" +
            "2021-12-31  *************************************************\n" +
            "2022-05-15  ****************************************\n" +
            "\n" +
            "Scale: * = $17785.0", sb.toString());
  }

  @Test
  public void testGraphBuyAndSell() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2014-11-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-03-08");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-07-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2014-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-03-08");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-07-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AAPL", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    StringBuilder sb = user.drawGraph("p1", "2014-10-15", "2015-10-15");
    assertEquals("\nPerformance of portfolio p1 from 2014-10-15 to 2015-10-15\n" +
            "\n" +
            "OCT 2014  \n" +
            "NOV 2014  *****************************************\n" +
            "DEC 2014  ***************************************\n" +
            "JAN 2015  ****************************************\n" +
            "FEB 2015  ******************************************\n" +
            "MAR 2015  \n" +
            "APR 2015  \n" +
            "MAY 2015  \n" +
            "JUN 2015  \n" +
            "JUL 2015  **********************************************\n" +
            "AUG 2015  *********************************************\n" +
            "SEP 2015  ********************************************\n" +
            "OCT 2015  ************************************************\n" +
            "\n" +
            "Scale: * = $16.0", sb.toString());
  }

  @Test
  public void testGraphJan2019() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2014-11-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-03-08");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-07-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2014-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-03-08");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-07-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AAPL", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    StringBuilder sb = user.drawGraph("p1", "2014-01-01", "2014-12-05");
    assertEquals("\nPerformance of portfolio p1 from 2014-01-01 to 2014-12-05\n" +
            "\n" +
            "JAN 2014  \n" +
            "MAR 2014  \n" +
            "APR 2014  \n" +
            "MAY 2014  \n" +
            "JUN 2014  \n" +
            "JUL 2014  \n" +
            "AUG 2014  \n" +
            "SEP 2014  \n" +
            "OCT 2014  \n" +
            "NOV 2014  ***********************************************\n" +
            "DEC 2014  *********************************************\n" +
            "\n" +
            "Scale: * = $14.0", sb.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGraphJan2014() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2014-01-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2014-01-1");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-01-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AAPL", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    StringBuilder sb = user.drawGraph("p1", "2014-01-01", "2014-02-05");
  }

  @Test
  public void testGraphJan() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2014-1-1");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2014-1-6");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2014-1-12");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AAPL", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    StringBuilder sb = user.drawGraph("p1", "2014-01-01", "2014-2-05");
    assertEquals("\nPerformance of portfolio p1 from 2014-01-01 to 2014-2-05\n" +
            "\n" +
            "2014-01-01  ****************\n" +
            "2014-01-03  ***************\n" +
            "2014-01-05  ***************\n" +
            "2014-01-07  *******************************\n" +
            "2014-01-09  *******************************\n" +
            "2014-01-11  *******************************\n" +
            "2014-01-13  ***********************************************\n" +
            "2014-01-15  *************************************************\n" +
            "2014-01-17  ***********************************************\n" +
            "2014-01-19  ***********************************************\n" +
            "2014-01-21  ************************************************\n" +
            "2014-01-23  *************************************************\n" +
            "2014-01-25  ************************************************\n" +
            "2014-01-27  ************************************************\n" +
            "2014-01-29  ********************************************\n" +
            "2014-01-31  ********************************************\n" +
            "2014-02-02  ********************************************\n" +
            "2014-02-04  ********************************************\n" +
            "2014-02-06  *********************************************\n" +
            "\n" +
            "Scale: * = $34.0", sb.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGraphError() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2014-11-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-03-08");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-07-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2014-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-03-08");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-07-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AAPL", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    StringBuilder sb = user.drawGraph("p1", "2014-01-01", "2014-02-05");
  }

  @Test
  public void testForYears() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2014-11-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-03-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2015-07-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2016-09-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2016-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2019-11-08");
    val_string.add("s");
    val_string.add("3");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2017-03-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AAPL", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2018-09-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2018-05-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2018-03-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("V", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    StringBuilder sb = user.drawGraph("p1", "2012-10-15", "2020-05-15");
    assertEquals("\nPerformance of portfolio p1 from 2012-10-15 to 2020-05-15\n" +
            "\n" +
            "2012-12-31  \n" +
            "2013-12-31  \n" +
            "2014-12-31  *****\n" +
            "2015-12-31  ************************\n" +
            "2016-12-31  ***************************\n" +
            "2017-12-31  **************************************\n" +
            "2018-12-31  ******************************************\n" +
            "2019-12-31  ************************************************\n" +
            "2020-05-15  *************************************************\n" +
            "\n" +
            "Scale: * = $94.0", sb.toString());
  }


  /**
   * This mock test checks control flow to all methods of the StocksImpl class and
   * the PortfolioImpl class.
   */
  @Test
  public void inflexibleMockTest1() {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 1 p1 goog 10 n 3 p1 2020-01-01 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showMainMenu method called " +
            "askPortfolioName method called " +
            "enterTickerNOS method called " +
            "continueAddingStocks method called " +
            "showMainMenu method called " +
            "askPortfolioName method called " +
            "enterDate method called " +
            "showPortfolioValueHeading method called " +
            "printMapElement method called with mapElement=goog=[1337.02, 10.0, 13370.2]" +
            "printTotalValue method called with total=13370.2" +
            "showMainMenu method called " +
            "selectPortfolioType method called", log.toString().trim());
  }

  @Test
  public void inflexibleMockTest2() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 1 p1 goog 10 n 2 p1 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showMainMenu method called " +
            "askPortfolioName method called " +
            "enterTickerNOS method called " +
            "continueAddingStocks method called " +
            "showMainMenu method called " +
            "askPortfolioName method called " +
            "printPortfolio method called with p=[goog 10]" +
            "showMainMenu method called " +
            "selectPortfolioType method called", log.toString().trim());
  }

  @Test
  public void inflexibleMockTest3() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 1 p1 goog 10 n 4 p1 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showMainMenu method called " +
            "askPortfolioName method called " +
            "enterTickerNOS method called " +
            "continueAddingStocks method called " +
            "showMainMenu method called " +
            "askPortfolioName method called " +
            "savePortfolioSuccessful method called with portfolioName=p1" +
            "showMainMenu method called " +
            "selectPortfolioType method called", log.toString().trim());
  }

  @Test
  public void inflexibleMockTest4() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 1 p1 goog 10 n 5 p1 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showMainMenu method called " +
            "askPortfolioName method called " +
            "enterTickerNOS method called " +
            "continueAddingStocks method called " +
            "showMainMenu method called " +
            "askPortfolioName method called " +
            "loadPortfolioSuccessful method called with portfolioName=p1" +
            "showMainMenu method called " +
            "selectPortfolioType method called", log.toString().trim());
  }

  @Test
  public void inflexibleMockTest5() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    log = new StringBuilder();
    in = new ByteArrayInputStream(("1 1 p1 goog 10 n 6 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showMainMenu method called " +
            "askPortfolioName method called " +
            "enterTickerNOS method called " +
            "continueAddingStocks method called " +
            "showMainMenu method called " +
            "savePortfolioSuccessful method called with portfolioName=p1" +
            "selectPortfolioType method called", log.toString().trim());
  }

  @Test
  public void flexiStocksMockTest1() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    // buy once
    log = new StringBuilder();
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 10 n 3 p1 2020-01-01 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with " +
            "s=goog 10 b 2020-01-01 and portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "enterDate method called " +
            "getPortfolio method called with portfolioName=p1 " +
            "invalidPortfolioName method called " +
            "showFlexibleMenu method called " +
            "selectPortfolioType method called", log.toString().trim());
  }

  @Test
  public void flexiStocksMockTest2() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    // buy twice
    log = new StringBuilder();
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 10 n " +
            "1 p1 msft 2020-01-02 20 n 3 p1 2020-01-01 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=goog 10 b 2020-01-01 and " +
            "portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=msft 20 b 2020-01-02 and " +
            "portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "enterDate method called " +
            "getPortfolio method called with portfolioName=p1 " +
            "invalidPortfolioName method called " +
            "showFlexibleMenu method called " +
            "selectPortfolioType method called", log.toString().trim());
  }

  @Test
  public void flexiStocksMockTest3() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    // buy twice and sell
    log = new StringBuilder();
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 10 n " +
            "1 p1 msft 2020-01-02 20 n 2 p1 goog 2021-01-01 5 n 3 p1 2021-01-02 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=goog 10 b 2020-01-01 and " +
            "portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=msft 20 b 2020-01-02 and " +
            "portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "checkPortfolioExists method called with name=p1 " +
            "enterSellTicker method called " +
            "getPortfolio method called with portfolioName=p1 " +
            "enterSellDate method called " +
            "stockNotSold method called " +
            "sellAnotherStock method called " +
            "showFlexibleMenu method called " +
            "invalidUserInput method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "enterDate method called " +
            "getPortfolio method called with portfolioName=p1 " +
            "showPortfolio method called with ticker=MSFT and noOfShares=100" +
            "showPortfolio method called with ticker=GOOG and noOfShares=100" +
            "showPortfolio method called with ticker=AAPL and noOfShares=100" +
            "showPortfolio method called with ticker=AMZN and noOfShares=100" +
            "showFlexibleMenu method called " +
            "selectPortfolioType method called", log.toString().trim());
  }

  @Test
  public void flexiStocksMockTest4() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    // buy twice and save, load
    log = new StringBuilder();
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 10 n " +
            "1 p1 msft 2020-01-02 20 n 4 p1 5 p1 3 p1 2021-01-02 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=goog 10 b 2020-01-01 and " +
            "portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=msft 20 b 2020-01-02 and " +
            "portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "saveFlexiPortfolio method called with portfolioName=p1 " +
            "savePortfolioSuccessful method called with portfolioName=p1" +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "loadFlexiPortfolio method called with portfolioName=p1 " +
            "loadPortfolioSuccessful method called with portfolioName=p1" +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "enterDate method called " +
            "getPortfolio method called with portfolioName=p1 " +
            "showPortfolio method called with ticker=MSFT and noOfShares=100" +
            "showPortfolio method called with ticker=GOOG and noOfShares=100" +
            "showPortfolio method called with ticker=AAPL and noOfShares=100" +
            "showPortfolio method called with ticker=AMZN and noOfShares=100" +
            "showFlexibleMenu method called " +
            "selectPortfolioType method called", log.toString().trim());
  }

  @Test
  public void flexiStocksMockTest5() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    // buy twice and cost basis
    log = new StringBuilder();
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 10 n " +
            "1 p1 msft 2020-01-02 20 n 6 p1 2021-01-01 3 p1 2021-01-02 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=goog 10 b 2020-01-01 and " +
            "portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=msft 20 b 2020-01-02 and " +
            "portfolioName=p1 continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "checkPortfolioExists method called with name=p1 " +
            "enterDate method called " +
            "calculateCostBasis method called with portfolioName=p1 and date=2021-01-01 " +
            "displayCostBasis method called with cb=0.0 and portfolioName=p1" +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "enterDate method called " +
            "getPortfolio method called with portfolioName=p1 " +
            "showPortfolio method called with ticker=MSFT and noOfShares=100" +
            "showPortfolio method called with ticker=GOOG and noOfShares=100" +
            "showPortfolio method called with ticker=AAPL and noOfShares=100" +
            "showPortfolio method called with ticker=AMZN and noOfShares=100" +
            "showFlexibleMenu method called " +
            "selectPortfolioType method called", log.toString().trim());
  }

  @Test
  public void flexiStocksMockTest6() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    // buy twice and portfolio value
    log = new StringBuilder();
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 10 n " +
            "1 p1 msft 2020-01-02 20 n 7 p1 2021-01-01 3 p1 2021-01-02 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=goog 10 b 2020-01-01 and " +
            "portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=msft 20 b 2020-01-02 and " +
            "portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "enterDate method called " +
            "getFlexiPortfolioValue method called with fName=p1 and date=2021-01-01 " +
            "printTotalValue method called with total=0.0" +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "enterDate method called " +
            "getPortfolio method called with portfolioName=p1 " +
            "showPortfolio method called with ticker=MSFT and noOfShares=100" +
            "showPortfolio method called with ticker=GOOG and noOfShares=100" +
            "showPortfolio method called with ticker=AAPL and noOfShares=100" +
            "showPortfolio method called with ticker=AMZN and noOfShares=100" +
            "showFlexibleMenu method called " +
            "selectPortfolioType method called", log.toString().trim());
  }

  @Test
  public void flexiStocksMockTest7() throws ParseException, IOException,
          ParserConfigurationException, TransformerException, SAXException {
    // buy twice and portfolio value
    log = new StringBuilder();
    in = new ByteArrayInputStream(("2 1 p1 goog 2020-01-01 10 n " +
            "1 p1 msft 2020-01-02 20 n 8 p1 2021-01-01 2022-01-01 0 0").getBytes());
    FlexiViewImplTest.MockFlexiView mv = new FlexiViewImplTest.MockFlexiView(log);
    FlexiUser mu = new FlexiUserImplTest.MockFlexiUser(log);
    FlexiController fc = new FlexiControllerImpl(mu, mv, in);
    fc.showPortfolioTypeSelection();
    assertEquals("selectPortfolioType method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=goog 10 b 2020-01-01 and " +
            "portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "checkPortfolioExists method called with name=p1 " +
            "enterTicker method called " +
            "enterBuyDate method called " +
            "enterNOS method called " +
            "addStockToExistingPortfolio method called with s=msft 20 b 2020-01-02 and " +
            "portfolioName=p1 " +
            "continueAddingStocks method called " +
            "showFlexibleMenu method called " +
            "askPortfolioName method called " +
            "enterPlotStartDate method called " +
            "enterPlotEndDate method called " +
            "drawGraph method called with fPortfolio=p1 and startDate2021-01-01 and " +
            "endDate=2022-01-01 " +
            "showGraph method called with g=Dummy Graph " +
            "showFlexibleMenu method called " +
            "selectPortfolioType method called", log.toString().trim());
  }


  /**
   * A MockFlexiUser class to mock test the FlexiController.
   */
  static class MockFlexiUser implements FlexiUser {
    private StringBuilder log;
    Portfolio mp;

    public MockFlexiUser(StringBuilder log) {
      this.log = log;
      mp = new FlexiUserImplTest.MockFlexiPortfolio(this.log);
    }

    @Override
    public void addNewFlexiPortfolio(Map<String, List<List<String>>> p, String name) {
      log.append("addNewFlexiPortfolio method called with inputs p=")
              .append(p)
              .append(" and name=")
              .append(name);
    }

    @Override
    public float getFlexiPortfolioValue(String fName, String date) {
      log.append("getFlexiPortfolioValue method called with fName=")
              .append(fName)
              .append(" and date=")
              .append(date)
              .append(" ");
      return 0;
    }

    @Override
    public int getNoOfPortfolios() {
      log.append("getNoOfPortfolios method called ");
      return 0;
    }

    @Override
    public FlexiPortfolio getPortfolio(String portfolioName) {
      log.append("getPortfolio method called with portfolioName=")
              .append(portfolioName)
              .append(" ");
      FlexiPortfolio dummyPortfolio = new FlexiPortfolioImpl();
      FlexiStocks dummyStock = new FlexiStocksImpl(100F, 0, "GOOG",
              LocalDate.of(2000, 1, 1), "b", 1F);
      dummyPortfolio.addStock(dummyStock);
      dummyStock = new FlexiStocksImpl(100F, 0, "AMZN",
              LocalDate.of(2000, 1, 1), "b", 1F);
      dummyPortfolio.addStock(dummyStock);
      dummyStock = new FlexiStocksImpl(100F, 0, "MSFT",
              LocalDate.of(2000, 1, 1), "b", 1F);
      dummyPortfolio.addStock(dummyStock);
      dummyStock = new FlexiStocksImpl(100F, 0, "AAPL",
              LocalDate.of(2000, 1, 1), "b", 1F);
      dummyPortfolio.addStock(dummyStock);
      return dummyPortfolio;
    }

    @Override
    public Boolean checkPortfolioExists(String name) {
      log.append("checkPortfolioExists method called with name=")
              .append(name)
              .append(" ");
      return true;
    }

    @Override
    public List<String> getAllPortfolioNames() {
      log.append("getAllPortfolioNames method called ");
      return null;
    }

    @Override
    public Boolean checkPortfolioFileExists(String name) {
      log.append("checkPortfolioFileExists method called with name=")
              .append(name)
              .append(" ");
      return null;
    }

    @Override
    public void addStockToExistingPortfolio(FlexiStocks s, String portfolioName) {
      log.append("addStockToExistingPortfolio method called with s=")
              .append(s)
              .append(" and portfolioName=")
              .append(portfolioName)
              .append(" ");
    }

    @Override
    public float calculateCostBasis(String portfolioName, String date,
                                    Map<String, Map<String, Float>> cache) {
      log.append("calculateCostBasis method called with portfolioName=")
              .append(portfolioName)
              .append(" and date=")
              .append(date)
              .append(" ");
      return 0;
    }

    @Override
    public void saveFlexiPortfolio(String portfolioName) {
      log.append("saveFlexiPortfolio method called with portfolioName=")
              .append(portfolioName)
              .append(" ");
    }

    @Override
    public void loadFlexiPortfolio(String portfolioName) throws IllegalArgumentException {
      log.append("loadFlexiPortfolio method called with portfolioName=")
              .append(portfolioName)
              .append(" ");
    }

    @Override
    public StringBuilder drawGraph(String fPortfolio, String startDate, String endDate) {
      log.append("drawGraph method called with fPortfolio=")
              .append(fPortfolio)
              .append(" and startDate")
              .append(startDate)
              .append(" and endDate=")
              .append(endDate)
              .append(" ");
      StringBuilder sb = new StringBuilder("Dummy Graph");
      return sb;
    }

    @Override
    public Map<String, List<String>> graphComputation(String portfolioName, String startDate,
                                                      String endDate) {
      log.append("graphComputation method called with portfolioName=")
              .append(portfolioName)
              .append(" and startDate")
              .append(startDate)
              .append(" and endDate=")
              .append(endDate)
              .append(" ");
      return null;
    }

    @Override
    public void dollarCostAveraging(String portfolioName, DollarCostAveraging obj) {
      log.append("dollarCostAveraging method called with fPortfolio=")
              .append(portfolioName)
              .append("proportion map")
              .append(obj)
              .append(" ");
      StringBuilder sb = new StringBuilder("Dummy Graph");
    }

    @Override
    public void dollarCostAveragingSingle(String portfolioName, DollarCostAveragingSingle obj) {
      log.append("dollarCostAveraging method called with fPortfolio=")
              .append(portfolioName)
              .append("proportion map")
              .append(obj)
              .append(" ");
      StringBuilder sb = new StringBuilder("Dummy Graph");
    }

    @Override
    public void updateDollarCostAvg() {
      //Update the portfolio with the dollar cost average strategies.

    }

    @Override
    public void addNewDollarCostAvg(Map<String, List<String>> p, String name) {
      log.append("dollarCostAveraging method called with fPortfolio=")
              .append(p)
              .append("name")
              .append(name)
              .append(" ");
      StringBuilder sb = new StringBuilder("Dummy Graph");
    }

    @Override
    public void addNewDollarCostAvgSingle(Map<String, List<String>> d, String portfolioName) {
      log.append("dollarCostAveraging method called with fPortfolio=")
              .append(d)
              .append("name")
              .append(portfolioName)
              .append(" ");
      StringBuilder sb = new StringBuilder("Dummy Graph");
    }

    @Override
    public float calculateDCAPortfolioCostBasis(String portfolioName, String date,
                                                Map<String, Map<String, Float>> cache)
            throws ParseException {
      log.append("Cost Basis function of fPortfolio=")
              .append(portfolioName)
              .append("date")
              .append(date)
              .append(" ");
      return 0;
    }
  }


  /**
   * A MockFlexiPortfolio class to mock test the FlexiController.
   */
  static class MockFlexiPortfolio implements FlexiPortfolio {
    private StringBuilder log;
    FlexiUserImplTest.MockFlexiStock ms;

    public MockFlexiPortfolio(StringBuilder log) {
      this.log = log;
      this.ms = new FlexiUserImplTest.MockFlexiStock(log);
    }

    @Override
    public Map<String, Float> getComposition(LocalDate date) {
      log.append("getComposition method called with date=")
              .append(date)
              .append(" ");
      Map<String, Float> dummyComposition = new HashMap<>();
      dummyComposition.put("GOOG", 100F);
      dummyComposition.put("MSFT", 100F);
      dummyComposition.put("AMZN", 100F);
      dummyComposition.put("AAPL", 100F);
      return dummyComposition;
    }

    @Override
    public Map<String, Float> getComposition() {
      log.append("getComposition method called ");
      return null;
    }

    @Override
    public Boolean validateSell(LocalDate sellDate, String ticker, Float sellQuantity) {
      log.append("validateSell method called with sellDate=")
              .append(sellDate)
              .append(" and ticker=")
              .append(ticker)
              .append(" and sellQuantity=")
              .append(sellQuantity)
              .append(" ");
      return null;
    }

    @Override
    public void addStock(FlexiStocks s) {
      log.append("addStock method called with s=").append(s).append(" ");
    }

    @Override
    public float calculatePortfolioCostBasis(String date, Map<String, Map<String, Float>> cache) {
      log.append("calculatePortfolioCostBasis method called ")
              .append(" date=")
              .append(date)
              .append(" ");
      return 0;
    }

    @Override
    public int size() {
      log.append("size method called ");
      return 0;
    }

    @Override
    public FlexiStocks getAt(int x) {
      log.append("getAt method called with x=")
              .append(x)
              .append(" ");
      return null;
    }

    @Override
    public int getNoOfStocks() {
      log.append("getNoOfStocks method called ");
      return 0;
    }
  }


  /**
   * A MockFlexiStock class to mock test the FlexiController.
   */
  static class MockFlexiStock implements FlexiStocks {

    private StringBuilder log;

    public MockFlexiStock(StringBuilder log) {
      this.log = log;
    }

    @Override
    public LocalDate getBuyDate() {
      log.append("getBuyDate method called ");
      return null;
    }

    @Override
    public String getStatus() {
      log.append("getStatus method called ");
      return null;
    }

    @Override
    public float getCommissionFee() {
      log.append("getCommissionFee method called ");
      return 0;
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

  @Test
  public void testGraphMSFTAMZN() throws ParseException {
    StringBuilder str;
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2020-1-1");
    val_string.add("b");
    val_string.add("10");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2021-1-1");
    val_string.add("b");
    val_string.add("34");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2021-6-6");
    val_string.add("s");
    val_string.add("30");
    val_string1.add(val_string);
    h.put("MSFT", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    str = user.drawGraph("p1", "2010-1-1", "2022-10-30");
    assertEquals("\nPerformance of portfolio p1 from 2010-1-1 to 2022-10-30\n" +
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
            "2020-12-31  **********************************************\n" +
            "2021-12-31  *************************************************\n" +
            "2022-10-30  **\n" +
            "\n" +
            "Scale: * = $694.0", str.toString());
  }

  @Test
  public void saveLoadTest2() throws IOException, ParserConfigurationException,
          TransformerException, SAXException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    user.saveFlexiPortfolio("p1");
    String p1_original = user.getPortfolio("p1").toString();
    user.loadFlexiPortfolio("p1");
    String p1_loaded = user.getPortfolio("p1").toString();
    assertEquals(p1_original, p1_loaded);
  }

  @Test
  public void LoadTest1() throws IOException, ParserConfigurationException, SAXException,
          TransformerException {
    user = new FlexiUserImpl();
    user.loadFlexiPortfolio("p1");
    String p1_loaded = user.getPortfolio("p1").toString();
    assertEquals("[GOOG 1 b 2022-11-08, GOOG 1 b 2022-11-08, GOOG 1 b 2022-11-08, " +
            "AMZN 1 b 2022-11-08, AMZN 1 b 2022-11-08, AMZN 1 s 2022-11-08]", p1_loaded);
  }

  @Test
  public void test12() throws ParseException {
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("s");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    val_string = new ArrayList<>();
    val_string1 = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("GOOG", val_string1);
    p = new FlexiPortfolioImpl(h);
    user = new FlexiUserImpl();
    user.addNewFlexiPortfolio(h, "p1");
    float x = 0;
    x = user.getFlexiPortfolioValue("p1", "2022-11-09");
    Map<String, Float> maps = new HashMap<>();
    maps.put("AAPL", 50f);
    maps.put("GOOGL", 50f);
    System.out.println(user.getPortfolio("p1"));
    assertEquals(348.34002685546875, x, 0);
  }

  @Test
  public void testExistingPortfolio() throws IOException, ParserConfigurationException,
          TransformerException {
    user = new FlexiUserImpl();
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string.add("1");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("s");
    val_string.add("1");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    p = new FlexiPortfolioImpl(h);
    user.addNewFlexiPortfolio(h, "p1");
    assertEquals("[AMZN 1.0 b 2022-11-08, AMZN 1.0 b 2022-11-08, AMZN 1.0 s 2022-11-08]",
            user.getPortfolio("p1").toString());
    Map<String, List<String>> d = new HashMap<>();
    List<String> li = new ArrayList<>();
    li.add("2022-1-1");
    li.add("2022-03-22");
    li.add("100");
    li.add("30");
    li.add("AAPL:50f,GOOG:50f");
    li.add("5");
    d.put("d1", li);
    Map<String, List<String>> d1 = new HashMap<>();
    List<String> li1 = new ArrayList<>();
    li1.add("2022-1-1");
    li1.add("2023-03-22");
    li1.add("100");
    li1.add("30");
    li1.add("MSFT:50f,VZ:50f");
    li1.add("10");
    d1.put("d2", li1);
    user.addNewDollarCostAvg(d, "p1");
    user.saveFlexiPortfolio("p1");
    assertEquals("[AMZN 1.0 b 2022-11-08, AMZN 1.0 b 2022-11-08, AMZN 1.0 s 2022-11-08," +
                    " GOOG 0.0155516155 b 2022-01-01, AAPL 0.2534212 b 2022-01-01, " +
                    "GOOG 0.016580876 b 2022-01-31, AAPL 0.25746652 b 2022-01-31, " +
                    "GOOG 0.016697401 b 2022-03-02, AAPL 0.27017292 b 2022-03-02]",
            user.getPortfolio("p1").toString());
  }

  @Test
  public void testNotExistingPortfolio() throws FileNotFoundException, ParserConfigurationException,
          TransformerException {
    user = new FlexiUserImpl();
    Map<String, List<String>> d = new HashMap<>();
    List<String> li = new ArrayList<>();
    li.add("2022-1-1");
    li.add("2023-03-22");
    li.add("1000");
    li.add("100");
    li.add("AAPL:50f,GOOG:50f");
    li.add("3");
    d.put("d1", li);
    user.addNewDollarCostAvg(d, "p3");
    assertEquals("[GOOG 0.17175895 b 2022-01-01, AAPL 2.798896 b 2022-01-01, " +
            "GOOG 0.19145355 b 2022-04-11, AAPL 2.9984918 b 2022-04-11, GOOG 4.3330426 b " +
            "2022-07-20, AAPL 3.247517 b 2022-07-20, GOOG 5.1459928 b 2022-10-28, " +
            "AAPL 3.191216 b 2022-10-28]", user.getPortfolio("p3").toString());
  }

  @Test
  public void testExistingPortfolioForMultipleDCA() throws IOException,
          ParserConfigurationException, TransformerException {
    user = new FlexiUserImpl();
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-10");
    val_string.add("s");
    val_string.add("1");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    p = new FlexiPortfolioImpl(h);
    user.addNewFlexiPortfolio(h, "p1");
    assertEquals("[AMZN 1.0 b 2022-11-08, AMZN 1.0 s 2022-11-10]",
            user.getPortfolio("p1").toString());
    Map<String, List<String>> d = new HashMap<>();
    List<String> li = new ArrayList<>();
    li.add("2022-1-1");
    li.add("2022-03-22");
    li.add("100");
    li.add("30");
    li.add("AAPL:50f,GOOG:50f");
    li.add("5");
    d.put("d1", li);
    Map<String, List<String>> d1 = new HashMap<>();
    List<String> li1 = new ArrayList<>();
    li1.add("2022-1-1");
    li1.add("2023-03-22");
    li1.add("100");
    li1.add("150");
    li1.add("MSFT:50f,VZ:50f");
    li1.add("10");
    d1.put("d2", li1);
    user.addNewDollarCostAvg(d, "p1");
    user.addNewDollarCostAvg(d1, "p1");
    user.saveFlexiPortfolio("p1");
    assertEquals("[AMZN 1.0 b 2022-11-08, AMZN 1.0 s 2022-11-10, GOOG 0.0155516155 b " +
                    "2022-01-01, AAPL 0.2534212 b 2022-01-01, GOOG 0.016580876 b 2022-01-31, " +
                    "AAPL 0.25746652 b 2022-01-31, GOOG 0.016697401 b 2022-03-02, " +
                    "AAPL 0.27017292 b 2022-03-02, MSFT 0.11893435 b 2022-01-01, " +
                    "VZ 0.76982296 b 2022-01-01, MSFT 0.14712915 b 2022-05-31, " +
                    "VZ 0.7798791 b 2022-05-31, MSFT 0.16958494 b 2022-10-28, " +
                    "VZ 1.0618529 b 2022-10-28]",
            user.getPortfolio("p1").toString());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNonExisitingAddDollarSingle() throws FileNotFoundException,
          ParserConfigurationException, TransformerException {
    user = new FlexiUserImpl();
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2022-10-08");
    val_string.add("b");
    val_string.add("10");
    val_string.add("4");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-11");
    val_string.add("b");
    val_string.add("10");
    val_string.add("4");
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    p = new FlexiPortfolioImpl(h);
    user.addNewFlexiPortfolio(h, "p1");
    Map<String, List<String>> d1 = new HashMap<>();
    List<String> li1 = new ArrayList<>();
    li1.add("2022-1-1");
    li1.add("100");
    li1.add("AAPL:50f,GOOG:50f");
    li1.add("3");
    d1.put("d2", li1);
    user.addNewDollarCostAvgSingle(d1, "p3");
  }

  @Test
  public void testExisitingAddDollarSingle() throws FileNotFoundException,
          ParserConfigurationException, TransformerException {
    user = new FlexiUserImpl();
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2022-10-08");
    val_string.add("b");
    val_string.add("10");
    val_string.add("4");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-11");
    val_string.add("b");
    val_string.add("10");
    val_string.add("4");
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    p = new FlexiPortfolioImpl(h);
    user.addNewFlexiPortfolio(h, "p1");
    Map<String, List<String>> d1 = new HashMap<>();
    List<String> li1 = new ArrayList<>();
    li1.add("2022-1-1");
    li1.add("100");
    li1.add("AAPL:50f,GOOG:50f");
    li1.add("3");
    d1.put("d2", li1);
    user.addNewDollarCostAvgSingle(d1, "p1");
    assertEquals("[AMZN 10.0 b 2022-10-08, AMZN 10.0 b 2022-11-11, GOOG 0.016242798 b " +
                    "2022-01-01, AAPL 0.26468435 b 2022-01-01]",
            user.getPortfolio("p1").toString());
  }

  @Test
  public void testAddDollarSingleAndDouble() throws FileNotFoundException,
          ParserConfigurationException, TransformerException {
    user = new FlexiUserImpl();
    Map<String, List<String>> d = new HashMap<>();
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2022-11-08");
    val_string.add("b");
    val_string.add("1");
    val_string.add("1");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-10");
    val_string.add("s");
    val_string.add("1");
    val_string.add("1");
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    p = new FlexiPortfolioImpl(h);
    user.addNewFlexiPortfolio(h, "p3");
    List<String> li = new ArrayList<>();
    li.add("2022-1-1");
    li.add("2023-03-22");
    li.add("150");
    li.add("30");
    li.add("VZ:50f,PG:50f");
    li.add("5");
    d.put("d1", li);
    Map<String, List<String>> d1 = new HashMap<>();
    List<String> li1 = new ArrayList<>();
    li1.add("2022-1-1");
    li1.add("100");
    li1.add("AAPL:50f,GOOG:50f");
    li1.add("10");
    d1.put("d2", li1);
    user.addNewDollarCostAvgSingle(d1, "p3");
    user.addNewDollarCostAvg(d, "p3");
    assertEquals("[AMZN 1.0 b 2022-11-08, AMZN 1.0 s 2022-11-10, " +
                    "GOOG 0.013823658 b 2022-01-01, AAPL 0.22526327 b 2022-01-01, " +
                    "VZ 1.3471901 b 2022-01-01, PG 0.42792517 b 2022-01-01, " +
                    "VZ 1.3150479 b 2022-01-31, PG 0.43627298 b 2022-01-31, " +
                    "VZ 1.2929442 b 2022-03-02, PG 0.45516616 b 2022-03-02, " +
                    "VZ 1.3430545 b 2022-04-01, PG 0.45135084 b 2022-04-01, " +
                    "VZ 1.5118791 b 2022-05-01, PG 0.43600124 b 2022-05-01, " +
                    "VZ 1.3647884 b 2022-05-31, PG 0.47335675 b 2022-05-31, " +
                    "VZ 1.3793104 b 2022-06-30, PG 0.4868211 b 2022-06-30, " +
                    "VZ 1.5154796 b 2022-07-30, PG 0.5039234 b 2022-07-30, " +
                    "VZ 1.6155088 b 2022-08-29, PG 0.49403626 b 2022-08-29, " +
                    "VZ 1.7771008 b 2022-09-28, PG 0.5303834 b 2022-09-28, " +
                    "VZ 1.8582428 b 2022-10-28, PG 0.5176749 b 2022-10-28, " +
                    "VZ 1.7939517 b 2022-11-27, PG 0.47709924 b 2022-11-27]",
            user.getPortfolio("p3").toString());
  }

  @Test
  public void testDCACostBasis() throws ParseException, FileNotFoundException,
          ParserConfigurationException, TransformerException {
    Map<String, Map<String, Float>> cache = new HashMap<>();
    user = new FlexiUserImpl();
    float costBasis;
    float costBasisFuture;
    Map<String, List<String>> d = new HashMap<>();
    List<String> li = new ArrayList<>();
    li.add("2022-03-10");
    li.add("2023-03-22");
    li.add("100");
    li.add("30");
    // li.add("AAPL:50f,GOOG:50f");
    li.add("AAPL:20f,GOOG:20f,AMZN:20f,MSFT:20f,TSLA:20f");
    li.add("5");
    d.put("d1", li);
    user.addNewDollarCostAvg(d, "p1");
    costBasisFuture = user.calculateDCAPortfolioCostBasis("p1",
            "2023-09-15", cache);
    assertEquals(1072.292724609375, costBasisFuture, 0);
    costBasis = user.calculateDCAPortfolioCostBasis("p1",
            "2022-09-15", cache);
    assertEquals(522.283203125, costBasis, 0);
  }

  @Test
  public void testNotExistingPortfolioIntervals() throws FileNotFoundException,
          ParserConfigurationException, TransformerException {
    user = new FlexiUserImpl();
    Map<String, List<String>> d = new HashMap<>();
    List<String> li = new ArrayList<>();
    li.add("2022-1-1");
    li.add("2023-03-22");
    li.add("1000");
    li.add("100");
    li.add("AAPL:50f,GOOG:50f");
    li.add("3");
    d.put("d1", li);
    user.addNewDollarCostAvg(d, "p3");
    LocalDate dateS = LocalDate.of(2022, 3, 3);
    assertEquals("{GOOG=0.17175895, AAPL=2.798896}",
            user.getPortfolio("p3").getComposition(dateS).toString());
    LocalDate dateS1 = LocalDate.of(2022, 5, 5);
    assertEquals("{GOOG=0.3632125, AAPL=5.797388}",
            user.getPortfolio("p3").getComposition(dateS1).toString());
    LocalDate dateS2 = LocalDate.of(2022, 10, 10);
    assertEquals("{GOOG=4.696255, AAPL=9.044905}",
            user.getPortfolio("p3").getComposition(dateS2).toString());
  }

  @Test
  public void testExistingPortfolioIntervals() throws FileNotFoundException,
          ParserConfigurationException, TransformerException {
    user = new FlexiUserImpl();
    List<List<String>> val_string1 = new ArrayList<>();
    List<String> val_string = new ArrayList<>();
    val_string.add("2022-10-08");
    val_string.add("b");
    val_string.add("10");
    val_string.add("4");
    Map<String, List<List<String>>> h = new HashMap<>();
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2022-11-11");
    val_string.add("b");
    val_string.add("10");
    val_string.add("4");
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    p = new FlexiPortfolioImpl(h);
    user.addNewFlexiPortfolio(h, "p1");
    Map<String, List<String>> d = new HashMap<>();
    List<String> li = new ArrayList<>();
    li.add("2022-1-1");
    li.add("2023-03-22");
    li.add("1000");
    li.add("100");
    li.add("AAPL:50f,GOOG:50f");
    li.add("3");
    d.put("d1", li);
    user.addNewDollarCostAvg(d, "p3");
    LocalDate dateS = LocalDate.of(2022, 3, 3);
    assertEquals("{GOOG=0.17175895, AAPL=2.798896}",
            user.getPortfolio("p3").getComposition(dateS).toString());
    LocalDate dateS1 = LocalDate.of(2022, 5, 5);
    assertEquals("{GOOG=0.3632125, AAPL=5.797388}",
            user.getPortfolio("p3").getComposition(dateS1).toString());
    LocalDate dateS2 = LocalDate.of(2022, 10, 10);
    assertEquals("{GOOG=4.696255, AAPL=9.044905}",
            user.getPortfolio("p3").getComposition(dateS2).toString());
  }
}
