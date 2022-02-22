import com.google.gson.JsonObject;

public interface Delegate {
    void onMessage(String api, JsonObject body) throws Exception;
    void onError (Exception e);
}
