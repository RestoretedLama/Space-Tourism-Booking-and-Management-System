package view.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Destination;
import model.Rocket;

public class RocketAndDestinationTabBuilder {
    public static TabPane build() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Rocket Form
        VBox rocketBox = new VBox(10);
        rocketBox.setPadding(new Insets(20));
        TextField rocketIdField = new TextField();
        TextField rocketNameField = new TextField();
        TextField rocketTypeField = new TextField();
        TextField rocketRangeField = new TextField();
        Label rocketLabel = new Label();
        Button rocketBtn = new Button("Save Rocket");
        rocketBtn.setOnAction(e -> {
            Rocket r = new Rocket(
                    Integer.parseInt(rocketIdField.getText()),
                    rocketNameField.getText(),
                    rocketTypeField.getText(),
                    Double.parseDouble(rocketRangeField.getText()),
                    0 // kapasiteyi default 0 yapıyorum
            );

        });
        rocketBox.getChildren().addAll(
                new Label("Rocket ID:"), rocketIdField,
                new Label("Name:"), rocketNameField,
                new Label("Type:"), rocketTypeField,
                new Label("Max Range (million km):"), rocketRangeField,
                rocketBtn, rocketLabel
        );

        // Destination Form
        VBox destBox = new VBox(10);
        destBox.setPadding(new Insets(20));
        TextField destIdField = new TextField();
        TextField planetField = new TextField();
        TextField regionField = new TextField();
        TextField distanceField = new TextField();
        Label destLabel = new Label();
        Button destBtn = new Button("Save Destination");
        destBtn.setOnAction(e -> {
            try {
                Destination d = new Destination(
                        Integer.parseInt(destIdField.getText()),
                        planetField.getText(),
                        regionField.getText(),
                        Double.parseDouble(distanceField.getText())
                );
                destLabel.setText(d.getSummary());
            } catch (Exception ex) {
                destLabel.setText("Invalid input.");
            }
        });
        destBox.getChildren().addAll(
                new Label("Destination ID:"), destIdField,
                new Label("Planet Name:"), planetField,
                new Label("Region Name:"), regionField,
                new Label("Distance (million km):"), distanceField,
                destBtn, destLabel
        );

        tabPane.getTabs().addAll(
                new Tab("Rocket", rocketBox),
                new Tab("Destination", destBox)
        );
        return tabPane;
    }
}
