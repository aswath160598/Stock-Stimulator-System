import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import model.DollarCostAveraging;
import model.DollarCostAveragingImpl;
import model.DollarCostAveragingSingle;
import model.DollarCostAveragingSingleImpl;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the DollarCostAveragingImplTest.
 */
public class DollarCostAveragingImplTest {
  @Test
  public void test1() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    LocalDate d1 = LocalDate.of(2022, 10, 10);
    Map<String, Float> hm = new HashMap<>();
    hm.put("goog", 30f);
    hm.put("amzn", 70f);
    DollarCostAveraging abc = new DollarCostAveragingImpl("p1", d, d1,
            1020f, 30, hm, 5f);
    assertEquals("p1 2020-01-01 2022-10-10 1020.0 30{goog=30.0, amzn=70.0}",
            abc.toString());
  }

  @Test
  public void test2() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    Map<String, Float> hm = new HashMap<>();
    hm.put("goog", 30f);
    hm.put("amzn", 70f);
    DollarCostAveragingSingle abc = new DollarCostAveragingSingleImpl("p1", d,
            1020f, hm, 5f);
    assertEquals("p1 2020-01-01 1020.0 {goog=30.0, amzn=70.0}",
            abc.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoreProportion() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    Map<String, Float> hm = new HashMap<>();
    hm.put("goog", 120f);
    hm.put("amzn", 70f);
    DollarCostAveragingSingle abc = new DollarCostAveragingSingleImpl("p1", d,
            1020f, hm, 5f);
    assertEquals("p1 2020-01-01 1020.0 {goog=120.0, amzn=70.0}",
            abc.toString());
  }

  @Test
  public void testFutureDate() {
    LocalDate d = LocalDate.of(2024, 1, 1);
    Map<String, Float> hm = new HashMap<>();
    hm.put("goog", 120f);
    hm.put("amzn", 70f);
    DollarCostAveragingSingle abc = new DollarCostAveragingSingleImpl("p1", d,
            1020f, hm, 5f);
    assertEquals("p1 2020-01-01 1020.0 {goog=120.0, amzn=70.0}",
            abc.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartDateEndDate() {
    LocalDate sDate = LocalDate.of(2023, 1, 1);
    LocalDate eDate = LocalDate.of(2020, 10, 10);
    Map<String, Float> hm = new HashMap<>();
    hm.put("goog", 30f);
    hm.put("amzn", 70f);
    DollarCostAveraging abc = new DollarCostAveragingImpl("p1", sDate, eDate,
            1020f, 30, hm, 5f);
    assertEquals("p1 2020-01-01 2022-10-10 1020.0 30{goog=30.0, amzn=70.0}",
            abc.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLessProportion2() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    Map<String, Float> hm = new HashMap<>();
    hm.put("goog", 10f);
    hm.put("amzn", 30f);
    DollarCostAveragingSingle abc = new DollarCostAveragingSingleImpl("p1", d,
            1020f, hm, 5f);
    assertEquals("p1 2020-01-01 1020.0 {goog=120.0, amzn=70.0}",
            abc.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCommissionFee() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    Map<String, Float> hm = new HashMap<>();
    hm.put("goog", 10f);
    hm.put("amzn", 30f);
    DollarCostAveragingSingle abc = new DollarCostAveragingSingleImpl("p1", d,
            1020f, hm, -5f);
    assertEquals("p1 2020-01-01 1020.0 {goog=120.0, amzn=70.0}",
            abc.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCommissionFee1() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    Map<String, Float> hm = new HashMap<>();
    hm.put("goog", 10f);
    hm.put("amzn", 30f);
    DollarCostAveragingSingle abc = new DollarCostAveragingSingleImpl("p1", d,
            1020f, hm, 0);
    assertEquals("p1 2020-01-01 1020.0 {goog=120.0, amzn=70.0}",
            abc.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTicker() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    Map<String, Float> hm = new HashMap<>();
    hm.put("ancvvg", 10f);
    hm.put("hhj", 30f);
    DollarCostAveragingSingle abc = new DollarCostAveragingSingleImpl("p1", d,
            1020f, hm, 0);
    assertEquals("p1 2020-01-01 1020.0 {goog=120.0, amzn=70.0}",
            abc.toString());
  }

  @Test
  public void testDCAInvalidProportion() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    LocalDate d1 = LocalDate.of(2022, 10, 10);
    Map<String, Float> hm = new HashMap<>();
    hm.put("goog", 30f);
    hm.put("amzn", 100f);
    DollarCostAveraging abc = new DollarCostAveragingImpl("p1", d, d1,
            1020f, 30, hm, 5f);
    assertEquals("p1 2020-01-01 2022-10-10 1020.0 30{goog=30.0, amzn=70.0}",
            abc.toString());
  }

  @Test
  public void testDCAMoreProportion() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    LocalDate d1 = LocalDate.of(2022, 10, 10);
    Map<String, Float> hm = new HashMap<>();
    hm.put("goog", 30f);
    hm.put("amzn", 100f);
    DollarCostAveraging abc = new DollarCostAveragingImpl("p1", d, d1,
            1020f, 30, hm, 5f);
    assertEquals("p1 2020-01-01 2022-10-10 1020.0 30{goog=30.0, amzn=70.0}",
            abc.toString());
  }

  @Test
  public void testDCALessProportion() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    LocalDate d1 = LocalDate.of(2022, 10, 10);
    Map<String, Float> hm = new HashMap<>();
    hm.put("goog", 30f);
    hm.put("amzn", 20f);
    DollarCostAveraging abc = new DollarCostAveragingImpl("p1", d, d1,
            1020f, 30, hm, 5f);
    assertEquals("p1 2020-01-01 2022-10-10 1020.0 30{goog=30.0, amzn=70.0}",
            abc.toString());
  }

  @Test
  public void testDCAInvalidTicker() {
    LocalDate d = LocalDate.of(2020, 1, 1);
    LocalDate d1 = LocalDate.of(2022, 10, 10);
    Map<String, Float> hm = new HashMap<>();
    hm.put("fecd", 30f);
    hm.put("cef", 20f);
    DollarCostAveraging abc = new DollarCostAveragingImpl("p1", d, d1,
            1020f, 30, hm, 5f);
    assertEquals("p1 2020-01-01 2022-10-10 1020.0 30{goog=30.0, amzn=70.0}",
            abc.toString());
  }
}
