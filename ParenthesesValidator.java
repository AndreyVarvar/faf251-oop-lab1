public class ParenthesesValidator {
  static Logger logger = new Logger("Parentheses");

  public static Boolean test(String parentheses) {
    Integer count = 0;

    Integer index = 0;
    for (char ch : parentheses.toCharArray()) {
      switch (ch) {
        case '(' -> {
          logger.log(Logger.Level.TRACE, "opening parenthesis found");
          count++;
        }
        case ')' -> {
          count--;
          if (count < 0) {
            logger.log(Logger.Level.WARN,
                "invalid closing parenthesis\n" + parentheses + "\n" + " ".repeat(index) + "^");
            return false;
          }
          logger.log(Logger.Level.INFO, "matching pair found");
        }
        default -> {
          logger.log(Logger.Level.ERROR,
              "Invalid string '" + parentheses + "': '" + ch + "' is not a parenthesis!");
          return false;
        }
      }
      index++;
    }

    Boolean result = count == 0;

    logger.log(Logger.Level.INFO, "validation result: " + result.toString());

    return result;
  }
}
