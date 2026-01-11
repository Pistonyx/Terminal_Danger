package Commands;

import java.util.ArrayList;

import MainGame.Game;
import Playuh.*;
public class MoveNextCommand implements GameCommand{
    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        // Advances player unless at end or blocked
        if (p.currentRoomIndex < rooms.size() - 1) {
            // Room index 5 is the room before the Cellar (Room 6)
            if (p.currentRoomIndex == 6 && Game.isCellarLocked) {
                System.out.println("\n[!] The door to the Cellar is LOCKED. Leon needs that drink to help you!");
            } else {
                p.currentRoomIndex++;
            }
        } else {
            System.out.println("You're at the end");
        }
    }
}
