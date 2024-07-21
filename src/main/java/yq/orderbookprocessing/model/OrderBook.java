package yq.orderbookprocessing.model;

import yq.orderbookprocessing.service.OrderBookList;
import yq.orderbookprocessing.service.OrderComparator;

import java.time.Instant;

public class OrderBook {

    private String symbol;
    private OrderBookList<Order> bids;
    private OrderBookList<Order> asks;
    private long checksum;
    private Instant timestamp;

    public OrderBook(String symbol, int orderBookDepth) {
        this.symbol = symbol;
        this.bids = new OrderBookList<>(orderBookDepth, new OrderComparator().reversed());
        this.asks = new OrderBookList<>(orderBookDepth, new OrderComparator());
    }

    @Override
    public String toString() {
        return "OrderBook{" +
                "symbol='" + symbol + '\'' +
                ", timestamp=" + timestamp +
                ", bids=" + bids +
                ", asks=" + asks +
                ", checksum=" + checksum +
                '}';
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public OrderBookList<Order> getBids() {
        return bids;
    }

    public void setBids(OrderBookList<Order> bids) {
        this.bids = bids;
    }

    public OrderBookList<Order> getAsks() {
        return asks;
    }

    public void setAsks(OrderBookList<Order> asks) {
        this.asks = asks;
    }

    public long getChecksum() {
        return checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }


}
