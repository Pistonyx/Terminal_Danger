package Testing;
import static org.junit.jupiter.api.Assertions.*;

import Commands.SearchCommand;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import Playuh.Player;
import Playuh.Room;
import Playuh.Item;
public class SearchTesting {
    @Test
    void testExecute_PickUpFromStorageSuccess() {
        Player p = new Player("Test");
        p.currentRoomIndex = 0;
        p.inventory = new ArrayList<>(Arrays.asList("Flashlight"));

        Room storage = new Room();
        storage.name = "loc_storage";
        // Store flashlight into storage rooms ArrayList
        storage.storedItems = new ArrayList<>(Arrays.asList("Flashlight"));

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(storage);

        System.setIn(new ByteArrayInputStream("1\n".getBytes()));

        SearchCommand cmd = new SearchCommand();
        cmd.execute(p, rooms, new ArrayList<>());

        assertTrue(p.inventory.contains("Flashlight"), "Flashlight should be in inventory");
        assertTrue(storage.storedItems.contains("Flashlight"),"Flashlight should be stored");
    }

    @Test
    void testExecute_InventoryFullLimit() {
        Player p = new Player("Test");
        // Inventory is full at 3 items
        p.inventory = new ArrayList<>(Arrays.asList("Item1", "Item2", "Item3"));
        p.currentRoomIndex = 0;

        Room room = new Room();
        room.hasItem = true;
        room.name="idk";

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(room);

        ArrayList<Item> worldItems = new ArrayList<>();
        worldItems.add(new Item("Magnifying Glass"));

        System.setIn(new ByteArrayInputStream("y\n".getBytes()));

        SearchCommand cmd = new SearchCommand();
        cmd.execute(p, rooms, worldItems);

        assertEquals(3, p.inventory.size(), "Inventory should not exceed 3 items");
        assertFalse(p.inventory.contains("Magnifying Glass"));
    }
}
