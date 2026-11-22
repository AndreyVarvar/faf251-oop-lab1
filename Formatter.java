import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Formatter {
  private String template;

  private static Map<Logger.Level, List<String>> funnyMessages = Map.ofEntries(
      Map.entry(Logger.Level.TRACE,
          List.of("Here is your useless trace", "I'm useless", "I bet you won't even see me")),
      Map.entry(Logger.Level.INFO,
          List.of("Top 10 interesting facts. No 1: ", "Want to hear something interesting?", "Hey")),
      Map.entry(Logger.Level.WARN,
          List.of("Woah there buddy", "I'll let you go off with a warning this time", "I'm not mad, I'm dissapointed")),
      Map.entry(Logger.Level.ERROR,
          List.of("Let me talk to the developer directly", "Sell me this error", "Uh oh, someone fumbled big time!")));

  private static Random RANDOM = new Random();

  public static enum Color {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    ORANGE("\033[38;5;208m"),
    BLUE("\u001B[34m");

    private final String colorCode;

    Color(String colorCode) {
      this.colorCode = colorCode;
    }

    public String getColorCode() {
      return this.colorCode;
    }
  }

  Formatter() {
    this.template = "[%t] [%l] %n: %m";
  }

  Formatter(String template) {
    this.template = template;
  }

  private static String getTimestamp() {
    Calendar cal = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SS");

    String formattedTime = sdf.format(cal.getTime());

    return formattedTime;
  }

  private Integer indexOf(String message, char toFindIndexOf) {
    char previous = ' '; // if previous == '\', then we ignore the next character
    Integer index = -1;

    for (int i = 0; i < message.length(); i++) {
      if (previous == '\\') {
        previous = ' ';
        continue;
      }

      char c = message.charAt(i);

      previous = c;

      if (c == toFindIndexOf) {
        index = i;
        break;
      }
    }

    return index;
  }

  public String format(Logger.Level logLevel, String loggerName, String message, Color color, Object... args) {
    // first, replace reserved special characters
    message = this.template
        .replace("%t", getTimestamp())
        .replace("%l", logLevel.toString())
        .replace("%n", loggerName)
        .replace("%m", message)
        .replace("%r", funnyMessages.get(logLevel).get(RANDOM.nextInt(3))); // forgive me for hardcoded number :P

    // then, replace all other '%'
    for (Object arg : args) {
      Integer index = this.indexOf(message, '%');
      if (index == -1) {
        break;
      }

      message = message.substring(0, index) + arg.toString() + message.substring(index + 1);
    }

    message = color.getColorCode() + message + Color.RESET.getColorCode();

    return message;
  }
}
