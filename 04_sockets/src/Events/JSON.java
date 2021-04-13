package Events;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSON {
    private static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Event.class, new EventSerializer())
            .registerTypeAdapter(Event.class, new EventDeserializer())
            .create();

    public static Gson get() {
        return gson;
    }
}
