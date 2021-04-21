// LAB S2 - DD1362 Programmeringsparadigm.
// Simon Larpers Qvist
// Beata Johansson

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        try {
            Lexer sequenceToTokens = new Lexer();
            Parser parsedTokens = new Parser(sequenceToTokens);
            ParseTree parseTree = parsedTokens.parse();
            Evaluate e = new Evaluate(parseTree);
            e.traverse(parseTree);
            System.out.println(e.output.toString());
        } catch (SyntaxError syntaxError){
            //syntaxError.printStackTrace();
            System.out.println(syntaxError.getMessage());
        }
    }
}
