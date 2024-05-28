package Projects;

import java.sql.*;
import java.util.Scanner;

public class OnlineReservationSystem {
  
    private static Connection connection;
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isLoggedIn = false;

    public static void main(String[] args) {
        System.out.println("******* Welcome to Online Reservation ********");

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reservation_system", "root", "root");

            while (true) {
                System.out.println("Enter 1 :- Login Form \n"
                        + "Enter 2 :- Reservation System \n"
                        + "Enter 3 :- Cancellation Form \n"
                        + "Enter 4 :- Add User \n"
                        + "Enter 5 :- Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        if (isLoggedIn) {
                            makeReservation();
                        } else {
                            System.out.println("Please login first.");
                        }
                        break;
                    case 3:
                        if (isLoggedIn) {
                            cancelReservation();
                        } else {
                            System.out.println("Please login first.");
                        }
                        break;
                    case 4:
                        addUsers();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        connection.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void login() throws SQLException {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    isLoggedIn = true;
                    System.out.println("Login successful.");
                } else {
                    System.out.println("Invalid username or password.");
                }
            }
        }
    }

    private static void makeReservation() throws SQLException {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Time (HH:MM:SS): ");
        String time = scanner.nextLine();
        System.out.print("Number of people: ");
        int people = scanner.nextInt();
        scanner.nextLine(); 

        String sql = "INSERT INTO reservations (name, email, date, time, people) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, date);
            stmt.setString(4, time);
            stmt.setInt(5, people);
            stmt.executeUpdate();
            System.out.println("Reservation made successfully.");
        }
    }
    
    private static void addUsers() throws SQLException {
        System.out.println("Enter the number of users you want to add:");
        int numUsers = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 0; i < numUsers; i++) {
            System.out.println("Enter username for user " + (i + 1) + ":");
            String username = scanner.nextLine();
            System.out.println("Enter password for user " + (i + 1) + ":");
            String password = scanner.nextLine();

            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();
            }
            System.out.println("User added successfully.");
        }
    }

    private static void cancelReservation() throws SQLException {
        System.out.print("Enter Reservation ID to cancel: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); 

        String sql = "DELETE FROM reservations WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
            System.out.println("Reservation cancelled successfully.");
        }

       
        String cancelSql = "INSERT INTO cancellations (reservation_id) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(cancelSql)) {
            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
        }
    }
}
