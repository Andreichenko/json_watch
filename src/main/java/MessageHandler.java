import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public interface MessageHandler {
    void onMessage(@NotNull JsonObject message);
    void onError(BackendException exception);
}
