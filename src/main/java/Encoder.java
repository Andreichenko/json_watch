abstract class Encoder {
    abstract byte[] encode() throws BackendException;
    abstract StatusCode getStatusCode();
}
