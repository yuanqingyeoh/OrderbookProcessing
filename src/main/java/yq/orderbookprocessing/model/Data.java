package yq.orderbookprocessing.model;

import java.time.Instant;
import java.util.List;

public class Data {

    private String symbol;
    private List<Order> bids;
    private List<Order> asks;
    private long checksum;
    private Instant timestamp;

    @Override
    public String toString() {
        return "Data{" +
                "symbol='" + symbol + '\'' +
                ", bids=" + bids +
                ", asks=" + asks +
                ", checksum=" + checksum +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<Order> getBids() {
        return bids;
    }

    public void setBids(List<Order> bids) {
        this.bids = bids;
    }

    public List<Order> getAsks() {
        return asks;
    }

    public void setAsks(List<Order> asks) {
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
