import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Logger {
  private static Map<Level, List<String>> funnyMessages = Map.ofEntries(
      Map.entry(Level.TRACE, List.of("Here is your useless trace", "I'm useless", "I bet you won't even see me")),
      Map.entry(Level.INFO, List.of("Top 10 interesting facts. No 1: ", "Want to hear something interesting?", "Hey")),
      Map.entry(Level.WARN,
          List.of("Woah there buddy", "I'll let you go off with a warning this time", "I'm not mad, I'm dissapointed")),
      Map.entry(Level.ERROR,
          List.of("Let me talk to the developer directly", "Sell me this error", "Uh oh, someone fumbled big time!")));

  private static Random RANDOM = new Random();

  public String name;
  public static Level level = Level.INFO;
  public static String format = "[%t] [%l] %n (%r): %m";

  public static enum Level { // no need for per-class enums
    TRACE,
    INFO,
    WARN,
    ERROR
  }

  Logger(String name) {
    this.name = name;
  }

  private static String getTimestamp() {
    Calendar cal = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SS");

    String formattedTime = sdf.format(cal.getTime());

    return formattedTime;
  }

  private String getFormattedString(Level logLevel, String message) {
    return Logger.format
        .replace("%t", getTimestamp())
        .replace("%l", logLevel.toString())
        .replace("%n", this.name)
        .replace("%m", message)
        .replace("%r", funnyMessages.get(logLevel).get(RANDOM.nextInt(3))); // forgive me for hardcoded number :P
                                                                            // (not as bad as hardcoded 4)
  }

  public static void setFormat(String format) {
    Logger.format = format;
  }

  public static void setLogLevel(Level newLevel) {
    Logger.level = newLevel;
  }

  public void log(Level logLevel, String message) {
    if (logLevel.compareTo(Logger.level) >= 0) {
      System.out.println(getFormattedString(logLevel, message));
    }
  }
}
