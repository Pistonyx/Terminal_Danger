package Commands;

import Playuh.Item;
import Playuh.Player;
import Playuh.Room;

import java.util.ArrayList;

public class QuestCommand implements GameCommand{
    @Override
    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        Room current = rooms.get(p.currentRoomIndex);
        if (p.inventory.isEmpty()){
            System.out.println("Pick up an item to get a quest.");
            return;
        }
        //TODO
    }
}
