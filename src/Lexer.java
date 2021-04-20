// LAB S2 - DD1362 Programmeringsparadigm.
// Simon Larpers Qvist
// Beata Johansson

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private List<Token> tokens;
    private static final Pattern validTokenPattern = Pattern.compile("DOWN\\s*|UP\\s*|FORW\\s+|BACK\\s+|LEFT\\s+|RIGHT\\s+|REP\\s+|COLOR\\s+|\\.\\s*|\"\\s*|#[A-F0-9]{6}\\s*|[1-9]\\d*(?=(\\.|\\s))\\s*|^\\s$|\\n");
    private int current = 0;

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
        return list;
    }

    public Lexer(List<Token> tokens) {
      this.tokens = tokens;
    }

    public Lexer() {
        List<String> lines = Lexer.readInput();
        int rowNum = 1;
        tokens = new ArrayList<Token>();

        for (String line : lines) {
            Matcher m = validTokenPattern.matcher(line);
            int position = 0;
            while (m.find()) {
                // Check if input contains non-tokens.
                if (m.start() != position) {
                    tokens.add(new Token(TokenType.ERROR, rowNum));
                }
                switch (m.group().trim()) {
                    case "FORW":
                        tokens.add(new Token(TokenType.FORW, rowNum));
                        break;
                    case "BACK":
                        tokens.add(new Token(TokenType.BACK, rowNum));
                        break;
                    case "UP":
                        tokens.add(new Token(TokenType.UP, rowNum));
                        break;
                    case "DOWN":
                        tokens.add(new Token(TokenType.DOWN, rowNum));
                        break;
                    case "LEFT": tokens.add(new Token(TokenType.LEFT, rowNum));
                        break;
                    case "RIGHT": tokens.add(new Token(TokenType.RIGHT, rowNum));
                        break;
                    case "REP":
                        tokens.add(new Token(TokenType.REP, rowNum));
                        break;
                    case "\"":
                        tokens.add(new Token(TokenType.QUOTE, rowNum));
                        break;
                    case ".":
                        tokens.add(new Token(TokenType.PERIOD, rowNum));
                        break;
                    case "COLOR":
                        tokens.add(new Token(TokenType.COLOR, rowNum));
                        break;
                }
                if (m.group().matches("#[A-F0-9]{6}\\s*")) {
                    tokens.add(new Token(TokenType.HEX, rowNum, m.group().trim()));
                } else if (m.group().matches("\\d+\\s*")) {
                    tokens.add(new Token(TokenType.DECIMAL, rowNum, Integer.parseInt(m.group().trim())));
                }
                position = m.end();
            }
            if (position != line.length()) {
                tokens.add(new Token(TokenType.ERROR, rowNum));
            }
            rowNum++;
        }
    }

    /**
     * Peek on next token, without moving forward in the sequence.
     * @return Next token.
     */
  	public Token peekToken(){
  		return tokens.get(current);
  	}

    /**
     * Get the current token.
     * @return Current token.
     */
  	public Token currentToken() {
        return tokens.get(current - 1);
    }

    /**
     * Get the next token and move forward in the sequence.
     * @return
     */
  	public Token nextToken() {
  		Token res = peekToken();
  		current++;
  		return res;
  	}

    /**
     * Check if there are tokens left in the sequence.
     * @return true or false.
     */
    public boolean hasMoreTokens() {
      return current < tokens.size();
    }

    /**
     * Print method for the sequence of tokens.
     */
    @Override
    public String toString() {
      return tokens.toString();
    }
}
