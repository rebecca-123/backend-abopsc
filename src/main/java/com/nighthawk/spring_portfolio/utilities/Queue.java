package com.nighthawk.spring_portfolio.utilities;
import java.util.Iterator;

public class Queue<T> implements Iterable<T> {
    LinkedList<T> head = null, tail = null;

    /**
     *  Add a new object at the end of the Queue,
     *
     * @param  data,  is the data to be inserted in the Queue.
     */
    public void add(T data) {
        // add new object to end of Queue
        LinkedList<T> tail = new LinkedList<>(data, null);


        if (this.head == null)  // initial condition
            this.head = this.tail = tail;
        else {  // nodes in queue
            this.tail.setNextNode(tail); // current tail points to new tail
            this.tail = tail;  // update tail
        }
    }

    /**
     *  Returns the data of head.
     *
     * @return  data, the dequeued data
     */
    public T delete() {
        T data = this.peek();
        if (this.tail != null) { // initial condition
            this.head = this.head.getNext(); // current tail points to new tail
            if (this.head != null) {
                this.head.setPrevNode(tail);
            }
        }
        return data;
    }

    /**
     *  Returns the data of head.
     *
     * @return  this.head.getData(), the head data in Queue.
     */
    public T peek() {
        return this.head.getData();
    }

    /**
     *  Returns the head object.
     *
     * @return  this.head, the head object in Queue.
     */
    public LinkedList<T> getHead() {
        return this.head;
    }

    /**
     *  Returns the tail object.
     *
     * @return  this.tail, the last object in Queue
     */
    public LinkedList<T> getTail() {
        return this.tail;
    }
    
    public boolean isEmpty() {
        if ((this.getHead() == null) && (this.getTail() == null)) {
            return true;
        }
        else {
            return false;
        }
    }

    
    public int getSize() {
        int size = 0;
        for (T data : this)
            size += 1;
        return size;
    }

    /**
     *  Returns the iterator object.
     *
     * @return  this, instance of object
     */
    
    public LinkedList<T> next() {
        LinkedList<T> next = this.head.getNext();
        this.head = next;
        return next;
    }
    
    public static <T extends Comparable<T>> int compare(T x, T y) {
        return x.compareTo(y);
    }

    
    public Iterator<T> iterator() {
        return new QueueIterator<>(this.head);
    }
}