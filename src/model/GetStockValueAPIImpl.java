package model;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the API computations where the stock value is returned on a specific date.
 */
public class GetStockValueAPIImpl implements GetStockValueAPI {
  @Override
  public float getStockPrice(String ticker, String sDate1) throws ParseException {
    String apiKey = "MOIOJBEOTYOYQLVI";
    String stockSymbol = ticker; //ticker symbol for Google
    URL url;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    List<String> ticker1 = new ArrayList<>();
    List<Float> intArray = new ArrayList<>();
    String outputString = output.toString();
    String[] lines = outputString.split("\\r?\\n");
    for (int i = 1; i < lines.length; i++) {
      String[] s = lines[i].split(",");
      ticker1.add(s[0]);
      intArray.add(Float.valueOf(s[4]));
    }

    String[] d = sDate1.split("-");
    LocalDate ld = LocalDate.of(Integer.parseInt(d[0]),
            Integer.parseInt(d[1]), Integer.parseInt(d[2]));

    if (ld.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Date cannot be in the future.");
    }

    while (!ticker1.contains(sDate1)) {
      ld = ld.minusDays(1);
      sDate1 = ld.toString();
    }

    int x = ticker1.indexOf(sDate1);
    float value = intArray.get(x);
    BigDecimal salary3 = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
    float r = Float.parseFloat(String.valueOf(salary3));
    return r;
  }

  @Override
  public Map<String, Float> getStockString(String ticker) {
    String apiKey = "MOIOJBEOTYOYQLVI";
    String stockSymbol = ticker; //ticker symbol for Google
    URL url;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    List<String> ticker1 = new ArrayList<>();
    List<Float> intArray = new ArrayList<>();
    Map<String, Float> outputMap = new HashMap<>();
    String outputString = output.toString();
    String[] lines = outputString.split("\\r?\\n");
    for (int i = 1; i < lines.length; i++) {
      String[] s = lines[i].split(",");
      outputMap.put(s[0], Float.valueOf(s[4]));
    }
    return outputMap;
  }
}
