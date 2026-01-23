package Commands;

import Playuh.Item;
import Playuh.Player;
import Playuh.Room;

import java.util.ArrayList;
import java.util.Scanner;

public class ItemInteract implements GameCommand{
    @Override
    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        Room current = rooms.get(p.currentRoomIndex);
        if (p.inventory.isEmpty()){
            System.out.println("You have no items to interact with.");
            return;
        }
        System.out.println("Inventory: "+p.inventory);
        System.out.println("Enter index to interact with an item. (1-"+p.inventory.size()+"):");
        try {
            Scanner sc = new Scanner(System.in);
            int idx = Integer.parseInt(sc.nextLine());
            if (idx > 0 && idx <= p.inventory.size()) {
            } else {
                System.out.println("Invalid choice.");
            }

        } catch (Exception e) {
            System.out.println("Invalid choice.");
        }
    }
}
