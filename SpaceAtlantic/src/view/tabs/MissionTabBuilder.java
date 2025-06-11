package view.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.*;

import java.util.List;

public class MissionTabBuilder {

    public static VBox build(List<Rocket> rockets,
                             List<Destination> destinations,
                             List<LaunchSite> launchSites,
                             List<Astronaut> supervisors,
                             List<Astronaut> crews) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        // Mission ID (optional, or auto-generated in DB)
        TextField missionIdField = new TextField();
        missionIdField.setPromptText("Mission ID (optional)");

        // Travel time in days
        TextField travelDaysField = new TextField();
        travelDaysField.setPromptText("Travel Time (days)");

        // Capacity (usually from Rocket capacity, but editable here)
        TextField capacityField = new TextField();
        capacityField.setPromptText("Capacity");

        // ComboBoxes for selecting existing objects
        ComboBox<Rocket> rocketComboBox = new ComboBox<>();
        rocketComboBox.getItems().addAll(rockets);
        rocketComboBox.setPromptText("Select Rocket");

        ComboBox<Destination> destinationComboBox = new ComboBox<>();
        destinationComboBox.getItems().addAll(destinations);
        destinationComboBox.setPromptText("Select Destination");

        ComboBox<LaunchSite> launchSiteComboBox = new ComboBox<>();
        launchSiteComboBox.getItems().addAll(launchSites);
        launchSiteComboBox.setPromptText("Select Launch Site");

        ComboBox<Astronaut> supervisorComboBox = new ComboBox<>();
        supervisorComboBox.getItems().addAll(supervisors);
        supervisorComboBox.setPromptText("Select Supervisor");

        ComboBox<Astronaut> crewComboBox = new ComboBox<>();
        crewComboBox.getItems().addAll(crews);
        crewComboBox.setPromptText("Select Crew");

        Label resultLabel = new Label();

        Button submitButton = new Button("Create Mission");
        submitButton.setOnAction(e -> {
            try {
                int missionId = missionIdField.getText().isEmpty() ? 0 : Integer.parseInt(missionIdField.getText());
                int travelDays = Integer.parseInt(travelDaysField.getText());
                int capacity = Integer.parseInt(capacityField.getText());

                Rocket selectedRocket = rocketComboBox.getValue();
                Destination selectedDestination = destinationComboBox.getValue();
                LaunchSite selectedLaunchSite = launchSiteComboBox.getValue();
                Astronaut selectedSupervisor = supervisorComboBox.getValue();
                Astronaut selectedCrew = crewComboBox.getValue();

                if (selectedRocket == null || selectedDestination == null || selectedLaunchSite == null
                        || selectedSupervisor == null || selectedCrew == null) {
                    resultLabel.setText("Please select all fields!");
                    return;
                }

                // Burada Mission objesini oluştur, ya da DB'ye kaydet
                // Örnek:
                Mission mission = new Mission(
                        missionId,
                        selectedRocket.getName(),
                        selectedDestination.toString(),
                        selectedLaunchSite.getName(),
                        travelDays,
                        selectedSupervisor.getFullName(),
                        selectedCrew.getFullName()
                );

                resultLabel.setText("Mission created: " + missionSummary(mission));

            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numeric values.");
            } catch (Exception ex) {
                resultLabel.setText("Error: " + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(
                new Label("Mission ID (optional):"), missionIdField,
                new Label("Travel Time (days):"), travelDaysField,
                new Label("Capacity:"), capacityField,
                new Label("Rocket:"), rocketComboBox,
                new Label("Destination:"), destinationComboBox,
                new Label("Launch Site:"), launchSiteComboBox,
                new Label("Supervisor:"), supervisorComboBox,
                new Label("Crew:"), crewComboBox,
                submitButton,
                resultLabel
        );

        return vbox;
    }

    private static String missionSummary(Mission mission) {
        return "ID: " + mission.getId() +
                ", Rocket: " + mission.getRocketName() +
                ", Destination: " + mission.getDestinationName() +
                ", Launch Site: " + mission.getLaunchSiteName() +
                ", Days: " + mission.getTravelDays() +
                ", Supervisor: " + mission.getSupervisorName() +
                ", Crew: " + mission.getCrewName();
    }
}
