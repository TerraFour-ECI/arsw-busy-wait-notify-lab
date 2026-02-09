package edu.eci.arsw.pc;

import java.util.ArrayDeque;
import java.util.Deque;

/** Correct implementation with monitors: synchronized + wait/notifyAll. */
public final class BoundedBuffer<T> {
  private final Deque<T> q = new ArrayDeque<>();
  private final int capacity;

  public BoundedBuffer(int capacity) {
    if (capacity <= 0)
      throw new IllegalArgumentException("capacity must be > 0");
    this.capacity = capacity;
  }

  public void put(T item) throws InterruptedException {
    synchronized (this) {
      while (q.size() == capacity) {
        this.wait(); // wait until there is space
      }
      q.addLast(item);
      this.notifyAll(); // wake up consumers
    }
  }

  public T take() throws InterruptedException {
    synchronized (this) {
      while (q.isEmpty()) {
        this.wait(); // wait until there are elements
      }
      T v = q.removeFirst();
      this.notifyAll(); // wake up producers
      return v;
    }
  }

  public synchronized int size() {
    return q.size();
  }

  public int capacity() {
    return capacity;
  }
}
