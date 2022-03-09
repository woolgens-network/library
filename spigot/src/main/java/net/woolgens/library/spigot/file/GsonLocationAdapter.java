package net.woolgens.library.spigot.file;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.woolgens.library.spigot.location.LocationParser;
import org.bukkit.Location;

import java.io.IOException;

public class GsonLocationAdapter extends TypeAdapter<Location> {

    public void write(JsonWriter jsonWriter, Location location) throws IOException {
        jsonWriter.value(LocationParser.locationToString(location));
    }

    public Location read(JsonReader jsonReader) throws IOException {
        return LocationParser.stringToLocation(jsonReader.nextString());
    }
}
