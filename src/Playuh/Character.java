package Playuh;

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
        System.out.println("\n--- NPC: " + name + " ---");

        if (bio != null && !bio.isBlank()) {
            System.out.println(bio);
        }

        if (dialogue != null && !dialogue.isBlank()) {
            System.out.println("Dialogue: '" + dialogue + "'");
        } else {
            System.out.println("Dialogue: They don’t say anything.");
        }
    }
}