package Testing;

import Commands.GameCommand;
import Playuh.Item;
import Playuh.Player;
import Playuh.Room;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DropTesting {
    class StorageTest {

        @Test
        void testExecute_SuccessStoreItem() {
            // Setup Data
            Player player = new Player("test");
            player.currentRoomIndex = 0;
            player.inventory = new ArrayList<>(Arrays.asList("Flashlight", "Map"));

            Room storageRoom = new Room();
            storageRoom.name = "Storage room";
            storageRoom.storedItems = new ArrayList<>();

            ArrayList<Room> rooms = new ArrayList<>();
            rooms.add(storageRoom);

            // Simulate User Input ("1" to select the Flashlight)
            String input = "1\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            // Execute
            GameCommand actions = new GameCommand() {
                @Override
                public String execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
                    return "";
                }
            };
            actions.execute(player, rooms, new ArrayList<>());

            // Assertions
            assertEquals(1, player.inventory.size(), "Inventory should have 1 item left");
            assertEquals("Flashlight", storageRoom.storedItems.get(0), "Storage should contain the Flashlight");
            assertFalse(player.inventory.contains("Flashlight"), "Flashlight should be removed from player");
        }
    }

}
