import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;

public class BadResponseEncoder extends Encoder{

    @NotNull
    private final String message;

    public BadResponseEncoder(String message) {
        this.message = message;
    }

    @Override
    byte[] encode() throws BackendException {
        JsonObject jsonObject = new JsonObject();
        if (message != null){
            jsonObject.addProperty(Protocol.KEY_MESSAGE, message);
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
        return StatusCode.BADREQUEST;
    }
}
