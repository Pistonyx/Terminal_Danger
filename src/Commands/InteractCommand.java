package Commands;

import MainGame.Game;
import MainGame.Texts;
import Playuh.Item;
import Playuh.Player;
import Playuh.Room;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class InteractCommand implements GameCommand {

    private int letters = 5;
    private long leverTime = 3000;

    public String execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        Room current = rooms.get(p.currentRoomIndex);
        Scanner sc = new Scanner(System.in);

        // Lever puzzle
        // uses the runLeverMinigame and then checks if it returned true or false. Based on that, it outputs 2 texts.
        if (current.name.contains("The garage") && p.hasItem("Broken lever handle")) {
            System.out.print(Texts.t("interact.lever.prompt") + " ");
            if (!sc.nextLine().equalsIgnoreCase("y")) {
                return "LEVER_FIX_CANCELLED";
            }

            boolean won = runLeverMinigame(sc);
            if (won) {
                p.replaceItem("Broken lever handle", "Lever handle");
                System.out.println(Texts.t("interact.lever.success"));
                return "LEVER_FIX_SUCCESS";
            } else {
                System.out.println(Texts.t("interact.lever.failed"));
                return "LEVER_FIX_FAILED";
            }
        }

        // If there's no NPC, interacting does nothing
        if (current.npc == null) {
            System.out.println(Texts.t("interact.nothing"));
            return "INTERACT_NO_NPC";
        }

        // Always show NPC bio + dialogue when interacting
        current.npc.showBio();

        // Final choice: kill or spare (only once)
        if (!Game.missionComplete && current.name.contains("The cold cellar")) {

            System.out.print("\nThe criminal is right in front of you. Youre confronted with a choise. Will you kill him? Or will you spare him and detain him? (k/s): ");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("k")) {
                System.out.println("You chose to kill the criminal.");
                Game.missionComplete = true;
                return "CRIMINAL_KILLED";
            } else if (choice.equalsIgnoreCase("s")) {
                System.out.println("You chose to spare the criminal.");
                Game.missionComplete = true;
                return "CRIMINAL_SPARED";
            } else {
                // Hesitation outcome depends on whether Leon opened the cellar
                if (!Game.usedLeonToOpenCellar) {
                    System.out.println("You hesitate... but Leon doesn't.");
                    System.out.println(">> Leon steps in and kills the criminal.");
                    Game.missionComplete = true;
                    return "CRIMINAL_KILLED_BY_LEON";
                } else {
                    System.out.println("You hesitate... Leon tries to apprehend him but he's too tired and in the confusion, the criminal gets away.");
                    Game.missionComplete = true;
                    return "CRIMINAL_ESCAPED";
                }
            }
        }

        // Safe logic (Apartment 102, Tobias Reviero)
        if (current.name != null
                && current.name.contains("102")
                && current.npc.name != null
                && current.npc.name.contains("Tobias Reviero")) {

            // First time discovery requires the Small key, and consumes it.
            if (!p.safeDiscovered) {
                if (!p.hasItem("Small key")) {
                    System.out.println("Tobias Reviero: 'There's a safe back there... if only we had a small key to access the mechanism.'");
                    return "SAFE_NEEDS_KEY_TO_DISCOVER";
                }

                System.out.println("Tobias Reviero: 'I found a safe in the back... but I can't open it. It's locked by a mechanism.'");
                removeFirstIgnoreCase(p.inventory, "Small key");
                p.safeDiscovered = true;
            }

            // After discovery: no key required anymore
            if (p.safeSolved) {
                System.out.println("Tobias Reviero: 'The safe is already open. We got what we needed.'");
                return "SAFE_ALREADY_SOLVED";
            }

            System.out.print("\nTry to unlock the safe? (y/n): ");
            if (!sc.nextLine().equalsIgnoreCase("y")) {
                System.out.println("You decide to leave the safe alone for now.");
                return "SAFE_NOT_ATTEMPTED";
            }

            if (p.safeProgress == 0) {
                System.out.println("There seems to be a hole that can fit a gear.");
            } else if (p.safeProgress == 1) {
                System.out.println("Now theres some square shape.");
            } else if (p.safeProgress == 2) {
                System.out.println("I need a lever for this one.");
            }

            System.out.println("\n--- SAFE MECHANISM ---");
            System.out.println("Press 0 to cancel.");
            System.out.println("Inventory: " + p.inventory);
            System.out.print("Select item index to use on the safe (1-" + p.inventory.size() + "): ");

            int idx;
            try {
                idx = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid choice.");
                return "SAFE_INVALID_INPUT";
            }

            if (idx == 0) {
                return "SAFE_CANCELLED";
            }

            if (idx < 1 || idx > p.inventory.size()) {
                System.out.println("Index out of bounds of inventory.");
                return "SAFE_INVALID_INDEX";
            }

            String chosen = p.inventory.get(idx - 1);
            // sets the order for safeProgress
            String[] order = new String[] { "Rotating gear", "Weighted cube", "Lever handle" };
            String needed = order[p.safeProgress];

            if (!chosen.equalsIgnoreCase(needed)) {
                System.out.println("That doesn't fit.");
                System.out.println(">> The mechanism doesn't move.");
                return "SAFE_WRONG_ITEM";
            }

            // removes the item from the inventory after youve used it
            removeFirstIgnoreCase(p.inventory, needed);
            System.out.println(">> Installed: " + needed);

            p.safeProgress++;

            if (p.safeProgress >= order.length) {
                if (!p.inventory.contains("Code")) {
                    p.inventory.add("Code");
                }

                p.safeSolved = true;
                p.safeProgress = 0;

                System.out.println("\nThe mechanism clicks... the safe opens!");
                System.out.println(">> You received: Code");
                return "SAFE_SOLVED_CODE_RECEIVED";
            }

            return "SAFE_PROGRESS_" + p.safeProgress;
        }

        // Water Bottle Puzzle
        if ((current.name.contains("101") || current.name.contains("102")) && p.hasItem("Empty water bottle")) {
            System.out.print("\nYo you lowkey have an empty water bottle. Do you want to use the sink to fill your bottle with water? (y/n): ");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                p.replaceItem("Empty water bottle", "Full water bottle");
                System.out.println("You now have a Full water bottle.");
                return "BOTTLE_FILLED";
            } else {
                System.out.println("You didn't fill up your bottle");
                return "BOTTLE_FILL_DECLINED";
            }
        }
        // Cellar Unlocking using Leon
        else if (current.npc != null && current.npc.name != null && current.npc.name.contains("Leon") && p.hasItem("Full water bottle")) {
            System.out.println("'Leon: Wait a second...is that..Dziekuje! Exactly what I needed.'");
            System.out.println(">> Leon stands up and KICKS the cellar door open for you!");
            Game.isCellarLocked = false;
            Game.usedLeonToOpenCellar = true;
            p.replaceItem("Full water bottle", "Empty water bottle");
            return "CELLAR_UNLOCKED";
        }

        return "INTERACT_NO_EFFECT";
    }

    private void removeFirstIgnoreCase(ArrayList<String> list, String itemName) {
        for (int i = 0; i < list.size(); i++) {
            String v = list.get(i);
            if (v != null && v.equalsIgnoreCase(itemName)) {
                list.remove(i);
                return;
            }
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