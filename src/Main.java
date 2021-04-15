public class Main {

  public static void main(String args[]) {
    Lexer sequenceToTokens = new Lexer();
    Parser parsedTokens = new Parser(sequenceToTokens);
    System.out.println(sequenceToTokens);
    ParseTree parseTree = parsedTokens.parse();
    Runner r = new Runner(parseTree);
    r.move();
    //System.out.println(parseTree.process());
  }
}
