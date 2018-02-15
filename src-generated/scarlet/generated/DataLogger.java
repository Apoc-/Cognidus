package scarlet.generated;

import java.lang.String;

public class DataLogger {
  private static DataLogger instance;

  private int logCount;

  private DataLogger() {

  }

  public static DataLogger getInstance() {
    if (instance == null) {
            instance = new DataLogger();
        }
        return instance;
  }

  public void logInfo(String message) {
    this.log("[info]", message);
  }

  public void logError(String message) {
    this.log("[error]", message);
  }

  private void log(String prefix, String message) {
    System.out.println(prefix + " " + message);
    logCount++;
  }

  public int getLogCount() {
    return logCount;
  }
}
