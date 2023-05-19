package model;

import java.io.IOException;

/**
 * This Interface represents the operations involved with ticker Validation.
 */
public interface TickerValidator {

  /**
   * THis function read the ticker validation file.
   *
   * @throws IOException throws the error if any IO error occurs.
   */
  void readFile() throws IOException;

  /**
   * Function to check if a ticker is valid or not.
   *
   * @param ticker Inputs the ticker symbol to be validated
   * @return true if the ticker is valid else returns false.
   */
  boolean checkTicker(String ticker);
}
