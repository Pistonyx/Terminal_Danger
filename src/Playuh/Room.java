package Playuh;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Room {
    public boolean hasItem = true;
    public Character npc;
    private String id;
    public String name;
    private String description;

    @SerializedName("neighbours")
    private ArrayList<String> neighbors;

    private ArrayList<String> lootTable;

    // Items dropped/stored in this room (used for the Storage room mechanic)
    public ArrayList<String> storedItems = new ArrayList<>();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", neighbors=" + neighbors +
                ", lootTable=" + lootTable +
                '}';
    }
}