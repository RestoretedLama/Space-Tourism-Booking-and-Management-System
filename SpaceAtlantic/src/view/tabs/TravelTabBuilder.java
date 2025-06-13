package view.tabs;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Planet;
import model.Travel;

public class TravelTabBuilder {
    public static VBox build(MainController controller, TabPane tabPane) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        ComboBox<Planet> fromPlanetBox = new ComboBox<>();
        ComboBox<Planet> toPlanetBox = new ComboBox<>();
        ComboBox<String> baseToBox = new ComboBox<>();
        ComboBox<String> genderBox = new ComboBox<>();

        fromPlanetBox.getItems().addAll(Planet.values());
        toPlanetBox.getItems().addAll(Planet.values());
        baseToBox.getItems().addAll("Base Alpha", "Base Beta");
        genderBox.getItems().addAll("Male", "Female", "Other");

        CheckBox luggageCheck = new CheckBox("Bringing luggage?");
        TextField luggageWeightField = new TextField();
        luggageWeightField.setDisable(true);
        luggageCheck.setOnAction(e -> luggageWeightField.setDisable(!luggageCheck.isSelected()));

        Label resultLabel = new Label();
        Button submitButton = new Button("Submit Travel");

        submitButton.setOnAction(e -> {
            Planet from = fromPlanetBox.getValue();
            Planet to = toPlanetBox.getValue();
            String gender = genderBox.getValue();
            String base = baseToBox.getValue();
            boolean hasLuggage = luggageCheck.isSelected();

            double luggageWeight = 0;
            if (hasLuggage) {
                try {
                    luggageWeight = Double.parseDouble(luggageWeightField.getText());
                } catch (Exception ex) {
                    resultLabel.setText("Invalid luggage weight.");
                    return;
                }
            }

            if (from == null || to == null || from == to) {
                resultLabel.setText("Source and destination planets must be different!");
                return;
            }

            Travel travel = new Travel(from, to, base, hasLuggage, luggageWeight, gender);
            resultLabel.setText(controller.processTravel(travel));
            tabPane.getSelectionModel().selectNext();
        });

        vbox.getChildren().addAll(
                new Label("From Planet:"), fromPlanetBox,
                new Label("To Planet:"), toPlanetBox,
                new Label("Target Base on Arrival:"), baseToBox,
                new Label("Gender:"), genderBox,
                luggageCheck,
                new Label("Luggage Weight (kg):"), luggageWeightField,
                submitButton, resultLabel
        );

        // Create ScrollPane and wrap the content
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPrefViewportHeight(500);
        scrollPane.setPrefViewportWidth(600);

        // Create a container VBox to hold the ScrollPane
        VBox container = new VBox();
        container.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);
        container.setPrefHeight(600);
        container.setPrefWidth(800);
        container.setMaxHeight(600);
        container.setMaxWidth(800);

        return container;
    }
}
