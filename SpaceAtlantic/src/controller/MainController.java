package controller;

import database.DatabaseConnector;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    public String processTravel(Travel travel) {
        if (travel == null) return "Invalid travel data.";

        // Doğru karşılaştırma burada yapılmalı
        if (travel.getFromPlanet() == travel.getToPlanet()) {
            return "Source and destination planets must be different!";
        }

        return travel.getSummary();
    }
    public String processGuest(Guest guest) {
        if (guest == null) return "Invalid guest data.";
        return guest.getSummary();
    }

    public List<Rocket> getRockets() {
        List<Rocket> rockets = new ArrayList<>();
        String query = "SELECT * FROM Rockets";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                rockets.add(new Rocket(
                    rs.getInt("rocket_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("max_range_million_km"),
                    rs.getInt("capacity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rockets;
    }

    public List<Destination> getDestinations() {
        List<Destination> destinations = new ArrayList<>();
        String query = "SELECT * FROM Destinations";
        
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

    public List<LaunchSite> getLaunchSites() {
        List<LaunchSite> launchSites = new ArrayList<>();
        String query = "SELECT * FROM Launch_Sites";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                launchSites.add(new LaunchSite(
                    rs.getInt("site_id"),
                    rs.getString("site_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return launchSites;
    }

    public List<Astronaut> getSupervisors() {
        List<Astronaut> supervisors = new ArrayList<>();
        String query = "SELECT * FROM Astronauts WHERE role = 'Supervisor'";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int supervisorIdRaw = rs.getInt("supervisor_id");
                Integer supervisorId = rs.wasNull() ? null : supervisorIdRaw;
                
                supervisors.add(new Astronaut(
                    rs.getInt("astronaut_id"),
                    rs.getString("full_name"),
                    0, // experience_years field doesn't exist in DB, default to 0
                    rs.getString("role"),
                    supervisorId
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supervisors;
    }

    public List<Astronaut> getCrews() {
        List<Astronaut> crews = new ArrayList<>();
        String query = "SELECT * FROM Astronauts WHERE role = 'Crew'";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int supervisorIdRaw = rs.getInt("supervisor_id");
                Integer supervisorId = rs.wasNull() ? null : supervisorIdRaw;
                
                crews.add(new Astronaut(
                    rs.getInt("astronaut_id"),
                    rs.getString("full_name"),
                    0, // experience_years field doesn't exist in DB, default to 0
                    rs.getString("role"),
                    supervisorId
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crews;
    }

}
