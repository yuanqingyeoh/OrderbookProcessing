package yq.orderbookprocessing.service;

import yq.orderbookprocessing.model.Order;

import java.util.Comparator;

/**
 * Comparator class for Order
 */
public class OrderComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        return o1.getPrice().compareTo(o2.getPrice());
    }
}
