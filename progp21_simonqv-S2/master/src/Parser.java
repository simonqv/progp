// LAB S2 - DD1362 Programmeringsparadigm.
// Simon Larpers Qvist
// Beata Johansson

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final Lexer lexer;

    /**
     * Construct a Parser object.
     *
     * @param lexer Lexer object.
     */
    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    /**
     * Parse the tokens into a parse tree.
     *
     * @return A parse tree.
     * @throws SyntaxError if more tokens are left.
     */
    public ParseTree parse() throws SyntaxError {
        // Start building the tree.
        ParseTree parseTree = expressionBranch();
        // If more tokens exists after building the tree, there's an error.
        if (lexer.hasMoreTokens()) {
            throw new SyntaxError(errorMessage(lexer.peekToken().getRow()));
        }
        return parseTree;
    }

    /**
     * Build the parse tree by adding Branch nodes that can contain either Leaf nodes (terminal tokens)
     * or other Branch nodes (non-terminal tokens).
     *
     * @return ParseTree, which is a Leaf or Branch node.
     * @throws SyntaxError If "wrong" token.
     */
    private ParseTree expressionBranch() throws SyntaxError {
        ParseTree node = tokenLeaf();

        // If next token is quote, move one token forward (end quote).
        if (lexer.hasMoreTokens() && lexer.peekToken().getType() == TokenType.QUOTE) {
            return node;
        }
        // If next token is an instruction, make recursive call on right side.
        else if (lexer.hasMoreTokens() && lexer.peekToken().validInstruction()) {
            return new BranchNode(node, expressionBranch());
        } else if (lexer.hasMoreTokens() && !lexer.peekToken().validInstruction()) {
            throw new SyntaxError(errorMessage(lexer.peekToken().getRow()));
        }
        return node;
    }

    /**
     * Match each token to the corresponding instruction and discover potential errors in the token sequence.
     *
     * @return Either a Branch Node or a Leaf Node.
     * @throws SyntaxError if there's something wrong with the input.
     */
    private ParseTree tokenLeaf() throws SyntaxError {
        // Look ahead to the next token in the sequence.
        Token token;

        if (lexer.hasMoreTokens()) {
            token = lexer.nextToken();
        } else {
            return null;
        }

        // REP token.
        if (token.getType() == TokenType.REP) {
            // Check if not end of file.
            if (!lexer.hasMoreTokens()) {
                throw new SyntaxError(errorMessage(token.getRow()));
            }

            // Save the next token, should be DECIMAL.
            Token decimal = lexer.nextToken();
            if (decimal.getType() != TokenType.DECIMAL || !lexer.hasMoreTokens()) {
                // if not DECIMAL, throw.
                throw new SyntaxError(errorMessage(token.getRow()));
            }
            // After DECIMAL there should be a QUOTE.
            if (lexer.peekToken().getType() == TokenType.QUOTE) {
                // Add a new branch with REP-token to the left, and REP expression to the right.

                // Eat start QUOTE.
                lexer.nextToken();

                // REP expression.
                ParseTree expr = expressionBranch();

                if (lexer.hasMoreTokens() && lexer.peekToken().getType() == TokenType.QUOTE) {
                    // Eat corresponding end QUOTE
                    lexer.nextToken();
                    // Return branch with REP leaf to the left and expression to the right.
                    return new BranchNode(new LeafNode(token.getType(), decimal.getData()), expr);
                } else {
                    // If something wrong, throw error.
                    throw new SyntaxError(errorMessage(lexer.currentToken().getRow()));
                }

                // If no QUOTE exists, then only repeat one instruction.
            } else if (lexer.peekToken().validInstruction()) {
                // Add a new branch with REP-token to the left, and the argument to the right as a leaf.
                return new BranchNode(new LeafNode(token.getType(), decimal.getData()), tokenLeaf());
            } else {
                // if something wrong, throw error.
                throw new SyntaxError(errorMessage(decimal.getRow()));
            }

        // Terminal tokens that must be followed by a DECIMAL: FORW, BACK, LEFT, RIGHT.
        } else if (token.getType() == TokenType.FORW || token.getType() == TokenType.BACK
                || token.getType() == TokenType.LEFT || token.getType() == TokenType.RIGHT) {

            // Reached end of file too soon.
            if (!lexer.hasMoreTokens()) {
                throw new SyntaxError(errorMessage(token.getRow()));
            }
            // Save the next token.
            Token decimal = lexer.nextToken();
            // After the token there should be a DECIMAL.
            if (decimal.getType() != TokenType.DECIMAL || !lexer.hasMoreTokens()) {
                throw new SyntaxError(errorMessage(decimal.getRow()));
            }
            // After DECIMAL there should be a PERIOD.
            Token period = lexer.nextToken();
            if (period.getType() != TokenType.PERIOD) {
                throw new SyntaxError(errorMessage(period.getRow()));
            }
            // Add the terminal token to the parse tree.
            return new LeafNode(token.getType(), decimal.getData());

        // Terminal tokens that takes no arguments: UP, DOWN.
        } else if (token.getType() == TokenType.UP || token.getType() == TokenType.DOWN) {
            // Reached end of file too soon.
            if (!lexer.hasMoreTokens()) {
                throw new SyntaxError(errorMessage(token.getRow()));
            }
            // Next token should be a PERIOD.
            Token period = lexer.nextToken();
            if (period.getType() != TokenType.PERIOD) {
                throw new SyntaxError(errorMessage(period.getRow()));
            }
            // Add the terminal token to the parse tree.
            return new LeafNode(token.getType());

        // Terminal tokens that require a HEX argument: COLOR.
        } else if (token.getType() == TokenType.COLOR) {
            // Reached end of file too soon.
            if (!lexer.hasMoreTokens()) {
                throw new SyntaxError(errorMessage(token.getRow()));
            }
            // Next token should be a HEX.
            Token hex = lexer.nextToken();
            if (hex.getType() != TokenType.HEX || !lexer.hasMoreTokens()) {
                throw new SyntaxError(errorMessage(hex.getRow()));
            }
            // Next token should be a PERIOD.
            Token period = lexer.nextToken();
            if (period.getType() != TokenType.PERIOD) {
                throw new SyntaxError(errorMessage(period.getRow()));
            }
            // Add the terminal token to the parse tree.
            return new LeafNode(token.getType(), hex.getData());
        } else {
            // If no valid instruction: error.
            throw new SyntaxError(errorMessage(token.getRow()));
        }
    }

    /**
     * Get error message for output.
     * @param rowNum row number of incorrect token.
     * @return String error message.
     */
    private String errorMessage(int rowNum) {
        return String.format("Syntaxfel på rad %d", rowNum);
    }
}

