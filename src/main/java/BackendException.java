
public class BackendException extends Exception{
    public BackendException(String message, Throwable cause) {
        super(message, cause);
    }


    public BackendException(String message) {
        super(message);
    }

    public BackendException(Throwable cause) {
        super(cause);
    }

    public BackendException(){
        super();
    }

    static BackendException unexpectedEndOfStream(){
        return new BackendException("unexpected input stream");
    }

    static BackendException malformedInputData(){
        return new BackendException("malformed input data");
    }

    static BackendException incompatibleVersion(){
        return new BackendException("incompatible version");
    }

    static BackendException timeout(){
        return new BackendException("timeout");
    }

}
