/* *****************************************************************************
 *  Name: Koh Jun Jie
 *  Date: 7/16/2021
 *  Description: An implementation of a Deque data structure using singly linked list
 *  Citation: https://algs4.cs.princeton.edu/13stacks/LinkedStack.java.html
 *            https://algs4.cs.princeton.edu/13stacks/ResizingArrayStack.java.html
 ****************************************************************************
 * */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int n;      // size of the deque
    private Node first;  // first node of the deque
    private Node last;  // last node of the deque

    // helper doubly linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        Node(Item item2) {
            item = item2;
            next = null;
            prev = null;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
        assert check();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null)
            throw new IllegalArgumentException("null argument detected for addFirst");

        if (isEmpty()) { // when linked list is empty
            first = new Node(item);
            last = first;
            n++;
        }
        else { // normal scenario
            Node node = new Node(item);
            node.next = first;
            first.prev = node;
            first = node;
            n++;
        }
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null)
            throw new IllegalArgumentException("null argument detected for addLast");

        if (isEmpty()) { // when linked list is empty
            first = new Node(item);
            last = first;
            n++;
        }
        else { // normal scenario
            Node node = new Node(item);
            last.next = node;
            node.prev = last;
            last = node;
            n++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) // when linked list is empty
            throw new NoSuchElementException("deque is empty when removing first!");

        if (n == 1) { // only one node in the linked list
            Item item = first.item;
            first = null;
            last = null;
            n--;
            return item;
        }
        else { // normal scenario
            Item item = first.item;
            first = first.next;
            first.prev = null;
            n--;
            return item;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) // when linked list is empty
            throw new NoSuchElementException("deque is empty when removing last!");

        if (n == 1) { // only one node in the linked list
            Item item = first.item;
            first = null;
            last = null;
            n--;
            return item;
        }
        else {
            Item item = last.item;
            last = last.prev;
            last.next = null;
            n--;
            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // check internal invariants
    private boolean check() {

        // check a few properties of instance variables 'head' and 'tail'
        if (n < 0) {
            return false;
        }
        if (n == 0) {
            if (first != null && last != null) return false;
        }
        else if (n == 1) {
            if (first == null || last == null) return false;
            if (first.next != null || last.next != null) return false;
        }
        else {
            if (first == null || last == null) return false;
            if (first.next == null || last.next != null) return false;
        }

        // check internal consistency
        int numberOfNodes = 0;
        for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != n) return false;

        return true;
    }


    public static void main(String[] args) {
        int n = 5;

        Deque<Integer> deque = new Deque<Integer>();
        StdOut.println("Is the deque empty?: " + deque.isEmpty());
        StdOut.println("Size of the deque: " + deque.size());

        StdOut.print("Add first: ");
        for (int i = 0; i < n; i++) {
            deque.addFirst(i);
            StdOut.print(i + " ");
        }
        StdOut.println();

        StdOut.print("Remove last: ");
        for (int i = 0; i < n; i++) {
            int key = deque.removeLast();
            StdOut.print(key + " ");
        }
        StdOut.println();

        StdOut.print("Add last: ");
        for (int i = 0; i < n; i++) {
            deque.addLast(i);
        }
        for (int i : deque) {
            StdOut.print(i + " ");
        }
        StdOut.println();


        StdOut.print("Remove first: ");
        for (int i = 0; i < n; i++) {
            int key = deque.removeFirst();
            StdOut.print(key + " ");
        }
        StdOut.println();

        StdOut.println("Is the deque empty?: " + deque.isEmpty());
        StdOut.println("Size of the deque: " + deque.size());
    }
}
