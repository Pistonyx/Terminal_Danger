package Commands;

import java.util.ArrayList;
import java.util.Scanner;

import MainGame.Game;
import Playuh.*;
public class InteractCommand implements GameCommand {
    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        Room current = rooms.get(p.currentRoomIndex);
        //checks if theres anyone or anything to interact with in the room
        if (current.npc == null && current.hasItem == false) {
            System.out.println("Nothing to do here.");
            return;
        }

        current.npc.showBio();
        Scanner sc = new Scanner(System.in);

        // Water Bottle Puzzle
        if ((current.name.contains("101") || current.name.contains("102")) && p.hasItem("Empty water bottle")) {
            System.out.print("\nYo you lowkey have an empty water bottle. Do you want to use the sink to fill your bottle with water? (y/n): ");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                p.replaceItem("Empty water bottle", "Full water bottle");
                System.out.println("You now have a Full water bottle.");
                return;
            } else {
                System.out.println("You didn't fill up your bottle");
                return;
            }
        }
        // Cellar Unlocking using Leon
        else if (current.npc.name != null && current.npc.name.contains("Leon") && p.hasItem("Full water bottle")) {
            System.out.println("'Leon: Wait a second...is that..Dziekuje! Exactly what I needed.'");
            System.out.println(">> Leon stands up and KICKS the cellar door open for you!");
            Game.isCellarLocked = false;
            p.replaceItem("Full water bottle", "Empty water bottle");
        } else if (current.name.contains("The garage") && p.hasItem("Broken lever handle")) {
            System.out.println("You can fix your broken lever handle here. Would you like to fix it? (y/n)");
            if (sc.nextLine().equalsIgnoreCase("y")){
                //TODO
                p.replaceItem("Broken lever handle","Lever handle");
                System.out.println("You now have a fixed lever handle");
                return;
            }else {
                System.out.println("You decided to not fix the lever.");
                return;
            }
        }
    }
}