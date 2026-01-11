package Commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import Playuh.*;

public class HelpCommand implements GameCommand {
    private String file;
    public HelpCommand(String file) {
        this.file = file;
    }

    public void execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        System.out.println("\n--- AVAILABLE COMMANDS ---");
        // Reads and prints help file; reports missing file
        try {
            Scanner s = new Scanner(new File(file));
            while (s.hasNextLine()) System.out.println(s.nextLine());
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("Help file missing.");
        }
        System.out.println("--------------------------");
    }
}