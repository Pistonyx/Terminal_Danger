package Commands;
import java.util.ArrayList;
import java.util.Scanner;
import Playuh.Player;
import Playuh.Room;
import Playuh.Item;

public class DropCommand implements GameCommand{

      // Drops an item from inventory based on user input

    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        Room current = rooms.get(p.currentRoomIndex);

        // Only allows dropping items while in the Storage room
        if (current.getId() == null || !current.getId().equals("loc_storage")) {
            System.out.println("You can only drop items in the Storage room.");
            return;
        }

        if (p.inventory.isEmpty()) {
            System.out.println("Nothing to drop.");
            return;
        }

        System.out.println("Inventory: " + p.inventory);
        System.out.print("Enter index to store (1-" + (p.inventory.size()) + "): ");

        try {
            Scanner sc = new Scanner(System.in);
            int idx = Integer.parseInt(sc.nextLine());

            if (idx > 0 && idx <= p.inventory.size()) {
                String stored = p.inventory.remove(idx - 1);
                current.storedItems.add(stored);
                System.out.println("Stored in Storage room: " + stored);
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Invalid choice.");
        }
    }
}
