package yq.orderbookprocessing.model;

import java.math.BigDecimal;

public class Order {

    private BigDecimal price;
    private BigDecimal qty;

    @Override
    public String toString() {
        return "Order{" +
                "price=" + price +
                ", qty=" + qty +
                '}';
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
}
