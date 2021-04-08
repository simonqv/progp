
public class Main {

  public static void main(String args[]) {
    Lexer sequenceToTokens = new Lexer();
    Parser parsedTokens = new Parser(sequenceToTokens);
    ParseTree parseTree = parsedTokens.parse();
    System.out.println(parseTree.process());
  }
}
