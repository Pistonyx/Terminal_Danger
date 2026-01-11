package MainGame;

import Commands.*;
import Playuh.*;
import Playuh.Character;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;

public class Game {
    public static boolean isCellarLocked = true;
    public static Map<String, GameCommand> commandMap = new HashMap<>();
    public static Scanner sc = new Scanner(System.in);


    /**
     * Initializes game; runs input loop; processes commands
     */
    public static void main(String[] args) {
        ArrayList<Room> gameRooms = loadRooms("rooms.txt");
        if (gameRooms.isEmpty()) {
            System.out.println("Fatal Error: No rooms loaded. Check rooms.txt");
            return;
        }
        ArrayList<Item> gameItems = loadItems("items.txt");
        Player timofey = new Player("Timofey Mufasa");

        HelpCommand help = new HelpCommand("commands.txt");
        commandMap.put("h", help);
        commandMap.put("n", new MoveNextCommand());
        commandMap.put("p", new MovePrevCommand());
        commandMap.put("s", new SearchCommand());
        commandMap.put("i", new InteractCommand());
        commandMap.put("d", new DropCommand());

        setupCharacters(gameRooms);

        System.out.println("=== MISSION: THE CELLAR ASSASSINATION ===");

        boolean running = true;
        // Runs game loop; handles player input and commands
        while (running) {
            if (timofey.currentRoomIndex < 0 || timofey.currentRoomIndex >= gameRooms.size()) {
                System.out.println("Error: Player is out of bounds.");
                break;
            }
            Room current = gameRooms.get(timofey.currentRoomIndex);
            System.out.println("\n--- " + current.name + " ---");
            checkNPCPresence(current);
            System.out.println("Inv: " + timofey.inventory);
            System.out.print("Action > ");
            String action = sc.nextLine().toLowerCase();
            if (action.equals("q")) {
                running = false;
            } else if (commandMap.containsKey(action)) {
                commandMap.get(action).execute(timofey, gameRooms, gameItems);
            } else {
                System.out.println("Unknown command. Type 'h' for help.");
            }
        }
    }

    /**
     * Sets npc's in specific rooms
     */
    private static void setupCharacters(ArrayList<Room> rooms) {
        if (rooms.size() > 0) {
            rooms.get(0).npc = new Character("Mysterious Person", "Dressed in dark clothes.", "Welcome Timothy. Your mission is to reach the criminal in the cellar.");
        }

        if (rooms.size() > 2) {
            rooms.get(2).npc = new Character("Linda Nada Pérez",
                    "- Half Filipino, half Serbian\n- Speaks English and a tiny bit of Tagalog\n- Age: 22 (DOB: 6.9.2002)",
                    "Kumusta? I mean, hello. If you want the cellar, find Leon or Tobias.");
        }

        if (rooms.size() > 3) {
            rooms.get(3).npc = new Character("Tobias Reviero",
                    "- Half Trinidadian, half Czech\n- Age: 20 (DOB: 10.4.2004)\n- Likes: Dexter, Breaking Bad, Invincible\n- Favorite bands: The Strokes, The Fratellis, RATM, Gorillaz",
                    "Yo. I'm busy watching Invincible, but the cellar is a mess.");
        }

        if (rooms.size() > 5) {
            rooms.get(5).npc = new Character("Leon Marcin Klamer",
                    "- Half Polish, half African American\n- Speaks English, Polish, understands Czech\n- Age: 22 (DOB: 5.11.2002)\n- Combat fighter and drinker",
                    "I bet I could kick that door down...but im too thirsty to do anything right now.");
        }
    }

    public static ArrayList<Room> loadRooms(String file) {
        ArrayList<Room> list = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File(file));
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (!line.isEmpty()) list.add(new Room(line));
            }
            s.close();
        } catch (Exception e) {
            System.out.println("Rooms file error.");
        }
        return list;
    }
    public static void checkNPCPresence(Room r) {
        if (r.npc != null) System.out.println("[!] " + r.npc.name + " is here. Press 'i' to interact.");
    }

    public static ArrayList<Item> loadItems(String file) {
        ArrayList<Item> list = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File(file));
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (!line.isEmpty()) list.add(new Item(line));
            }
            s.close();
        } catch (Exception e) {
            System.out.println("Items file error.");
        }
        return list;
    }
}