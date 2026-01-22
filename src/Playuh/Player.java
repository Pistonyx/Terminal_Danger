package Playuh;

import java.util.ArrayList;

public class Player {
    String name;
    // Makes an inventory for the player
    public ArrayList<String> inventory = new ArrayList<>(3);
    public int currentRoomIndex = 0;
    public Player(String n) {
        this.name = n;
    }
    public boolean hasItem(String i) {
        return inventory.contains(i);
    }
    // used for replacing the empty water bottle with the full one
    public void replaceItem(String oldInventory, String newInventory) {
        int idx = inventory.indexOf(oldInventory);
        if (idx != -1) inventory.set(idx, newInventory);
    }
}