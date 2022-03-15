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
        Object obj = gson.fromJson(sourceJson, clazz);
        return gson.toJson(obj);
    }
}
