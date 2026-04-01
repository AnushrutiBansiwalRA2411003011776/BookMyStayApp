import java.util.HashMap;
import java.util.Map;
public class BookMyStay {
    static class RoomInventory {

        private java.util.HashMap<String, Integer> inventory;

        // Constructor → initialize inventory
        RoomInventory() {
            inventory = new java.util.HashMap<>();

            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 2);
        }

        // Get availability
        int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        // Update availability
        void updateAvailability(String roomType, int count) {
            inventory.put(roomType, count);
        }

        // Display full inventory
        void displayInventory() {
            System.out.println("\nCurrent Room Inventory:");

            for (java.util.Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + " → Available: " + entry.getValue());
            }
        }
    }

    // =========================
    // Abstract Room Class
    // =========================
    static abstract class Room {
        String type;
        int beds;
        double price;

        Room(String type, int beds, double price) {
            this.type = type;
            this.beds = beds;
            this.price = price;
        }

        abstract void displayDetails();
    }

    // =========================
    // Concrete Room Classes
    // =========================
    static class SingleRoom extends Room {
        SingleRoom() {
            super("Single Room", 1, 1000);
        }

        void displayDetails() {
            System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
        }
    }

    static class DoubleRoom extends Room {
        DoubleRoom() {
            super("Double Room", 2, 1800);
        }

        void displayDetails() {
            System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
        }
    }

    static class SuiteRoom extends Room {
        SuiteRoom() {
            super("Suite Room", 3, 3000);
        }

        void displayDetails() {
            System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        // UC1
        System.out.println("=== Book My Stay App ===");
        System.out.println("Hotel Booking System v2.1");
        System.out.println("Welcome to the application!");

        // =========================
        // UC2 — Room Initialization
        // =========================

        // Create Room Objects (Polymorphism)
        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        // Static availability (simple variables)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display Details
        System.out.println("\nRoom Details:");

        r1.displayDetails();
        System.out.println("Available: " + singleAvailable);

        r2.displayDetails();
        System.out.println("Available: " + doubleAvailable);

        r3.displayDetails();
        System.out.println("Available: " + suiteAvailable);

        // =========================
// UC3 — Use Inventory
// =========================

        RoomInventory inventory = new RoomInventory();

// Display inventory
        inventory.displayInventory();

// Example update
        inventory.updateAvailability("Single Room", 4);

// Display after update
        System.out.println("\nAfter updating Single Room availability:");
        inventory.displayInventory();
    }
}