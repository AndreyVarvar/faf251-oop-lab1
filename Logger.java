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
    this.log(Logger.Level.TRACE, Formatter.Color.YELLOW, message, args);
  }

  public void info(String message, Object... args) {
    this.log(Logger.Level.INFO, Formatter.Color.BLUE, message, args);
  }

  public void warn(String message, Object... args) {
    this.log(Logger.Level.WARN, Formatter.Color.ORANGE, message, args);
  }

  public void error(String message, Object... args) {
    this.log(Logger.Level.ERROR, Formatter.Color.RED, message, args);
  }

  private void log(Level logLevel, Formatter.Color color, String message, Object... args) {
    if (logLevel.ordinal() < Logger.level.ordinal()) {
      return;
    }

    String formattedString = formatter.format(logLevel, this.name, message, color, args);
    sink.write(formattedString);
  }
}
