package yq.orderbookprocessing.service;

import java.math.BigDecimal;
import java.util.*;

import yq.orderbookprocessing.model.Order;

/**
 * Custom class list class for maintaining bids and asks price
 *  Features:
 *  1. When adding will put the element at position according to comparator
 *  2. When qty = 0 will remove the element
 *  3. When price is equal to existing record will replace the records
 *  4. Maintain a maxSize, any element bigger than the size is discarded
 */
public class OrderBookList<T extends Order>{

    private int maxSize;
    private Comparator<T> comparator;
    private List<T> list;

    public OrderBookList(int maxSize, Comparator<T> comparator) {
        this.maxSize = maxSize;
        this.comparator = comparator;
        this.list = new ArrayList<>(maxSize+1); // +1 to avoid expanding the array size

    }

    public T peek() {
        if (this.list.isEmpty()) {
            return null;
        }
        return this.list.get(0);
    }

    public void add(T element) {
        boolean isAdded = false;
        for (int i=0; i< this.list.size(); i++) {
            if (this.comparator.compare(list.get(i), element) == 0) {
                if (BigDecimal.ZERO.compareTo(element.getQty()) == 0) {
                    // Qty = 0 remove
                    list.remove(i);
                } else {
                    // Same value replace
                    list.set(i, element);
                }
                isAdded = true;
                break;
            } else if (this.comparator.compare(element, list.get(i)) < 0) {
                // Less than remove
                if (BigDecimal.ZERO.compareTo(element.getQty()) != 0) {
                    list.add(i, element);
                    isAdded = true;
                    break;
                }
            }
        }

        if(!isAdded && BigDecimal.ZERO.compareTo(element.getQty()) != 0) {
            this.list.add(element);
        }

        // Maintain the size of the array
        while (list.size() > maxSize) {
            list.remove(list.size()-1);
        }
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
