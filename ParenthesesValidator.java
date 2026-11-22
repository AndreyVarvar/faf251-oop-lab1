public class ParenthesesValidator {
  static Logger logger = new Logger("Parentheses");

  public static Boolean test(String parentheses) {
    Integer count = 0;

    Integer index = 0;
    for (char ch : parentheses.toCharArray()) {
      switch (ch) {
        case '(' -> {
          logger.trace("opening parenthesis found");
          count++;
        }
        case ')' -> {
          count--;
          if (count < 0) {
            logger.warn(
                "invalid closing parenthesis:\n%\n%%", parentheses, " ".repeat(index), "^");
            return false;
          }
          logger.info("matching pair found");
        }
        default -> {
          logger.error(
              "Invalid string '%': '%' is not a parenthesis!", parentheses, ch);
          return false;
        }
      }
      index++;
    }

    Boolean result = count == 0;

    logger.info("validation result: %", result);

    return result;
  }
}
