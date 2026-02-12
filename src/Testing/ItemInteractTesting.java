package Testing;
import static org.junit.jupiter.api.Assertions.*;

import Commands.ItemInteract;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import Playuh.Player;
import Playuh.Room;
import Playuh.Item;
public class ItemInteractTesting {
    @Test
    void testExecute_ShowItemDescriptionSuccess() {
        //  Setup Player with an item
        Player p = new Player("TestPlayer");
        p.inventory = new ArrayList<>(Arrays.asList("Empty water bottle"));

        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Item> worldItems = new ArrayList<>();

        //  Input: "1" to select the water bottle
        String simulatedInput = "1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        //  Execute
        ItemInteract command = new ItemInteract();

        //  Load gamedata.json from resources
        String result = command.execute(p, rooms, worldItems);

        // Assertions
        assertEquals("", result, "Should return empty string as per command design");
        assertFalse(p.inventory.isEmpty(), "Inventory should not be cleared by inspecting an item");
    }

    @Test
    void testExecute_EmptyInventory() {
        Player p = new Player("TestPlayer");
        p.inventory = new ArrayList<>(); // Empty

        ItemInteract command = new ItemInteract();
        String result = command.execute(p, new ArrayList<>(), new ArrayList<>());

        assertEquals("", result);
        // You would see "You have no items to interact with." in the console
    }
}
