import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class BadResponseDecoder extends Decoder{

    @NotNull
    private String message = "";

    @Override
    void decode(JsonObject object) throws BackendException {
        String message = object.get(Protocol.KEY_MESSAGE).getAsString();
        if (message != null){
            this.message = message;
        }
    }

    @Override
    JsonObject getResponseBody() {
        return null;
    }

    @Override
    String getErrorMessage() {
        return message;
    }

    @Override
    RequestDecoder someRequest() {
        return null;
    }
}
