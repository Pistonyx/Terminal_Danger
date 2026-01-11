package Playuh;

public class Character {
    public String name, bio, dialogue;
    public Character(String n, String b, String d) {
        name = n; bio = b; dialogue = d;
    }
    public void showBio() {
        System.out.println("\n--- NPC BIO: " + name + " ---");
        System.out.println(bio);
        System.out.println("Dialogue: '" + dialogue + "'");
    }
}