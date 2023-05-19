package view;

import java.util.List;

import controller.Features;
import utilities.GUIBarChart;

/**
 * This Interface represents the View Module for the GUI of our Portfolio Project.
 */
public interface IView {
  /**
   * Function to add features and to abstract controller.
   *
   * @param features the features object
   */
  void addFeatures(Features features);

  /**
   * Function to view cost basis menu.
   *
   * @param portfolios the list of portfolio
   */
  void viewCostBasisMenu(List<String> portfolios);

  /**
   * Function to view fixed amount strategy menu.
   *
   * @param portfolios the list of portfolio
   */
  void viewFixedAmountMenu(List<String> portfolios);

  /**
   * Function to view fixed portfolio value menu.
   *
   * @param portfolios the list of portfolios
   */
  void viewPortfolioValueMenu(List<String> portfolios);

  /**
   * Function to plot graph of a portfolio.
   *
   * @param portfolios the list of portfolios
   */
  void viewPlotGraphMenu(List<String> portfolios);

  /**
   * Function to view buy menu.
   */
  void viewBuyMenu();

  /**
   * Function to view main menu.
   */
  void mainMenu();

  /**
   * Function to view sell menu.
   *
   * @param portfolios the list of portfolios
   */
  void viewSellMenu(List<String> portfolios);

  /**
   * Function to show warning messages.
   *
   * @param message the message
   */
  void showWarningMessage(String message);

  /**
   * Function to show information messages.
   *
   * @param message the message
   */
  void showInformationMessage(String message);

  /**
   * Function to clear buy menu fields.
   */
  void clearBuyPanelTextFields();

  /**
   * Function to clear sell menu fields.
   */
  void clearSellPanelTextFields();

  /**
   * Function to view portfolio menu fields.
   *
   * @param portfolios the list of portfolios
   */
  void viewPortfolioMenu(List<String> portfolios);

  /**
   * Function to view portfolio menu fields.
   *
   * @param portfolios the list of portfolios
   */
  void viewSaveMenu(List<String> portfolios);

  /**
   * Function to view load menu fields.
   */
  void viewLoadMenu();

  /**
   * Function to view Dollar Cost Averaging menu fields.
   */
  void viewDCAMenu();

  /**
   * Function to show the bar chart.
   *
   * @param chart the chart
   */
  void showChart(GUIBarChart chart);
}
