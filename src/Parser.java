import java.util.Arrays;
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
      //System.out.println(parseTree.process());

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
        if (token.getType() == TokenType.FORW || token.getType() == TokenType.BACK
                || token.getType() == TokenType.LEFT || token.getType() == TokenType.RIGHT) {

            // Save the decimal token.
            Token decimal = lexer.nextToken();

            // DECIMAL
            if (decimal.getType() != TokenType.DECIMAL) {
                // ERROR
                System.out.println("ERROR DECIMAL");
            }
            // PERIOD
            if (lexer.nextToken().getType() != TokenType.PERIOD) {
                // ERROR
                System.out.println("ERROR PERIOD");
            }
            // NEW LeafNode
            return new LeafNode(token.getType(), decimal.getData());
        } else if (token.getType() == TokenType.UP || token.getType() == TokenType.DOWN) {

            if (lexer.nextToken().getType() != TokenType.PERIOD) {
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

            if (lexer.nextToken().getType() != TokenType.PERIOD) {
                // ERROR
                System.out.println("ERROR PERIOD");
            }

            return new LeafNode(token.getType(), hex.getData());
        } else if (token.getType() == TokenType.REP) {
            // Save the decimal token.
            Token decimal = lexer.nextToken();

            // DECIMAL
            if (decimal.getType() != TokenType.DECIMAL) {
                // ERROR
                System.out.println("ERROR DECIMAL");
            }
            Token next = lexer.nextToken();
            if (next.getType() == TokenType.QUOTE) {

                return new BranchNode(new BranchNode(new LeafNode(token.getType(),decimal.getData()), repHelper()), expressionBranch());

            } else if (next.validInstruction()) {
                // Ingen lista, bara ett leaf till höger.
                System.out.println("EJ KLAR");
            } else {
                System.out.println("ERROR ERROR");
                return null;
            }
            return null;
        }
        return null;
    }


    private ParseTree repHelper() {
        // No QUOTE
        List<Token> repArg = new ArrayList<>();
        while (lexer.hasMoreTokens() && lexer.peekToken().getType() != TokenType.QUOTE) {
            repArg.add(lexer.nextToken());
        }
        if (repArg.stream().anyMatch(o -> o.getType().equals(TokenType.REP))) {
            // Add QUOTE
            repArg.add(lexer.nextToken());

            while (lexer.hasMoreTokens() && lexer.peekToken().getType() != TokenType.QUOTE) {
                repArg.add(lexer.nextToken());
            }
            repArg.add(lexer.nextToken());
            Lexer ll = new Lexer((repArg));
            return new BranchNode(new Parser(ll).parse(), repHelper());
        }
        if (!repArg.isEmpty()) {
            Lexer l = new Lexer(repArg);
            return new Parser(l).parse();
        }
        // Throw away last quote
        //lexer.nextToken();
        return parse();
    }



        //    if (repArg.get(repArg.size() - 2).getType() == TokenType.REP) {
        //        return new BranchNode(new Parser(new Lexer(repArg)).parse(), repHelper());
        //    }
        //    if (repArg.contains(TokenType.REP)) {
        //
        //    }

    /*
      public ParseTree repHelper() {

          // bygga lista med barn
          List<Token> repArguments = new ArrayList<>();

          while (lexer.hasMoreTokens() && lexer.peekToken().getType() != TokenType.QUOTE) {

              if (lexer.peekToken().getType() == TokenType.REP) {
                  Token rep = lexer.nextToken();
                  Token dec = lexer.nextToken();
                  if (dec.getType() == TokenType.DECIMAL) {
                    return new BranchNode(new LeafNode(rep.getType(), dec.getData()), repHelper());
                  } else {
                      System.out.println("Error in repHelper");
                  }
              }

              repArguments.add(lexer.nextToken());
          }


          while (lexer.hasMoreTokens() && lexer.peekToken().getType() != TokenType.QUOTE) {

          }

          // kasta "
          lexer.nextToken();
          Lexer l = new Lexer(repArguments);
}
*/
}
