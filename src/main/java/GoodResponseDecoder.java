import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class GoodResponseDecoder extends Decoder{

    @NotNull
    private JsonObject body = new JsonObject();

    @Override
    void decode(JsonObject object) throws BackendException {
        JsonElement jsonElement = object.get(Protocol.KEY_BODY);
        if (jsonElement == null){
            throw new BackendException("null response received from server");
        }
        JsonObject body = jsonElement.getAsJsonObject();
        if (body != null){
            this.body = body;
        }
    }

    @Override
    JsonObject getResponseBody() {
        return body;
    }

    @Override
    String getErrorMessage() {
        return null;
    }

    @Override
    RequestDecoder someRequest() {
        return null;
    }
}
