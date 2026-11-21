public class Logger {
  private String name;
  private static Level level = Level.INFO;
  private Formatter formatter;
  private Sink sink;

  public static enum Level {
    TRACE,
    INFO,
    WARN,
    ERROR
  }

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

  Logger(String name) {
    this.name = name;
    this.formatter = new Formatter();
    this.sink = new ConsoleSink();
  }

  Logger(String name, Formatter formatter, Sink sink) {
    this.name = name;
    this.formatter = formatter;
    this.sink = sink;
  }

  public static void setLogLevel(Level newLevel) {
    Logger.level = newLevel;
  }

  public void trace(String message, Object... args) {
    this.log(Logger.Level.TRACE, Color.YELLOW, message, args);
  }

  public void info(String message, Object... args) {
    this.log(Logger.Level.INFO, Color.BLUE, message, args);
  }

  public void warn(String message, Object... args) {
    this.log(Logger.Level.WARN, Color.ORANGE, message, args);
  }

  public void error(String message, Object... args) {
    this.log(Logger.Level.ERROR, Color.RED, message, args);
  }

  private void log(Level logLevel, Color color, String message, Object... args) {
    if (logLevel.ordinal() < Logger.level.ordinal()) {
      return;
    }

    String formattedString = formatter.format(logLevel, this.name, message, args);
    sink.write(color.getColorCode() + formattedString + Color.RESET.getColorCode());
  }
}
