/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        double count = 1;
        String champion = "";
        double p = 0.0;

        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            p = 1 / count;
            if (StdRandom.bernoulli(p)) {
                champion = value;
            }
            count++;
        }

        StdOut.println(champion);
    }
}
