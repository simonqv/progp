

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
        } //else if (token.getType() == TokenType.REP) {}
        else {
          System.out.println("ERROR ERROR");
          return null;
        }
      }

}
