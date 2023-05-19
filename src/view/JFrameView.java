package view;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.InputVerifier;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import controller.Features;
import model.TickerValidator;
import model.TickerValidatorImpl;
import utilities.GUIBarChart;
import utilities.StdDraw;

/**
 * This class represent the GUI View Module for the flexible Portfolio which implements all the
 * methods of the IView interface.
 */
public class JFrameView extends JFrame implements IView {

  private final JButton buyButton;
  private final JButton sellButton;
  private final JButton viewPortfolio;
  private final JButton savePortfolio;
  private final JButton loadPortfolio;
  private final JButton viewCostBasis;
  private final JButton viewPortfolioValue;
  private final JButton plotGraph;
  private final JButton dollarCostAveraging;
  private final JButton investFixedAmount;
  private final JButton buyBackButton;
  private final JButton sellBackButton;
  private final JButton viewPortfolioBackButton;
  private final JButton savePortfolioBackButton;
  private final JButton loadPortfolioBackButton;
  private final JButton dcaBackButton;
  private final JButton cbBackButton;
  private final JButton addFixedAmountBackButton;
  private final JButton portfolioValueBackButton;
  private final JButton plotGraphBackButton;
  private final JButton exitButton;
  private final JPanel parentPanel;
  private final JTextField buyTicker;
  private final JTextField buyPortfolio;
  private final JTextField buyDate;
  private final JTextField buyNos;
  private final JTextField buyCommission;
  private final JButton buy;
  private final JTextField sellTicker;
  private final JTextField sellPortfolio;
  private final JTextField sellDate;
  private final JTextField sellNos;
  private final JTextField sellCommission;
  private final JComboBox<String> sellPortfoliosDropDown;
  private final JButton sell;

  private final JTextField viewPortfolioDate;
  private final JComboBox<String> viewPortfolioNameDropDown;
  private final JButton view;

  private final JComboBox<String> savePortfolioNameDropDown;
  private final JButton save;

  private final JTextField loadPortfolioName;
  private final JButton load;


  private final JTextField dcaName;
  private final JTextField dcaPortfolioName;
  private final JTextField dcaStartDate;
  private final JTextField dcaEndDate;
  private final JTextField dcaInvestmentAmount;
  private final JTextField dcaInterval;
  private final JTextField dcaPropMap;
  private final JTextField dcaCommission;
  private final JButton dcaAdd;


  private final JTextField cbDate;
  private final JComboBox<String> cbPortfolioNameDropDown;
  private final JButton calCB;


  private final JTextField fixedAmountName;
  private final JTextField fixedAmountDate;
  private final JTextField fixedAmountInvestmentAmount;
  private final JTextField fixedAmountPropMap;
  private final JTextField fixedAmountCommission;
  private final JComboBox<String> fixedAmountDropDown;
  private final JButton fixedAmountAdd;

  private final JTextField portfolioValueDate;
  private final JComboBox<String> portfolioValueDropDown;
  private final JButton calValue;

  private final JTextField plotGraphStartDate;
  private final JTextField plotGraphEndDate;
  private final JComboBox<String> plotPortfolioDropDown;
  private final JButton plot;


  private final CardLayout cardLayout;


  private Boolean buyPortfolioNameValid;
  private Boolean buyTickerValid;
  private Boolean buyDateValid;
  private Boolean buyNosValid;
  private Boolean buyCommissionValid;

  private Boolean sellPortfolioNameValid;
  private Boolean sellTickerValid;
  private Boolean sellDateValid;
  private Boolean sellNosValid;
  private Boolean sellCommissionValid;

  private Boolean viewPortfolioNameValid;
  private Boolean viewDateValid;

  private Boolean savePortfolioNameValid;

  private Boolean loadPortfolioNameValid;

  private Boolean dcaNameValid;
  private Boolean dcaPortfolioNameValid;
  private Boolean dcaStartDateValid;
  private Boolean dcaEndDateValid;
  private Boolean dcaInvestmentAmountValid;
  private Boolean dcaIntervalValid;
  private Boolean dcaPropMapValid;
  private Boolean dcaCommissionValid;

  private Boolean cbPortfolioNameValid;
  private Boolean cbDateValid;

  private Boolean fixedAmountNameValid;
  private Boolean fixedAmountPortfolioNameValid;
  private Boolean fixedAmountDateValid;
  private Boolean fixedAmountInvestmentAmountValid;
  private Boolean fixedAmountPropMapValid;
  private Boolean fixedAmountCommissionValid;

  private Boolean portfolioValuePortfolioNameValid;
  private Boolean portfolioValueDateValid;

  private Boolean plotGraphPortfolioNameValid;
  private Boolean plotGraphStartDateValid;
  private Boolean plotGraphEndDateValid;


  /**
   * A constructor for JFrameView class. It will initialize all the components of the View GUI.
   *
   * @param caption for the JFrame
   */
  public JFrameView(String caption) {
    super(caption);


    buyPortfolioNameValid = false;
    buyTickerValid = false;
    buyDateValid = false;
    buyNosValid = false;
    buyCommissionValid = false;

    sellPortfolioNameValid = false;
    sellTickerValid = false;
    sellDateValid = false;
    sellNosValid = false;
    sellCommissionValid = false;

    viewPortfolioNameValid = false;
    viewDateValid = false;

    savePortfolioNameValid = false;

    loadPortfolioNameValid = false;

    dcaNameValid = false;
    dcaPortfolioNameValid = false;
    dcaStartDateValid = false;
    dcaEndDateValid = false;
    dcaInvestmentAmountValid = false;
    dcaIntervalValid = false;
    dcaPropMapValid = false;
    dcaCommissionValid = false;

    cbPortfolioNameValid = false;
    cbDateValid = false;

    fixedAmountNameValid = false;
    fixedAmountPortfolioNameValid = false;
    fixedAmountDateValid = false;
    fixedAmountInvestmentAmountValid = false;
    fixedAmountPropMapValid = false;
    fixedAmountCommissionValid = false;

    portfolioValuePortfolioNameValid = false;
    portfolioValueDateValid = false;

    plotGraphPortfolioNameValid = false;
    plotGraphStartDateValid = false;
    plotGraphEndDateValid = false;


    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    cardLayout = new CardLayout();

    JPanel menuPanel = new JPanel();
    menuPanel.setLayout(new GridLayout(0, 3, 1, 1));
    // menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    parentPanel = new JPanel();
    parentPanel.setLayout(cardLayout);

    // menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

    buyButton = new JButton("Buy a Stock");
    buyButton.setActionCommand("Buy a Stock");
    menuPanel.add(buyButton);

    sellButton = new JButton("Sell a Stock");
    sellButton.setActionCommand("Sell a Stock");
    menuPanel.add(sellButton);

    viewPortfolio = new JButton("View a Portfolio");
    viewPortfolio.setActionCommand("View a Portfolio");
    menuPanel.add(viewPortfolio);

    savePortfolio = new JButton("Save a Portfolio");
    savePortfolio.setActionCommand("Save a Portfolio");
    menuPanel.add(savePortfolio);

    loadPortfolio = new JButton("Load a Portfolio");
    loadPortfolio.setActionCommand("Load a Portfolio");
    menuPanel.add(loadPortfolio);

    viewCostBasis = new JButton("View Cost Basis");
    viewCostBasis.setActionCommand("View Cost Basis");
    menuPanel.add(viewCostBasis);

    viewPortfolioValue = new JButton("View Portfolio Value");
    viewPortfolioValue.setActionCommand("View Portfolio Value");
    menuPanel.add(viewPortfolioValue);

    plotGraph = new JButton("Plot Graph");
    plotGraph.setActionCommand("Plot Graph");
    menuPanel.add(plotGraph);

    dollarCostAveraging = new JButton("Dollar Cost Averaging");
    dollarCostAveraging.setActionCommand("Dollar Cost Averaging");
    menuPanel.add(dollarCostAveraging);

    investFixedAmount = new JButton("Invest a Fixed Amount");
    investFixedAmount.setActionCommand("investFixedAmount");
    menuPanel.add(investFixedAmount);

    buyBackButton = new JButton("Back");
    buyBackButton.setActionCommand("Back");

    sellBackButton = new JButton("Back");
    sellBackButton.setActionCommand("Back");

    viewPortfolioBackButton = new JButton("Back");
    viewPortfolioBackButton.setActionCommand("Back");

    savePortfolioBackButton = new JButton("Back");
    savePortfolioBackButton.setActionCommand("Back");

    loadPortfolioBackButton = new JButton("Back");
    loadPortfolioBackButton.setActionCommand("Back");

    dcaBackButton = new JButton("Back");
    dcaBackButton.setActionCommand("Back");

    cbBackButton = new JButton("Back");
    cbBackButton.setActionCommand("Back");

    addFixedAmountBackButton = new JButton("Back");
    addFixedAmountBackButton.setActionCommand("Back");

    portfolioValueBackButton = new JButton("Back");
    portfolioValueBackButton.setActionCommand("Back");

    plotGraphBackButton = new JButton("Back");
    plotGraphBackButton.setActionCommand("Back");

    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit");
    menuPanel.add(exitButton);

    // menuPanel.setVisible(true);
    // parentPanel.add(menuPanel);
    parentPanel.add(menuPanel, "menuPanel");

    JLabel buyTickerValidationLabel = new JLabel();
    buyTickerValidationLabel.setForeground(Color.red);
    JLabel buyNosValidationLabel = new JLabel();
    buyNosValidationLabel.setForeground(Color.red);
    JLabel buyDateValidationLabel = new JLabel();
    buyDateValidationLabel.setForeground(Color.red);
    JLabel buyPortfolioValidationLabel = new JLabel();
    buyPortfolioValidationLabel.setForeground(Color.red);
    JLabel buyCommissionValidationLabel = new JLabel();
    buyCommissionValidationLabel.setForeground(Color.red);

    JLabel buyPortfolioNameLabel = new JLabel("Enter Portfolio Name");
    JLabel buyTickerLabel = new JLabel("Enter ticker");
    JLabel buyDateLabel = new JLabel("Enter buy date (yyyy-mm-dd)");
    JLabel buyNosLabel = new JLabel("Enter no. of shares");
    JLabel buyCommissionLabel = new JLabel("Enter the commission fee for the transaction $");

    buyTicker = new JTextField(10);
    buyTicker.setToolTipText("Enter the stock ticker here (e.g. GOOG)");
    buyTicker.setInputVerifier(new TickerInputVerifier(buyTickerValidationLabel, "buy"));
    buyPortfolio = new JTextField(10);
    buyPortfolio.setToolTipText("Enter the Portfolio Name here (e.g. p1)");
    buyPortfolio.setInputVerifier(new PortfolioNameVerifier(buyPortfolioValidationLabel,
            "buy"));
    buyDate = new JTextField(10);
    buyDate.setToolTipText("Enter the buy date here (e.g. 2022-11-28)");
    buyDate.setInputVerifier(new DateInputVerifier(buyDateValidationLabel, "buy"));
    buyNos = new JTextField(10);
    buyNos.setToolTipText("Enter the number of shares here (e.g. 10)");
    buyNos.setInputVerifier(new NosInputVerifier(buyNosValidationLabel, "buy"));
    buyCommission = new JTextField(10);
    buyCommission.setToolTipText("Enter the commission fee for the transaction here (e.g. 1)");
    buyCommission.setInputVerifier(new DcaInvestmentAmountInputVerifier(
            buyCommissionValidationLabel, "buyCommission")
    );

    buy = new JButton("Add to Portfolio");
    sell = new JButton("Sell Stock");


    JPanel buyPanel = new JPanel();
    buyPanel.setLayout(new GridLayout(0, 3, 1, 1));
    buyPanel.add(buyPortfolioNameLabel);
    buyPanel.add(buyPortfolio);
    buyPanel.add(buyPortfolioValidationLabel);
    buyPanel.add(buyTickerLabel);
    buyPanel.add(buyTicker);
    buyPanel.add(buyTickerValidationLabel);
    buyPanel.add(buyDateLabel);
    buyPanel.add(buyDate);
    buyPanel.add(buyDateValidationLabel);
    buyPanel.add(buyNosLabel);
    buyPanel.add(buyNos);
    buyPanel.add(buyNosValidationLabel);
    buyPanel.add(buyCommissionLabel);
    buyPanel.add(buyCommission);
    buyPanel.add(buyCommissionValidationLabel);
    buyPanel.add(buy);
    buyPanel.add(buyBackButton);


    JLabel sellTickerValidationLabel = new JLabel();
    sellTickerValidationLabel.setForeground(Color.red);
    JLabel sellNosValidationLabel = new JLabel();
    sellNosValidationLabel.setForeground(Color.red);
    JLabel sellDateValidationLabel = new JLabel();
    sellDateValidationLabel.setForeground(Color.red);
    JLabel sellPortfolioValidationLabel = new JLabel();
    sellPortfolioValidationLabel.setForeground(Color.red);
    JLabel sellCommissionValidationLabel = new JLabel();
    sellCommissionValidationLabel.setForeground(Color.red);

    sellPortfoliosDropDown = new JComboBox<>();
    sellPortfoliosDropDown.setToolTipText("Select Portfolio Name");

    JLabel sellPortfolioNameLabel = new JLabel("Enter Portfolio Name");
    JLabel sellTickerLabel = new JLabel("Enter ticker");
    JLabel sellDateLabel = new JLabel("Enter sell date (yyyy-mm-dd)");
    JLabel sellNosLabel = new JLabel("Enter no. of shares");
    JLabel sellCommissionLabel = new JLabel("Enter the commission fee for the transaction $");

    sellTicker = new JTextField(10);
    sellTicker.setToolTipText("Enter the stock ticker here (e.g. GOOG)");
    sellTicker.setInputVerifier(new TickerInputVerifier(sellTickerValidationLabel, "sell"));
    sellPortfolio = new JTextField(10);
    sellPortfolio.setToolTipText("Enter the Portfolio Name here (e.g. p1)");
    sellPortfolio.setInputVerifier(new PortfolioNameVerifier(sellPortfolioValidationLabel,
            "sell"));
    sellDate = new JTextField(10);
    sellDate.setToolTipText("Enter the sell date here (e.g. 2022-11-28)");
    sellDate.setInputVerifier(new DateInputVerifier(sellDateValidationLabel, "sell"));
    sellNos = new JTextField(10);
    sellNos.setToolTipText("Enter the number of shares here (e.g. 10)");
    sellNos.setInputVerifier(new NosInputVerifier(sellNosValidationLabel, "sell"));
    sellCommission = new JTextField(10);
    sellCommission.setToolTipText("Enter the commission fee for the transaction here (e.g. 1)");
    sellCommission.setInputVerifier(new DcaInvestmentAmountInputVerifier(
            sellCommissionValidationLabel, "sellCommission")
    );


    JPanel sellPanel = new JPanel();
    sellPanel.setLayout(new GridLayout(0, 3, 1, 1));
    sellPanel.add(sellPortfolioNameLabel);
    // sellPanel.add(sellPortfolio);
    sellPanel.add(sellPortfoliosDropDown);
    sellPanel.add(sellPortfolioValidationLabel);
    sellPanel.add(sellTickerLabel);
    sellPanel.add(sellTicker);
    sellPanel.add(sellTickerValidationLabel);
    sellPanel.add(sellDateLabel);
    sellPanel.add(sellDate);
    sellPanel.add(sellDateValidationLabel);
    sellPanel.add(sellNosLabel);
    sellPanel.add(sellNos);
    sellPanel.add(sellNosValidationLabel);
    sellPanel.add(sellCommissionLabel);
    sellPanel.add(sellCommission);
    sellPanel.add(sellCommissionValidationLabel);
    sellPanel.add(sell);
    sellPanel.add(sellBackButton);


    JLabel viewPortfolioNameLabel = new JLabel("Enter Portfolio Name");
    JLabel viewPortfolioNameValidationLabel = new JLabel();
    viewPortfolioNameValidationLabel.setForeground(Color.red);
    JLabel viewPortfolioDateLabel = new JLabel("Enter date");
    JLabel viewPortfolioDateValidationLabel = new JLabel();
    viewPortfolioDateValidationLabel.setForeground(Color.red);

    JTextField viewPortfolioName = new JTextField(10);
    viewPortfolioName.setToolTipText("Enter the Portfolio Name here (e.g. p1)");
    viewPortfolioName.setInputVerifier(new PortfolioNameVerifier(viewPortfolioNameValidationLabel,
            "view"));
    viewPortfolioDate = new JTextField(10);
    viewPortfolioDate.setToolTipText("Enter the date here (e.g. 2022-11-28)");
    viewPortfolioDate.setInputVerifier(new DateInputVerifier(viewPortfolioDateValidationLabel,
            "view"));

    view = new JButton("View Portfolio");

    viewPortfolioNameDropDown = new JComboBox<>();
    viewPortfolioNameDropDown.setPreferredSize(new Dimension(200, 400));
    viewPortfolioNameDropDown.setToolTipText("Select Portfolio Name");

    JPanel viewPanel = new JPanel();
    // viewPanel.setLayout(new GridLayout(0, 3, 1, 1));
    viewPanel.add(viewPortfolioNameLabel);
    // viewPanel.add(viewPortfolioName);
    viewPanel.add(viewPortfolioNameDropDown);
    viewPanel.add(viewPortfolioNameValidationLabel);
    viewPanel.add(viewPortfolioDateLabel);
    viewPanel.add(viewPortfolioDate);
    viewPanel.add(viewPortfolioDateValidationLabel);
    viewPanel.add(view);
    viewPanel.add(viewPortfolioBackButton);


    JLabel savePortfolioNameLabel = new JLabel("Enter Portfolio Name");
    JLabel savePortfolioNameValidationLabel = new JLabel();
    savePortfolioNameValidationLabel.setForeground(Color.red);

    JTextField savePortfolioName = new JTextField(10);
    savePortfolioName.setToolTipText("Enter the Portfolio Name here (e.g. p1)");
    savePortfolioName.setInputVerifier(new PortfolioNameVerifier(savePortfolioNameValidationLabel,
            "save"));

    save = new JButton("Save");

    savePortfolioNameDropDown = new JComboBox<>();
    savePortfolioNameDropDown.setPreferredSize(new Dimension(200, 400));
    savePortfolioNameDropDown.setToolTipText("Select Portfolio Name");

    JPanel savePanel = new JPanel();
    savePanel.add(savePortfolioNameLabel);
    // savePanel.add(savePortfolioName);
    savePanel.add(savePortfolioNameDropDown);
    savePanel.add(savePortfolioNameValidationLabel);
    savePanel.add(save);
    savePanel.add(savePortfolioBackButton);


    JLabel loadPortfolioNameLabel = new JLabel("Enter Portfolio Name");
    JLabel loadPortfolioNameValidationLabel = new JLabel();
    loadPortfolioNameValidationLabel.setForeground(Color.red);

    loadPortfolioName = new JTextField(10);
    loadPortfolioName.setToolTipText("Enter the Portfolio Name here (e.g. p1)");
    loadPortfolioName.setInputVerifier(new PortfolioNameVerifier(loadPortfolioNameValidationLabel,
            "load"));

    load = new JButton("Load");

    JPanel loadPanel = new JPanel();
    loadPanel.add(loadPortfolioNameLabel);
    loadPanel.add(loadPortfolioName);
    loadPanel.add(loadPortfolioNameValidationLabel);
    loadPanel.add(load);
    loadPanel.add(loadPortfolioBackButton);


    JLabel dcaNameLabel = new JLabel("Enter dollar cost averaging strategy name");
    dcaName = new JTextField(10);
    dcaName.setToolTipText("Enter the Dollar Cost Averaging Strategy Name here (e.g. d1)");
    // dcaName.setMaximumSize(new Dimension(100, dcaName.getPreferredSize().height));
    JLabel dcaNameValidationLabel = new JLabel();
    dcaNameValidationLabel.setForeground(Color.red);
    dcaName.setInputVerifier(new PortfolioNameVerifier(dcaNameValidationLabel, "dcaName"));
    JLabel dcaPortfolioNameLabel = new JLabel("Enter Portfolio Name");
    dcaPortfolioName = new JTextField(10);
    dcaPortfolioName.setToolTipText("Enter the Portfolio Name "
            + "to which Dollar Cost Averaging Strategy will be applied (e.g. p1)");
    JLabel dcaPortfolioNameValidationLabel = new JLabel();
    dcaPortfolioNameValidationLabel.setForeground(Color.red);
    dcaPortfolioName.setInputVerifier(new PortfolioNameVerifier(dcaPortfolioNameValidationLabel,
            "dcaPortfolioName"));
    JLabel dcaStartDateLabel = new JLabel("Enter start date (yyyy-mm-dd)");
    dcaStartDate = new JTextField(10);
    dcaStartDate.setToolTipText("Enter the start date for "
            + "the Dollar Cost Averaging Strategy (e.g. 2020-03-04)");
    JLabel dcaStartDateValidationLabel = new JLabel();
    dcaStartDateValidationLabel.setForeground(Color.red);
    dcaStartDate.setInputVerifier(new FutureDateInputVerifier(dcaStartDateValidationLabel,
            "dcaStartDate"));
    JLabel dcaEndDateLabel = new JLabel("Enter end date (yyyy-mm-dd) (optional)");
    dcaEndDate = new JTextField(10);
    dcaEndDate.setToolTipText("Enter the end date for the Dollar Cost Averaging Strategy. "
            + "Leave empty if no end date can be specified. (e.g. 2025-03-04)");
    JLabel dcaEndDateValidationLabel = new JLabel();
    dcaEndDateValidationLabel.setForeground(Color.red);
    dcaEndDate.setInputVerifier(new FutureDateInputVerifier(dcaEndDateValidationLabel,
            "dcaEndDate"));
    JLabel dcaInvestmentAmountLabel = new JLabel("Enter amount $");
    dcaInvestmentAmount = new JTextField(10);
    dcaInvestmentAmount.setToolTipText("Enter the Investment amount "
            + "for the Dollar Cost Averaging Strategy (e.g. 1000)");
    JLabel dcaInvestmentAmountValidationLabel = new JLabel();
    dcaInvestmentAmountValidationLabel.setForeground(Color.red);
    dcaInvestmentAmount.setInputVerifier(new DcaInvestmentAmountInputVerifier(
            dcaInvestmentAmountValidationLabel, "dcaInvestmentAmount"));
    JLabel dcaIntervalLabel = new JLabel("Enter the interval (days)");
    dcaInterval = new JTextField(10);
    dcaInterval.setToolTipText("Enter the Interval (in no. of days) "
            + "for the Dollar Cost Averaging Strategy (e.g. 30)");
    JLabel dcaIntervalValidationLabel = new JLabel();
    dcaIntervalValidationLabel.setForeground(Color.red);
    dcaInterval.setInputVerifier(new DcaIntervalInputVerifier(dcaIntervalValidationLabel,
            "dcaInterval"));
    JLabel dcaPropMapLabel = new JLabel("Enter tickers and weightage (e.g. GOOG:10, AMZN:20)");
    dcaPropMap = new JTextField(30);
    dcaPropMap.setToolTipText("Enter the tickers and the weightage using colon "
            + "for stocks in the Dollar Cost Averaging Strategy "
            + "separated by comma (e.g. GOOG:10, AMZN:20)");
    JLabel dcaPropMapValidationLabel = new JLabel();
    dcaPropMapValidationLabel.setForeground(Color.red);
    dcaPropMap.setInputVerifier(new DcaPropMapInputVerifier(dcaPropMapValidationLabel,
            "dcaPropMap"));
    JLabel dcaCommissionLabel = new JLabel("Enter the commission for the "
            + "Dollar Cost Averaging Strategy $");
    dcaCommission = new JTextField(10);
    dcaCommission.setToolTipText("Enter the commission for the "
            + "Dollar Cost Averaging Strategy (e.g. 1)");
    JLabel dcaCommissionValidationLabel = new JLabel();
    dcaCommissionValidationLabel.setForeground(Color.red);
    dcaCommission.setInputVerifier(new DcaInvestmentAmountInputVerifier(
            dcaCommissionValidationLabel, "dcaCommission")
    );

    dcaAdd = new JButton("Add");

    JPanel dcaPanel = new JPanel();
    dcaPanel.setLayout(new GridLayout(0, 3, 1, 1));
    dcaPanel.add(dcaNameLabel);
    dcaPanel.add(dcaName);
    dcaPanel.add(dcaNameValidationLabel);
    dcaPanel.add(dcaPortfolioNameLabel);
    dcaPanel.add(dcaPortfolioName);
    dcaPanel.add(dcaPortfolioNameValidationLabel);
    dcaPanel.add(dcaStartDateLabel);
    dcaPanel.add(dcaStartDate);
    dcaPanel.add(dcaStartDateValidationLabel);
    dcaPanel.add(dcaEndDateLabel);
    dcaPanel.add(dcaEndDate);
    dcaPanel.add(dcaEndDateValidationLabel);
    dcaPanel.add(dcaInvestmentAmountLabel);
    dcaPanel.add(dcaInvestmentAmount);
    dcaPanel.add(dcaInvestmentAmountValidationLabel);
    dcaPanel.add(dcaIntervalLabel);
    dcaPanel.add(dcaInterval);
    dcaPanel.add(dcaIntervalValidationLabel);
    dcaPanel.add(dcaPropMapLabel);
    dcaPanel.add(dcaPropMap);
    dcaPanel.add(dcaPropMapValidationLabel);
    dcaPanel.add(dcaCommissionLabel);
    dcaPanel.add(dcaCommission);
    dcaPanel.add(dcaCommissionValidationLabel);
    dcaPanel.add(dcaAdd);
    dcaPanel.add(dcaBackButton);


    JLabel cbPortfolioNameLabel = new JLabel("Enter portfolio name");
    JTextField cbPortfolioName = new JTextField(10);
    cbPortfolioName.setToolTipText("Enter the Portfolio Name here (e.g. p1)");
    JLabel cbPortfolioNameValidationLabel = new JLabel();
    cbPortfolioNameValidationLabel.setForeground(Color.red);
    cbPortfolioName.setInputVerifier(new PortfolioNameVerifier(cbPortfolioNameValidationLabel,
            "cbPortfolioName"));
    JLabel cbDateLabel = new JLabel("Enter the date");
    cbDate = new JTextField(10);
    cbDate.setToolTipText("Enter the date here (e.g. 2022-11-28)");
    JLabel cbDateValidationLabel = new JLabel();
    cbDateValidationLabel.setForeground(Color.red);
    cbDate.setInputVerifier(new FutureDateInputVerifier(cbDateValidationLabel, "cbDate"));
    calCB = new JButton("Calculate Cost Basis");

    cbPortfolioNameDropDown = new JComboBox<>();
    cbPortfolioNameDropDown.setPreferredSize(new Dimension(200, 400));
    cbPortfolioNameDropDown.setToolTipText("Select Portfolio Name");

    JPanel cbPanel = new JPanel();
    cbPanel.add(cbPortfolioNameLabel);
    // cbPanel.add(cbPortfolioName);
    cbPanel.add(cbPortfolioNameDropDown);
    cbPanel.add(cbPortfolioNameValidationLabel);
    cbPanel.add(cbDateLabel);
    cbPanel.add(cbDate);
    cbPanel.add(cbDateValidationLabel);
    cbPanel.add(calCB);
    cbPanel.add(cbBackButton);


    JLabel fixedAmountNameLabel = new JLabel("Enter Fixed Investment Amount Strategy Name");
    fixedAmountName = new JTextField(10);
    fixedAmountName.setToolTipText("Enter the "
            + "Fixed Investment Amount Strategy Name here (e.g. f1)");
    JLabel fixedAmountNameValidationLabel = new JLabel();
    fixedAmountNameValidationLabel.setForeground(Color.red);
    fixedAmountName.setInputVerifier(new PortfolioNameVerifier(fixedAmountNameValidationLabel,
            "fixedAmountName"));
    JLabel fixedAmountPortfolioNameLabel = new JLabel("Enter Portfolio Name");
    JTextField fixedAmountPortfolioName = new JTextField(10);
    fixedAmountPortfolioName.setToolTipText("Enter the Portfolio Name "
            + "to which Fixed Investment Amount Strategy will be applied (e.g. p1)");
    JLabel fixedAmountPortfolioNameValidationLabel = new JLabel();
    fixedAmountPortfolioNameValidationLabel.setForeground(Color.red);
    fixedAmountPortfolioName.setInputVerifier(new PortfolioNameVerifier(
            fixedAmountPortfolioNameValidationLabel, "fixedAmountPortfolioName"));
    JLabel fixedAmountDateLabel = new JLabel("Enter investment date (yyyy-mm-dd)");
    fixedAmountDate = new JTextField(10);
    fixedAmountDate.setToolTipText("Enter the date for "
            + "the Fixed Investment Amount Strategy (e.g. 2020-03-04)");
    JLabel fixedAmountDateValidationLabel = new JLabel();
    fixedAmountDateValidationLabel.setForeground(Color.red);
    fixedAmountDate.setInputVerifier(new FutureDateInputVerifier(fixedAmountDateValidationLabel,
            "fixedAmountDate"));
    JLabel fixedAmountInvestmentAmountLabel = new JLabel("Enter amount $");
    fixedAmountInvestmentAmount = new JTextField(10);
    fixedAmountInvestmentAmount.setToolTipText("Enter the Investment amount "
            + "for the Fixed Investment Amount Strategy (e.g. 1000)");
    JLabel fixedAmountInvestmentAmountValidationLabel = new JLabel();
    fixedAmountInvestmentAmountValidationLabel.setForeground(Color.red);
    fixedAmountInvestmentAmount.setInputVerifier(new DcaInvestmentAmountInputVerifier(
            fixedAmountInvestmentAmountValidationLabel, "fixedAmountInvestmentAmount"));
    JLabel fixedAmountPropMapLabel = new JLabel("Enter tickers "
            + "and weightage (e.g. GOOG:10, AMZN:20)");
    fixedAmountPropMap = new JTextField(30);
    fixedAmountPropMap.setToolTipText("Enter the tickers and the weightage using colon "
            + "for stocks in the Fixed Investment Amount Strategy "
            + "separated by comma (e.g. GOOG:10, AMZN:20)");
    JLabel fixedAmountPropMapValidationLabel = new JLabel();
    fixedAmountPropMapValidationLabel.setForeground(Color.red);
    fixedAmountPropMap.setInputVerifier(new DcaPropMapInputVerifier(
            fixedAmountPropMapValidationLabel, "fixedAmountPropMap"));
    JLabel fixedAmountCommissionLabel = new JLabel("Enter the commission for the "
            + "Fixed Investment Amount Strategy $");
    fixedAmountCommission = new JTextField(10);
    fixedAmountCommission.setToolTipText("Enter the commission for the "
            + "Fixed Investment Amount Strategy (e.g. 1)");
    JLabel fixedAmountCommissionValidationLabel = new JLabel();
    fixedAmountCommissionValidationLabel.setForeground(Color.red);
    fixedAmountCommission.setInputVerifier(new DcaInvestmentAmountInputVerifier(
            fixedAmountCommissionValidationLabel, "fixedAmountCommission"));
    fixedAmountAdd = new JButton("Add");

    fixedAmountDropDown = new JComboBox<>();
    // fixedAmountDropDown.setPreferredSize(new Dimension(200, 400));
    fixedAmountDropDown.setToolTipText("Select Portfolio Name");

    JPanel fixedAmountPanel = new JPanel();
    fixedAmountPanel.setLayout(new GridLayout(0, 3, 1, 1));
    fixedAmountPanel.add(fixedAmountNameLabel);
    fixedAmountPanel.add(fixedAmountName);
    fixedAmountPanel.add(fixedAmountNameValidationLabel);
    fixedAmountPanel.add(fixedAmountPortfolioNameLabel);
    // fixedAmountPanel.add(fixedAmountPortfolioName);
    fixedAmountPanel.add(fixedAmountDropDown);
    fixedAmountPanel.add(fixedAmountPortfolioNameValidationLabel);
    fixedAmountPanel.add(fixedAmountDateLabel);
    fixedAmountPanel.add(fixedAmountDate);
    fixedAmountPanel.add(fixedAmountDateValidationLabel);
    fixedAmountPanel.add(fixedAmountInvestmentAmountLabel);
    fixedAmountPanel.add(fixedAmountInvestmentAmount);
    fixedAmountPanel.add(fixedAmountInvestmentAmountValidationLabel);
    fixedAmountPanel.add(fixedAmountPropMapLabel);
    fixedAmountPanel.add(fixedAmountPropMap);
    fixedAmountPanel.add(fixedAmountPropMapValidationLabel);
    fixedAmountPanel.add(fixedAmountCommissionLabel);
    fixedAmountPanel.add(fixedAmountCommission);
    fixedAmountPanel.add(fixedAmountCommissionValidationLabel);
    fixedAmountPanel.add(fixedAmountAdd);
    fixedAmountPanel.add(addFixedAmountBackButton);


    JLabel portfolioValuePortfolioNameLabel = new JLabel("Enter portfolio name");
    JTextField portfolioValuePortfolioName = new JTextField(10);
    portfolioValuePortfolioName.setToolTipText("Enter the Portfolio Name here (e.g. p1)");
    JLabel portfolioValuePortfolioNameValidationLabel = new JLabel();
    portfolioValuePortfolioNameValidationLabel.setForeground(Color.red);
    portfolioValuePortfolioName.setInputVerifier(new PortfolioNameVerifier(
            portfolioValuePortfolioNameValidationLabel, "portfolioValuePortfolioName"));
    JLabel portfolioValueDateLabel = new JLabel("Enter the date");
    portfolioValueDate = new JTextField(10);
    portfolioValueDate.setToolTipText("Enter the date here (e.g. 2022-11-28)");
    JLabel portfolioValueDateValidationLabel = new JLabel();
    portfolioValueDateValidationLabel.setForeground(Color.red);
    portfolioValueDate.setInputVerifier(new DateInputVerifier(
            portfolioValueDateValidationLabel, "portfolioValueDate"));
    calValue = new JButton("Calculate Portfolio Value");

    portfolioValueDropDown = new JComboBox<>();
    portfolioValueDropDown.setPreferredSize(new Dimension(200, 400));
    portfolioValueDropDown.setToolTipText("Select Portfolio Name");

    JPanel portfolioValuePanel = new JPanel();
    portfolioValuePanel.add(portfolioValuePortfolioNameLabel);
    // portfolioValuePanel.add(portfolioValuePortfolioName);
    portfolioValuePanel.add(portfolioValueDropDown);
    portfolioValuePanel.add(portfolioValuePortfolioNameValidationLabel);
    portfolioValuePanel.add(portfolioValueDateLabel);
    portfolioValuePanel.add(portfolioValueDate);
    portfolioValuePanel.add(portfolioValueDateValidationLabel);
    portfolioValuePanel.add(calValue);
    portfolioValuePanel.add(portfolioValueBackButton);


    JLabel plotGraphPortfolioNameLabel = new JLabel("Enter portfolio name");
    JTextField plotGraphPortfolioName = new JTextField(10);
    plotGraphPortfolioName.setToolTipText("Enter the Portfolio Name here (e.g. p1)");
    JLabel plotGraphPortfolioNameValidationLabel = new JLabel();
    plotGraphPortfolioNameValidationLabel.setForeground(Color.red);
    plotGraphPortfolioName.setInputVerifier(new PortfolioNameVerifier(
            plotGraphPortfolioNameValidationLabel, "plotGraphPortfolioName"));
    JLabel plotGraphStartDateLabel = new JLabel("Enter start the date");
    plotGraphStartDate = new JTextField(10);
    plotGraphStartDate.setToolTipText("Enter the start date here (e.g. 2022-11-28)");
    JLabel plotGraphStartDateValidationLabel = new JLabel();
    plotGraphStartDateValidationLabel.setForeground(Color.red);
    plotGraphStartDate.setInputVerifier(new DateInputVerifier(
            plotGraphStartDateValidationLabel, "plotGraphStartDate"));
    JLabel plotGraphEndDateLabel = new JLabel("Enter the end date");
    plotGraphEndDate = new JTextField(10);
    plotGraphEndDate.setToolTipText("Enter the end date here (e.g. 2022-12-28)");
    JLabel plotGraphEndDateValidationLabel = new JLabel();
    plotGraphEndDateValidationLabel.setForeground(Color.red);
    plotGraphEndDate.setInputVerifier(new DateInputVerifier(
            plotGraphEndDateValidationLabel, "plotGraphEndDate"));
    plot = new JButton("Plot Graph");

    plotPortfolioDropDown = new JComboBox<>();
    plotPortfolioDropDown.setPreferredSize(new Dimension(200, 400));
    plotPortfolioDropDown.setToolTipText("Select Portfolio Name");

    JPanel graphPanel = new JPanel();
    graphPanel.add(plotGraphPortfolioNameLabel);
    // graphPanel.add(plotGraphPortfolioName);
    graphPanel.add(plotPortfolioDropDown);
    graphPanel.add(plotGraphPortfolioNameValidationLabel);
    graphPanel.add(plotGraphStartDateLabel);
    graphPanel.add(plotGraphStartDate);
    graphPanel.add(plotGraphStartDateValidationLabel);
    graphPanel.add(plotGraphEndDateLabel);
    graphPanel.add(plotGraphEndDate);
    graphPanel.add(plotGraphEndDateValidationLabel);
    graphPanel.add(plot);
    graphPanel.add(plotGraphBackButton);


    parentPanel.add(buyPanel, "buyPanel");
    parentPanel.add(sellPanel, "sellPanel");
    parentPanel.add(viewPanel, "viewPanel");
    parentPanel.add(savePanel, "savePanel");
    parentPanel.add(loadPanel, "loadPanel");
    parentPanel.add(dcaPanel, "dcaPanel");
    parentPanel.add(cbPanel, "cbPanel");
    parentPanel.add(fixedAmountPanel, "fixedAmountPanel");
    parentPanel.add(portfolioValuePanel, "portfolioValuePanel");
    parentPanel.add(graphPanel, "graphPanel");


    // parentPanel.setVisible(true); // is this needed???

    this.setContentPane(parentPanel);
    cardLayout.show(parentPanel, "menuPanel");
    // cardLayout.show(parentPanel,"buyPanel");


    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    buyBackButton.addActionListener(evt -> mainMenu());
    sellBackButton.addActionListener(evt -> mainMenu());
    viewPortfolioBackButton.addActionListener(evt -> mainMenu());
    savePortfolioBackButton.addActionListener(evt -> mainMenu());
    loadPortfolioBackButton.addActionListener(evt -> mainMenu());
    dcaBackButton.addActionListener(evt -> mainMenu());
    cbBackButton.addActionListener(evt -> mainMenu());
    addFixedAmountBackButton.addActionListener(evt -> mainMenu());
    portfolioValueBackButton.addActionListener(evt -> mainMenu());
    plotGraphBackButton.addActionListener(evt -> mainMenu());
    buyButton.addActionListener(evt -> viewBuyMenu());
    sellButton.addActionListener(evt -> features.viewSellMenu());
    viewPortfolio.addActionListener(evt -> features.viewPortfolioMenu());
    savePortfolio.addActionListener(evt -> features.viewSaveMenu());
    loadPortfolio.addActionListener(evt -> viewLoadMenu());
    viewCostBasis.addActionListener(evt -> features.viewCostBasisMenu());
    dollarCostAveraging.addActionListener(evt -> viewDCAMenu());
    investFixedAmount.addActionListener(evt -> features.viewFixedAmountMenu());
    viewPortfolioValue.addActionListener(evt -> features.viewPortfolioValueMenu());
    plotGraph.addActionListener(evt -> features.viewPlotGraphMenu());
    exitButton.addActionListener(evt -> features.exitProgram());
    buy.addActionListener(evt -> {
      if (buyPortfolioNameValid
              && buyTickerValid
              && buyDateValid
              && buyNosValid
              && buyCommissionValid) {
        features.buyClicked(buyPortfolio.getText(), buyTicker.getText(),
                buyDate.getText(), buyNos.getText(), buyCommission.getText());
      }
    });
    sell.addActionListener(evt -> {
      if (sellTickerValid && sellDateValid
              && sellNosValid && sellCommissionValid) {
        features.sellClicked(sellPortfoliosDropDown.getItemAt(
                        sellPortfoliosDropDown.getSelectedIndex()),
                sellTicker.getText(),
                sellDate.getText(),
                sellNos.getText(),
                sellCommission.getText());
      }
    });
    view.addActionListener(evt -> {
      if (viewDateValid) {
        features.viewPortfolio(viewPortfolioNameDropDown
                .getItemAt(viewPortfolioNameDropDown
                        .getSelectedIndex()), viewPortfolioDate.getText());
      }
    });
    save.addActionListener(evt -> features.savePortfolio(savePortfolioNameDropDown
            .getItemAt(savePortfolioNameDropDown.getSelectedIndex())));
    load.addActionListener(evt -> {
      if (loadPortfolioNameValid) {
        features.loadPortfolio(loadPortfolioName.getText());
      }
    });
    dcaAdd.addActionListener(evt -> {
      if (dcaNameValid && dcaPortfolioNameValid && dcaStartDateValid && dcaEndDateValid
              && dcaInvestmentAmountValid && dcaIntervalValid && dcaPropMapValid
              && dcaCommissionValid) {
        try {
          features.addDCA(dcaName.getText(),
                  dcaPortfolioName.getText(),
                  dcaStartDate.getText(),
                  dcaEndDate.getText(),
                  dcaInvestmentAmount.getText(),
                  dcaInterval.getText(),
                  dcaPropMap.getText(),
                  dcaCommission.getText());
        } catch (FileNotFoundException | ParserConfigurationException | TransformerException e) {
          throw new RuntimeException(e);
        }
      }
    });
    calCB.addActionListener(evt -> {
      if (cbDateValid) {
        features.calculateCB(cbPortfolioNameDropDown
                .getItemAt(cbPortfolioNameDropDown
                        .getSelectedIndex()), cbDate.getText());
      }
    });
    fixedAmountAdd.addActionListener(evt -> {
      if (fixedAmountNameValid
              && fixedAmountDateValid
              && fixedAmountInvestmentAmountValid
              && fixedAmountPropMapValid
              && fixedAmountCommissionValid) {
        features.addFixedAmountInvestment(fixedAmountName.getText(),
                fixedAmountDropDown.getItemAt(fixedAmountDropDown.getSelectedIndex()),
                fixedAmountDate.getText(),
                fixedAmountInvestmentAmount.getText(),
                fixedAmountPropMap.getText(),
                fixedAmountCommission.getText());
      }
    });
    calValue.addActionListener(evt -> {
      if (portfolioValueDateValid) {
        features.calculatePortfolioValue(portfolioValueDropDown
                .getItemAt(portfolioValueDropDown
                        .getSelectedIndex()), portfolioValueDate.getText());
      }
    });
    plot.addActionListener(evt -> {
      if (plotGraphStartDateValid && plotGraphEndDateValid) {
        features.plotGraph(plotPortfolioDropDown
                        .getItemAt(plotPortfolioDropDown
                                .getSelectedIndex()),
                plotGraphStartDate.getText(),
                plotGraphEndDate.getText());
      }
    });
  }

  @Override
  public void viewBuyMenu() {
    cardLayout.show(parentPanel, "buyPanel");
  }

  @Override
  public void mainMenu() {
    cardLayout.show(parentPanel, "menuPanel");
  }

  @Override
  public void viewSellMenu(List<String> portfolios) {
    if (sellPortfoliosDropDown.getItemCount() > 0) {
      sellPortfoliosDropDown.removeAllItems();
    }
    for (String p : portfolios) {
      sellPortfoliosDropDown.addItem(p);
    }
    cardLayout.show(parentPanel, "sellPanel");
  }

  @Override
  public void viewCostBasisMenu(List<String> portfolios) {
    if (cbPortfolioNameDropDown.getItemCount() > 0) {
      cbPortfolioNameDropDown.removeAllItems();
    }
    for (String p : portfolios) {
      cbPortfolioNameDropDown.addItem(p);
    }
    cardLayout.show(parentPanel, "cbPanel");
  }

  @Override
  public void viewFixedAmountMenu(List<String> portfolios) {
    if (fixedAmountDropDown.getItemCount() > 0) {
      fixedAmountDropDown.removeAllItems();
    }
    for (String p : portfolios) {
      fixedAmountDropDown.addItem(p);
    }
    cardLayout.show(parentPanel, "fixedAmountPanel");
  }

  @Override
  public void viewPortfolioValueMenu(List<String> portfolios) {
    if (portfolioValueDropDown.getItemCount() > 0) {
      portfolioValueDropDown.removeAllItems();
    }
    for (String p : portfolios) {
      portfolioValueDropDown.addItem(p);
    }
    cardLayout.show(parentPanel, "portfolioValuePanel");
  }

  @Override
  public void viewPlotGraphMenu(List<String> portfolios) {
    if (plotPortfolioDropDown.getItemCount() > 0) {
      plotPortfolioDropDown.removeAllItems();
    }
    for (String p : portfolios) {
      plotPortfolioDropDown.addItem(p);
    }
    cardLayout.show(parentPanel, "graphPanel");
  }

  @Override
  public void showWarningMessage(String message) {
    JOptionPane.showMessageDialog(null,
            message,
            "",
            JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public void showInformationMessage(String message) {
    JOptionPane.showMessageDialog(null,
            message,
            "",
            JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void clearBuyPanelTextFields() {
    buyPortfolio.setText("");
    buyTicker.setText("");
    buyDate.setText("");
    buyNos.setText("");
    buyCommission.setText("");
  }

  @Override
  public void clearSellPanelTextFields() {
    sellPortfolio.setText("");
    sellTicker.setText("");
    sellDate.setText("");
    sellNos.setText("");
    sellCommission.setText("");
  }

  @Override
  public void viewPortfolioMenu(List<String> portfolios) {
    if (viewPortfolioNameDropDown.getItemCount() > 0) {
      viewPortfolioNameDropDown.removeAllItems();
    }
    for (String p : portfolios) {
      viewPortfolioNameDropDown.addItem(p);
    }
    cardLayout.show(parentPanel, "viewPanel");
  }

  @Override
  public void viewSaveMenu(List<String> portfolios) {
    if (savePortfolioNameDropDown.getItemCount() > 0) {
      savePortfolioNameDropDown.removeAllItems();
    }
    for (String p : portfolios) {
      savePortfolioNameDropDown.addItem(p);
    }
    cardLayout.show(parentPanel, "savePanel");
  }

  @Override
  public void viewLoadMenu() {
    cardLayout.show(parentPanel, "loadPanel");
  }

  @Override
  public void viewDCAMenu() {
    cardLayout.show(parentPanel, "dcaPanel");
  }

  @Override
  public void showChart(GUIBarChart chart) {
    chart.draw();
    StdDraw.show();
  }


  private class TickerInputVerifier extends InputVerifier {
    JLabel tickerValidationLabel;
    Boolean tickerValid;
    TickerValidator tv;
    String caller;

    {
      try {
        tv = new TickerValidatorImpl("stock_master_file.csv");
        tv.readFile();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    public TickerInputVerifier(JLabel tickerValidationLabel, String caller) {
      this.tickerValidationLabel = tickerValidationLabel;
      this.caller = caller;
    }

    public boolean verify(JComponent input) {
      JTextField textField = (JTextField) input;
      tickerValid = tv.checkTicker(textField.getText());
      if (textField.getText().equals("")) {
        tickerValid = false;
      }
      if (!tickerValid) {
        tickerValidationLabel.setText("Ticker is not valid!!!");
      } else {
        tickerValidationLabel.setText("");
      }
      switch (caller) {
        case "buy":
          buyTickerValid = tickerValid;
          break;
        case "sell":
          sellTickerValid = tickerValid;
          break;
        default:
          break;
      }
      return tickerValid;
    }
  }

  private class NosInputVerifier extends InputVerifier {
    JLabel nosValidationLabel;
    Boolean nosValid;
    String caller;

    public NosInputVerifier(JLabel nosValidationLabel, String caller) {
      this.nosValidationLabel = nosValidationLabel;
      this.caller = caller;
    }

    public boolean verify(JComponent input) {
      JTextField textField = (JTextField) input;
      String nos = textField.getText();
      nosValid = true;
      try {
        if (Float.parseFloat(nos) <= 0) {
          nosValid = false;
        } else if (Float.parseFloat(nos) - Math.floor(Float.parseFloat(nos)) > 0) {
          nosValid = false;
        }
      } catch (NumberFormatException e) {
        nosValid = false;
      }
      if (!nosValid) {
        nosValidationLabel.setText("Number of shares is not valid!!!");
      } else {
        nosValidationLabel.setText("");
      }
      switch (caller) {
        case "buy":
          buyNosValid = nosValid;
          break;
        case "sell":
          sellNosValid = nosValid;
          break;
        default:
          break;
      }
      return nosValid;
    }
  }

  private class DcaIntervalInputVerifier extends InputVerifier {
    JLabel intervalValidationLabel;
    Boolean intervalValid;
    String caller;

    public DcaIntervalInputVerifier(JLabel intervalValidationLabel, String caller) {
      this.intervalValidationLabel = intervalValidationLabel;
      this.caller = caller;
    }

    public boolean verify(JComponent input) {
      JTextField textField = (JTextField) input;
      String nos = textField.getText();
      intervalValid = true;
      try {
        if (Float.parseFloat(nos) <= 0) {
          intervalValid = false;
        } else if (Float.parseFloat(nos) - Math.floor(Float.parseFloat(nos)) > 0) {
          intervalValid = false;
        }
      } catch (NumberFormatException e) {
        intervalValid = false;
      }
      if (!intervalValid) {
        intervalValidationLabel.setText("Interval is not valid!!!");
      } else {
        intervalValidationLabel.setText("");
      }
      if ("dcaInterval".equals(caller)) {
        dcaIntervalValid = intervalValid;
      }
      return intervalValid;
    }
  }

  private class DcaInvestmentAmountInputVerifier extends InputVerifier {
    JLabel amountValidationLabel;
    Boolean amountValid;
    String caller;

    public DcaInvestmentAmountInputVerifier(JLabel amountValidationLabel, String caller) {
      this.amountValidationLabel = amountValidationLabel;
      this.caller = caller;
    }

    public boolean verify(JComponent input) {
      JTextField textField = (JTextField) input;
      String amount = textField.getText();
      amountValid = true;
      try {
        if (Float.parseFloat(amount) <= 0) {
          amountValid = false;
        }
      } catch (NumberFormatException e) {
        amountValid = false;
      }
      if (!amountValid) {
        amountValidationLabel.setText("Amount is not valid!!!");
      } else {
        amountValidationLabel.setText("");
      }
      switch (caller) {
        case "dcaInvestmentAmount":
          dcaInvestmentAmountValid = amountValid;
          break;
        case "fixedAmountInvestmentAmount":
          fixedAmountInvestmentAmountValid = amountValid;
          break;
        case "buyCommission":
          buyCommissionValid = amountValid;
          break;
        case "sellCommission":
          sellCommissionValid = amountValid;
          break;
        case "dcaCommission":
          dcaCommissionValid = amountValid;
          break;
        case "fixedAmountCommission":
          fixedAmountCommissionValid = amountValid;
          break;
        default:
          break;
      }
      return amountValid;
    }
  }

  private class DateInputVerifier extends InputVerifier {
    JLabel dateValidationLabel;
    Boolean dateValid;
    String caller;

    public DateInputVerifier(JLabel dateValidationLabel, String caller) {
      this.dateValidationLabel = dateValidationLabel;
      this.caller = caller;
    }

    public boolean verify(JComponent input) {
      JTextField textField = (JTextField) input;
      try {
        // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
        LocalDate d = LocalDate.parse(textField.getText(),
                DateTimeFormatter.ofPattern("uuuu-MM-dd")
                        .withResolverStyle(ResolverStyle.STRICT));

        dateValid = !d.isAfter(LocalDate.now());

      } catch (DateTimeParseException e) {
        dateValid = false;
      }

      if (textField.getText().equals("")) {
        dateValid = false;
      }

      if (!dateValid) {
        dateValidationLabel.setText("Date is not valid!!!");
      } else {
        dateValidationLabel.setText("");
      }
      switch (caller) {
        case "buy":
          buyDateValid = dateValid;
          break;
        case "sell":
          sellDateValid = dateValid;
          break;
        case "view":
          viewDateValid = dateValid;
          break;
        case "portfolioValueDate":
          portfolioValueDateValid = dateValid;
          break;
        case "plotGraphStartDate":
          plotGraphStartDateValid = dateValid;
          break;
        case "plotGraphEndDate":
          plotGraphEndDateValid = dateValid;
          break;
        default:
          break;
      }
      return dateValid;
    }
  }

  private class FutureDateInputVerifier extends InputVerifier {
    JLabel dateValidationLabel;
    Boolean dateValid;
    String caller;

    public FutureDateInputVerifier(JLabel dateValidationLabel, String caller) {
      this.dateValidationLabel = dateValidationLabel;
      this.caller = caller;
      this.dateValid = true;
    }

    public boolean verify(JComponent input) {
      JTextField textField = (JTextField) input;
      dateValid = true;
      try {
        // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
        LocalDate d = LocalDate.parse(textField.getText(),
                DateTimeFormatter.ofPattern("uuuu-MM-dd")
                        .withResolverStyle(ResolverStyle.STRICT));

      } catch (DateTimeParseException e) {
        dateValid = false;
      }

      if (textField.getText().equals("")) {
        dateValid = false;
        if (caller.equals("dcaEndDate")) {
          dateValid = true;
        }
      }

      if (!dateValid) {
        dateValidationLabel.setText("Date is not valid!!!");
      } else {
        dateValidationLabel.setText("");
      }
      switch (caller) {
        case "dcaStartDate":
          dcaStartDateValid = dateValid;
          break;
        case "dcaEndDate":
          dcaEndDateValid = dateValid;
          break;
        case "cbDate":
          cbDateValid = dateValid;
          break;
        case "fixedAmountDate":
          fixedAmountDateValid = dateValid;
          break;
        default:
          break;
      }
      return dateValid;
    }
  }

  private class PortfolioNameVerifier extends InputVerifier {
    JLabel portfolioValidationLabel;
    Boolean portfolioNameValid;
    String caller;

    public PortfolioNameVerifier(JLabel portfolioValidationLabel, String caller) {
      this.portfolioValidationLabel = portfolioValidationLabel;
      this.caller = caller;
    }

    public boolean verify(JComponent input) {
      portfolioNameValid = true;
      JTextField textField = (JTextField) input;
      if (textField.getText().trim().equals("")) {
        portfolioNameValid = false;
      }
      if (!portfolioNameValid) {
        portfolioValidationLabel.setText("Name is not valid!!!");
      } else {
        portfolioValidationLabel.setText("");
      }
      switch (caller) {
        case "buy":
          buyPortfolioNameValid = portfolioNameValid;
          break;
        case "sell":
          sellPortfolioNameValid = portfolioNameValid;
          break;
        case "view":
          viewPortfolioNameValid = portfolioNameValid;
          break;
        case "save":
          savePortfolioNameValid = portfolioNameValid;
          break;
        case "load":
          loadPortfolioNameValid = portfolioNameValid;
          break;
        case "dcaName":
          dcaNameValid = portfolioNameValid;
          break;
        case "dcaPortfolioName":
          dcaPortfolioNameValid = portfolioNameValid;
          break;
        case "cbPortfolioName":
          cbPortfolioNameValid = portfolioNameValid;
          break;
        case "fixedAmountName":
          fixedAmountNameValid = portfolioNameValid;
          break;
        case "fixedAmountPortfolioName":
          fixedAmountPortfolioNameValid = portfolioNameValid;
          break;
        case "portfolioValuePortfolioName":
          portfolioValuePortfolioNameValid = portfolioNameValid;
          break;
        case "plotGraphPortfolioName":
          plotGraphPortfolioNameValid = portfolioNameValid;
          break;
        default:
          break;
      }
      return portfolioNameValid;
    }
  }


  private class DcaPropMapInputVerifier extends InputVerifier {
    JLabel propMapValidationLabel;
    Boolean propMapValid;
    String caller;
    TickerValidator tv;

    {
      try {
        tv = new TickerValidatorImpl("stock_master_file.csv");
        tv.readFile();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    public DcaPropMapInputVerifier(JLabel propMapValidationLabel, String caller) {
      this.propMapValidationLabel = propMapValidationLabel;
      this.caller = caller;
    }

    public boolean verify(JComponent input) {
      propMapValid = true;
      JTextField textField = (JTextField) input;

      List<String> propMap = List.of(textField.getText().split(","));

      List<String> tickers = new ArrayList<>();
      List<String> weightsString = new ArrayList<>();

      for (String s : propMap) {
        try {
          tickers.add(s.split(":")[0].trim());
          weightsString.add(s.split(":")[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
          propMapValid = false;
        }
      }

      for (String ticker : tickers) {
        if (!tv.checkTicker(ticker)) {
          propMapValid = false;
          break;
        }
      }

      float sum = 0F;

      for (String weight : weightsString) {
        try {
          if (Float.parseFloat(weight) <= 0) {
            propMapValid = false;
          } else {
            sum += Float.parseFloat(weight);
          }
        } catch (NumberFormatException e) {
          propMapValid = false;
        }
      }

      if (sum != 100) {
        propMapValid = false;
      }

      if (textField.getText().equals("")) {
        propMapValid = false;
      }
      if (!propMapValid) {
        propMapValidationLabel.setText("Invalid Input!!!");
      } else {
        propMapValidationLabel.setText("");
      }
      switch (caller) {
        case "dcaPropMap":
          dcaPropMapValid = propMapValid;
          break;
        case "fixedAmountPropMap":
          fixedAmountPropMapValid = propMapValid;
          break;
        default:
          break;
      }
      return propMapValid;
    }
  }
}