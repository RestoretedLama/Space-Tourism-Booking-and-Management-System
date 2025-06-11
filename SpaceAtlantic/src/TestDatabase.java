import controller.CustomerController;
import model.Destination;
import model.Mission;
import database.DatabaseConnector;
import java.util.List;

public class TestDatabase {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        try {
            // Test database connection first
            DatabaseConnector.getConnection();
            System.out.println("Database connection successful!");
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        CustomerController controller = new CustomerController();
        
        // Test destinations
        System.out.println("\n=== Testing Destinations ===");
        List<Destination> destinations = controller.getAvailableDestinations();
        System.out.println("Found " + destinations.size() + " destinations:");
        for (Destination dest : destinations) {
            System.out.println("- " + dest.getPlanet() + " (ID: " + dest.getId() + ")");
        }
        
        // Test missions for each destination
        System.out.println("\n=== Testing Missions ===");
        for (Destination dest : destinations) {
            System.out.println("\nMissions for " + dest.getPlanet() + " (ID: " + dest.getId() + "):");
            List<Mission> missions = controller.getAvailableMissionsForDestination(dest.getId());
            System.out.println("Found " + missions.size() + " missions");
            
            for (Mission mission : missions) {
                System.out.println("- Mission ID: " + mission.getId() + 
                                 ", Rocket: " + mission.getRocketName() + 
                                 ", Destination: " + mission.getDestinationName());
            }
        }
    }
} 