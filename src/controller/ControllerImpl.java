package controller;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.TickerValidator;
import model.TickerValidatorImpl;
import view.View;
import model.User;

/**
 * This class represents our Controller. It implements all the controller functions
 * and interacts with the view and the model.
 */
public class ControllerImpl implements Controller {
  private View view;
  private User user;
  private Scanner sc;

  /**
   * This represents the Controller implementation function.
   *
   * @param user  user object
   * @param view  view object
   * @param input input object
   */
  public ControllerImpl(User user, View view, InputStream input) {
    this.user = user;
    this.view = view;
    this.sc = new Scanner(input);
  }

  /**
   * Protected constructor to be used by child classes.
   *
   * @param user object
   * @param view view object
   * @param sc   scanner object
   */
  protected ControllerImpl(User user, View view, Scanner sc) {
    this.user = user;
    this.view = view;
    this.sc = sc;
  }

  /**
   * Default Constructor for the controller implementation.
   */
  public ControllerImpl() {
    //A default Constructor to be used in the controller Implementation.
  }

  @Override
  public void display1() {
    view.welcomeMessage();
  }

  @Override
  public void display2() {
    view.showMainMenu();
    String userInputString = sc.next();
    int userInput;
    try {
      userInput = Integer.parseInt(userInputString);
      while (userInput > 0 && userInput <= 6) {
        if (userInput == 1) {
          addPortfolio();
          view.showMainMenu();
          userInput = Integer.parseInt(sc.next());
        } else if (userInput == 2) {
          displayPortfolio();
          view.showMainMenu();
          userInput = Integer.parseInt(sc.next());
        } else if (userInput == 3) {
          getTotalPortfolioValue();
          view.showMainMenu();
          userInput = Integer.parseInt(sc.next());
        } else if (userInput == 4) {
          savePortfolio();
          view.showMainMenu();
          userInput = Integer.parseInt(sc.next());
        } else if (userInput == 5) {
          controllerLoadPortfolio();
          view.showMainMenu();
          userInput = Integer.parseInt(sc.next());
        } else if (userInput == 6) {
          saveAllPortfolios();
          break;
        }
      }
    } catch (NumberFormatException e) {
      view.invalidUserInput();
      display2();
    }
  }

  @Override
  public void addPortfolio() {
    Map<String, Float> h = new HashMap<>();
    String name;

    try {
      TickerValidator tv = new TickerValidatorImpl("stock_master_file.csv");
      tv.readFile();

      view.askPortfolioName();
      name = sc.next();

      while (user.checkPortfolioExists(name)) {
        view.portfolioExists();
        name = sc.next();
      }

      String a;
      String b = "y";
      while (!b.equals("n")) {
        view.enterTickerNOS();
        a = sc.next();

        if (tv.checkTicker(a)) {
          float s1 = Float.parseFloat(sc.next());
          if (s1 - Math.floor(s1) > 0) {
            view.invalidUserInput();
            continue;
          }
          float s = s1;

          if (s > 0) {
            if (h.containsKey(a)) {
              h.put(a, h.get(a) + s);
            } else {
              h.put(a, s);
            }
            view.continueAddingStocks();
            b = sc.next();
          } else {
            view.invalidNOS();
          }
        } else {
          view.invalidTicker();
          sc.next();
        }
      }
      user.addNewPortfolio(h, name);
    } catch (IOException e) {
      view.tickerValidatorFileMissing();
    }
  }

  @Override
  public void displayPortfolio() {
    view.askPortfolioName();
    String input2 = sc.next();
    if (user.getPortfolio(input2) != null) {
      view.printPortfolio(user.getPortfolio(input2));
    } else {
      view.invalidPortfolioName();
    }
  }

  @Override
  public void savePortfolio() {
    view.askPortfolioName();
    String input2 = sc.next();
    if (!user.checkPortfolioExists(input2)) {
      view.invalidPortfolioName();
    } else {
      try {
        user.savePortfolio(input2);
        view.savePortfolioSuccessful(input2);
      } catch (FileNotFoundException e) {
        view.portfolioFileMissing();
      } catch (ParserConfigurationException | TransformerException e) {
        view.invalidXMLFormat();
      }
    }
  }

  @Override
  public void saveAllPortfolios() {
    List<String> portfolioNames = user.getAllPortfolioNames();
    for (String portfolioName : portfolioNames) {
      try {
        user.savePortfolio(portfolioName);
        view.savePortfolioSuccessful(portfolioName);
      } catch (FileNotFoundException e) {
        view.portfolioFileMissing();
      } catch (ParserConfigurationException | TransformerException e) {
        view.invalidXMLFormat();
      }
    }
  }

  @Override
  public void getTotalPortfolioValue() {
    Map<String, List<Float>> val;
    List<Float> f;
    view.askPortfolioName();
    float sum = 0;
    String input2 = (sc.next());
    view.enterDate();
    String sDate1 = sc.next();
    if (!user.checkPortfolioExists(input2)) {
      view.invalidPortfolioName();
    } else {
      try {
        val = user.getPortfolioValue(user.getPortfolio(input2), sDate1);
        view.showPortfolioValueHeading();
        for (Map.Entry<String, List<Float>> mapElement : val.entrySet()) {
          view.printMapElement(mapElement);
        }
        sum = user.getPortfolioSumValue(val);
        view.printTotalValue(sum);
      } catch (ParseException e) {
        view.invalidDateFormat();
      } catch (IndexOutOfBoundsException e) {
        view.invalidDate();
      } catch (IOException e) {
        view.csvStockPriceNotFound();
      }
    }
  }

  @Override
  public void controllerLoadPortfolio() {
    view.askPortfolioName();
    String input2 = sc.next();
    if (!user.checkPortfolioFileExists(input2)) {
      view.invalidPortfolioName();
    } else {
      try {
        user.loadPortfolio(input2);
        view.loadPortfolioSuccessful(input2);
      } catch (IllegalArgumentException e) {
        view.invalidTicker();
      } catch (IOException e) {
        view.portfolioFileMissing();
      } catch (SAXException | ParserConfigurationException e) {
        view.invalidXMLFormat();
      }
    }
  }

  @Override
  public List<String> getPortfolioNames() {
    return user.getAllPortfolioNames();
  }
}
