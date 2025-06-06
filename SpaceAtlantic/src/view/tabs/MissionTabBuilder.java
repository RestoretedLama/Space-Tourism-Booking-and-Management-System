package view.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Mission;

public class MissionTabBuilder {
    public static VBox build() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField missionIdField = new TextField();
        TextField locationField = new TextField();
        TextField timeDaysField = new TextField();
        TextField capacityField = new TextField();
        TextField siteIdField = new TextField();
        TextField destinationIdField = new TextField();
        TextField rocketIdField = new TextField();

        Label resultLabel = new Label();
        Button submitButton = new Button("Create Mission");

        submitButton.setOnAction(e -> {
            try {
                int missionId = Integer.parseInt(missionIdField.getText());
                String location = locationField.getText();
                int days = Integer.parseInt(timeDaysField.getText());
                int cap = Integer.parseInt(capacityField.getText());
                int siteId = Integer.parseInt(siteIdField.getText());
                int destId = Integer.parseInt(destinationIdField.getText());
                int rocketId = Integer.parseInt(rocketIdField.getText());

                Mission mission = new Mission(missionId, location, days, cap, siteId, destId, rocketId);
                resultLabel.setText(mission.getSummary());

            } catch (Exception ex) {
                resultLabel.setText("Invalid input.");
            }
        });

        vbox.getChildren().addAll(
                new Label("Mission ID:"), missionIdField,
                new Label("Departure Location:"), locationField,
                new Label("Travel Time (days):"), timeDaysField,
                new Label("Capacity:"), capacityField,
                new Label("Site ID:"), siteIdField,
                new Label("Destination ID:"), destinationIdField,
                new Label("Rocket ID:"), rocketIdField,
                submitButton, resultLabel
        );
        return vbox;
    }
}
