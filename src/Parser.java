import java.util.ArrayList;
import java.util.List;

public class Parser {

    private Lexer lexer;

    /**
     * Construct a Parser object.
     * @param lexer
     */
    public Parser(Lexer lexer) {
  		this.lexer = lexer;
  	}

    /**
     * Parse the tokens into a parse tree.
     * 
     * @return A parse tree.
     */
    public ParseTree parse() {
      // Start building the tree.
      ParseTree parseTree = expressionBranch();
      // Print the parse tree.
      return parseTree;
    }

    /**
     * Build the parse tree by adding Branch nodes that can contain either terminal tokens (leaves) or other non-terminal tokens (branches).
     * @return node, which can be a leaf or branch.
     */
    private ParseTree expressionBranch() {
      ParseTree node = tokenLeaf();
      // If next token is quote, move one token forward (end quote).
      if (lexer.hasMoreTokens() && lexer.peekToken().getType() == TokenType.QUOTE){
        lexer.nextToken();
      }

      // If next token is an instruction, make recursive call on right side.
      else if (lexer.hasMoreTokens() && lexer.peekToken().validInstruction()){
        node = new BranchNode(node, expressionBranch());

      } else if (lexer.hasMoreTokens() && !lexer.peekToken().validInstruction()) {
          return new LeafNode(TokenType.ERROR, lexer.peekToken().getRow());
      }
      return node;
    }

    /**
     * Match each token to the corresponding production rule and discover potential errors in the token sequence.
     * 
     * @return Either a branchNode, LeafNode or Error.
     */
    private ParseTree tokenLeaf() {
        // Look ahead to the next token in the sequence.
        Token token = lexer.nextToken();

        // Select the corresponding production.

        // REP token.
        if (token.getType() == TokenType.REP) {
            // Check not end of file.
            if (!lexer.hasMoreTokens()) {
                return new LeafNode(TokenType.ERROR, token.getRow());
            }

            // Save the next token.
            Token decimal = lexer.nextToken();
            // After REP there should be a DECIMAL.
            if (decimal.getType() != TokenType.DECIMAL || !lexer.hasMoreTokens()) {
                // Throw error.
                return new LeafNode(TokenType.ERROR, decimal.getRow());
            }
            // After DECIMAL there should be a QUOTE.
            if (lexer.peekToken().getType() == TokenType.QUOTE) {
                // Add a new branch with REP-token to the left, and arguments to the right.
                // The REP arguments will be a branch.
                // Eat QUOTE.
                lexer.nextToken();
                ParseTree expr = expressionBranch();
                if (lexer.currentToken().getType() == TokenType.QUOTE) {
                    return new BranchNode(new LeafNode(token.getType(), decimal.getData()), expr);
                } else {
                    return new LeafNode(TokenType.ERROR, token.getRow());
                }

                // If no QUOTE exists, then only repeat one instruction.
            } else if (lexer.peekToken().validInstruction() && lexer.peekToken().getType() != TokenType.REP) {
                // Add a new branch with REP-token to the left, and the argument to the right as a leaf.
                return new BranchNode(new LeafNode(token.getType(), decimal.getData()), tokenLeaf());
            } else if (lexer.peekToken().getType() == TokenType.REP) {
                return new BranchNode(new LeafNode(token.getType(), decimal.getData()), tokenLeaf());
            } else {
                // Throw error.
                return new LeafNode(TokenType.ERROR, decimal.getRow());
            }
        } else {
            // Terminal tokens that must be followed by a DECIMAL: FORW, BACK, LEFT, RIGHT.
            if (token.getType() == TokenType.FORW || token.getType() == TokenType.BACK
                    || token.getType() == TokenType.LEFT || token.getType() == TokenType.RIGHT) {
                if (!lexer.hasMoreTokens()) {
                    return new LeafNode(TokenType.ERROR, token.getRow());
                }
                // Save the next token.
                Token decimal = lexer.nextToken();
                // After the token there should be a DECIMAL.
                if (decimal.getType() != TokenType.DECIMAL || !lexer.hasMoreTokens()) {
                    // Throw error.
                    return new LeafNode(TokenType.ERROR, token.getRow());
                }
                Token period = lexer.nextToken();
                // After DECIMAL there should be a PERIOD.
                if (period.getType() != TokenType.PERIOD) {
                    // Throw error.
                    return new LeafNode(TokenType.ERROR, period.getRow());
                }
                // Add the terminal token to the parse tree.
                return new LeafNode(token.getType(), decimal.getData());
            // Terminal tokens that takes no arguments: UP, DOWN.
            } else if (token.getType() == TokenType.UP || token.getType() == TokenType.DOWN) {
                if (!lexer.hasMoreTokens()) {
                    return new LeafNode(TokenType.ERROR, token.getRow());
                }
                // Save the next token.
                Token period = lexer.nextToken();
                // After the token there should be a PERIOD.
                if (period.getType() != TokenType.PERIOD) {
                    // Throw error.
                    return new LeafNode(TokenType.ERROR, period.getRow());
                }
                // Add the terminal token to the parse tree.
                return new LeafNode(token.getType());
            // Terminal tokens that require a HEX argument: COLOR.
            } else if (token.getType() == TokenType.COLOR) {
                if (!lexer.hasMoreTokens()) {
                    return new LeafNode(TokenType.ERROR, token.getRow());
                }
                // Save the next token.
                Token hex = lexer.nextToken();
                // After the token there should be a HEX.
                if (hex.getType() != TokenType.HEX || !lexer.hasMoreTokens()) {
                    // Throw error.
                    return new LeafNode(TokenType.ERROR, hex.getRow());
                }
                Token period = lexer.nextToken();
                // After HEX there should be a PERIOD.
                if (period.getType() != TokenType.PERIOD) {
                    // Throw error.
                    return new LeafNode(TokenType.ERROR, period.getRow());
                }
                // Add the terminal token to the parse tree.
                return new LeafNode(token.getType(), hex.getData());
            } else {
                // Throw error.
                return new LeafNode(TokenType.ERROR, token.getRow());
            }
        }

    }
}
