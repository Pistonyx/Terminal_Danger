package Playuh;

import java.util.ArrayList;

public class Player {
    String name;
    public ArrayList<String> inventory = new ArrayList<>(3);
    public int currentRoomIndex = 0;
    public Player(String n) {
        this.name = n;
    }
    public boolean hasItem(String i) {
        return inventory.contains(i);
    }
    public void replaceItem(String oldInventory, String newInventory) {
        int idx = inventory.indexOf(oldInventory);
        if (idx != -1) inventory.set(idx, newInventory);
    }
}