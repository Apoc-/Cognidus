package scarlet.generated;

class DataLoggerTester {
  private int id;

  private DataLoggerTester() {

  }

  public DataLoggerTester(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int value) {
    this.id = value;
  }

  public static void main() {
    DataLogger logger = DataLogger.getInstance();
    logger.logInfo("Hello, World!");
    logger.logError("Don't Panic!");
    int c = logger.getLogCount();
    logger.logInfo("Logged Messages: " + c);
  }
}
