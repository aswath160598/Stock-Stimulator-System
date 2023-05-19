import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.TickerValidator;
import model.TickerValidatorImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for the TickerValidatorImpl class.
 */
public class TickerValidatorImplTest {
  TickerValidator tv;

  @Before
  public void setUp() throws IOException {
    tv = new TickerValidatorImpl("stock_master_file.csv");
    tv.readFile();
  }

  @Test
  public void checkTickerTest1() {
    assertTrue(tv.checkTicker("GOOG"));
  }

  @Test
  public void checkTickerTest2() {
    assertTrue(tv.checkTicker("goog"));
  }

  @Test
  public void checkTickerTest3() {
    assertFalse(tv.checkTicker("GOOGO"));
  }

  @Test
  public void checkTickerTest4() {
    assertFalse(tv.checkTicker("googo"));
  }

  @Test
  public void checkTickerTest5() {
    assertFalse(tv.checkTicker(""));
  }

  @Test
  public void checkTickerTest6() {
    assertFalse(tv.checkTicker(null));
  }

  @Test
  public void checkTickerTest7() {
    assertFalse(tv.checkTicker("123"));
  }

  @Test
  public void checkTickerTest8() {
    assertFalse(tv.checkTicker("0"));
  }

  @Test
  public void checkTickerTest9() {
    assertFalse(tv.checkTicker("1"));
  }
}
