package Playuh;

import MainGame.Texts;

public class Character {
    public String id;
    public String name;
    public String role;
    public String homeLocationId;
    public String bio;
    public String dialogue;

    public Character(String n, String b, String d) {
        name = n;
        bio = b;
        dialogue = d;
    }
    // checks if the npc has a bio and if they do then print it out
    public void showBio() {
        System.out.println(Texts.tf("npc.header", name));
        if (bio != null && !bio.isBlank()) {
            System.out.println(bio);
        }
        if (dialogue != null && !dialogue.isBlank()) {
            System.out.println(Texts.tf("npc.dialogue", dialogue));
        } else {
            System.out.println(Texts.t("npc.noDialogue"));
        }
    }
}