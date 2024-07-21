package yq.orderbookprocessing.service;

import yq.orderbookprocessing.model.Candle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yq.orderbookprocessing.model.OrderBook;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Class responsible for candle related processing
 */
public class CandleService {

    private static final Logger LOG = LoggerFactory.getLogger(CandleService.class);
    public static void triggerCandle(String symbol) {
        Candle candle = CandleFactory.getCandle(symbol, 1);
        if (candle != null) {
            LOG.info(candle.toString());
            CandleFactory.removeCandle(symbol, 1);
        }
    }

    public static void processCandle(OrderBook orderBook) {
        Candle candle = CandleFactory.getCurrentCandle(orderBook.getSymbol());
        Optional<BigDecimal> midPriceOpt = getMidPrice(orderBook);

        if (midPriceOpt.isPresent()) {
            BigDecimal midPrice = midPriceOpt.get();

            if (candle.getOpen() == null) {
                candle.setOpen(midPrice);
            }

            if (candle.getHigh() == null || candle.getHigh().compareTo(midPrice) < 0) {
                candle.setHigh(midPrice);
            }

            if (candle.getLow() == null || candle.getLow().compareTo(midPrice) > 0) {
                candle.setLow(midPrice);
            }

            candle.setClose(midPrice);

            candle.setTicks(candle.getTicks()+1);
        }


    }
    private static Optional<BigDecimal> getMidPrice(OrderBook orderBook) {
        if (basicCheck(orderBook)) {
            return Optional.of(orderBook.getAsks().peek().getPrice().add(orderBook.getBids().peek().getPrice()).divide(BigDecimal.valueOf(2)));
        }
        return Optional.empty();
    }

    private static boolean basicCheck(OrderBook orderBook) {
        return !orderBook.getAsks().isEmpty() && !orderBook.getBids().isEmpty() && orderBook.getAsks().peek().getPrice().compareTo(orderBook.getBids().peek().getPrice()) > 0;
    }

}
