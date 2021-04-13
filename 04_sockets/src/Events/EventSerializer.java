package Events;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

class EventSerializer implements JsonSerializer<Event> {
    @Override
    public JsonElement serialize(Event event, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("type", String.valueOf(event.type));
        if (event.message != null)
            result.addProperty("message", event.message);
        if (event.clock != null) {
            JsonObject clock = new JsonObject();
            clock.addProperty("h", event.clock.getHours());
            clock.addProperty("m", event.clock.getMinutes());
            try {
                clock.addProperty("s", event.clock.getSeconds());
            } catch (NoSuchMethodException e) {
            }
            result.add("clock", clock);
        }
        if (event.alarm != null) {
            JsonObject alarm = new JsonObject();
            alarm.addProperty("h", event.alarm.getHours());
            alarm.addProperty("m", event.alarm.getMinutes());
            try {
                alarm.addProperty("s", event.alarm.getSeconds());
            } catch (NoSuchMethodException e) {
            }
            result.add("alarm", alarm);
        }
        result.addProperty("running", event.running);
        return result;
    }
}
