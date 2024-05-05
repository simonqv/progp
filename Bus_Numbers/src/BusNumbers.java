/*
X1
Simon Larspers Qvist
Beata Johansson
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BusNumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        var n = sc.nextInt();
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            l.add(sc.nextInt());
        }

        var lSorted = l.stream().sorted().collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < lSorted.size(); i++) {
            int k = 1;
            while (i + k < lSorted.size() && lSorted.get(i + k - 1) == lSorted.get(i + k) - 1) {
                k++;

            }
            if (k >= 3) {
                sb.append(lSorted.get(i)).append("-").append(lSorted.get(i + k - 1));
                i = i + k - 1;
            } else {
                sb.append(lSorted.get(i));
            }
            sb.append(" ");
        }

        sb.deleteCharAt(sb.length() - 1);

        System.out.println(sb);
    }
}
