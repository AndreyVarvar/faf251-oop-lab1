import java.util.ArrayList;
import java.util.List;

public class Main {
  private static ArrayList<Display> displays = new ArrayList<>();
  private static ArrayList<DisplayManager> managers = new ArrayList<>();

  private static Formatter formatter = new Formatter();
  private static Sink sink = new ConsoleSink();
  private static Logger logger = new Logger("main", formatter, sink);

  public static void main(String[] args) throws Exception {
    Logger.setLogLevel(Logger.Level.TRACE);

    // lab1();
    // lab2_1();
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
    String filename = args[0];
    List<String> commands = CommandReader.read(filename, logger);

    for (String command : commands) {
      parseCommand(command);
    }
  }

  private static void parseCommand(String line) {
    // step 1: get rid of comments
    String[] command = line.split("#");
    if (command.length < 1) {
      return; // empty line
    }

    // step 2: clearn the line
    String cleaned = command[0]; // very primitive cleaning, other extra spaces are not cleaned

    if (cleaned == "") {
      return; // also an empty line
    }

    // step 3: tokenize
    Tokens tokens = new Tokens(List.of(cleaned.split(" ")));

    // step 4: execute the command
    if (tokens.size() == 0) {
      // empty line
      return;
    }

    String c = tokens.get(0).strip();

    if (c.length() == 0) {
      // empty line
      return;
    }

    switch (c) {
      case "MANAGER":
        logger.trace("MANAGER command invoked");
        commandManager(tokens);
        break;

      case "DISPLAY":
        logger.trace("DISPLAY command invoked");
        commandDisplay(tokens);
        break;

      case "ASSIGN":
        logger.trace("ASSIGN command invoked");
        commandAssign(tokens);
        break;

      case "COMPARE":
        logger.trace("COMPARE command invoked");
        commandCompare(tokens);
        break;

      case "ASSIST":
        logger.trace("ASSIST command invoked");
        commandAssist(tokens);
        break;

      case "BUY":
        logger.trace("BUY command invoked");
        commandBuy(tokens);
        break;

      default:
        logger.error("Unknown command: '%'", c);
        break;
    }

  }

  private static void commandManager(Tokens tokens) {
    if (tokens.size() != 2) {
      logger.error("Incorrect number of arguments passed into the MANAGER command\nUsage: MANAGER <name>");
      return;
    }

    String name = tokens.get(1);

    logger.info("A manager was added: '%'", name);
    addManager(new DisplayManager(name, newLogger(name)));
  }

  private static void addDisplay(Display d) {
    if (getDisplay(d.getModel()) != null) {
      logger.warn("A logger with this name (%) already exists.");
    }

    displays.add(d);
  }

  private static void addManager(DisplayManager manager) {
    if (getDisplayManager(manager.getName()) != null) {
      logger.warn("A manager with this name (%) already exists.", manager.getName());
    }

    managers.add(manager);
  }

  private static Display getDisplay(String model) {
    for (Display d : displays) {
      if (d.getModel().equals(model)) {
        return d;
      }
    }

    return null;
  }

  private static DisplayManager getDisplayManager(String name) {
    for (DisplayManager m : managers) {
      if (m.getName().equals(name)) {
        return m;
      }
    }

    return null;
  }

  private static void commandDisplay(Tokens tokens) {
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

    logger.info("A display was created: '%'", model);

    addDisplay(new Display(model, width, height, ppi, newLogger(model)));
  }

  private static void commandAssign(Tokens tokens) {
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

    logger.info("'%' was assigned to '%'", model, managerName);

    manager.assignDisplay(display);
  }

  private static void commandCompare(Tokens tokens) {
    if (tokens.size() != 3) {
      logger.error("Incorrect number of arguments passed into the COMPARE command\nUsage: COMPARE <model1> <model2>");
      return;
    }

    String model1 = tokens.get(1);
    String model2 = tokens.get(2);

    Display display1 = getDisplay(model1);

    if (display1 == null) {
      logger.error("Unknown model: '%'", model1);
      return;
    }

    Display display2 = getDisplay(model2);

    if (display2 == null) {
      logger.error("Unknown model: '%'", model2);
      return;
    }

    logger.info("'%' is being compared to '%'", model1, model2);

    display1.compareWithMonitor(display2);
  }

  private static void commandAssist(Tokens tokens) {
    if (tokens.size() != 2) {
      logger.error("Incorrent number of parameters for ASSIST command\nUsage: ASSIST <manager>");
      return;
    }

    String name = tokens.get(1);
    DisplayManager manager = getDisplayManager(name);

    if (manager == null) {
      logger.error("Unknown manager: '%'", name);
      return;
    }

    logger.info("'%' is assisting a customer", name);
    manager.assist();
  }

  private static void commandBuy(Tokens tokens) {
    if (tokens.size() != 3) {
      logger.error("Incorrect number of parameters supplied to BUY command\nUsage: BUY <model> <manager>");
      return;
    }

    String model = tokens.get(1);
    Display display = getDisplay(model);

    if (display == null) {
      logger.error("Unknown display: '%'", model);
      return;
    }

    String name = tokens.get(2);
    DisplayManager manager = getDisplayManager(name);

    if (manager == null) {
      logger.error("Unknown manager: '%'", name);
      return;
    }

    logger.info("'%' sold '%'", name, model);
    manager.buyDisplay(model);
  }
}
