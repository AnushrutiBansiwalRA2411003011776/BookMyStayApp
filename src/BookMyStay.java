/**
 * Book My Stay Application
 * Entry point for the Hotel Booking Management System.
 *
 * @author Anu
 * @version 2.1
 */
public class BookMyStay {

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
    }
}