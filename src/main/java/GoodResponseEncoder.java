import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;

public class GoodResponseEncoder extends Encoder{

    @NotNull
    private final JsonObject body;

    public GoodResponseEncoder(JsonObject body) {
        this.body = body;
    }

    @Override
    byte[] encode() throws BackendException {
        JsonObject jsonObject = new JsonObject();
        if (body != null){
            jsonObject.add(Protocol.KEY_BODY, body);

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
        return StatusCode.GOODREQUEST;
    }
}
