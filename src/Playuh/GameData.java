package Playuh;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GameData {
    public ArrayList<Item> items;
    public ArrayList<Character> characters;
    public ArrayList<Room> locations;
    public ArrayList<Quest> quests;


    //  Loads game data from a JSON file that is on the classpath
    //  @param resourcePath path to the resource
    //  return a GameData object filled with the loaded data

    public static GameData loadGameDataFromResources(String resourcePath) {
        Gson gson = new Gson();

        String normalized = resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;

        try (InputStream in = GameData.class.getResourceAsStream(normalized)) {
            if (in == null) {
                throw new RuntimeException("Resource not found on classpath: " + normalized);
            }

            try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                return gson.fromJson(reader, GameData.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("Problem while loading json: " + e.getMessage(), e);
        }
    }

    // loads data from gamdata json
    public static GameData loadGamaDataFromResources(String resourcePath) {
        return loadGameDataFromResources(resourcePath);
    }

    // find a location by its ID in the gamedata json file
    public Room findLocation(String id) {
        for (Room l : locations) {
            if (l.getId().equals(id)) {
                return l;
            }
        }
        throw new IllegalArgumentException("There doesn't exist a location with the id: " + id);
    }
}