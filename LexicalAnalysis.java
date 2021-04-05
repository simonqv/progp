import java.util.List;
import java.util.Scanner;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LexicalAnalysis {

    private List<Token> tokens;
 
    public static String readInput() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder inputAsString = new StringBuilder();
        String nextLine;
        while (scanner.hasNext()) {
            nextLine = scanner.nextLine();
            
            if(nextLine.matches(".*%.*")) {
                nextLine = nextLine.replaceAll("%.*", "");
            }
            
            inputAsString.append(nextLine);

            if (!nextLine.equals("")) {
                inputAsString.append("\n");
            }
        }
        scanner.close();
        return inputAsString.toString().toUpperCase();
    }  

    // Group 1: REP ______ ."
    // Group 2: REP
    // Group 3: "___"
    // Group 4: Same as 3 without ""
    // Group 5: .
    // Group 6: REP _ MATCH _
    // Group 7: UP/DOWN .
    // Group 8: UP/DOWN
    // Group 9: LEFT/RIGHT/FORW/BACK __ .
    // Group 10: LEFT/RIGHT/FORW/BACK
    // Group 11: Decimal in Group 9
    // Group 12: COLOR #______ .
    // Group 13: COLOR
    // Group 14: #______
    private static final Pattern validTokenPattern = Pattern.compile("((REP)\\s+\\d+\\s+(\"((.|\\n)+)\"|(DOWN|BACK|UP|FORW|LEFT|RIGHT)\\s+\\d*\\.))|((DOWN|UP)\\s*\\.)|((FORW|BACK|LEFT|RIGHT)\\s+(\\d+)\\s*\\.)|((COLOR)\\s+(#[A-F0-9]{6})\\s*\\.)");


    public LexicalAnalysis() {
        String inputAsString = LexicalAnalysis.readInput();
        Matcher m = validTokenPattern.matcher(inputAsString);
        tokens = new ArrayList<Token>();

        //System.out.println(inputAsString);
            // INVALID TOKEN

            // FORW
        while (m.find()) {

            String temp = m.group();
            // Match REP group
            if (temp.equals(m.group(1))) {

                List<Token> repList = lexRepHelper(m.group(4));

            // Match UP or DOWN Group.
            } else if (temp.equals(m.group(7))) {
                // Creates and adds Token with UP or DOWN Type.
                String ud = m.group(8);
                tokens.add(new Token(TokenType.valueOf(ud)));

            // Matches LEFT, RIGHT, FORW, BACK Group.
            } else if (temp.equals(m.group(9))) {
                // Creates and adds Token with LEFT, RIGHT, FORW or BACK Type.
                String lrfb = m.group(10);
                tokens.add(new Token(TokenType.valueOf(lrfb), Integer.parseInt(m.group(11))));

            // Matches COLOR Group.
            } else if (temp.equals(m.group(12))) {

                tokens.add(new Token(TokenType.COLOR, m.group(14)));
            }

            for (Token token : tokens) {
                System.out.println(token.getType());
            }
        }
    }

    private static List<Token> lexRepHelper(String innerRep) {
        Matcher ma = validTokenPattern.matcher(innerRep);



        if (temp.equals(ma.group(7))) {
            // Creates and adds Token with UP or DOWN Type.
            String ud = ma.group(8);
            tokens.add(new Token(TokenType.valueOf(ud)));

            // Matches LEFT, RIGHT, FORW, BACK Group.
        } else if (temp.equals(ma.group(9))) {
            // Creates and adds Token with LEFT, RIGHT, FORW or BACK Type.
            String lrfb = ma.group(10);
            tokens.add(new Token(TokenType.valueOf(lrfb), Integer.parseInt(ma.group(11))));

            // Matches COLOR Group.
        } else if (temp.equals(ma.group(12))) {

            tokens.add(new Token(TokenType.COLOR, ma.group(14)));
        }

    }

    public static void main(String args[]) throws java.io.IOException {

        LexicalAnalysis x = new LexicalAnalysis();
        //String s = x.readInput();
        //System.out.println(s);
    } 



}