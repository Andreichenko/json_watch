import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class BackendServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackendServer.class);

    private final int port;
    private Delegate delegate;

    private boolean started;
    private ExecutorService executor;
    private ServerSocket serverSocket;

    public BackendServer(int port) {
        this.port = port;
    }
}
