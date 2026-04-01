import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.*;
public class BookMyStay {
    // =========================
// UC9 — Custom Exception
// =========================
    static class InvalidBookingException extends Exception {
        InvalidBookingException(String message) {
            super(message);
        }
    }
    // =========================
// UC9 — Validator
// =========================
    static void validateBooking(String roomType, int available) throws InvalidBookingException {

        // Validate room type
        if (!(roomType.equals("Single Room") ||
                roomType.equals("Double Room") ||
                roomType.equals("Suite Room"))) {

            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Validate availability
        if (available <= 0) {
            throw new InvalidBookingException("No availability for: " + roomType);
        }
    }
    // =========================
// UC7 — Add-On Service Model
// =========================
    static class Service {
        String name;
        double price;

        Service(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }
    // =========================
// UC5 — Reservation Model
// =========================
    static class Reservation {
        String guestName;
        String roomType;

        Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }
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
        // =========================
// UC4 — Room Search (Read-Only)
// =========================

        System.out.println("\nAvailable Rooms (Search Results):");

// Create room objects again (domain usage)
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

// Loop through rooms and check availability
        for (Room room : rooms) {

            int available = inventory.getAvailability(room.type);

            // Show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
// =========================
// UC5 — Booking Request Queue
// =========================

        System.out.println("\nBooking Requests (Queue - FIFO):");

// Create Queue
        java.util.Queue<Reservation> bookingQueue = new java.util.LinkedList<>();

// Add booking requests
        bookingQueue.add(new Reservation("Anu", "Single Room"));
        bookingQueue.add(new Reservation("Rahul", "Double Room"));
        bookingQueue.add(new Reservation("Priya", "Suite Room"));

// Display queue
        System.out.println("Requests in order:");

        for (Reservation r : bookingQueue) {
            System.out.println(r.guestName + " requested " + r.roomType);
        }
// =========================
// UC6 — Room Allocation
// =========================

        System.out.println("\nProcessing Booking Requests...");

// Set to track ALL allocated room IDs (global uniqueness)
        java.util.Set<String> allocatedRoomIds = new java.util.HashSet<>();

// Map: Room Type → Assigned Room IDs
        java.util.Map<String, java.util.Set<String>> allocationMap = new java.util.HashMap<>();
// =========================
// UC10 — Cancellation Support
// =========================

// RoomID → RoomType mapping
        java.util.Map<String, String> roomIdToType = new java.util.HashMap<>();

// Stack for rollback (LIFO)
        java.util.Stack<String> rollbackStack = new java.util.Stack<>();

// =========================
        // =========================
// UC8 — Booking History (ADD HERE)
// =========================
        java.util.List<Reservation> bookingHistory = new java.util.ArrayList<>();

        while (!bookingQueue.isEmpty()) {

            Reservation request = bookingQueue.poll(); // FIFO

            String roomType = request.roomType;

            int available = inventory.getAvailability(roomType);

            try {

                // UC9 Validation
                validateBooking(roomType, available);

                // Generate unique room ID
                String roomId;
                do {
                    roomId = roomType.substring(0, 2).toUpperCase() + (int)(Math.random() * 1000);
                } while (allocatedRoomIds.contains(roomId));

                allocatedRoomIds.add(roomId);
                // UC10 — track mapping
                roomIdToType.put(roomId, roomType);

                allocationMap.putIfAbsent(roomType, new java.util.HashSet<>());
                allocationMap.get(roomType).add(roomId);

                inventory.updateAvailability(roomType, available - 1);

                bookingHistory.add(request); // UC8

                System.out.println("Booking Confirmed for " + request.guestName +
                        " | Room: " + roomType +
                        " | ID: " + roomId);

            } catch (InvalidBookingException e) {

                System.out.println("Booking Failed for " + request.guestName +
                        " | Reason: " + e.getMessage());
            }
        }
        System.out.println("\nFinal Room Allocations:");

        for (java.util.Map.Entry<String, java.util.Set<String>> entry : allocationMap.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
// =========================
// UC8 — Booking History Display
// =========================
        System.out.println("\nBooking History:");

        for (Reservation r : bookingHistory) {
            System.out.println(r.guestName + " booked " + r.roomType);
        }

// UC8 — Report
// =========================
        System.out.println("\nBooking Report Summary:");

        java.util.Map<String, Integer> report = new java.util.HashMap<>();

        for (Reservation r : bookingHistory) {
            report.put(r.roomType, report.getOrDefault(r.roomType, 0) + 1);
        }

        for (java.util.Map.Entry<String, Integer> entry : report.entrySet()) {
            System.out.println(entry.getKey() + " → Total Bookings: " + entry.getValue());
        }
// =========================
// UC10 — Booking Cancellation
// =========================

        System.out.println("\nProcessing Cancellation...");

// Example: cancel ONE booking (latest one)
        if (!allocatedRoomIds.isEmpty()) {

            // Get a room ID (simulate cancellation)
            String cancelRoomId = allocatedRoomIds.iterator().next();

            if (roomIdToType.containsKey(cancelRoomId)) {

                String roomType = roomIdToType.get(cancelRoomId);

                // Push to rollback stack
                rollbackStack.push(cancelRoomId);

                // Remove from allocated sets
                allocatedRoomIds.remove(cancelRoomId);
                allocationMap.get(roomType).remove(cancelRoomId);

                // Restore inventory
                int current = inventory.getAvailability(roomType);
                inventory.updateAvailability(roomType, current + 1);

                // Remove mapping
                roomIdToType.remove(cancelRoomId);

                System.out.println("Cancellation successful for Room ID: " + cancelRoomId);
                System.out.println("Inventory restored for: " + roomType);

            } else {
                System.out.println("Invalid cancellation request.");
            }

        } else {
            System.out.println("No bookings available to cancel.");
        }
// =========================
// UC7 — Add-On Service Mapping
// =========================

        System.out.println("\nAdd-On Services for Reservations:");

// Map: Reservation ID → List of Services
        java.util.Map<String, java.util.List<Service>> serviceMap = new java.util.HashMap<>();

// Example: Attach services to allocated rooms
        for (String roomId : allocatedRoomIds) {

            java.util.List<Service> services = new java.util.ArrayList<>();

            // Add services (example)
            services.add(new Service("Breakfast", 200));
            services.add(new Service("WiFi", 100));

            serviceMap.put(roomId, services);
        }
        // Display services and cost
        for (java.util.Map.Entry<String, java.util.List<Service>> entry : serviceMap.entrySet()) {

            String roomId = entry.getKey();
            java.util.List<Service> services = entry.getValue();

            double totalCost = 0;

            System.out.println("\nReservation ID: " + roomId);
            System.out.println("Selected Services:");

            for (Service s : services) {
                System.out.println("- " + s.name + " : ₹" + s.price);
                totalCost += s.price;
            }

            System.out.println("Total Add-On Cost: ₹" + totalCost);
        }
    }
}