
// ParseTree.
abstract class ParseTree {
	abstract public String process();
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

	public String process() {
		return instruction.toString() + " " + data;
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

	public String process() {
		return "[" + left.process() + " - " + right.process() + "]"; //"[" + left.process() + ";" + right.process() + " ]";
	}

}
