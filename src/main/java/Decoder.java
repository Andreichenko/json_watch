import com.google.gson.JsonObject;

abstract class Decoder {
    abstract void decode(JsonObject object) throws BackendException;
    abstract JsonObject getResponseBody();
    abstract String getErrorMessage();
    abstract RequestDecoder someRequest();
}
