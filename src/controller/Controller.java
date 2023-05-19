package controller;

import java.util.List;

/**
 * This Interface represents the Controller Module of our Portfolio Project.
 */
public interface Controller {
  /**
   * Displays the Welcome Message.
   */
  void display1();

  /**
   * Displays the Main menu to the User inorder for the User to choose his desired option.
   */
  void display2();

  /**
   * Add a new portfolio by the User.
   */
  void addPortfolio();

  /**
   * Display already created or loaded Portfolio.
   */
  void displayPortfolio();

  /**
   * Save a Portfolio after creating the portfolio object.
   */
  void savePortfolio();

  /**
   * Save all created portfolios.
   */
  void saveAllPortfolios();

  /**
   * Function to calculate the total value of a Portfolio.
   */
  void getTotalPortfolioValue();

  /**
   * Function to load a Portfolio from an xml file.
   */
  void controllerLoadPortfolio();

  List<String> getPortfolioNames();
}
