package Commands;

import java.util.ArrayList;
import java.util.Scanner;
import Playuh.Player;
import Playuh.Room;
import Playuh.Item;

/**
 * Handles the logic for searching a room to find or retrieve items.
 * This command branches into two behaviors: retrieving items previously stored
 * in a storage location, or discovering new items hidden within the environment.
 * * @author Trong Hieu Tran
 */
public class SearchCommand implements GameCommand {

    /**
     * Executes the search logic for the player's current location.
     * Checks for a specific "storage" room ID to allow item retrieval, or performs
     * a standard room search to find environmental items. Enforces a 3-item
     * inventory limit.
     *
     * @param p      The player performing the search.
     * @param rooms  The list of rooms defining the game world.
     * @param items  The global list of hidden items mapped to room indices.
     * @return       An empty String, as the outcome is printed to the console.
     */
    @Override
    public String execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        Room current = rooms.get(p.currentRoomIndex);

        // Branch 1: Storage Room Logic
        // If the room is designated as storage, allow picking up specific stored items
        if (current.getId() != null && current.getId().equals("loc_storage") && !current.storedItems.isEmpty()) {
            System.out.println("\nStored items here: " + current.storedItems);

            if (p.inventory.size() >= 3) {
                System.out.println(">> Your inventory is full!");
                return "";
            }

            System.out.print("Pick up which item? Enter index (1-" + current.storedItems.size() + "), or 0 to cancel: ");
            Scanner sc = new Scanner(System.in);

            try {
                int idx = Integer.parseInt(sc.nextLine());
                if (idx == 0) return "";

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
            return "";
        }

        // Branch 2: Normal Room Search Logic
        // Searches for a specific item mapped to the current room index
        if (current.hasItem && p.currentRoomIndex < items.size()) {
            String found = items.get(p.currentRoomIndex).name;

            // Integrity Check: Prevent bypassing the water puzzle via searching
            if (found != null && found.equalsIgnoreCase("Full water bottle")) {
                System.out.println("\nYou search the room, but find nothing.");
                return "";
            }

            System.out.print("\nYou found a [" + found + "]. Pick up? (y/n): ");
            Scanner sc = new Scanner(System.in);

            if (sc.nextLine().equalsIgnoreCase("y")) {
                // Inventory capacity validation
                if (p.inventory.size() < 3) {
                    p.inventory.add(found);
                    current.hasItem = false; // Item is now removed from the world
                    System.out.println(">> Added to inventory.");
                } else {
                    System.out.println(">> Your inventory is full!");
                }
            } else {
                System.out.println("You decide not to pick it up.");
            }
        } else {
            System.out.println("\nYou search the room, but find nothing.");
        }
        return "";
    }
}