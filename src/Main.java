// LAB S2 - DD1362 Programmeringsparadigm.
// Simon Larpers Qvist
// Beata Johansson

import java.util.ArrayList;

public class Main {

  public static void main(String args[]) {
    // Lexical analysis.
    Lexer sequenceToTokens = new Lexer();

    // Parse tokens.
    Parser parsedTokens = new Parser(sequenceToTokens);
    ParseTree parseTree = parsedTokens.parse();

    // Evaluate the parse tree.
    Evaluate e = new Evaluate(parseTree);
    e.traverse(parseTree);

    // If no error has been found, print the line segments.
    if (e.foundError == false) {
      e.printLineSegments();
    }
  }
}
