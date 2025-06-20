// controller/AdminController.java
package controller;

import database.DatabaseConnector;
import javafx.scene.control.ComboBox;
import model.*;

import java.sql.*;
import java.util.*;

public class AdminController {
    // Yeni metod: Tüm missionları detaylı çek
    public List<Mission> getAllMissions() {
        List<Mission> list = new ArrayList<>();
        
        // First check if the new columns exist
        boolean hasNewColumns = checkIfNewColumnsExist();
        
        String query;
        if (hasNewColumns) {
            query = """
                SELECT m.mission_id, m.name, r.name AS rocket_name, d.planet_name AS destination_name, ls.site_name AS launch_site_name,
                       m.travel_time_days, sup.full_name AS supervisor_name, crew.full_name AS crew_name,
                       m.amount, m.launch_date, m.return_date
                FROM Missions m
                JOIN Rockets r ON m.rocket_id = r.rocket_id
                JOIN Destinations d ON m.destination_id = d.destination_id
                JOIN Launch_Sites ls ON m.launch_site_id = ls.site_id
                JOIN Mission_Astronauts sup_assoc ON m.mission_id = sup_assoc.mission_id AND sup_assoc.role = 'Supervisor'
                JOIN Astronauts sup ON sup_assoc.astronaut_id = sup.astronaut_id
                JOIN Mission_Astronauts crew_assoc ON m.mission_id = crew_assoc.mission_id AND crew_assoc.role = 'Crew'
                JOIN Astronauts crew ON crew_assoc.astronaut_id = crew.astronaut_id
                """;
        } else {
            query = """
                SELECT m.mission_id, r.name AS rocket_name, d.planet_name AS destination_name, ls.site_name AS launch_site_name,
                       m.travel_time_days, sup.full_name AS supervisor_name, crew.full_name AS crew_name
                FROM Missions m
                JOIN Rockets r ON m.rocket_id = r.rocket_id
                JOIN Destinations d ON m.destination_id = d.destination_id
                JOIN Launch_Sites ls ON m.launch_site_id = ls.site_id
                JOIN Mission_Astronauts sup_assoc ON m.mission_id = sup_assoc.mission_id AND sup_assoc.role = 'Supervisor'
                JOIN Astronauts sup ON sup_assoc.astronaut_id = sup.astronaut_id
                JOIN Mission_Astronauts crew_assoc ON m.mission_id = crew_assoc.mission_id AND crew_assoc.role = 'Crew'
                JOIN Astronauts crew ON crew_assoc.astronaut_id = crew.astronaut_id
                """;
        }

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Mission mission;
                if (hasNewColumns) {
                    mission = new Mission(
                            rs.getInt("mission_id"),
                            rs.getString("name"),
                            rs.getString("rocket_name"),
                            rs.getString("destination_name"),
                            rs.getString("launch_site_name"),
                            rs.getInt("travel_time_days"),
                            rs.getString("supervisor_name"),
                            rs.getString("crew_name"),
                            rs.getDouble("amount"),
                            rs.getDate("launch_date").toLocalDate(),
                            rs.getDate("return_date").toLocalDate()
                    );
                } else {
                    // Use default values for missing columns
                    mission = new Mission(
                            rs.getInt("mission_id"),
                            "Mission " + rs.getInt("mission_id"), // default name
                            rs.getString("rocket_name"),
                            rs.getString("destination_name"),
                            rs.getString("launch_site_name"),
                            rs.getInt("travel_time_days"),
                            rs.getString("supervisor_name"),
                            rs.getString("crew_name"),
                            5000.0, // default amount
                            java.time.LocalDate.now().plusDays(30), // default launch date
                            java.time.LocalDate.now().plusDays(60)  // default return date
                    );
                }
                list.add(mission);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Check if the new columns exist in the Missions table
    private boolean checkIfNewColumnsExist() {
        try (Connection conn = DatabaseConnector.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "Missions", "amount");
            return columns.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to add missing columns to the database
    public void addMissingColumns() {
        try (Connection conn = DatabaseConnector.getConnection()) {
            Statement stmt = conn.createStatement();
            
            // Add name column if it doesn't exist
            try {
                stmt.execute("ALTER TABLE Missions ADD COLUMN name VARCHAR(200)");
                System.out.println("Added 'name' column");
            } catch (SQLException e) {
                if (e.getMessage().contains("Duplicate column name")) {
                    System.out.println("'name' column already exists");
                } else {
                    throw e;
                }
            }
            
            // Add amount column if it doesn't exist
            try {
                stmt.execute("ALTER TABLE Missions ADD COLUMN amount DECIMAL(10,2) DEFAULT 0.00");
                System.out.println("Added 'amount' column");
            } catch (SQLException e) {
                if (e.getMessage().contains("Duplicate column name")) {
                    System.out.println("'amount' column already exists");
                } else {
                    throw e;
                }
            }
            
            // Add launch_date column if it doesn't exist
            try {
                stmt.execute("ALTER TABLE Missions ADD COLUMN launch_date DATE");
                System.out.println("Added 'launch_date' column");
            } catch (SQLException e) {
                if (e.getMessage().contains("Duplicate column name")) {
                    System.out.println("'launch_date' column already exists");
                } else {
                    throw e;
                }
            }
            
            // Add return_date column if it doesn't exist
            try {
                stmt.execute("ALTER TABLE Missions ADD COLUMN return_date DATE");
                System.out.println("Added 'return_date' column");
            } catch (SQLException e) {
                if (e.getMessage().contains("Duplicate column name")) {
                    System.out.println("'return_date' column already exists");
                } else {
                    throw e;
                }
            }
            
            // Update existing missions with default values
            stmt.execute("UPDATE Missions SET amount = 5000.00, " +
                       "launch_date = DATE_ADD(CURDATE(), INTERVAL 30 DAY), " +
                       "return_date = DATE_ADD(CURDATE(), INTERVAL 60 DAY) " +
                       "WHERE amount IS NULL OR launch_date IS NULL OR return_date IS NULL");
            System.out.println("Updated existing missions with default values");
            
            // Update existing missions with default names
            stmt.execute("UPDATE Missions SET name = CONCAT('Mission ', mission_id, ' - ', " +
                       "(SELECT planet_name FROM Destinations WHERE destination_id = Missions.destination_id)) " +
                       "WHERE name IS NULL OR name = ''");
            System.out.println("Updated existing missions with default names");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initComboBoxes(ComboBox<Destination> destinationBox, ComboBox<LaunchSite> launchSiteBox,
                               ComboBox<Astronaut> supervisorBox, ComboBox<Astronaut> crewBox) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            Statement stmt = conn.createStatement();

            ResultSet destRS = stmt.executeQuery("SELECT * FROM Destinations");
            while (destRS.next()) {
                destinationBox.getItems().add(
                        new Destination(destRS.getInt(1), destRS.getString(2), destRS.getString(3), destRS.getDouble(4)));
            }

            ResultSet siteRS = stmt.executeQuery("SELECT * FROM Launch_Sites");
            while (siteRS.next()) {
                launchSiteBox.getItems().add(new LaunchSite(siteRS.getInt(1), siteRS.getString(2)));
            }

            supervisorBox.getItems().addAll(getAstronautsByRole("Supervisor"));
            crewBox.getItems().addAll(getAstronautsByRole("Crew"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadRockets(Destination destination, ComboBox<Rocket> rocketBox) {
        rocketBox.getItems().clear();
        try (Connection conn = DatabaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Rockets WHERE max_range_million_km >= ?");
            stmt.setDouble(1, destination.getDistance());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rocketBox.getItems().add(new Rocket(
                        rs.getInt(1),         // id
                        rs.getString(2),      // name
                        rs.getString(3),      // type
                        rs.getDouble(4),      // maxRange
                        rs.getInt(5)          // capacity  << burası önemli
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Astronaut> getAstronautsByRole(String role) throws SQLException {
        List<Astronaut> list = new ArrayList<>();
        Connection conn = DatabaseConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Astronauts WHERE role = ?");
        stmt.setString(1, role);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("astronaut_id");
            String name = rs.getString("full_name");
            int experience = 0; // Şu an tabloda bu yoksa varsayılan 0 ver
            String roleFromDb = rs.getString("role");
            int supervisorIdRaw = rs.getInt("supervisor_id");
            Integer supervisorId = rs.wasNull() ? null : supervisorIdRaw;

            list.add(new Astronaut(id, name, experience, roleFromDb, supervisorId));
        }
        return list;
    }


    public void createMission(Rocket rocket, Destination dest, LaunchSite site,
                              Astronaut supervisor, Astronaut crew, String missionName, int days,
                              double amount, java.time.LocalDate launchDate, java.time.LocalDate returnDate) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            // Check if new columns exist, if not add them
            if (!checkIfNewColumnsExist()) {
                addMissingColumns();
            }

            // Use provided mission name or generate default
            String finalMissionName = (missionName != null && !missionName.trim().isEmpty()) 
                ? missionName.trim() 
                : "Mission " + dest.getPlanet() + " - " + rocket.getName();

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Missions (name, rocket_id, destination_id, launch_site_id, travel_time_days, capacity, amount, launch_date, return_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, finalMissionName);
            stmt.setInt(2, rocket.getId());
            stmt.setInt(3, dest.getId());
            stmt.setInt(4, site.getId());
            stmt.setInt(5, days);
            stmt.setInt(6, rocket.getCapacity());
            stmt.setDouble(7, amount);
            stmt.setDate(8, java.sql.Date.valueOf(launchDate));
            stmt.setDate(9, java.sql.Date.valueOf(returnDate));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int missionId = rs.getInt(1);

            PreparedStatement astroStmt = conn.prepareStatement(
                    "INSERT INTO Mission_Astronauts (mission_id, astronaut_id, role) VALUES (?, ?, ?)"
            );
            astroStmt.setInt(1, missionId);
            astroStmt.setInt(2, supervisor.getId());
            astroStmt.setString(3, "Supervisor");
            astroStmt.executeUpdate();

            astroStmt.setInt(1, missionId);
            astroStmt.setInt(2, crew.getId());
            astroStmt.setString(3, "Crew");
            astroStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mission silme metodu
    public boolean deleteMission(int missionId) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false);
            
            // Önce bu mission'a ait booking'leri kontrol et
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT COUNT(*) FROM Bookings WHERE mission_id = ? AND status = 'Confirmed'"
            );
            checkStmt.setInt(1, missionId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int bookingCount = rs.getInt(1);
            
            if (bookingCount > 0) {
                // Eğer aktif booking varsa silmeye izin verme
                return false;
            }
            
            // Mission_Astronauts tablosundan kayıtları sil
            PreparedStatement astroStmt = conn.prepareStatement(
                "DELETE FROM Mission_Astronauts WHERE mission_id = ?"
            );
            astroStmt.setInt(1, missionId);
            astroStmt.executeUpdate();
            
            // Missions tablosundan kaydı sil
            PreparedStatement missionStmt = conn.prepareStatement(
                "DELETE FROM Missions WHERE mission_id = ?"
            );
            missionStmt.setInt(1, missionId);
            int result = missionStmt.executeUpdate();
            
            conn.commit();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}