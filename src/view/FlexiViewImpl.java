package view;

import java.io.PrintStream;

/**
 * This class represent the view Module for the flexible Portfolio which implements all the
 * methods of the View interface.
 */
public class FlexiViewImpl extends ViewImpl implements FlexiView {

  private final PrintStream out;

  public FlexiViewImpl() {
    this.out = System.out;
  }

  public FlexiViewImpl(PrintStream out) {
    super(out);
    this.out = out;
  }

  @Override
  public void selectPortfolioType() {
    this.out.println("Please select an option:");
    this.out.println("1.) Operate on an inflexible portfolio");
    this.out.println("2.) Operate on a flexible portfolio");
  }

  @Override
  public void showFlexibleMenu() {
    this.out.println("\nPlease Enter select option");
    this.out.println("1.)  Buy a stock");
    this.out.println("2.)  Sell a stock");
    this.out.println("3.)  View a Portfolio");
    this.out.println("4.)  Save a Portfolio");
    this.out.println("5.)  Load a Portfolio");
    this.out.println("6.)  Determine the cost basis");
    this.out.println("7.)  Determine the value");
    this.out.println("8.)  Plot portfolio performance");
    this.out.println("9.)  Show all portfolios");
    this.out.println("0.)  Go to previous menu");
  }

  @Override
  public void enterTicker() {
    this.out.println("Enter ticker");
  }

  @Override
  public void enterBuyDate() {
    this.out.println("Enter buy date in format yyyy-mm-dd.");
  }

  @Override
  public void enterNOS() {
    this.out.println("Enter number of shares");
  }

  @Override
  public void enterSellTicker() {
    this.out.println("Enter ticker of stock to be sold");
  }

  @Override
  public void enterSellDate() {
    this.out.println("Enter the sell date in format yyyy-mm-dd.");
  }

  @Override
  public void enterSellQuantity() {
    this.out.println("Enter no. of shares to be sold.");
  }

  @Override
  public void stockSold() {
    this.out.println("STOCK SOLD!");
  }

  @Override
  public void stockNotSold() {
    this.out.println("Stock cannot be sold.");
  }

  @Override
  public void sellAnotherStock() {
    this.out.println("Do you want to sell another stock?");
  }

  @Override
  public void displayCostBasis(float cb, String portfolioName) {
    this.out.println("Cost Basis of portfolio " + portfolioName
            + " is $" + String.format("%.02f", cb));
  }

  @Override
  public void showPortfolio(String ticker, int noOfShares) {
    this.out.println(ticker + " " + noOfShares);
  }

  @Override
  public void showError(String err) {
    this.out.println(err);
  }

  @Override
  public void enterPlotStartDate() {
    this.out.println("Enter the start date for the plot");
  }

  @Override
  public void enterPlotEndDate() {
    this.out.println("Enter the end date for the plot");
  }

  @Override
  public void futureDate() {
    this.out.println("Date cannot be in the future.");
  }

  @Override
  public void show(String g) {
    this.out.println(g);
  }
}
