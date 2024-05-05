// LAB S2 - DD1362 Programmeringsparadigm.
// Simon Larpers Qvist
// Beata Johansson

import java.util.ArrayList;

public class Main {

    // Runs the program.
    public static void main(String[] args) {

        try {
            // Lexical analysis.
            Lexer lexer = new Lexer();
            // Produce syntax tree.
            ParseTree parseTree = new Parser(lexer).parse();
            // Evaluate syntax tree.
            Evaluate evaluate = new Evaluate();
            evaluate.traverse(parseTree);
            System.out.println(evaluate.output.toString());

        } catch (SyntaxError syntaxError){
            // For any thrown error, print error message.
            System.out.println(syntaxError.getMessage());
        }
    }
}
