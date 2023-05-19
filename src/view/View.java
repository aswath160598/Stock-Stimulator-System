package view;

import java.util.List;
import java.util.Map;

import model.Portfolio;

/**
 * This Interface represents the View Module of our Portfolio Project.
 */
public interface View {

  /**
   * Display the Welcome Message to the User.
   */
  void welcomeMessage();

  /**
   * Display the Main Menu to the User.
   */
  void showMainMenu();

  /**
   * Message to the User asking for Portfolio name.
   */
  void askPortfolioName();

  /**
   * Display the  Message to the User saying that the portfolio already exists and
   * prompting to enter a new Portfolio name.
   */
  void portfolioExists();

  /**
   * Display the Invalid Portfolio name Message to the User.
   */
  void invalidPortfolioName();

  /**
   * Message requesting the User to enter protfolio ticker and number of shares.
   */
  void enterTickerNOS();

  /**
   * Message to ask user whether he wants to continue adding stocks.
   */
  void continueAddingStocks();

  /**
   * Message asking user to enter valid number of shares.
   */
  void invalidNOS();

  /**
   * Message prompting the user that invalid ticker has been entered.
   */
  void invalidTicker();

  /**
   * Function to print Portfolio.
   *
   * @param p portfolio to be printed
   */
  void printPortfolio(Portfolio p);

  /**
   * Message asking the user to enter the date.
   */
  void enterDate();

  /**
   * Display the Portfolio value to the User.
   *
   * @param total portfolio value
   */
  void printTotalValue(float total);

  /**
   * Display the computer Portfolio Stocks to the User.
   *
   * @param mapElement to be printed
   */
  void printMapElement(Map.Entry<String, List<Float>> mapElement);

  /**
   * Display the heading of Portfolio Stocks to the User.
   */
  void showPortfolioValueHeading();

  /**
   * Display the Invalid Date Format Message to the User.
   */
  void invalidDateFormat();

  /**
   * Display the Invalid input Message to the User.
   */
  void invalidUserInput();

  /**
   * Display the Invalid Date Message to the User.
   */
  void invalidDate();

  /**
   * Display the Ticker CSV file missing Message to the User.
   */
  void tickerValidatorFileMissing();

  /**
   * Display the Portfolio file missing Message to the User.
   */
  void portfolioFileMissing();

  /**
   * Display the Invalid XML Format Message to the User.
   */
  void invalidXMLFormat();

  /**
   * Display the CSV Stock Price not found Message to the User.
   */
  void csvStockPriceNotFound();

  /**
   * Display the Message to the User prompting that the Portfolio is loaded successfully.
   *
   * @param portfolioName to be loaded
   */
  void loadPortfolioSuccessful(String portfolioName);

  /**
   * Display the Message to the User prompting that the Portfolio is saved successfully.
   *
   * @param portfolioName name of the portfolio to be saved
   */
  void savePortfolioSuccessful(String portfolioName);
}
