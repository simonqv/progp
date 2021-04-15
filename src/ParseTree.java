
// ParseTree.
abstract class ParseTree {
	abstract public ParseTree process();
}

// Node for terminal tokens.
class LeafNode extends ParseTree {
	TokenType instruction;
	Object data; // HEX, DECIMAL

	public LeafNode(TokenType instruction) {
		this.instruction = instruction;
		this.data = null;
	}

	public LeafNode(TokenType instruction, Object data) {
		this.instruction = instruction;
		this.data = data;
	}

    public TokenType getInstruction() {
        return instruction;
    }

    public Object getData() {
        return data;
    }

    public LeafNode process() {
		//return instruction.toString() + " " + data;
        return this;
	}
}

// Node for expressions and non-terminal tokens.
class BranchNode extends ParseTree {
	ParseTree left;
	ParseTree right;

	public BranchNode(ParseTree left, ParseTree right) {
		this.left = left;
		this.right = right;
	}

	public ParseTree process() {

            return this.left.process();



        //return "[" + left.process() + " - " + right.process() + "]"; //"[" + left.process() + ";" + right.process() + " ]";
	}

}
