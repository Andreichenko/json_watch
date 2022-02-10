import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Backend {

 private static final JsonParser parser = new JsonParser();
 private final String host;
 private final int port;
 private final int timeout;

 private Socket socket;
 private Object inputStreamLock = new Object();

 private InputStream inputStream;
 private OutputStream outputStream;

 private Object outputStreamLock = new Object();
 private boolean stopRequested = false;
 private MessageHandler messageHandler;

    public Backend(String host, int port, int timeout, MessageHandler messageHandler) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.messageHandler = messageHandler;
        this.socket = new Socket();

        try {
            socket.connect(new InetSocketAddress(host, port), timeout);

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("Socket can't be connected", e);
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }


}
