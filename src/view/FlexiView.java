package view;

/**
 * This Interface represents the View Module of our Flexible Portfolio Project.
 */
public interface FlexiView extends View {
  /**
   * Function to display Message which Portfolio type to be chosen.
   */
  void selectPortfolioType();

  /**
   * Function to show flexible menu to the user for the flexible portfolio.
   */
  void showFlexibleMenu();

  /**
   * Function to display Message to enter a ticker symbol.
   */
  void enterTicker();

  /**
   * Function to display Message prompting user to enter buy date of a particular stock in the
   * flexible Portfolio.
   */
  void enterBuyDate();

  /**
   * Function to display Message prompting user to enter number of stocks of a particular stock
   * in the flexible Portfolio.
   */
  void enterNOS();

  /**
   * Function to display Message prompting user to enter ticker symbol of a particular stock in the
   * flexible Portfolio to be sold.
   */
  void enterSellTicker();

  /**
   * Function to display Message prompting user to enter sell date of a particular stock in the
   * flexible Portfolio to be sold.
   */
  void enterSellDate();

  /**
   * Function to display Message prompting user to enter sell quantity of a particular stock in the
   * flexible Portfolio to be sold.
   */
  void enterSellQuantity();

  /**
   * Function to display Message prompting user to enter ticker symbol of a particular stock in the
   * flexible Portfolio to be sold.
   */
  void stockSold();

  /**
   * Function to display Message prompting user that the particular stock cannot be sold.
   */
  void stockNotSold();

  /**
   * Function to display Message prompting user to ask if he wants another stock to be sold.
   */
  void sellAnotherStock();

  /**
   * Function to display Message prompting user about the cost basis of the flexible Portfolio.
   *
   * @param cb            cost basis of the portfolio
   * @param portfolioName name of the portfolio
   */
  void displayCostBasis(float cb, String portfolioName);

  /**
   * Function to display the flexible portfolio.
   *
   * @param ticker     stock ticker
   * @param noOfShares in portfolio
   */
  void showPortfolio(String ticker, int noOfShares);

  /**
   * Function to show error message to the user.
   *
   * @param err the error message to be displayed.
   */
  void showError(String err);

  /**
   * Function to request user to enter the start date for plotting the graph.
   */
  void enterPlotStartDate();

  /**
   * Function to request user to enter the end date for plotting the graph.
   */
  void enterPlotEndDate();

  /**
   * Function to prompt user that the date cannot be in the future.
   */
  void futureDate();

  void show(String g);
}
