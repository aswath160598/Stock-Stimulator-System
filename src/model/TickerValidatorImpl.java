package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the TickerValidator Interface methods and validates a ticker symbol.
 */
public class TickerValidatorImpl implements TickerValidator {
  String line;
  String splitBy;
  BufferedReader br;
  List<String> data;

  /**
   * Function to validate the ticker.
   *
   * @param filePath the file path.
   * @throws FileNotFoundException throws this exception if there are is no file found.
   */
  public TickerValidatorImpl(String filePath) throws FileNotFoundException {
    line = "";
    splitBy = ",";
    br = new BufferedReader(new FileReader(filePath));
    data = new ArrayList<>();
  }

  @Override
  public void readFile() throws IOException {
    while ((line = br.readLine()) != null) {
      data.add(line.split(splitBy)[0]);
    }
  }

  @Override
  public boolean checkTicker(String ticker) {
    if (ticker == null) {
      return false;
    }
    ticker = ticker.toUpperCase();
    return data.contains(ticker);
  }
}
