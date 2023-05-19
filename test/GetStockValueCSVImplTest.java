import model.GetStockValueCSV;
import model.GetStockValueCSVImpl;

import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the GetStockValueCSVImpl class.
 */
public class GetStockValueCSVImplTest {
  private GetStockValueCSV p;

  @Test
  public void test1() throws ParseException, IOException {
    p = new GetStockValueCSVImpl();
    float x = p.getStockPrice("GOOG", "2022-10-25");
    assertEquals("104.93", String.valueOf(x));
  }

  @Test
  public void testWeekendSunday() throws ParseException, IOException {
    p = new GetStockValueCSVImpl();
    float x = p.getStockPrice("GOOG", "2022-10-23");
    assertEquals("101.48", String.valueOf(x));
  }

  @Test
  public void testWeekendSaturday() throws ParseException, IOException {
    p = new GetStockValueCSVImpl();
    float x = p.getStockPrice("GOOG", "2022-10-22");
    assertEquals("101.48", String.valueOf(x));
  }

  @Test
  public void testWeekFriday() throws ParseException, IOException {
    p = new GetStockValueCSVImpl();
    float x = p.getStockPrice("GOOG", "2022-10-22");
    assertEquals("101.48", String.valueOf(x));
  }

  @Test(expected = ParseException.class)
  public void testInvalidDate() throws ParseException, IOException {
    p = new GetStockValueCSVImpl();
    float x = p.getStockPrice("GOOG", "2022/10/22");
  }

  @Test(expected = ParseException.class)
  public void testInvalidDate1() throws ParseException, IOException {
    p = new GetStockValueCSVImpl();
    float x = p.getStockPrice("GOOG", "2022,10,22");
  }

  @Test
  public void test2() throws ParseException, IOException {
    p = new GetStockValueCSVImpl();
    float x = p.getStockPrice("AMZN", "2022-10-17");
    assertEquals("113.79", String.valueOf(x));
  }
}
