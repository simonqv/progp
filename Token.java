// Source: Exempelkod

enum TokenType {
    FORW, BACK, LEFT, RIGHT, UP, DOWN, QUOTE, REP, PERIOD, COLOR, DECMIAL, HEX,  ERROR
}

class Token {
	private TokenType type;
    private Object data;
    private int rowNumber;
    
    // UP, DOWN, QUOTE, PERIOD, FORW, BACK, LEFT, RIGHT, COLOR, REP
	public Token(TokenType type, int rowNumber) {
		this.type = type;
		this.rowNumber = rowNumber;
		this.data = null;
	}

    //  DECIMAL, HEX
	public Token(TokenType type, int rowNumber, Object data) {
		this.type = type;
		this.rowNumber = rowNumber;
		this.data = data;
    }

	public TokenType getType() { return type; }
	public Object getData() { return data; }

	@Override
    public String toString() {
	    return rowNumber + ": " + type + " " + data;
    }

    // Print error message.
    public void printError(int row) {
        System.out.println("Syntaxfel på rad " + row);
    }
}
