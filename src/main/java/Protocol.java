import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;


import java.io.*;


public class Protocol {

    private static final byte[] FLAG = {'a', 'b'};
    private static final byte[] VERSION = {1,0};
    private static final JsonParser parser = new JsonParser();

    static final String KEY_API = "api";
    static final String KEY_BODY = "body";
    static final String KEY_MESSAGE = "msg";

    static final String GET_CLIENT_ID_API = "backend got client id";
    static final String CLOSE_MESSAGE_API = "backend is closed";

    static void write(OutputStream outputStream, Encoder encoder) throws BackendException {
        try {
            outputStream.write(FLAG);
            outputStream.write(VERSION);
            outputStream.write(encoder.getStatusCode().rawValue);
            outputStream.write(0);
            outputStream.write(0);
            byte[] data = encoder.encode();

            int length = data.length;
            int b0 = length & 0xff;
            int b1 = (length & 0xff00) >> 8;
            int b2 = (length & 0xff0000) >> 16;
            int b3 = (length & 0xff000000) >> 24;

            outputStream.write(b0);
            outputStream.write(b1);
            outputStream.write(b2);
            outputStream.write(b3);

            outputStream.write(data);

            outputStream.flush();

        } catch (IOException e) {
            throw new BackendException(e);
        }

    }

    static Decoder read(InputStream inputStream) throws BackendException, IOException {
        try {
            int b;
            b = inputStream.read();
            if (b == -1) throw BackendException.unexpectedEndOfStream();
            if (b != FLAG[0]) throw BackendException.malformedInputData();
            b = inputStream.read();
            if (b == -1) throw BackendException.unexpectedEndOfStream();
            if (b != FLAG[1]) throw BackendException.malformedInputData();

            b = inputStream.read();
            if (b == -1) throw BackendException.unexpectedEndOfStream();
            if (b != VERSION[0]) throw BackendException.incompatibleVersion();
            b = inputStream.read();
            if (b == -1) throw BackendException.unexpectedEndOfStream();
            if (b != VERSION[1]) throw BackendException.incompatibleVersion();

            b = inputStream.read();
            if (b == -1) throw BackendException.unexpectedEndOfStream();
            StatusCode statusCode = StatusCode.fromRawValue(b);
            if (statusCode == null) {
                throw BackendException.malformedInputData();
            }

            Decoder decoder = statusCode.makeDecoder();
            b = inputStream.read();
            if (b == -1) throw BackendException.unexpectedEndOfStream();
            b = inputStream.read();
            if (b == -1) throw BackendException.unexpectedEndOfStream();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int len;
            b = inputStream.read();
            if (b == -1) throw BackendException.unexpectedEndOfStream();
            len = b;

            b = inputStream.read();
            if (b == -1) throw BackendException.unexpectedEndOfStream();
            len |= (b << 8);
            b = inputStream.read();
            if (b == -1) throw BackendException.unexpectedEndOfStream();
            len |= (b << 16);
            b = inputStream.read();
            if (b == -1) throw BackendException.unexpectedEndOfStream();
            len |= (b << 24);

            if (len <= 0){
                throw BackendException.unexpectedEndOfStream();
            }

            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            int numberRead;
            byte[] data = new byte[len];  //reads only len characters as of protocol definition.
            while ((numberRead = inputStream.read(data, 0, data.length)) != -1){
                buff.write(data, 0, numberRead);
                if (buff.size() >= len){
                    break;

                }
            }
            if (buff.size() != len) {
                throw BackendException.malformedInputData();
            }

            buff.flush();
            data = buff.toByteArray();
            String jsonText = new String(data, "UTF8");
            JsonObject object = parser.parse(jsonText).getAsJsonObject();

            decoder.decode(object);
            return decoder;

        } catch (IOException e){
            throw new BackendException(e);
        } catch (JsonParseException e){
            throw BackendException.malformedInputData();
        }
    }


}
