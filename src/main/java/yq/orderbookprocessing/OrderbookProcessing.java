package yq.orderbookprocessing;

import yq.orderbookprocessing.service.CandleService;
import yq.orderbookprocessing.service.Constant;
import yq.orderbookprocessing.service.KrakenWebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OrderbookProcessing {
    private static final Logger LOG = LoggerFactory.getLogger(OrderbookProcessing.class);

    public static final int ORDER_DEPTH = 10;
    public static final List<String> SYMBOLS = Arrays.asList("BTC/USD", "ETH/USD");
    public static final String symbolList = String.join(",", SYMBOLS.stream().map(symbol -> "\"" + symbol +"\"").collect(Collectors.toList()));
    public static String subscribeMsg = "{\"method\":\"subscribe\",\"params\":{\"channel\":\"book\",\"symbol\":[" + symbolList + "] ,\"depth\":" + ORDER_DEPTH +",\"snapshot\":false}}";

    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        Runnable triggerCandleTask = () -> {
            SYMBOLS.forEach(CandleService::triggerCandle);
        };

        Runnable subscribeTask = () -> {

            try {
                KrakenWebSocketClient client  = new KrakenWebSocketClient(new URI(Constant.URL));
                client.connectBlocking(); // Connect to the server
                client.send(subscribeMsg);
            } catch (URISyntaxException | InterruptedException e) {
                e.printStackTrace();
            }
        };

        long intialDelay = calculateNanoSecondsToNextMinute();
        scheduler.scheduleAtFixedRate(triggerCandleTask, intialDelay + Duration.ofSeconds(60).toNanos(),  Duration.ofSeconds(60).toNanos(), TimeUnit.NANOSECONDS);
        scheduler.schedule(subscribeTask, intialDelay - Duration.ofSeconds(5).toNanos(), TimeUnit.NANOSECONDS);
        LOG.info("Scheduler started");

    }

    private static long calculateNanoSecondsToNextMinute() {
        Instant now = Instant.now();
        Instant nextMinute = now.truncatedTo(ChronoUnit.MINUTES).plusSeconds(60);
        return Duration.between(now, nextMinute).toNanos();
    }
}