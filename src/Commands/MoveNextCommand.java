package Commands;

import java.util.ArrayList;

import MainGame.Game;
import MainGame.Texts;
import Playuh.*;

public class MoveNextCommand implements GameCommand {
    public String execute(Player p, ArrayList<Room> rooms, ArrayList<Item> items) {
        // Advances player unless at end or blocked
        if (p.currentRoomIndex < rooms.size() - 1) {

            // asks the player for confirmation to try to open the cellar.
            // if the player puts in the correct password then the player goes to the end of the game. Otherwise the player stays in room 6 (the balcony)
            if (p.currentRoomIndex == 6 && Game.isCellarLocked) {
                System.out.println(Texts.t("move.cellar.locked"));
                System.out.println(Texts.t("move.cellar.enterPrompt"));

                String choice = Game.sc.nextLine().trim();

                if (choice.equalsIgnoreCase("y")) {
                    System.out.print(Texts.t("move.cellar.passwordPrompt") + " ");
                    String passwordAttempt = Game.sc.nextLine();

                    if (passwordAttempt.equalsIgnoreCase("spsejecna")) {
                        Game.isCellarLocked = false;
                        System.out.println(Texts.t("move.cellar.unlocked"));
                        p.currentRoomIndex++; // move into the cellar now that it's unlocked
                    } else {
                        System.out.println(Texts.t("move.cellar.incorrect"));
                        p.currentRoomIndex = 6; // ensure player stays on balcony
                    }
                } else {
                    System.out.println(Texts.t("move.cellar.stay"));
                    p.currentRoomIndex = 6;
                }

            } else {
                p.currentRoomIndex++;
            }

        } else {
            System.out.println(Texts.t("move.end"));
        }
        return "";
    }
}