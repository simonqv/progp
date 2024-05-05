// LAB S2 - DD1362 Programmeringsparadigm.
// Simon Larpers Qvist
// Beata Johansson

enum TokenType {
    FORW, BACK, LEFT, RIGHT, UP, DOWN, QUOTE, REP, PERIOD, COLOR, DECIMAL, HEX, ERROR
}

class Token {
	private final TokenType type;
    private final Object data;
    private final int rowNumber;


    /**
     * Construction for tokens.
     * @param type UP, DOWN, QUOTE, PERIOD, FORW, BACK, LEFT, RIGHT, COLOR, REP, ERROR.
     * @param rowNumber row number.
     */
	public Token(TokenType type, int rowNumber) {
		this.type = type;
		this.rowNumber = rowNumber;
		this.data = null;
	}

    /**
     * Construction for the tokens.
     * @param type DECIMAL, HEX.
     * @param rowNumber row number.
     * @param data Token value.
     */
	public Token(TokenType type, int rowNumber, Object data) {
		this.type = type;
		this.rowNumber = rowNumber;
		this.data = data;
    }

    /**
     * Get the row number of token.
     * @return Row number.
     */
    public int getRow() {
        return rowNumber;
    }

    /**
     * Get the type of token.
     * @return Token type.
     */
	public TokenType getType() { 
        return type; 
    }

     /**
     * Get the data of token.
     * @return data.
     */
    public Object getData() { 
        return data; 
    }
    
     /**
     * Check if token is an instruction.
     * @return true if valid.
     */
    public boolean validInstruction() {
        return type == TokenType.FORW ||
            type == TokenType.BACK ||
            type == TokenType.UP ||
            type == TokenType.DOWN ||
            type == TokenType.REP ||
            type == TokenType.COLOR ||
            type == TokenType.LEFT ||
            type == TokenType.RIGHT;
    }

    /**
     * Print a token.
     */
	@Override
    public String toString() {
	    return rowNumber + ": " + type + " " + data;
    }
}
