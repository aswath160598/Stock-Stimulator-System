package view;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import model.Portfolio;

/**
 * This class represent the view Module which implements all the methods of the View interface.
 */
public class ViewImpl implements View {

  private final PrintStream out;

  public ViewImpl() {
    out = System.out;
  }

  public ViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void welcomeMessage() {
    this.out.println("Hi, Welcome to your portfolio");
  }

  @Override
  public void showMainMenu() {
    this.out.println("\nPlease Enter your option");
    this.out.println("1.) Add a new Portfolio");
    this.out.println("2.) View a Portfolio");
    this.out.println("3.) Show the total value of a Portfolio");
    this.out.println("4.) Save a Portfolio");
    this.out.println("5.) Load a Portfolio");
    this.out.println("6.) Exit the program");
    this.out.println("0.) Go to previous menu");
  }

  @Override
  public void askPortfolioName() {
    this.out.println("Enter Portfolio name");
  }

  @Override
  public void portfolioExists() {
    this.out.println("Portfolio already exists. Please enter a new name.");
  }

  @Override
  public void invalidPortfolioName() {
    this.out.println("Enter a valid Portfolio Name");
  }

  @Override
  public void enterTickerNOS() {
    this.out.println("Enter Stock ticker and number of shares");
  }

  @Override
  public void continueAddingStocks() {
    this.out.println("Do you want to continue adding Stocks?");
  }

  @Override
  public void invalidNOS() {
    this.out.println("Enter valid number of shares");
  }

  @Override
  public void invalidTicker() {
    this.out.println("Invalid ticker entered");
  }

  @Override
  public void printPortfolio(Portfolio p) {
    this.out.println(p);
  }

  @Override
  public void enterDate() {
    this.out.println("Enter the date in format yyyy-mm-dd.");
  }

  @Override
  public void printTotalValue(float total) {
    this.out.println("The Total Value is : $" + String.format("%.02f", total));
  }

  @Override
  public void printMapElement(Map.Entry<String, List<Float>> mapElement) {
    this.out.println(mapElement.getKey() + " " + mapElement.getValue());
  }

  @Override
  public void showPortfolioValueHeading() {
    this.out.println("\nTicker [unit_Stock_Price, no_Of_Shares, stock_Value]");
  }

  @Override
  public void invalidDateFormat() {
    this.out.println("Invalid date format. Please enter date in yyyy-mm-dd format.");
  }

  @Override
  public void invalidUserInput() {
    this.out.println("Invalid user input entered.");
  }

  @Override
  public void invalidDate() {
    this.out.println("Invalid date entered.");
  }

  // new
  @Override
  public void tickerValidatorFileMissing() {
    this.out.println("Can not find ticker validator file.");
  }

  @Override
  public void portfolioFileMissing() {
    this.out.println("Can not find portfolio file.");
  }

  @Override
  public void invalidXMLFormat() {
    this.out.println("Portfolio file is in invalid format.");
  }

  @Override
  public void csvStockPriceNotFound() {
    this.out.println("Stock Price CSV File not found.");
  }

  @Override
  public void loadPortfolioSuccessful(String portfolioName) {
    this.out.println("Portfolio " + portfolioName + " successfully loaded.");
  }

  @Override
  public void savePortfolioSuccessful(String portfolioName) {
    this.out.println("Portfolio " + portfolioName + " saved.");
  }
}
