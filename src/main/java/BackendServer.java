import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class BackendServer<ConnectionSendMessages> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackendServer.class);

    private final int port;
    private Delegate delegate;

    private boolean started;
    private ExecutorService executor;
    private ServerSocket serverSocket;

    private AtomicInteger clientIDSequence = new AtomicInteger(1);

    private ConcurrentHashMap<Integer, ConcurrentLinkedQueue<JsonObject>> mapOfMessagesToSendQueue = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, ConnectionSendMessages> mapOfClientSenders = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Semaphore> mapOfClientSemaphores = new ConcurrentHashMap<>();

    public BackendServer(int port) {
        this.port = port;
    }
}
