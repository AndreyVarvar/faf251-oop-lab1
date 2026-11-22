import java.util.ArrayList;
import java.util.List;

class Tokens {
  public ArrayList<String> tokens = new ArrayList<>();

  Tokens(List<String> tokens) {
    for (String token : tokens) {
      this.tokens.add(token.strip());
    }
  }

  public int size() {
    return this.tokens.size();
  }

  public String get(int index) {
    return this.tokens.get(index);
  }

  public String join(String joinString) {
    if (this.size() == 0) {
      return "";
    }

    String result = this.tokens.get(0);

    for (int i = 1; i < this.tokens.size(); i++) {
      result += joinString;
      result += this.tokens.get(i);
    }

    return result;
  }
}
