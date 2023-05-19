import model.FlexiPortfolio;
import model.FlexiPortfolioImpl;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for the Flexible Portfolio Implementation class.
 */
public class FlexiPortfolioImplTest {
  private FlexiPortfolio p;


  @Test
  public void createTest1() {
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
    p = new FlexiPortfolioImpl(h);
    assertEquals("{GOOG=14.0}", p.getComposition(LocalDate.of(2022,
            2, 1)).toString().trim());
  }

  @Test
  public void createTest2() {
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
    val_string1 = new ArrayList<>();
    val_string = new ArrayList<>();
    val_string.add("2020-08-01");
    val_string.add("b");
    val_string.add("53");
    val_string1.add(val_string);
    val_string = new ArrayList<>();
    val_string.add("2020-08-05");
    val_string.add("s");
    val_string.add("21");
    val_string1.add(val_string);
    h.put("AMZN", val_string1);
    p = new FlexiPortfolioImpl(h);
    assertEquals("{GOOG=14.0, AMZN=32.0}", p.getComposition(LocalDate.of(2022,
            2, 1)).toString().trim());
  }

  @Test
  public void sellTest1() {
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
    p = new FlexiPortfolioImpl(h);
    LocalDate sd = LocalDate.of(2022, 1, 1);
    assertTrue(p.validateSell(sd, "GOOG", 12F));
  }
}
