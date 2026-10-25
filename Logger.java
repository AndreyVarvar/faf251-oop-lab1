import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
  public String name;
  public static Level level = Level.INFO;
  public static String format = "[%t] [%l] %n: %m";

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
        .replace("%m", message);
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
