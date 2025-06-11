package controller;

import model.*;
import database.DatabaseConnector;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    
    // Müşteri kaydı
    public int registerGuest(String fullName, int age, String nationality, String gender, String contactInfo) {
        String query = "INSERT INTO Guests (full_name, age, nationality, gender, contact_info) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, fullName);
            stmt.setInt(2, age);
            stmt.setString(3, nationality);
            stmt.setString(4, gender);
            stmt.setString(5, contactInfo);
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    // Mevcut destinasyonları getir
    public List<Destination> getAvailableDestinations() {
        List<Destination> destinations = new ArrayList<>();
        String query = "SELECT * FROM Destinations ORDER BY planet_name";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                destinations.add(new Destination(
                    rs.getInt("destination_id"),
                    rs.getString("planet_name"),
                    rs.getString("region_name"),
                    rs.getDouble("distance_million_km")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinations;
    }
    
    // Belirli bir destinasyon için uygun misyonları getir
    public List<Mission> getAvailableMissionsForDestination(int destinationId) {
        List<Mission> missions = new ArrayList<>();
        System.out.println("Querying missions for destination ID: " + destinationId);
        
        String query = """
            SELECT m.mission_id, r.name AS rocket_name, d.planet_name AS destination_name, 
                   ls.site_name AS launch_site_name, m.travel_time_days, m.capacity,
                   m.amount, m.launch_date, m.return_date,
                   (m.capacity - COALESCE(booked_seats.count, 0)) AS available_seats
            FROM Missions m
            JOIN Rockets r ON m.rocket_id = r.rocket_id
            JOIN Destinations d ON m.destination_id = d.destination_id
            JOIN Launch_Sites ls ON m.launch_site_id = ls.site_id
            LEFT JOIN (
                SELECT mission_id, COUNT(*) as count 
                FROM Bookings 
                WHERE status = 'Confirmed' 
                GROUP BY mission_id
            ) booked_seats ON m.mission_id = booked_seats.mission_id
            WHERE m.destination_id = ? AND (m.capacity - COALESCE(booked_seats.count, 0)) > 0
            ORDER BY m.travel_time_days
            """;
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, destinationId);
            ResultSet rs = stmt.executeQuery();
            
            int count = 0;
            while (rs.next()) {
                count++;
                // Get supervisor and crew names separately
                String supervisorName = getAstronautNameForMission(rs.getInt("mission_id"), "Supervisor");
                String crewName = getAstronautNameForMission(rs.getInt("mission_id"), "Crew");
                
                Mission mission = new Mission(
                    rs.getInt("mission_id"),
                    rs.getString("rocket_name"),
                    rs.getString("destination_name"),
                    rs.getString("launch_site_name"),
                    rs.getInt("travel_time_days"),
                    supervisorName != null ? supervisorName : "Not Assigned",
                    crewName != null ? crewName : "Not Assigned",
                    rs.getDouble("amount"),
                    rs.getDate("launch_date").toLocalDate(),
                    rs.getDate("return_date").toLocalDate()
                );
                missions.add(mission);
                System.out.println("Found mission: " + mission.getRocketName() + " to " + mission.getDestinationName());
            }
            System.out.println("Total missions found: " + count);
        } catch (SQLException e) {
            System.err.println("SQL Error in getAvailableMissionsForDestination: " + e.getMessage());
            e.printStackTrace();
        }
        return missions;
    }
    
    // Helper method to get astronaut name for a specific mission and role
    private String getAstronautNameForMission(int missionId, String role) {
        String query = """
            SELECT a.full_name
            FROM Mission_Astronauts ma
            JOIN Astronauts a ON ma.astronaut_id = a.astronaut_id
            WHERE ma.mission_id = ? AND ma.role = ?
            """;
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, missionId);
            stmt.setString(2, role);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("full_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Rezervasyon oluştur
    public int createBooking(int guestId, int missionId, int seatNumber, LocalDate launchDate, LocalDate returnDate) {
        String query = "INSERT INTO Bookings (guest_id, mission_id, seat_number, launch_date, return_date, status) VALUES (?, ?, ?, ?, ?, 'Confirmed')";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, guestId);
            stmt.setInt(2, missionId);
            stmt.setInt(3, seatNumber);
            stmt.setDate(4, java.sql.Date.valueOf(launchDate));
            stmt.setDate(5, java.sql.Date.valueOf(returnDate));
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    // Ödeme yöntemlerini getir
    public List<String> getPaymentMethods() {
        List<String> methods = new ArrayList<>();
        methods.add("Credit Card");
        methods.add("Crypto");
        methods.add("Bank Transfer");
        return methods;
    }
    
    // Ödeme işlemi
    public boolean processPayment(int bookingId, double amount, String paymentMethod) {
        String query = "INSERT INTO Payments (booking_id, amount, payment_date, payment_method, status) VALUES (?, ?, ?, ?, 'Paid')";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, bookingId);
            stmt.setDouble(2, amount);
            stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setString(4, paymentMethod);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Rezervasyon detaylarını getir
    public Booking getBookingDetails(int bookingId) {
        String query = """
            SELECT b.booking_id, b.seat_number, b.launch_date, b.return_date, b.status,
                   g.full_name AS guest_name, m.mission_id,
                   r.name AS rocket_name, d.planet_name AS destination_name
            FROM Bookings b
            JOIN Guests g ON b.guest_id = g.guest_id
            JOIN Missions m ON b.mission_id = m.mission_id
            JOIN Rockets r ON m.rocket_id = r.rocket_id
            JOIN Destinations d ON m.destination_id = d.destination_id
            WHERE b.booking_id = ?
            """;
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Booking(
                    rs.getInt("booking_id"),
                    String.valueOf(rs.getInt("seat_number")),
                    rs.getDate("launch_date").toLocalDate(),
                    rs.getDate("return_date").toLocalDate(),
                    rs.getString("status"),
                    -1, // guestId not needed for display
                    rs.getInt("mission_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Review ekle
    public boolean addReview(int bookingId, int rating, String comment) {
        String query = "INSERT INTO Reviews (booking_id, rating, comment, review_date) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, bookingId);
            stmt.setInt(2, rating);
            stmt.setString(3, comment);
            stmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Misyon için boş koltuk sayısını getir
    public int getAvailableSeatsForMission(int missionId) {
        String query = """
            SELECT (m.capacity - COALESCE(booked_seats.count, 0)) AS available_seats
            FROM Missions m
            LEFT JOIN (
                SELECT mission_id, COUNT(*) as count 
                FROM Bookings 
                WHERE status = 'Confirmed' 
                GROUP BY mission_id
            ) booked_seats ON m.mission_id = booked_seats.mission_id
            WHERE m.mission_id = ?
            """;
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, missionId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("available_seats");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Test method to check if there are any missions
    public boolean hasAnyMissions() {
        String query = "SELECT COUNT(*) as count FROM Missions";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                int count = rs.getInt("count");
                System.out.println("Total missions in database: " + count);
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking missions: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    // Test method to check if there are any destinations
    public boolean hasAnyDestinations() {
        String query = "SELECT COUNT(*) as count FROM Destinations";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                int count = rs.getInt("count");
                System.out.println("Total destinations in database: " + count);
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking destinations: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
} 