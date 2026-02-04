package Commands;

import Playuh.GameData;
import Playuh.Item;
import Playuh.Player;
import Playuh.Room;

import java.util.ArrayList;
import java.util.Scanner;

public class ItemInteract implements GameCommand{
    @Override
    public String execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        if (p.inventory.isEmpty()){
            System.out.println("You have no items to interact with.");
            return "";
        }

        System.out.println("Inventory: " + p.inventory);
        System.out.println("Enter index to interact with an item. (1-" + p.inventory.size() + "):");

        try {
            Scanner sc = new Scanner(System.in);
            int idx = Integer.parseInt(sc.nextLine());

            if (idx >= 1 && idx <= p.inventory.size()) {
                String itemName = p.inventory.get(idx - 1);

                // Use GameData to load items/descriptions from gamedata.json
                GameData data = GameData.loadGamaDataFromResources("/gamedata.json");
                Item fromJson = findItemByName(data, itemName);

                Item selected = new Item(itemName);
                if (fromJson != null && fromJson.description != null && !fromJson.description.isBlank()) {
                    selected.description = fromJson.description;
                }

                selected.showDescription();
            } else {
                System.out.println("Index out of bounds of inventory.");
            }
        } catch (Exception e) {
            System.out.println("Invalid choice.");
        }
        return "";
    }

    private Item findItemByName(GameData data, String itemName) {
        if (data == null || data.items == null) return null;

        for (Item it : data.items) {
            if (it == null || it.name == null) continue;
            if (it.name.equalsIgnoreCase(itemName)) {
                return it;
            }
        }
        return null;
    }
}
