package yq.orderbookprocessing.service;

import yq.orderbookprocessing.OrderbookProcessing;
import yq.orderbookprocessing.model.OrderBook;

import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for getting orderbook for each symbol
 */
public class OrderBookFactory {

    private static final Map<String, OrderBook> orderBookMap = new HashMap<>();

    public static OrderBook getOrderBook(String symbol) {
        orderBookMap.putIfAbsent(symbol, new OrderBook(symbol, OrderbookProcessing.ORDER_DEPTH));
        return orderBookMap.get(symbol);
    }

}
