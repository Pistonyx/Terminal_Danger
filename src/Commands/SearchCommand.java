package Commands;

import java.util.ArrayList;
import java.util.Scanner;
import Playuh.Player;
import Playuh.Room;
import Playuh.Item;

public class SearchCommand implements GameCommand{
    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        Room current = rooms.get(p.currentRoomIndex);
        // Searches current room; adds item if present
        if (current.hasItem && p.currentRoomIndex < items.size()) {
            String found = items.get(p.currentRoomIndex).name;
            System.out.print("\nYou found a [" + found + "]. Pick up? (y/n): ");
            Scanner sc = new Scanner(System.in);
            // Adds found item to inventory if space available
            if (sc.nextLine().equalsIgnoreCase("y")) {
                if (p.inventory.size() < 3) {
                    p.inventory.add(found);
                    current.hasItem = false;
                    System.out.println(">> Added to inventory.");
                } else {
                    System.out.println(">> Your inventory is full!");
                }
            }
        } else {
            System.out.println("\nYou search the room, but find nothing.");
        }
    }
}
