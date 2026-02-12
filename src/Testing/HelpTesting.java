package Testing;

import static org.junit.jupiter.api.Assertions.*;

import Commands.HelpCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import Playuh.Player;
import Playuh.Room;
import Playuh.Item;

public class HelpTesting {

    @TempDir
    Path tempDir;

    @Test
    void testExecute_DisplaysSpecificMenu() throws Exception {
        // Create physical file
        File helpFile = tempDir.resolve("commands.txt").toFile();
        try (PrintWriter out = new PrintWriter(helpFile)) {
            out.println("[n] Next          - Move deeper into the building.");
            out.println("[p] Previous      - Move back toward the alley.");
            out.println("[s] Search        - Look for items in the room.");
            out.println("[i] Interact      - Talk to NPCs or use objects.");
            out.println("[d] Drop          - Remove an item from your bag.");
            out.println("[h] Help          - Display this list of commands.");
            out.println("[items]           - Display an items description.");
            out.println("[quest] Quest     - Display your current quests.");
            out.println("[quit] Quit       - Exit the game.");
        }

        // Initialize the command with the path to our new test file
        HelpCommand helpCmd = new HelpCommand(helpFile.getAbsolutePath());

        Player p = new Player("test");
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();

        //  Run the command
        //  print the menu above to your console during the test run
        String result = helpCmd.execute(p, rooms, items);

        //  Verification
        assertEquals("", result, "HelpCommand should return an empty string.");
        assertTrue(helpFile.exists(), "The test failed because the help file wasn't created.");
    }
}