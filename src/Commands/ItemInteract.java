package Commands;

import Playuh.Item;
import Playuh.Player;
import Playuh.Room;

import java.util.ArrayList;
import java.util.Scanner;

public class ItemInteract implements GameCommand{
    @Override
    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        if (p.inventory.isEmpty()){
            System.out.println("You have no items to interact with.");
            return;
        }
        System.out.println("Inventory: "+p.inventory);
        System.out.println("Enter index to interact with an item. (1-"+p.inventory.size()+"):");
        try {
            Scanner sc = new Scanner(System.in);
            int idx = Integer.parseInt(sc.nextLine());
            System.out.println(idx);
            if (idx > 0 && idx <= 3) {
                String item = p.inventory.get(idx-1);
                Item buh = new Item(item);
                buh.showDescription();
                // make the description get loaded from gamedata.json
                //TODO
            } else {
                System.out.println("Index out of bounds of inventory.");
            }
        } catch (Exception e) {
            System.out.println("Invalid choice.");
        }
    }
}
