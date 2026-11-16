public class ConsoleSink implements Sink {
  public void write(String message) {
    System.out.println(message);
  }
}
