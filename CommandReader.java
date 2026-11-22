import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CommandReader {
  public static List<String> read(String filename, Logger logger) {
    Path path = Path.of(filename);

    ArrayList<String> result = new ArrayList<String>();
    logger.info("Opening command file: '%'", filename);

    try (Stream<String> lines = Files.lines(path)) {
      lines.forEach(result::add);
    } catch (IOException e) {
      logger.error("This file does not exist: %", filename);
    }

    return result;
  }
}
