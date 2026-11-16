import java.util.ArrayList;
import java.util.List;

public class DisplayManager {
  private String name;
  private List<Display> assignedDisplays;
  private Logger logger;

  public DisplayManager(String name, Logger logger) {
    this.name = name;
    this.assignedDisplays = new ArrayList<Display>() {
    };
    this.logger = logger;

    this.logger.trace("Created manager: %", name);
  }

  public DisplayManager(String name) {
    this(name, new Logger(name));
  }

  public void assignDisplay(Display d) {
    this.assignedDisplays.add(d);
    this.logger.trace("A display was assigned to '%': %", this.name, d.getModel());
  }

  public void assist() {
    for (int i = 1; i < this.assignedDisplays.size(); i++) {
      this.assignedDisplays.get(i - 1).compareWithMonitor(this.assignedDisplays.get(i));
    }
  }

  public String getName() {
    return this.name;
  }

  public Display buyDisplay(Display d) {
    if (!this.assignedDisplays.contains(d)) {
      this.logger.warn("A display was removed that was not in the assigned display list: %", d.getModel());
    }

    this.assignedDisplays.remove(d);
    this.logger.info("A display was bought: %", d.getModel());
    return d;
  }

  public Display buyDisplay(String model) {
    for (Display display : this.assignedDisplays) {
      if (display.getModel() == model) {
        this.assignedDisplays.remove(display);
        this.logger.info("A display was bought: %", model);
        return display;
      }
    }

    this.logger.warn("'%' model cannot be bought as it is not in the assigned display list", model);
    return null;
  }
}
