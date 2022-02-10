import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Base64;

public enum StatusCode {
    REQUEST(0),
    BADREQUEST(1),
    GOODREQUEST(2)
    ;
    int rawValue;

    StatusCode(int rawValue) {
        this.rawValue = rawValue;
    }

    @Nullable
    static StatusCode fromRawValue(int rawValue){
        for (StatusCode statusCode : StatusCode.values()){
            if(statusCode.rawValue == rawValue){
                return statusCode;
            }
        }
        return null;
    }
    @NotNull
    Decoder makeDecoder() throws BackendException {
        switch (this){
            case REQUEST: return new RequestDecoder();
            case GOODREQUEST: return new GoodResponseDecoder();
            case BADREQUEST: return new BadResponseDecoder();
            default: throw new InternalError("unknown status code");
        }
    }
}
