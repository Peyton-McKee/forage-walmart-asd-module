package powerOfTwoMaxHeap;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Heap<T extends Comparable<T>> {
   private ArrayList<T> items;
   private int numberOfChildren;

   public Heap(int numberOfChildren) {
      this.numberOfChildren = numberOfChildren;
      this.items = new ArrayList<T>();
   }

   private void siftUp() {
      int k = items.size() - 1;
      while (k > 0) {
         int p = (int) ((k - 1) / Math.pow(2, numberOfChildren));
         T item = items.get(k);
         T parent = items.get(p);
         if (item.compareTo(parent) > 0) {
            // swap
            items.set(k, parent);
            items.set(p, item);
            // move up one level
            k = p;
         } else {
            break;
         }
      }
   }

   public void insert(T item) {
      items.add(item);
      siftUp();
   }

   private void siftDown() {
      int p = 0;
      int c = (int) (Math.pow(2, numberOfChildren) * p + 1);
      while (c < items.size()) {
         int max = c;
         int[] rangeOfChildren = new int[(int) Math.pow(2, numberOfChildren)];
         for (int i = 0; i < rangeOfChildren.length; i++) {
            rangeOfChildren[i] = c + i;
            if (rangeOfChildren[i] < items.size()) {
               if (items.get(rangeOfChildren[i]).compareTo(items.get(max)) > 0) {
                  max = rangeOfChildren[i];
               }
            }
         }
         if (items.get(p).compareTo(items.get(max)) < 0) {
            // switch

            T temp = items.get(p);
            items.set(p, items.get(max));
            items.set(max, temp);
            p = max;
            c = (int) (Math.pow(2, numberOfChildren) * p + 1);
         } else {
            break;
         }
      }
   }

   public T popmax() throws NoSuchElementException {
      if (items.size() == 0) {
         throw new NoSuchElementException();
      }
      if (items.size() == 1) {
         return items.remove(0);
      }
      T hold = items.get(0);
      items.set(0, items.remove(items.size() - 1));
      siftDown();
      return hold;
   }

   public int size() {
      return items.size();
   }

   public String toString() {
      return items.toString();
   }

   public boolean isEmpty() {
      return items.isEmpty();
   }
}