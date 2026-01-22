package Commands;

import java.util.ArrayList;
import java.util.Scanner;
import Playuh.Player;
import Playuh.Room;
import Playuh.Item;

public class SearchCommand implements GameCommand{
    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        Room current = rooms.get(p.currentRoomIndex);

        // If we're in the Storage room, allow picking up stored items first
        if (current.getId() != null && current.getId().equals("loc_storage") && !current.storedItems.isEmpty()) {
            System.out.println("\nStored items here: " + current.storedItems);

            if (p.inventory.size() >= 3) {
                System.out.println(">> Your inventory is full!");
                return;
            }

            System.out.print("Pick up which item? Enter index (1-" + current.storedItems.size() + "), or 0 to cancel: ");
            Scanner sc = new Scanner(System.in);

            try {
                int idx = Integer.parseInt(sc.nextLine());
                if (idx == 0) return;

                if (idx >= 1 && idx <= current.storedItems.size()) {
                    String picked = current.storedItems.remove(idx - 1);
                    p.inventory.add(picked);
                    System.out.println(">> Picked up: " + picked);
                } else {
                    System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Invalid choice.");
            }
            return;
        }

        // Normal room search logic (original behavior)
        if (current.hasItem && p.currentRoomIndex < items.size()) {
            String found = items.get(p.currentRoomIndex).name;

            // Prevent getting a full water bottle from searching (must be obtained via the puzzle)
            if (found != null && found.equalsIgnoreCase("Full water bottle")) {
                System.out.println("\nYou search the room, but find nothing.");
                return;
            }

            System.out.print("\nYou found a [" + found + "]. Pick up? (y/n): ");
            Scanner sc = new Scanner(System.in);

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
