/* *****************************************************************************
 *  Name: Koh Jun Jie
 *  Date: 19/7/2021
 *  Description: Program that takes an integer k as a command-line argument,
 *  reads a sequence of strings from standard input using StdIn.readString() and
 *  prints exactly k of them, uniformly at random
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < n; i++) {
            StdOut.println(queue.dequeue());
        }

    }
}
