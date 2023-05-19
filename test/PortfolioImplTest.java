import model.Portfolio;
import model.PortfolioImpl;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the PortfolioImpl class.
 */
public class PortfolioImplTest {
  private Portfolio p;

  @Test
  public void test1() {
    Map<String, Float> h = new HashMap<>();
    h.put("GOOG", 10F);
    h.put("MSFT", 20F);
    h.put("AMZN", 30F);
    h.put("APPL", 40F);
    p = new PortfolioImpl(h);
    assertEquals("[MSFT 20, GOOG 10, APPL 40, AMZN 30]", p.toString());
  }

  @Test
  public void test2() {
    Map<String, Float> h = new HashMap<>();
    h.put("GOOG", 10F);
    h.put("MSFT", 20F);
    h.put("AMZN", 30F);
    h.put("APPL", 40F);
    p = new PortfolioImpl(h);
    assertEquals("{MSFT=20.0, GOOG=10.0, APPL=40.0, AMZN=30.0}", p.getComposition().toString());
  }

  @Test
  public void test3() {
    Map<String, Float> h = new HashMap<>();
    h.put("GOOG", 10F);
    h.put("MSFT", 20F);
    h.put("AMZN", 30F);
    h.put("APPL", 40F);
    p = new PortfolioImpl(h);
    assertEquals(4, p.getNoOfStocks());
  }

  @Test
  public void test4() {
    Map<String, Float> h = new HashMap<>();
    p = new PortfolioImpl(h);
    assertEquals("[]", p.toString());
  }

  @Test
  public void test5() {
    Map<String, Float> h = new HashMap<>();
    p = new PortfolioImpl(h);
    assertEquals("{}", p.getComposition().toString());
  }

  @Test
  public void test6() {
    Map<String, Float> h = new HashMap<>();
    p = new PortfolioImpl(h);
    assertEquals(0, p.getNoOfStocks());
  }
}
