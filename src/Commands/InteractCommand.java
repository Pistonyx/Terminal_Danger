package Commands;
import Commands.GameCommand;
import MainGame.Game;
import Playuh.Item;
import Playuh.Player;
import Playuh.Room;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class InteractCommand implements GameCommand {

    private int letters = 5;
    private long leverTime = 3000;

    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        Room current = rooms.get(p.currentRoomIndex);
        Scanner sc = new Scanner(System.in);

        // Lever puzzle
        // uses the runLeverMinigame and then checks if it returned true or false. Based on that it outputs 2 texts.
        if (current.name != null && current.name.contains("The garage") && p.hasItem("Broken lever handle")) {
            System.out.print("You can fix your broken lever handle here. You will have 3 seconds to write 5 randomly generated letters. Start fixing it? (y/n): ");
            if (!sc.nextLine().equalsIgnoreCase("y")) {
                return;
            }

            boolean won = runLeverMinigame(sc);
            if (won) {
                p.replaceItem("Broken lever handle", "Lever handle");
                System.out.println("Success! You fixed the lever handle.");
            } else {
                System.out.println("You failed to fix it in time. The lever handle is still broken.");
            }
            return;
        }

        // If there's no NPC, interacting does nothing
        if (current.npc == null) {
            System.out.println("Nothing to do here.");
            return;
        }

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
        else if (current.npc != null && current.npc.name != null && current.npc.name.contains("Leon") && p.hasItem("Full water bottle")) {
            System.out.println("'Leon: Wait a second...is that..Dziekuje! Exactly what I needed.'");
            System.out.println(">> Leon stands up and KICKS the cellar door open for you!");
            Game.isCellarLocked = false;
            p.replaceItem("Full water bottle", "Empty water bottle");
        }
    }

    // reads player input, puts it all to Upper case and checks if the player put in the letters correctly and pressed enter in time. Returns true or false.
    private boolean runLeverMinigame(Scanner sc) {
        String targetLetters = generateRandomLetters(letters);
        System.out.println("\n--- Lever Fix Minigame ---");
        System.out.println("Type these " + letters + " letters within 3 seconds:");
        System.out.println(">> " + targetLetters);
        System.out.print("Enter letters: ");

        long start = System.nanoTime();
        String input = sc.nextLine();
        String inputUpperCase = input.toUpperCase();
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        if (elapsedMs > leverTime) {
            System.out.println("Too slow. (" + elapsedMs + "ms)");
            return false;
        }
        if (!inputUpperCase.equals(targetLetters)) {
            System.out.println("Incorrect.");
            return false;
        }
        return true;
    }

    // Generates random letters in the alphabet
    private String generateRandomLetters(int len) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = (char) ('A' + r.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }
}