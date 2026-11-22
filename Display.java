public class Display {
  private String model;
  private Integer width; // in pixels
  private Integer height;
  private double ppi; // pixels-per-inch
  private Logger logger;

  Display(String model, Integer width, Integer height, double ppi) {
    this(model, width, height, ppi, new Logger(model));
  }

  Display(String model, Integer width, Integer height, double ppi, Logger logger) {
    this.model = model;
    this.width = width;
    this.height = height;
    this.ppi = ppi;
    this.logger = logger;

    this.logger.trace("A display was created: '%'", this.model);
  }

  public String getModel() {
    return this.model;
  }

  private double getSize() {
    return Math.sqrt(width * width + height * height) / ppi;
  }

  public void compareSize(Display other) {
    if (this.getSize() == other.getSize()) {
      System.out
          .println(String.format("%s (%.1f'') is the same size as %s (%.1f'')",
              this.model, this.getSize(), other.model, other.getSize()));
      return;
    }

    Display bigger = this;
    Display smaller = other;
    if (smaller.getSize() > bigger.getSize()) {
      bigger = other;
      smaller = this;
    }

    System.out
        .println(String.format("%s (%.1f'') is bigger than %s (%.1f'')",
            bigger.model, bigger.getSize(), smaller.model, smaller.getSize()));
  }

  public void compareSharpness(Display other) {
    if (this.ppi == other.ppi) {
      System.out
          .println(String.format("%s (%.1f) has the same sharpness as %s (%.1f)",
              this.model, this.ppi, other.model, other.ppi));
      return;
    }

    Display bigger = this;
    Display smaller = other;
    if (smaller.ppi > bigger.ppi) {
      bigger = other;
      smaller = this;
    }

    System.out
        .println(String.format("%s (%.1f) is sharper than %s (%.1f)",
            bigger.model, bigger.ppi, smaller.model, smaller.ppi));
  }

  public void compareWithMonitor(Display other) {
    this.compareSize(other);
    this.compareSharpness(other);
  }
}
