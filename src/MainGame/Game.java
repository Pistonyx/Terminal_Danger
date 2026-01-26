package MainGame;

import Commands.*;
import Playuh.*;
import Playuh.Character;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    //locks the Cellar room from the player until they find a way to get in
    public static boolean isCellarLocked = true;
    public static Map<String, GameCommand> commandMap = new HashMap<>();
    public static Scanner sc = new Scanner(System.in);

    /**
     * Initializes game; runs input loop; processes commands
     */
    public static void main(String[] args) {
        GameData data = GameData.loadGamaDataFromResources("/gamedata.json");
        ArrayList<Room> gameRooms = data.locations;
        // Loads the rooms from gamedata json
        if (gameRooms == null || gameRooms.isEmpty()) {
            System.out.println("Fatal Error: No rooms loaded from gamedata.json");
            return;
        }

        ArrayList<Item> gameItems = data.items != null ? data.items : new ArrayList<>();

        // Assign NPCs to their locations
        if (data.characters != null) {
            for (Character c : data.characters) {
                if (c == null) continue;
                if (c.homeLocationId == null || c.homeLocationId.isBlank()) continue; // e.g., player

                Room home = data.findLocation(c.homeLocationId);
                home.npc = c;

                // ensure something is shown during interaction if dialogue is empty
                if (home.npc.dialogue == null || home.npc.dialogue.isBlank()) {
                    home.npc.dialogue = "(They don't say anything.)";
                }
            }
        }

        Player timofey = new Player("Timofey Mufasa");
        // Adds commands
        HelpCommand help = new HelpCommand("commands.txt");
        commandMap.put("h", help);
        commandMap.put("n", new MoveNextCommand());
        commandMap.put("p", new MovePrevCommand());
        commandMap.put("s", new SearchCommand());
        commandMap.put("i", new InteractCommand());
        commandMap.put("d", new DropCommand());
        commandMap.put("items", new ItemInteract());
        commandMap.put("quest", new QuestCommand());

        System.out.println("=== MISSION: THE CELLAR ASSASSINATION ===");

        boolean running = true;
        // Runs game loop; handles player input and commands
        while (running) {
            // Checks if the player somehow got out of bounds
            if (timofey.currentRoomIndex < 0 || timofey.currentRoomIndex >= gameRooms.size()) {
                System.out.println("Error: Player is out of bounds.");
                break;
            }
            Room current = gameRooms.get(timofey.currentRoomIndex);
            System.out.println("");
            System.out.println("--- " + current.name + " ---");
            System.out.println("Type 'h' for help.");
            checkNPCPresence(current);
            System.out.println("Inventory: " + timofey.inventory);
            System.out.print("Action -> ");
            String action = sc.nextLine().toLowerCase();
            if (action.equals("quit")) {
                running = false;
            } else if (commandMap.containsKey(action)) {
                for (int i = 0; i < 25; i++) {
                    System.out.println("");
                }
                commandMap.get(action).execute(timofey, gameRooms, gameItems);
            } else {
                for (int i = 0; i < 25; i++) {
                    System.out.println("");
                }
                System.out.println("Unknown command. Type 'h' for help.");
            }
        }
    }
        // Checks if there are any NPC's in the room
    public static void checkNPCPresence(Room r) {
        if (r.npc != null) System.out.println("[!] " + r.npc.name + " is here. Press 'i' to interact.");
    }
}