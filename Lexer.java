// Simon Larpers Qvist
// Beata Johansson

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private List<Token> tokens;
    private static final Pattern validTokenPattern = Pattern.compile("DOWN\\s*|UP\\s*|FORW\\s+|BACK\\s+|LEFT\\s+|RIGHT\\s+|REP\\s+|COLOR\\s+|\\.\\s*|\"\\s*|#[A-F0-9]{6}\\s*|\\d+\\s*|^\\s$|\\n");

    public static List<String> readInput() {
        List<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        StringBuilder inputAsString = new StringBuilder();
        String nextLine;
        while (scanner.hasNext()) {
            nextLine = scanner.nextLine();
            
            if(nextLine.matches(".*%.*")) {
                nextLine = nextLine.replaceAll("%.*", "");
            }
            
            inputAsString.replace(0, inputAsString.length(), nextLine);
            list.add(inputAsString.toString().toUpperCase().trim() + " ");
        }
        scanner.close();
        for (String x : list) {
            System.out.println(x);

        }
        return list;
    }

    public Lexer() {
        List<String> lines = Lexer.readInput();
        int rowNum = 1;
        tokens = new ArrayList<Token>();

        System.out.println(lines);

        for (String line : lines) {
            // "COLOR "
            Matcher m = validTokenPattern.matcher(line);
            int position = 0;

            while (m.find()) {

                // Check if input contains non-tokens.
                if (m.start() != position) {
                    tokens.add(new Token(TokenType.ERROR, rowNum));
                }

                // Non-terminal tokens. No data.
                switch (m.group().trim()) {
                    case "FORW" -> tokens.add(new Token(TokenType.FORW, rowNum));
                    case "BACK" -> tokens.add(new Token(TokenType.BACK, rowNum));
                    case "UP" -> tokens.add(new Token(TokenType.UP, rowNum));
                    case "DOWN" -> tokens.add(new Token(TokenType.DOWN, rowNum));
                    case "LEFT" -> tokens.add(new Token(TokenType.LEFT, rowNum));
                    case "RIGHT" -> tokens.add(new Token(TokenType.RIGHT, rowNum));
                    case "REP" -> tokens.add(new Token(TokenType.REP, rowNum));
                    case "\"" -> tokens.add(new Token(TokenType.QUOTE, rowNum));
                    case "." -> tokens.add(new Token(TokenType.PERIOD, rowNum));
                    case "COLOR" -> tokens.add(new Token(TokenType.COLOR, rowNum));
                }

                // Terminal tokens. With data.
                if (m.group().matches("#[A-F0-9]{6}\\s*")) {
                    tokens.add(new Token(TokenType.HEX, rowNum, m.group().trim()));
                } else if (m.group().matches("\\d+\\s*")) {
                    tokens.add(new Token(TokenType.DECMIAL, rowNum, Integer.parseInt(m.group().trim())));
                }

                position = m.end();

            }
            if (position != line.length()) {
                tokens.add(new Token(TokenType.ERROR, rowNum));
            }
            rowNum ++;
        }
    }

    public static void main(String args[]) throws java.io.IOException {
        Lexer x = new Lexer();
        for (Token t : x.tokens) {
            System.out.println(t);
        }
    }
}