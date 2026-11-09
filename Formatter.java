import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Formatter {
  private static String template = "[%t] [%l] %n (%r): %m";

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

  private static String getTimestamp() {
    Calendar cal = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SS");

    String formattedTime = sdf.format(cal.getTime());

    return formattedTime;
  }

  public String format(String message, Logger.Level logLevel, String loggerName) {
    return Formatter.template
        .replace("%t", getTimestamp())
        .replace("%l", logLevel.toString())
        .replace("%n", loggerName)
        .replace("%m", message)
        .replace("%r", funnyMessages.get(logLevel).get(RANDOM.nextInt(3))); // forgive me for hardcoded number :P
  }
}
