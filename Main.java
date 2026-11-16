import java.util.ArrayList;
import java.util.List;

public class Main {
  private static ArrayList<Display> displays = new ArrayList<>();
  private static ArrayList<DisplayManager> managers = new ArrayList<>();

  private static Formatter formatter = new Formatter();
  private static Sink sink = new ConsoleSink();
  private static Logger logger = new Logger("main", formatter, sink);

  public static void main(String[] args) throws Exception {
    // lab1();
    lab2_1();
    lab2_2(args);
  }

  private static Logger newLogger(String name) {
    return new Logger(name, formatter, sink);
  }

  private static void lab1() {
    // Logger
    Logger loggerTest = new Logger("test");

    Logger.setLogLevel(Logger.Level.TRACE);
    loggerTest.trace("setup");
    loggerTest.info("program start");
    loggerTest.error("Something broke");
    loggerTest.warn("Ignoring the broke");

    Logger.setLogLevel(Logger.Level.WARN);
    loggerTest.info("hello");
    loggerTest.error("something broke again");

    // Parentheses validator
    String[] testCases = { "()", ")((()))", "(", "(())((()())())" };

    Logger.setLogLevel(Logger.Level.TRACE);
    for (String testCase : testCases) {
      System.out.println(testCase);
      ParenthesesValidator.test(testCase);
    }
  }

  private static void lab2_1() {
    Display d1 = new Display("Random dispaly", 1000, 500, 100);
    Display d2 = new Display("Grandma's TV", 500, 300, 20);
    Display d3 = new Display("Acer CB2 CB382CURbmiiphuzx", 10000, 6000, 99);

    DisplayManager m1 = new DisplayManager("Bomba");
    m1.assignDisplay(d1);
    m1.assignDisplay(d2);
    DisplayManager m2 = new DisplayManager("Target");
    m2.assignDisplay(d2);
    m2.assignDisplay(d3);

    // m1.assist();
    // m2.assist();

    m1.buyDisplay(d2);
    m2.assist();
  }

  private static void lab2_2(String[] args) throws Exception {

  }

  private static void parseCommand(String line) {
    // step 1: get rid of comments
    String command = line.split("#")[0];

    // step 2: tokenize
    List<String> tokens = List.of(command.split(" "));

    switch (tokens.get(0)) {
      case "MANAGER":
        commandManager(tokens);
        break;

      case "DISPLAY":
        commandDisplay(tokens);
        break;

      case "ASSIGN":
        commandAssign(tokens);
        break;
    }

  }

  private static void commandManager(List<String> tokens) {
    if (tokens.size() != 2) {
      logger.error("Incorrect number of arguments passed into the MANAGER command\nUsage: MANAGER <name>");
      return;
    }

    String name = tokens.get(1);
    managers.add(new DisplayManager(name, newLogger(name)));
  }

  private static Display getDisplay(String model) {
    for (Display d : displays) {
      if (d.getModel() == model) {
        return d;
      }
    }

    return null;
  }

  private static DisplayManager getDisplayManager(String name) {
    for (DisplayManager m : managers) {
      if (m.getName() == name) {
        return m;
      }
    }

    return null;
  }

  private static void commandDisplay(List<String> tokens) {
    if (tokens.size() != 5) {
      logger.error(
          "Incorrect number of arguments passed into the DISPLAY command\nUsage: DISPLAY <model> <w> <h> <ppi>");
      return;
    }

    String model = tokens.get(1);
    Integer width;
    Integer height;
    Double ppi;
    try {
      width = Integer.parseInt(tokens.get(2));
    } catch (NumberFormatException e) {
      logger.error("'width' parameter of DISPLAY has wrong numeric format.");
      return;
    }

    try {
      height = Integer.parseInt(tokens.get(3));
    } catch (NumberFormatException e) {
      logger.error("'height' parameter of DISPLAY has wrong numeric format.");
      return;
    }

    try {
      ppi = Double.parseDouble(tokens.get(4));
    } catch (NumberFormatException e) {
      logger.error("'ppi' parameter of DISPLAY has wrong double format.");
      return;
    }

    displays.add(new Display(model, width, height, ppi, newLogger(model)));
  }

  private static void commandAssign(List<String> tokens) {
    if (tokens.size() != 3) {
      logger.error("Incorrect number of arguments passed into the ASSIGN command\nUsage: ASSIGN <model> <manager>");
      return;
    }

    String model = tokens.get(1);
    String managerName = tokens.get(2);

    Display display = getDisplay(model);

    if (display == null) {
      logger.error("Display '%' does not exist.", model);
      return;
    }

    DisplayManager manager = getDisplayManager(managerName);
    if (manager == null) {
      logger.error("Display manager '%' does not exist.", managerName);
      return;
    }

    manager.assignDisplay(display);
  }
}
