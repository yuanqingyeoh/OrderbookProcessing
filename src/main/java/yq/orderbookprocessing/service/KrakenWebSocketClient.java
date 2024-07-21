package yq.orderbookprocessing.service;

import yq.orderbookprocessing.model.OrderBook;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Arrays;

/**
 * Class for websocket connection
 */
public class KrakenWebSocketClient extends WebSocketClient {
    private static final Logger LOG = LoggerFactory.getLogger(KrakenWebSocketClient.class);
    public KrakenWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        LOG.info("Connected to server");
    }

    @Override
    public void onMessage(String message) {
        OrderBookService.processMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LOG.info("Disconnected from server with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        LOG.error("An error occurred: " + ex.getMessage());
        LOG.error(Arrays.toString(ex.getStackTrace()));
    }
}
