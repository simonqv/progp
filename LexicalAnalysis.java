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
        return inputAsString.toString();
    }  
    
    public LexicalAnalysis() {
        String inputAsString = LexicalAnalysis.readInput();
        Pattern validTokenPattern = Pattern.compile("REP\\s+\\d+\\s+(DOWN|BACK|UP|FORW|LEFT|RIGHT)\\s+\\d+\\.|REP\\s+\\d+\\s+\"+(.|\\n)+\"|DOWN\\s*\\.|UP\\s*\\.|FORW\\s+\\d+\\.|BACK\\s+\\d+\\s*\\.|LEFT\\s+\\d+\\s*\\.|RIGHT\\s+\\d+\\s*\\.|COLOR\\s+#[A-F0-9]{6}\\s*\\.");
        Matcher m = validTokenPattern.matcher(inputAsString);
        tokens = new ArrayList<Token>();
        

            // INVALID TOKEN

            // FORW
            System.out.println(m.group());
          

            // BACK

            // LEFT

            // RIGHT

            // UP

            // DOWN

            // COLOR


    
    }

    public static void main(String args[]) throws java.io.IOException {

        LexicalAnalysis x = new LexicalAnalysis();
        //String s = x.readInput();
        //System.out.println(s);
    } 



}