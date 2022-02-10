import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class RequestEncoder extends Encoder{

    @NotNull
    private final String api;
    @NotNull
    private final JsonObject body;


    public RequestEncoder(@NotNull String api, @NotNull JsonObject body) {
        this.api = api;
        this.body = body;
    }

    @Override
    byte[] encode() throws BackendException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Protocol.KEY_API, api);
        if (body != null){
            jsonObject.add(Protocol.KEY_BODY, body);
        } else {
            jsonObject.add(Protocol.KEY_BODY, new JsonObject());
        }
        String jsonText = jsonObject.toString();

        try {
            return jsonText.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BackendException(e);
        }
    }

    @Override
    StatusCode getStatusCode() {
        return StatusCode.REQUEST;
    }
}
