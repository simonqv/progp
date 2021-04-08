import java.util.List;
import java.util.ArrayList;

public class Parser {

    private Lexer lexer;

    // Constructor.
    public Parser(Lexer lexer) {
  		this.lexer = lexer;
  	}

    public ParseTree parse() {
      // Start symbol
      ParseTree parseTree = expressionBranch();
      System.out.println(parseTree.process());

      return parseTree;
    }

    private ParseTree expressionBranch() {
      ParseTree node = tokenLeaf();
      // Build branchnodes if next token is an instruction.
      if (lexer.hasMoreTokens() && lexer.peekToken().validInstruction()){
        node = new BranchNode(node, expressionBranch());
      }
      return node;
    }

    // Parse the Lexer object tokens.
    private ParseTree tokenLeaf() {
      // Look ahead to the next token in the sequence,
      // in order to select the corresponding production.
      Token token = lexer.nextToken();

      // Terminal token -> Create new BranchNode in syntax tree.
      if(token.getType() == TokenType.FORW || token.getType() == TokenType.BACK
      || token.getType() == TokenType.LEFT || token.getType() == TokenType.RIGHT) {

          // Save the decimal token.
          Token decimal = lexer.nextToken();

          // DECIMAL
          if(decimal.getType() != TokenType.DECIMAL) {
            // ERROR
            System.out.println("ERROR DECIMAL");
          }
          // PERIOD
          if(lexer.nextToken().getType() != TokenType.PERIOD) {
            // ERROR
            System.out.println("ERROR PERIOD");
          }
          // NEW LeafNode
          return new LeafNode(token.getType(), decimal.getData());
        } else if (token.getType() == TokenType.UP || token.getType() == TokenType.DOWN) {

            if(lexer.nextToken().getType() != TokenType.PERIOD){
              // ERROR
              System.out.println("ERROR PERIOD");
            }
            return new LeafNode(token.getType());
        } else if (token.getType() == TokenType.COLOR) {

            Token hex = lexer.nextToken();

            if (hex.getType() != TokenType.HEX) {
              // ERROR
              System.out.println("ERROR HEX");
            }

            if(lexer.nextToken().getType() != TokenType.PERIOD){
              // ERROR
              System.out.println("ERROR PERIOD");
            }

            return new LeafNode(token.getType(), hex.getData());
        } else if (token.getType() == TokenType.REP) {
            // Save the decimal token.
            Token decimal = lexer.nextToken();

            // DECIMAL
            if(decimal.getType() != TokenType.DECIMAL) {
              // ERROR
              System.out.println("ERROR DECIMAL");
            }

            Token next = lexer.nextToken();
            // If next == "
            if (next.getType() == TokenType.QUOTE) {
              // bygga lista med barn
              List<Token> hejsan = new ArrayList<>();
              while (lexer.peekToken().getType() != TokenType.QUOTE) {
                  hejsan.add(lexer.nextToken());
              }
              // kasta "
              lexer.nextToken();
              Lexer l = new Lexer(hejsan);
              return new BranchNode(new LeafNode(token.getType(), decimal.getData()), new Parser(l).parse());

            } else if (next.validInstruction()) {
              // Ingen lista, bara ett leaf till höger.
              System.out.println("EJ KLAR");
            } else {
              System.out.println("REP ERROR");
            }
          }

        else {
          System.out.println("ERROR ERROR");
          return null;
        }

      }
      return null;
}
