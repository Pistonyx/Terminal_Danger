package Commands;

import java.util.ArrayList;

import MainGame.Game;
import Playuh.*;

public class MovePrevCommand implements GameCommand{
    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        // Moves player back a room unless they're at the start
        if (p.currentRoomIndex > 0) {
            p.currentRoomIndex--;
            Game.checkNPCPresence(rooms.get(p.currentRoomIndex));
        } else {
            System.out.println("You are at the entrance.");
        }
    }
}
