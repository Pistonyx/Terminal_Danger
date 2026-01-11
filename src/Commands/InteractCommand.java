package Commands;

import java.util.ArrayList;
import java.util.Scanner;

import MainGame.Game;
import Playuh.*;
public class InteractCommand implements GameCommand {
    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        Room r = rooms.get(p.currentRoomIndex);
        if (r.npc == null) {
            System.out.println("No one here to talk to.");
            return;
        }

        r.npc.showBio();
        Scanner sc = new Scanner(System.in);

        // Water Bottle Puzzle
        if ((r.name.contains("101") || r.name.contains("102")) && p.hasItem("Empty Bottle")) {
            System.out.print("\nYo you lowkey have a water bottle. Do you want to use the sink to fill your bottle with water? (y/n): ");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                p.replaceItem("Empty Bottle", "Full Water Bottle");
                System.out.println("You now have a Full Water Bottle.");
            }
        }
        // Cellar Unlocking using Leon
        else if (r.npc.name.equals("Leon Marcin Klamer") && p.hasItem("Full Water Bottle")) {
            System.out.println("'Leon: Wait a second...is that..Dziekuje! Exactly what I needed.'");
            System.out.println(">> Leon stands up and KICKS the cellar door open for you!");
            Game.isCellarLocked = false;
            p.replaceItem("Full Water Bottle", "Empty Bottle");
        }
    }
}
