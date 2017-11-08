package be.hogent.jensbuysse.metartaff.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import be.hogent.jensbuysse.metartaff.models.Metar;

/**
 * Created by eothein on 08.11.17.
 */

public class MetarDeserializer implements JsonDeserializer<Metar>{
    @Override
    public Metar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {


        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonElement jsonRaw = jsonObject.get("Raw-Report");
        final String raw = jsonRaw.getAsString();

        final Metar metar = new Metar();
        metar.setRawMetar(raw);

        return metar;

    }
}
