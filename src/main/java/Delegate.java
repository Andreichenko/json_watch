import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public interface Delegate {
    void onMessage(@NotNull String api, @NotNull JsonObject body) throws Exception;
    void onError (Exception e);
}
