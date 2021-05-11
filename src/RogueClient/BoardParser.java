package RogueClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class BoardParser {
    byte[] mapArray;

    public BoardParser(InputStream inputStream, int w, int h) throws IOException {
        this.mapArray = inputStream.readNBytes(h * w);
    }

    public void printer(int w, int h) {
        StringBuilder sb = new StringBuilder();
        int k = 0;
        //sb.append(new String(mapArray, StandardCharsets.US_ASCII));
        String s = new String(mapArray, StandardCharsets.US_ASCII);



        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                sb.append(s.charAt(k));
                sb.append(" ");
                k++;
            }
            sb.append("\n");
        }

        // Gör så att den byter ut printen hela tiden.
        System.out.println(sb.toString());
    }
}
