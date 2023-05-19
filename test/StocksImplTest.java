import model.GetStockValueCSV;
import model.GetStockValueCSVImpl;
import model.Stocks;
import model.StocksImpl;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the StocksImpl class.
 */
public class StocksImplTest {
  private Stocks s;
  private StringBuilder log;
  private ByteArrayOutputStream bytes;
  private PrintStream out;
  private ByteArrayInputStream in;

  @Before
  public void setUp() {
    s = StocksImpl.getBuilder().noOfShares(6).costPrice(7).ticker("GOOG").create();
  }

  @Test
  public void builderCreateTest1() {
    s = StocksImpl.getBuilder().noOfShares(6).costPrice(7).ticker("GOOG").create();
    assertEquals("GOOG 6", s.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderCreateTest2() {
    s = StocksImpl.getBuilder().noOfShares(-3).costPrice(7).ticker("GOOG").create();
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderCreateTest3() {
    s = StocksImpl.getBuilder().noOfShares(3).costPrice(-17).ticker("GOOG").create();
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderCreateTest4() {
    s = StocksImpl.getBuilder().noOfShares(-3).costPrice(-17).ticker("GOOG").create();
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderCreateTest5() {
    s = StocksImpl.getBuilder().noOfShares(-3).costPrice(-17).ticker("").create();
  }

  @Test
  public void builderCreateTest6() {
    s = StocksImpl.getBuilder().create();
    assertEquals("null 0", s.toString());
  }

  @Test
  public void builderCreateTest7() {
    s = StocksImpl.getBuilder().costPrice(7).ticker("GOOG").create();
    assertEquals("GOOG 0", s.toString());
  }

  @Test
  public void builderCreateTest8() {
    s = StocksImpl.getBuilder().noOfShares(6).ticker("GOOG").create();
    assertEquals("GOOG 6", s.toString());
  }

  @Test
  public void builderCreateTest9() {
    s = StocksImpl.getBuilder().ticker("GOOG").create();
    assertEquals("GOOG 0", s.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderCreateTest10() {
    s = StocksImpl.getBuilder().ticker(null).create();
  }

  @Test
  public void getTickerTest1() {
    s = StocksImpl.getBuilder().noOfShares(6).ticker("GOOG").create();
    assertEquals("GOOG", s.getTicker());
  }

  @Test
  public void getTickerTest2() {
    s = StocksImpl.getBuilder().noOfShares(6).ticker("a").create();
    assertEquals("a", s.getTicker());
  }

  @Test
  public void getNoOfSharesTest1() {
    s = StocksImpl.getBuilder().noOfShares(6).ticker("GOOG").create();
    assertEquals(6, s.getNoOfShares(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNoOfSharesTest2() {
    s = StocksImpl.getBuilder().noOfShares(0).ticker("GOOG").create();
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNoOfSharesTest3() {
    s = StocksImpl.getBuilder().noOfShares(-10).ticker("GOOG").create();
  }

  @Test
  public void apiValueTest() throws ParseException, IOException {
    GetStockValueCSV t = new GetStockValueCSVImpl();
    assertEquals(101.48, t.getStockPrice("GOOG", "2022-10-23"), 0.01);
  }
}
