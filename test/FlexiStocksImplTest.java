import org.junit.Test;

import java.time.LocalDate;

import model.FlexiStocks;
import model.FlexiStocksImpl;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the Flexible StocksImpl class.
 */
public class FlexiStocksImplTest {
  FlexiStocks s;

  @Test
  public void test1() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    s = FlexiStocksImpl.getBuilder1().noOfShares(10).ticker("GOOG")
            .buyDate(d).status("s").create();
    assertEquals("2020-01-01", s.getBuyDate().toString());
  }
}
