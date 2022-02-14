import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class RequestDecoder extends Decoder{
    @NotNull
    String api = "";
    @NotNull
    JsonObject body = new JsonObject();

    @Override
    void decode(JsonObject object) throws BackendException {
    }

    @Override
    JsonObject getResponseBody() {
        return null;
    }

    @Override
    String getErrorMessage() {
        return null;
    }

    @Override
    RequestDecoder someRequest() {
        return this;
    }
}
