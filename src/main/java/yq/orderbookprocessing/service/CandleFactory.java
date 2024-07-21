package yq.orderbookprocessing.service;

import yq.orderbookprocessing.model.Candle;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for getting/removing Candle for specific symbol at specific timestamp
 */
public class CandleFactory {

    private static final Map<String, Candle> candleMap = new HashMap<>();

    public static Candle getCurrentCandle(String symbol) {
        Instant candleTimestamp = Instant.now().truncatedTo(ChronoUnit.MINUTES);

        candleMap.putIfAbsent(symbol+candleTimestamp.toString(), new Candle(symbol, candleTimestamp));
        return candleMap.get(symbol+ candleTimestamp);
    }

    public static Candle getCandle(String symbol, int minute) {
        Instant candleTimestamp = Instant.now().truncatedTo(ChronoUnit.MINUTES).minus(minute, ChronoUnit.MINUTES);
        return candleMap.get(symbol+ candleTimestamp);
    }

    public static void removeCandle(String symbol, int minute) {
        Instant candleTimestamp = Instant.now().truncatedTo(ChronoUnit.MINUTES).minus(minute, ChronoUnit.MINUTES);
        candleMap.remove(symbol+ candleTimestamp);
    }
}
