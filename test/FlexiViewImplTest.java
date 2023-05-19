import org.junit.Before;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import model.Portfolio;
import view.FlexiView;
import view.View;
import view.ViewImpl;

/**
 * A JUnit test class for the Flexible ViewImpl class.
 */
public class FlexiViewImplTest {
  private ByteArrayInputStream in;
  private StringBuilder log;

  @Before
  public void setUp() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    View view = new ViewImpl(out);
  }


  /**
   * A MockFlexiView class to mock test the FlexiController.
   */
  static class MockFlexiView implements FlexiView {

    private StringBuilder log;

    public MockFlexiView(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void selectPortfolioType() {
      log.append("selectPortfolioType method called ");
    }

    @Override
    public void showFlexibleMenu() {
      log.append("showFlexibleMenu method called ");
    }

    @Override
    public void enterTicker() {
      log.append("enterTicker method called ");
    }

    @Override
    public void enterBuyDate() {
      log.append("enterBuyDate method called ");
    }

    @Override
    public void enterNOS() {
      log.append("enterNOS method called ");
    }

    @Override
    public void enterSellTicker() {
      log.append("enterSellTicker method called ");
    }

    @Override
    public void enterSellDate() {
      log.append("enterSellDate method called ");
    }

    @Override
    public void enterSellQuantity() {
      log.append("enterSellQuantity method called ");
    }

    @Override
    public void stockSold() {
      log.append("stockSold method called ");
    }

    @Override
    public void stockNotSold() {
      log.append("stockNotSold method called ");
    }

    @Override
    public void sellAnotherStock() {
      log.append("sellAnotherStock method called ");
    }

    @Override
    public void displayCostBasis(float cb, String portfolioName) {
      log.append("displayCostBasis method called with cb=")
              .append(cb)
              .append(" and portfolioName=")
              .append(portfolioName);
    }

    @Override
    public void showPortfolio(String ticker, int noOfShares) {
      log.append("showPortfolio method called with ticker=")
              .append(ticker)
              .append(" and noOfShares=")
              .append(noOfShares);
    }

    @Override
    public void showError(String err) {
      log.append("showError method called with err=")
              .append(err);
    }

    @Override
    public void enterPlotStartDate() {
      log.append("enterPlotStartDate method called ");
    }

    @Override
    public void enterPlotEndDate() {
      log.append("enterPlotEndDate method called ");
    }

    @Override
    public void futureDate() {
      log.append("futureDate method called ");
    }

    @Override
    public void show(String g) {
      log.append("showGraph method called with g=").append(g).append(" ");
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
      log.append("printPortfolio method called with p=")
              .append(p);
    }

    @Override
    public void enterDate() {
      log.append("enterDate method called ");
    }

    @Override
    public void printTotalValue(float total) {
      log.append("printTotalValue method called with total=")
              .append(total);
    }

    @Override
    public void printMapElement(Map.Entry<String, List<Float>> mapElement) {
      log.append("printMapElement method called with mapElement=")
              .append(mapElement);
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
      log.append("loadPortfolioSuccessful method called with portfolioName=")
              .append(portfolioName);
    }

    @Override
    public void savePortfolioSuccessful(String portfolioName) {
      log.append("savePortfolioSuccessful method called with portfolioName=")
              .append(portfolioName);
    }
  }
}
