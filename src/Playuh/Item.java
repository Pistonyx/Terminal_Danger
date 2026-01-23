package Playuh;

public class Item {
    public String name;
    public String description;
    public Item(String name) {
        this.name = name;
    }
    public void showDescription(){
        System.out.println("\n--- Item: " + name + " ---");

        if (description != null && !description.isBlank()) {
            System.out.println(description);
        }

        if (description != null && !description.isBlank()) {
            System.out.println("Description: '" + description + "'");
        } else {
            System.out.println("This item doesnt have a description");
        }
    }
}
