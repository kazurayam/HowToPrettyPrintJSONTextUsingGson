package my;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class JsonUtil {

    static String prettyPrint(String sourceJson) {
        return prettyPrint(sourceJson, Map.class);
    }

    static <T> String prettyPrint(String sourceJson, Class<T> clazz) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // parse JSON text to an Map
        Object obj = gson.fromJson(sourceJson, clazz);
        // serialize the object back to a JSON text in pretty-print format
        return gson.toJson(obj);
    }
}
