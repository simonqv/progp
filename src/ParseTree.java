// LAB S2 - DD1362 Programmeringsparadigm.
// Simon Larpers Qvist
// Beata Johansson

abstract class ParseTree {
	abstract public String process();
	abstract public boolean isLeaf();
	abstract public ParseTree getLeft();
	abstract public ParseTree getRight();
	abstract public TokenType getInstruction();
	abstract public Object getData();
	abstract public String errorMessage();
}

// Leaf Node class for terminal tokens.
class LeafNode extends ParseTree {
	TokenType instruction;
	Object data; 

	// Constructor for the tokens: 
    // UP, DOWN.
	public LeafNode(TokenType instruction) {
		this.instruction = instruction;
		this.data = null;
	}
	
	// Constructor for the tokens: 
	// FORW, BACK, LEFT, RIGHT, COLOR, REP, ERROR.
	public LeafNode(TokenType instruction, Object data) {
		this.instruction = instruction;
		this.data = data; // Data is HEX or DECIMAL.
	}

	/**
	 * Get instruction from Leaf.
	 */
    public TokenType getInstruction() {
        return instruction;
    }

	/**
	 * Get data from Leaf.
	 */
    public Object getData() {
        return data;
    }

	/**
	 * Print Leaf Node.
	 */
    public String process() {
		return instruction.toString() + " " + data;
	}

	/**
	 * Check if a node in parse tree is a leaf.
	 */
	public boolean isLeaf() {
		return true;
	}

	/**
	 * Get the left branch, that is null.
	 */
	public ParseTree getLeft() {
		return null;
	}

	/**
	 * Get the right branch, that is null.
	 */
	public ParseTree getRight() {
		return null;
	}

	/**
	 * Return error message.
	 */
    public String errorMessage() {
        return String.format("Syntaxfel på rad %d", getData());
    }
}

// Branch Node class for expressions and non-terminal tokens.
class BranchNode extends ParseTree {
	ParseTree left;
	ParseTree right;

	public BranchNode(ParseTree left, ParseTree right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * Print Branch Node.
	 */
	public String process() {
        return "[" + left.process() + " - " + right.process() + "]"; //"[" + left.process() + ";" + right.process() + " ]";
	}

	/**
	 * Get instruction form Branch, that is null.
	 */
	public TokenType getInstruction() {
        return null;
    }

	/**
	 * Get data form Branch, that is null.
	 */
    public Object getData() {
        return null;
    }

	/**
	 * Check if a node in parse tree is a leaf.
	 */
	public boolean isLeaf() {
		return false;
	}

	/**
	 * Get the right branch.
	 */
	public ParseTree getLeft() {
		return this.left;
	}

	/**
	 * Get the right branch.
	 */
	public ParseTree getRight() {
		return this.right;
	}

    public String errorMessage() { return "";}
}
