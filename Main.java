public class Main {
  public static void main(String[] args) {
    // Logger
    Logger loggerTest = new Logger("test");

    Logger.setLogLevel(Logger.Level.TRACE);
    loggerTest.trace("setup");
    loggerTest.info("program start");
    loggerTest.error("Something broke");
    loggerTest.warn("Ignoring the broke");

    Logger.setLogLevel(Logger.Level.WARN);
    loggerTest.info("hello");
    loggerTest.error("something broke again");

    // Parentheses validator
    String[] testCases = { "()", ")((()))", "(", "(())((()())())" };

    Logger.setLogLevel(Logger.Level.TRACE);
    for (String testCase : testCases) {
      System.out.println(testCase);
      ParenthesesValidator.test(testCase);
    }
  }
}
