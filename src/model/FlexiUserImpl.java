package model;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import utilities.FileHandler;
import utilities.GraphDraw;
import utilities.GraphDrawImpl;
import utilities.XMLFileHandler;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This class implements the methods of the flexible User interface where all User operations
 * are performed on a Portfolio.
 */
public class FlexiUserImpl extends UserImpl implements FlexiUser {
  private final Map<String, List<DollarCostAveraging>> dollarCostAveraging;
  private final Map<String, List<DollarCostAveragingSingle>> dollarCostAveragingSingle;
  private final Map<String, FlexiPortfolio> fPortfolios;

  /**
   * A constructor to initialize a flexible user.
   */
  public FlexiUserImpl() {
    this.fPortfolios = new HashMap<>();
    this.dollarCostAveraging = new HashMap<>();
    this.dollarCostAveragingSingle = new HashMap<>();
  }

  /**
   * A constructor to initialize a parametrized flexible user.
   *
   * @param dollarCostAveraging       map of portfolios and dollar cost objects
   * @param dollarCostAveragingSingle map of portfolios and fixed investment objects
   * @param fPortfolios               map of portfolios and names
   */
  public FlexiUserImpl(Map<String, List<DollarCostAveraging>> dollarCostAveraging,
                       Map<String, List<DollarCostAveragingSingle>> dollarCostAveragingSingle,
                       Map<String, FlexiPortfolio> fPortfolios) {
    this.dollarCostAveraging = dollarCostAveraging;
    this.dollarCostAveragingSingle = dollarCostAveragingSingle;
    this.fPortfolios = fPortfolios;
  }

  @Override
  public void addNewFlexiPortfolio(Map<String, List<List<String>>> p, String name) {
    fPortfolios.put(name, new FlexiPortfolioImpl(p));
  }

  @Override
  public int getNoOfPortfolios() {
    return fPortfolios.size();
  }

  @Override
  public FlexiPortfolio getPortfolio(String portfolioName) {
    return this.fPortfolios.get(portfolioName);
  }

  @Override
  public String toString() {
    return this.fPortfolios.toString();
  }

  @Override
  public float getFlexiPortfolioValue(String portfolioName, String date)
          throws ParseException {
    FlexiPortfolio flexiPortfolio = getPortfolio(portfolioName);
    GetStockValueAPIImpl getVal = new GetStockValueAPIImpl();
    String[] buyDate = date.split("-");
    LocalDate buyDateL = LocalDate.of(Integer.parseInt(buyDate[0]),
            Integer.parseInt(buyDate[1]), Integer.parseInt(buyDate[2]));
    Map<String, Float> composition = flexiPortfolio.getComposition(buyDateL);
    float stockValue;
    float sum = 0;
    for (Map.Entry<String, Float> mapElement : composition.entrySet()) {
      String key = mapElement.getKey();
      float noOfShares = mapElement.getValue();
      stockValue = getVal.getStockPrice(key, date);
      sum = sum + (stockValue * noOfShares);
    }
    return sum;
  }

  @Override
  public Boolean checkPortfolioExists(String name) {
    return fPortfolios.containsKey(name);
  }

  @Override
  public List<String> getAllPortfolioNames() {
    List<String> s = new ArrayList<>();
    for (Map.Entry<String, FlexiPortfolio> mapElement : fPortfolios.entrySet()) {
      s.add(mapElement.getKey());
    }
    return s;
  }

  @Override
  public Boolean checkPortfolioFileExists(String name) {
    Path path = Paths.get(name + ".xml");
    return Files.exists(path);
  }

  @Override
  public void addStockToExistingPortfolio(FlexiStocks s, String portfolioName) {
    getPortfolio(portfolioName).addStock(s);
  }

  @Override
  public float calculateCostBasis(String portfolioName, String date,
                                  Map<String, Map<String, Float>> cache)
          throws ParseException {
    if (checkPortfolioExists(portfolioName)) {
      FlexiPortfolio fp = getPortfolio(portfolioName);
      return fp.calculatePortfolioCostBasis(date, cache);
    } else {
      return 0;
    }
  }

  @Override
  public void saveFlexiPortfolio(String portfolioName) throws ParserConfigurationException,
          FileNotFoundException, TransformerException {
    FlexiPortfolio fp = getPortfolio(portfolioName);

    FileHandler fh = new XMLFileHandler();
    fh.savePortfolio(fp, portfolioName);

  }

  @Override
  public void loadFlexiPortfolio(String portfolioName) throws IllegalArgumentException,
          ParserConfigurationException, IOException, SAXException, TransformerException {
    FileHandler fh = new XMLFileHandler();
    String[] fileList;
    Map<String, List<List<String>>> h = fh.loadPortfolio(portfolioName);
    addNewFlexiPortfolio(h, portfolioName);
    File f = new File(portfolioName);
    fileList = f.list();
    for (String pathname : fileList) {
      if (pathname.startsWith("DCA")) {
        Map<String, List<String>> x = new HashMap<>();
        x = fh.loadDCA(portfolioName, pathname);
        addNewDollarCostAvg(x, portfolioName);
      }
    }
  }

  private float getValHelper(LocalDate current, FlexiPortfolio flexiPortfolio,
                             GetStockValueAPI getVal, Map<String, Map<String, Float>> c) {
    float sum = 0;

    LocalDate cDate = current;
    sum = 0;
    Map<String, Float> comp = flexiPortfolio.getComposition(current);
    for (Map.Entry<String, Float> mapElement : comp.entrySet()) {

      String ticker = mapElement.getKey();
      if (!c.containsKey(ticker)) {
        c.put(ticker, getVal.getStockString(ticker));
      }

      Map<String, Float> v = c.get(ticker);

      while (!v.containsKey(cDate.toString())) {
        cDate = cDate.plusDays(1);
      }

      sum += v.get(cDate.toString()) * comp.get(ticker);

    }

    return sum;
  }

  @Override
  public StringBuilder drawGraph(String portfolioName, String startDate, String endDate) throws
          ParseException {
    StringBuilder str = new StringBuilder();
    StringBuilder str1 = new StringBuilder();
    GraphDraw gr = new GraphDrawImpl();
    Map<String, List<String>> x;
    List<String> timeStamp;
    List<String> values;
    x = graphComputation(portfolioName, startDate, endDate);
    timeStamp = x.get("timeStamp");
    values = x.get("values");
    List<Float> valuesFloat = new ArrayList<>();
    for (String i : values) {
      valuesFloat.add(Float.parseFloat(i));
    }
    str.append("\nPerformance of portfolio ")
            .append(portfolioName)
            .append(" from ")
            .append(startDate)
            .append(" to ")
            .append(endDate)
            .append("\n\n");
    str1 = gr.drawGraph(valuesFloat, timeStamp);
    str.append(str1);
    return str;
  }

  @Override
  public Map<String, List<String>> graphComputation(String portfolioName, String startDate,
                                                    String endDate) {
    Map<String, List<String>> h = new HashMap<>();
    String[] sDateString = startDate.split("-");
    String[] eDateString = endDate.split("-");
    float sum = 0;
    List<String> timeStamp = new ArrayList<>();

    Map<String, Map<String, Float>> c = new HashMap<>();
    GetStockValueAPI getVal = new GetStockValueAPIImpl();

    FlexiPortfolio flexiPortfolio = getPortfolio(portfolioName);

    LocalDate sDate1 = LocalDate.of(Integer.parseInt(sDateString[0]),
            Integer.parseInt(sDateString[1]), Integer.parseInt(sDateString[2]));
    LocalDate eDate1 = LocalDate.of(Integer.parseInt(eDateString[0]),
            Integer.parseInt(eDateString[1]), Integer.parseInt(eDateString[2]));

    if (eDate1.isBefore(sDate1) || eDate1.equals(sDate1)) {
      throw new IllegalArgumentException("End date cannot be before start date " +
              "or equal to start date.");
    }

    LocalDate current = sDate1;
    long daysBetween = DAYS.between(sDate1, eDate1);
    List<String> values = new ArrayList<>();
    if (daysBetween > 0 && daysBetween < 31) {
      while (current.isBefore(eDate1) || current.isEqual(eDate1)) {
        timeStamp.add(current.toString());
        sum = getValHelper(current, flexiPortfolio, getVal, c);
        values.add(String.valueOf(sum));
        current = current.plusDays(1);
      }
    } else if (daysBetween > 30 && daysBetween < 60) {
      while (current.isBefore(eDate1.plusDays(2)) || current.isEqual(eDate1)) {
        timeStamp.add(current.toString());
        if (eDate1.isBefore(current)) {
          current = eDate1;
        }
        sum = getValHelper(current, flexiPortfolio, getVal, c);
        values.add(String.valueOf(sum));
        current = current.plusDays(2);
      }
    } else if (daysBetween > 59 && daysBetween < 150) {
      while (current.isBefore(eDate1.plusDays((5))) || current.isEqual(eDate1)) {
        timeStamp.add(current.toString());
        if (eDate1.isBefore(current)) {
          current = eDate1;
        }
        sum = getValHelper(current, flexiPortfolio, getVal, c);
        values.add(String.valueOf(sum));
        current = current.plusDays(5);
      }
    } else if (daysBetween > 149 && daysBetween < 900) {
      while (current.isBefore(eDate1.plusDays(30))) {
        Month x = current.getMonth();
        String s = String.valueOf(x);
        String sub_str = (s.substring(0, 3));
        String monthYear;
        monthYear = (sub_str + " " + current.getYear());
        LocalDate lastDayOfMonth = LocalDate.parse(current.toString(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .with(TemporalAdjusters.lastDayOfMonth());
        if (eDate1.isBefore(lastDayOfMonth)) {
          lastDayOfMonth = eDate1;
          x = eDate1.getMonth();
          s = String.valueOf(x);
          sub_str = (s.substring(0, 3));
          monthYear = (sub_str + " " + current.getYear());
        }
        if (!timeStamp.contains(monthYear)) {
          timeStamp.add(monthYear);
          sum = getValHelper(lastDayOfMonth, flexiPortfolio, getVal, c);
          values.add(String.valueOf(sum));
        }
        current = current.plusDays(32);
      }
    } else if (daysBetween > 899 && daysBetween < 2700) {
      while (current.isBefore(eDate1.plusDays(90))) {
        Month x = current.getMonth();
        String s = String.valueOf(x);
        String sub_str = (s.substring(0, 3));
        String monthYear;
        monthYear = (sub_str + " " + current.getYear());
        LocalDate lastDayOfMonth = LocalDate.parse(current.toString(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .with(TemporalAdjusters.lastDayOfMonth());
        if (eDate1.isBefore(lastDayOfMonth)) {
          lastDayOfMonth = eDate1;
          x = eDate1.getMonth();
          s = String.valueOf(x);
          sub_str = (s.substring(0, 3));
          monthYear = (sub_str + " " + current.getYear());
        }
        if (!timeStamp.contains(monthYear)) {
          timeStamp.add(monthYear);
          sum = getValHelper(lastDayOfMonth, flexiPortfolio, getVal, c);
          values.add(String.valueOf(sum));
        }
        current = current.plusDays(90);
      }
    } else if (daysBetween > 2699) {
      while (current.isBefore(eDate1.plusDays(365))) {
        int year = current.getYear();
        LocalDate lastWorkingDay = LocalDate.of(year, 12, 31);
        if (eDate1.isBefore(lastWorkingDay)) {
          lastWorkingDay = eDate1;
        }
        timeStamp.add(lastWorkingDay.toString());
        sum = getValHelper(lastWorkingDay, flexiPortfolio, getVal, c);
        values.add(String.valueOf(sum));
        current = current.plusDays(365);
      }
    }
    h.put("values", values);
    h.put("timeStamp", timeStamp);
    return h;
  }

  private float getValStockHelper(LocalDate current, String ticker, GetStockValueAPI getVal,
                                  Map<String, Map<String, Float>> c) {
    LocalDate cDate = current;
    if (!c.containsKey(ticker)) {
      c.put(ticker, getVal.getStockString(ticker));
    }

    Map<String, Float> v = c.get(ticker);

    while (!v.containsKey(cDate.toString())) {
      cDate = cDate.minusDays(1);
    }
    return v.get(cDate.toString());
  }

  @Override
  public void dollarCostAveraging(String portfolioName, DollarCostAveraging obj)
          throws FileNotFoundException, ParserConfigurationException, TransformerException {
    FlexiStocks newStock;
    Map<String, Map<String, Float>> c = new HashMap<>();
    String startDate = obj.getStartDate().toString();
    String endDate = obj.getEndDate().toString();
    String[] startDate1 = startDate.split("-");
    LocalDate startDateL = LocalDate.of(Integer.parseInt(startDate1[0]),
            Integer.parseInt(startDate1[1]), Integer.parseInt(startDate1[2]));
    String[] endDate1 = endDate.split("-");
    LocalDate endDateL = LocalDate.of(Integer.parseInt(endDate1[0]),
            Integer.parseInt(endDate1[1]), Integer.parseInt(endDate1[2]));
    GetStockValueAPIImpl getVal = new GetStockValueAPIImpl();
    while ((startDateL.isBefore(LocalDate.now()) || startDateL.equals(LocalDate.now())) &&
            (obj.getDateList().contains(startDateL))) {
      float stockValue;
      for (Map.Entry<String, Float> mapElement : obj.getProportionMap().entrySet()) {
        String key = mapElement.getKey();
        float nos = mapElement.getValue();
        float value = (nos * obj.getTotalInvestment() / 100);
        stockValue = getValStockHelper(startDateL, key, getVal, c);
        float indShares = (value - obj.getCommissionFee()) / stockValue;
        newStock = FlexiStocksImpl.getBuilder1()
                .ticker(key)
                .buyDate(startDateL)
                .noOfShares(indShares)
                .status("b")
                .create();
        addStockToExistingPortfolio(newStock, portfolioName);
      }
      obj.getDateList().remove(startDateL);
      startDateL = startDateL.plusDays(obj.getInterval());
    }
    if (startDateL.isBefore(endDateL) && endDateL.isAfter(LocalDate.now())) {
      List<String> l = new ArrayList<>();
      Map<String, List<String>> d = new HashMap<>();
      String x = obj.getProportionMap().toString().replace('=', ':');
      l.add(obj.getDateList().get(0).toString());
      l.add(obj.getEndDate().toString());
      l.add(String.valueOf(obj.getTotalInvestment()));
      l.add(String.valueOf(obj.getInterval()));
      l.add(x.substring(1, x.length() - 1));
      l.add(String.valueOf(obj.getCommissionFee()));
      d.put(obj.getPlanName(), l);
      FileHandler fh = new XMLFileHandler();
      fh.saveDCA(d, portfolioName);
    }
    FileHandler fh = new XMLFileHandler();
    fh.savePortfolio(getPortfolio(portfolioName), portfolioName);
  }

  @Override
  public void dollarCostAveragingSingle(String portfolioName, DollarCostAveragingSingle obj)
          throws FileNotFoundException, ParserConfigurationException, TransformerException {
    FlexiStocks newStock;
    Map<String, Map<String, Float>> c = new HashMap<>();
    String startDate = obj.getStartDate().toString();
    String[] startDate1 = startDate.split("-");
    LocalDate startDateL = LocalDate.of(Integer.parseInt(startDate1[0]),
            Integer.parseInt(startDate1[1]), Integer.parseInt(startDate1[2]));
    GetStockValueAPIImpl getVal = new GetStockValueAPIImpl();
    float stockValue;
    if (obj.getStatus() == 'x') {
      for (Map.Entry<String, Float> mapElement : obj.getProportionMap().entrySet()) {
        String key = mapElement.getKey();
        float nos = mapElement.getValue();
        float value = (nos * obj.getTotalInvestment() / 100);
        stockValue = getValStockHelper(startDateL, key, getVal, c);
        float indShares = (value - obj.getCommissionFee()) / stockValue;
        newStock = FlexiStocksImpl.getBuilder1()
                .ticker(key)
                .buyDate(startDateL)
                .noOfShares(indShares)
                .status("b")
                .create();
        addStockToExistingPortfolio(newStock, portfolioName);
        obj.status();
      }
    }
    FileHandler fh = new XMLFileHandler();
    fh.savePortfolio(getPortfolio(portfolioName), portfolioName);
  }


  @Override
  public void updateDollarCostAvg() throws FileNotFoundException, ParserConfigurationException,
          TransformerException {
    String key;
    List<DollarCostAveraging> li = new ArrayList<>();
    List<DollarCostAveragingSingle> liSingle = new ArrayList<>();
    for (Map.Entry<String, List<DollarCostAveraging>> mapElement : dollarCostAveraging.entrySet()) {
      key = mapElement.getKey();
      li = mapElement.getValue();
      for (int i = 0; i < li.size(); i++) {
        DollarCostAveraging dollarInd = li.get(i);
        dollarCostAveraging(key, dollarInd);
      }
    }
    for (Map.Entry<String, List<DollarCostAveragingSingle>> mapElement :
            dollarCostAveragingSingle.entrySet()) {
      key = mapElement.getKey();
      liSingle = mapElement.getValue();
      for (int i = 0; i < liSingle.size(); i++) {
        DollarCostAveragingSingle dollarInd = liSingle.get(i);
        dollarCostAveragingSingle(key, dollarInd);
      }
    }
  }

  @Override
  public void addNewDollarCostAvg(Map<String, List<String>> d, String portfolioName)
          throws FileNotFoundException, ParserConfigurationException, TransformerException {
    String planName;
    LocalDate startDate;
    LocalDate endDate;
    float totalInvestment;
    Map<String, Float> proportionMap = new HashMap<>();
    int interval;
    float commissionFee;
    List<DollarCostAveraging> listDollarCostAveraging = new ArrayList<>();
    for (Map.Entry<String, List<String>> mapElement : d.entrySet()) {
      planName = mapElement.getKey();
      List<String> dollarList = mapElement.getValue();
      String[] sDate = dollarList.get(0).split("-");
      startDate = LocalDate.of(Integer.parseInt(sDate[0]),
              Integer.parseInt(sDate[1]), Integer.parseInt(sDate[2]));
      String[] eDate = dollarList.get(1).split("-");
      endDate = LocalDate.of(Integer.parseInt(eDate[0]),
              Integer.parseInt(eDate[1]), Integer.parseInt(eDate[2]));
      totalInvestment = Float.parseFloat(dollarList.get(2));
      interval = Integer.parseInt(dollarList.get(3));
      String[] prop = dollarList.get(4).split(",");
      commissionFee = Float.parseFloat(dollarList.get(5));
      for (int i = 0; i < prop.length; i++) {
        String[] proportion = prop[i].split(":");
        proportionMap.put(proportion[0].trim(), Float.parseFloat(proportion[1]));
      }
      if (dollarCostAveraging.containsKey(portfolioName)) {
        dollarCostAveraging.get(portfolioName).add(new DollarCostAveragingImpl(planName, startDate,
                endDate,
                totalInvestment, interval, proportionMap, commissionFee));
      } else {
        if (checkPortfolioExists(portfolioName)) {
          listDollarCostAveraging.add(new DollarCostAveragingImpl(planName, startDate, endDate,
                  totalInvestment, interval, proportionMap, commissionFee));
          dollarCostAveraging.put(portfolioName, listDollarCostAveraging);
        } else {
          Map<String, List<List<String>>> p = new HashMap<>();
          addNewFlexiPortfolio(p, portfolioName);
          listDollarCostAveraging.add(new DollarCostAveragingImpl(planName, startDate, endDate,
                  totalInvestment, interval, proportionMap, commissionFee));
          dollarCostAveraging.put(portfolioName, listDollarCostAveraging);
        }
      }
    }
    FileHandler fh = new XMLFileHandler();
    fh.saveDCA(d, portfolioName);
    updateDollarCostAvg();
  }

  @Override
  public void addNewDollarCostAvgSingle(Map<String, List<String>> d, String portfolioName)
          throws IllegalArgumentException, FileNotFoundException, ParserConfigurationException,
          TransformerException {
    if (!checkPortfolioExists(portfolioName)) {
      throw new IllegalArgumentException("Portfolio does not exist!!!");
    }
    String planName;
    LocalDate startDate;
    float totalInvestment;
    Map<String, Float> proportionMap = new HashMap<>();
    List<DollarCostAveragingSingle> listDollarCostAveragingSingle = new ArrayList<>();
    for (Map.Entry<String, List<String>> mapElement : d.entrySet()) {
      planName = mapElement.getKey();
      List<String> dollarList = mapElement.getValue();
      String[] sDate = dollarList.get(0).split("-");
      startDate = LocalDate.of(Integer.parseInt(sDate[0]),
              Integer.parseInt(sDate[1]), Integer.parseInt(sDate[2]));
      totalInvestment = Float.parseFloat(dollarList.get(1));
      String[] prop = dollarList.get(2).split(",");
      float commissionFee = Float.parseFloat(dollarList.get(3));
      for (int i = 0; i < prop.length; i++) {
        String[] proportion = prop[i].split(":");
        proportionMap.put(proportion[0].trim(), Float.parseFloat(proportion[1]));
      }
      if (dollarCostAveragingSingle.containsKey(portfolioName)) {
        dollarCostAveragingSingle.get(portfolioName).add(new DollarCostAveragingSingleImpl(planName,
                startDate, totalInvestment, proportionMap, commissionFee));
      } else {
        if (checkPortfolioExists(portfolioName)) {
          listDollarCostAveragingSingle.add(new DollarCostAveragingSingleImpl(planName, startDate,
                  totalInvestment, proportionMap, commissionFee));
          dollarCostAveragingSingle.put(portfolioName, listDollarCostAveragingSingle);
        } else {
          Map<String, List<List<String>>> p = new HashMap<>();
          addNewFlexiPortfolio(p, portfolioName);
          listDollarCostAveragingSingle.add(new DollarCostAveragingSingleImpl(planName, startDate,
                  totalInvestment, proportionMap, commissionFee));
          dollarCostAveragingSingle.put(portfolioName, listDollarCostAveragingSingle);
        }
      }
    }
    updateDollarCostAvg();
  }


  @Override
  public float calculateDCAPortfolioCostBasis(String portfolioName, String date,
                                              Map<String, Map<String, Float>> cache)
          throws ParseException {
    float cb = 0;
    List<DollarCostAveraging> x = dollarCostAveraging.get(portfolioName);
    float resultCostBasis;
    LocalDate dateCostBasis;
    FlexiPortfolio fs = new FlexiPortfolioImpl();
    fs = getPortfolio(portfolioName);
    String[] sDate = date.split("-");
    dateCostBasis = LocalDate.of(Integer.parseInt(sDate[0]),
            Integer.parseInt(sDate[1]), Integer.parseInt(sDate[2]));
    if (dateCostBasis.isBefore(LocalDate.now())) {
      resultCostBasis = fs.calculatePortfolioCostBasis(date, cache);
    } else {
      resultCostBasis = fs.calculatePortfolioCostBasis(LocalDate.now().toString(), cache);
      if (x.size() != 0) {
        for (int i = 0; i < x.size(); i++) {
          DollarCostAveraging ex = x.get(i);
          List<LocalDate> dateList = ex.getDateList();
          for (LocalDate d : dateList) {
            if (d.isBefore(dateCostBasis)) {
              resultCostBasis += ex.getTotalInvestment();
            }
          }
        }
      }
    }
    return resultCostBasis;
  }
}
