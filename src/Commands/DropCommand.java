package Commands;
import java.util.ArrayList;
import java.util.Scanner;
import Playuh.Player;
import Playuh.Room;
import Playuh.Item;

public class DropCommand implements GameCommand{
    /**
     * Drops item from inventory based on user input
     */
    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        if (p.inventory.isEmpty()) {
            System.out.println("Nothing to drop.");
            return;
        }
        System.out.println("Inventory: " + p.inventory);
        System.out.print("Enter index to drop (1-" + (p.inventory.size()) + "): ");
        // Prompts for item index; drops item or reports error
        try {
            Scanner sc = new Scanner(System.in);
            int idx = Integer.parseInt(sc.nextLine());
            // Drops item at index or reports invalid choice
            if (idx > 0 && idx <= p.inventory.size()) {
                System.out.println("Dropped: " + p.inventory.remove(idx-1));
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Invalid choice.");
        }
    }
}
