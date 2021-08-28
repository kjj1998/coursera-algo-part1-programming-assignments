/* *****************************************************************************
 *  Name: Koh Jun Jie
 *  Date: 18/7/2021
 *  Description: An implementation of the Randomized Queue using a resizing array
 ****************************************************************************
 * */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;

    private Item[] a;       // array of items
    private int n;          // number of elements in queue
    private int head;       // index of the first element in the randomized queue
    private int tail;       // index of the last element in the randomized queue

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
        head = 0;
        tail = 0;
    }

    private int getHead() {
        return head;
    }

    private int getTail() {
        return tail;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        // implementation for resizing a queue
        Item[] copy = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = head; i < tail; i++) {
            copy[j] = a[i];
            j++;
        }
        a = copy;
        head = 0;
        tail = j;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item to the back of the queue
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot enqueue a null object!");
        if (n == a.length) resize(2 * a.length);
        a[tail++] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Queue is empty! Nothing to dequeue!");
        int randomIndex = StdRandom.uniform(head, tail);
        Item item = a[randomIndex];

        // move the element at the end of the queue to replace the one that is
        // being dequeued
        /*
        for (int i = randomIndex; i < tail - 1; i++) {
            a[i] = a[i + 1];
        }
        */
        a[randomIndex] = a[tail - 1];
        
        a[--tail] = null;
        n--;

        // shrink the size of array if necessary
        if (n > 0 && n == a.length / 4) resize(a.length / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Queue is empty! Nothing to sample!");

        int randomIndex = StdRandom.uniform(head, tail);
        return a[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

        return new QueueIterator();
    }

    // an iterator for the queue
    private class QueueIterator implements Iterator<Item> {
        private int i;
        private Item[] copy = (Item[]) new Object[a.length];

        public QueueIterator() {
            i = head;
            StdRandom.shuffle(a, head, tail);
            for (int k = head; k < tail; k++) {
                copy[k] = a[k];
            }
        }

        public boolean hasNext() {
            return i < tail;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not allowed while iterating!");
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException(
                        "No more items to return from the iterator");

            return copy[i++];
        }

    }

    public static void main(String[] args) {

        int n = 10;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        StdOut.println("Queue Size: " + queue.size());
        StdOut.println("Queue Empty?: " + queue.isEmpty());

        for (int i = 0; i < n; i++)
            queue.enqueue(i);

        StdOut.print("Sample " + n + " times: ");
        for (int i = 0; i < n; i++)
            StdOut.print(queue.sample() + " ");
        StdOut.println();

        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }

        StdOut.print("Dequeue " + n + " times: ");
        for (int i = 0; i < n; i++)
            StdOut.print(queue.dequeue() + " ");
        StdOut.println();

        StdOut.println("Queue Size: " + queue.size());
        StdOut.println("Queue Empty?: " + queue.isEmpty());

        for (int i = 0; i < n; i++)
            queue.enqueue(i);

        StdOut.print("Sample " + n + " times: ");
        for (int i = 0; i < n; i++)
            StdOut.print(queue.sample() + " ");
        StdOut.println();

        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }

        StdOut.print("Dequeue " + n + " times: ");
        for (int i = 0; i < n; i++)
            StdOut.print(queue.dequeue() + " ");

        StdOut.println();
        StdOut.println("Queue Size: " + queue.size());
        StdOut.println("Queue Empty?: " + queue.isEmpty());
    }
}
