import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class CommandReader {
  public static List<String> read(String filename) {
    Scanner file;
    List<String> lines = List.of();
    try {
      file = new Scanner(new File(filename));
    } catch (FileNotFoundException e) {
      return lines;
    }

    while (file.hasNext()) {
      String line = file.next();
      lines.add(line);
    }

    file.close();

    return lines;
  }
}
