package yq.orderbookprocessing.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yq.orderbookprocessing.model.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Class responsible for order book processing logic
 */
public class OrderBookService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderBookService.class);
    private static Gson gson = getGson();

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantDeserializer())
                .create();
    }

    public static void processMessage(String message) {
        Payload obj = gson.fromJson(message, Payload.class);
        if (StringUtils.equalsIgnoreCase(obj.getChannel(), "book") &&
                StringUtils.equalsIgnoreCase(obj.getType(), "update") &&
                CollectionUtils.isNotEmpty(obj.getData())) {
            Data data = obj.getData().get(0);
            processMessage(OrderBookFactory.getOrderBook(data.getSymbol()), data);
            CandleService.processCandle(OrderBookFactory.getOrderBook(data.getSymbol()));
        }
    }

    private static void processMessage(OrderBook orderBook, Data data) {
        if (!StringUtils.equalsIgnoreCase(orderBook.getSymbol(), data.getSymbol()))
            return;
        orderBook.setTimestamp(data.getTimestamp());
        orderBook.setChecksum(data.getChecksum());
        addOrders(orderBook.getAsks(), data.getAsks());
        addOrders(orderBook.getBids(), data.getBids());
        LOG.debug(orderBook.toString());
    }

    private static void addOrders(OrderBookList<Order> currentOrders, List<Order> newOrders) {
        if (CollectionUtils.isEmpty(newOrders)) {
            return;
        }
        newOrders.forEach(currentOrders::add);
    }
}
