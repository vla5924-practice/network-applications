package Events;

import Alarm.BAlarm;
import Clock.BClock;
import Timeholders.TimeholderType;
import com.google.gson.*;

import java.lang.reflect.Type;

class EventDeserializer implements JsonDeserializer<Event> {
    @Override
    public Event deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        EventType eventType = EventType.valueOf(object.get("type").getAsString());
        Event event = new Event(eventType);
        if (object.has("message"))
            event.message = object.get("message").getAsString();
        if (object.has("clock")) {
            JsonObject clock = object.getAsJsonObject("clock");
            TimeholderType clockType = clock.has("s") ? TimeholderType.HMS : TimeholderType.HM;
            event.clock = BClock.build(clockType);
            event.clock.setHours(clock.get("h").getAsInt());
            event.clock.setMinutes(clock.get("m").getAsInt());
            if (clockType == TimeholderType.HMS) {
                try {
                    event.clock.setSeconds(clock.get("s").getAsInt());
                } catch (NoSuchMethodException e) {
                }
            }
        }
        if (object.has("alarm")) {
            JsonObject alarm = object.getAsJsonObject("alarm");
            TimeholderType alarmType = alarm.has("s") ? TimeholderType.HMS : TimeholderType.HM;
            event.alarm = BAlarm.build(alarmType);
            event.alarm.id = alarm.get("id").getAsInt();
            event.alarm.setHours(alarm.get("h").getAsInt());
            event.alarm.setMinutes(alarm.get("m").getAsInt());
            if (alarmType == TimeholderType.HMS) {
                try {
                    event.alarm.setSeconds(alarm.get("s").getAsInt());
                } catch (NoSuchMethodException e) {
                }
            }
        }
        if (object.has("running")) {
            event.running = object.get("running").getAsBoolean();
        }
        return event;
    }
}
