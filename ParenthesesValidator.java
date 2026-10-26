public class ParenthesesValidator {
  static Logger logger = new Logger("Parentheses");

  public Boolean test(String parentheses) {
    Integer index = 0;

    for (char ch : parentheses.toCharArray()) {
      switch (ch) {
        case '(' -> {
          logger.log(Logger.Level.TRACE, "opening parenthesis found");
          index++;
        }
        case ')' -> {
          index--;
          if (index < 0) {
            logger.log(Logger.Level.WARN, "invalid closing parenthesis");
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
    }

    Boolean result = index == 0;

    logger.log(Logger.Level.INFO, "validation result: " + result.toString());

    return result;
  }
}
