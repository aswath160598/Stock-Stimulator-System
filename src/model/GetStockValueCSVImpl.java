package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * This class represents the function to read from csv file where the stock value is
 * returned on a specific date.
 */
public class GetStockValueCSVImpl implements GetStockValueCSV {

  @Override
  public float getStockPrice(String ticker, String sDate1) throws ParseException, IOException {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date myDate = dateFormat.parse(sDate1);
    Date oneDayBefore;
    Date twoDayBefore;
    String file = "stock_data/" + ticker.toUpperCase() + ".csv";
    String delimiter = ",";
    String line;
    List<List<String>> lines = new ArrayList<>();
    try (BufferedReader br =
                 new BufferedReader(new FileReader(file))) {
      while ((line = br.readLine()) != null) {
        List<String> values = Arrays.asList(line.split(delimiter));
        lines.add(values);
      }
      List<String> ticker1 = new ArrayList<>();
      List<Float> intArray = new ArrayList<>();
      for (int i = 1; i < lines.size(); i++) {
        List<String> li;
        li = lines.get(i);
        ticker1.add(li.get(0));
        intArray.add(Float.valueOf(li.get(4)));
      }

      if (!ticker1.contains(sDate1)) {
        oneDayBefore = new Date(myDate.getTime() - 1);
        sDate1 = (dateFormat.format(oneDayBefore));
        oneDayBefore = dateFormat.parse(sDate1);
        if (!ticker1.contains(sDate1)) {
          twoDayBefore = new Date(oneDayBefore.getTime() - 2);
          sDate1 = (dateFormat.format(twoDayBefore));
        }
      }
      int x = ticker1.indexOf(sDate1);
      float value = intArray.get(x);
      BigDecimal salary3 = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
      float r = Float.parseFloat(String.valueOf(salary3));
      return r;
    }
  }
}
