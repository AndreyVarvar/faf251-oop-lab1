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
                "invalid closing parenthesis\n" + parentheses + "\n" + " ".repeat(index) + "^");
            return false;
          }
          logger.info("matching pair found");
        }
        default -> {
          logger.error(
              "Invalid string '" + parentheses + "': '" + ch + "' is not a parenthesis!");
          return false;
        }
      }
      index++;
    }

    Boolean result = count == 0;

    logger.info("validation result: " + result.toString());

    return result;
  }
}
