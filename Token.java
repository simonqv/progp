// Source: Exempelkod

// Tänk Lables 
enum TokenType {
    FORW, BACK, LEFT, RIGHT, UP, DOWN, QUOTE, REP, PERIOD, COLOR, DECMIAL, HEX,  ERROR
}

class Token {
	private TokenType type;
    private Object data;
    //private Int rowNumber;
    
    // UP, DOWN, QUOTE, PERIOD
	public Token(TokenType type) {
		this.type = type;
		this.data = null;
	}

    // FORW, BACK, LEFT, RIGHT, COLOR, DECIMAL, HEX, REP
	public Token(TokenType type, Object data) {
		this.type = type;
		this.data = data;
    }

	public TokenType getType() { return type; }
	public Object getData() { return data; }

}
