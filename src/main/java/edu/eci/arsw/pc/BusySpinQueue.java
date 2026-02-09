package edu.eci.arsw.pc;

import java.util.ArrayDeque;
import java.util.Deque;

/** Intentionally incorrect: uses busy-wait (high CPU). */
public final class BusySpinQueue<T> {
  private final Deque<T> q = new ArrayDeque<>();
  private final int capacity;

  public BusySpinQueue(int capacity) {
    this.capacity = capacity;
  }

  public void put(T item) {
    // spin until there is space
    while (true) {
      if (q.size() < capacity) {
        q.addLast(item);
        return;
      }
      // busy-wait
      Thread.onSpinWait();
    }
  }

  public T take() {
    // spin until there are elements
    while (true) {
      T v = q.pollFirst();
      if (v != null)
        return v;
      Thread.onSpinWait();
    }
  }

  public int size() {
    return q.size();
  }
}
