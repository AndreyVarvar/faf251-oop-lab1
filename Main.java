public class Main {
  public static void main(String[] args) {
    // Logger
    Logger loggerTest = new Logger("test");

    Logger.setLogLevel(Logger.Level.TRACE);
    loggerTest.log(Logger.Level.TRACE, "setup");
    loggerTest.log(Logger.Level.INFO, "program start");
    loggerTest.log(Logger.Level.ERROR, "Something broke");
    loggerTest.log(Logger.Level.WARN, "Ignoring the broke");

    Logger.setLogLevel(Logger.Level.WARN);
    loggerTest.log(Logger.Level.INFO, "hello");
    loggerTest.log(Logger.Level.ERROR, "something broke again");

    // Parentheses validator
    ParenthesesValidator validator = new ParenthesesValidator();

    String[] testCases = { "()a", ")((()))", "(", "(())((()())())" };

    Logger.setLogLevel(Logger.Level.TRACE);
    for (String testCase : testCases) {
      System.out.println(testCase);
      validator.test(testCase);
    }
  }
}
