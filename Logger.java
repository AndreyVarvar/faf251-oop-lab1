public class Logger {
  private String name;
  private static Level level = Level.INFO;
  private Formatter formatter = new Formatter();
  private ConsoleSink sink = new ConsoleSink();

  public static enum Level {
    TRACE,
    INFO,
    WARN,
    ERROR
  }

  Logger(String name) {
    this.name = name;
  }

  Logger(String name, Formatter formatter, ConsoleSink sink) {
    this.name = name;
    this.formatter = formatter;
    this.sink = sink;
  }

  public static void setLogLevel(Level newLevel) {
    Logger.level = newLevel;
  }

  public void trace(String message) {
    this.log(Logger.Level.TRACE, message);
  }

  public void info(String message) {
    this.log(Logger.Level.INFO, message);
  }

  public void warn(String message) {
    this.log(Logger.Level.WARN, message);
  }

  public void error(String message) {
    this.log(Logger.Level.ERROR, message);
  }

  private void log(Level logLevel, String message) {
    if (logLevel.ordinal() < Logger.level.ordinal()) {
      return;
    }

    String formattedString = formatter.format(message, logLevel, this.name);
    sink.write(formattedString);
  }
}
