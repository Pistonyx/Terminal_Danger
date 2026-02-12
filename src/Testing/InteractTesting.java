package Testing;

import static org.junit.jupiter.api.Assertions.*;

import Commands.InteractCommand;
import Playuh.Character;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import Playuh.Player;
import Playuh.Room;
import Playuh.Item;
import MainGame.Game;
public class InteractTesting {
    @Test
    void testExecute_WaterBottleSuccess() {
        Player p = new Player("TestPlayer");
        p.currentRoomIndex = 0;
        p.inventory = new ArrayList<>(Arrays.asList("Empty water bottle"));

        Room room101 = new Room();
        room101.name = "Apartment 101";

        // Add a dummy NPC
        room101.npc = new Character("Dummy","","");

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(room101);

        System.setIn(new ByteArrayInputStream("y\n".getBytes()));

        InteractCommand cmd = new InteractCommand();
        String result = cmd.execute(p, rooms, new ArrayList<>());

        assertEquals("BOTTLE_FILLED", result);
    }
    @Test
    void testExecute_SafeWrongItem() {
        Player p = new Player("TestPlayer");
        p.currentRoomIndex = 0;
        p.inventory = new ArrayList<>(Arrays.asList("Small key", "Wrong Item"));
        p.safeDiscovered = true; // Pretend we already used the key to find it
        p.safeProgress = 0; // Starting at the first item (Rotating gear)

        Room room102 = new Room();
        room102.name = "Apartment 102";
        room102.npc = new Character("Tobias Reviero","",""); // Required to trigger safe logic

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(room102);

        // Inputs "y" (to attempt), then "2" (to pick "Wrong Item")
        String input = "y\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InteractCommand cmd = new InteractCommand();
        String result = cmd.execute(p, rooms, new ArrayList<>());

        assertEquals("SAFE_WRONG_ITEM", result);
        assertEquals(2, p.inventory.size(), "Item should NOT be removed if it's wrong");
    }

    @Test
    void testExecute_NoNPCAction() {
        // Setup a room with no NPC
        Player p = new Player("TestPlayer");
        p.currentRoomIndex = 0;
        Room emptyRoom = new Room();
        emptyRoom.name = "Empty Hallway";
        emptyRoom.npc = null;

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(emptyRoom);

        InteractCommand cmd = new InteractCommand();
        String result = cmd.execute(p, rooms, new ArrayList<>());

        assertEquals("INTERACT_NO_NPC", result);
    }
    @Test
    void testExecute_CriminalSpared() {
        // Setup
        Player p = new Player("TestPlayer");
        p.currentRoomIndex = 0;

        // Ensure static game state is fresh
        MainGame.Game.missionComplete = false;

        Room cellar = new Room();
        cellar.name = "The cold cellar";
        cellar.npc = new Character("The Criminal","",""); // Must have an NPC to pass the check

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(cellar);

        // Input "s" to spare
        System.setIn(new java.io.ByteArrayInputStream("s\n".getBytes()));

        // Execute
        InteractCommand cmd = new InteractCommand();
        String result = cmd.execute(p, rooms, new ArrayList<>());

        // Assertions
        assertEquals("CRIMINAL_SPARED", result);
        assertTrue(MainGame.Game.missionComplete, "Mission should be marked as complete");
    }
    @Test
    void testExecute_CriminalKilledByLeon() {
        // 1. Setup
        MainGame.Game.missionComplete = false;
        MainGame.Game.usedLeonToOpenCellar = false; // Leon is nearby/ready

        Player p = new Player("TestPlayer");
        p.currentRoomIndex = 0;
        Room cellar = new Room();
        cellar.name = "The cold cellar";
        cellar.npc = new Character("The Criminal","","");

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(cellar);

        // 2. Mock Input: "wait" (not k or s, triggering the hesitation logic)
        System.setIn(new java.io.ByteArrayInputStream("wait\n".getBytes()));

        // 3. Execute
        InteractCommand cmd = new InteractCommand();
        String result = cmd.execute(p, rooms, new ArrayList<>());

        // 4. Assert
        assertEquals("CRIMINAL_KILLED_BY_LEON", result);
    }
}
