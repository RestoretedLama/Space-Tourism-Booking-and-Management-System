package view.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Astronaut;

public class AstronautTabBuilder {
    public static VBox build(TabPane tabPane) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField astronautIdField = new TextField();
        TextField fullNameField = new TextField();
        TextField experienceField = new TextField();
        TextField roleField = new TextField();
        TextField supervisorIdField = new TextField();

        Label resultLabel = new Label();
        Button submitButton = new Button("Register Astronaut");

        submitButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(astronautIdField.getText());
                String name = fullNameField.getText();
                int exp = Integer.parseInt(experienceField.getText());
                String role = roleField.getText();
                Integer supId = supervisorIdField.getText().isEmpty() ? null : Integer.parseInt(supervisorIdField.getText());

                Astronaut astronaut = new Astronaut(id, name, exp, role, supId);
                resultLabel.setText(astronaut.getSummary());
                tabPane.getSelectionModel().selectNext();
            } catch (Exception ex) {
                resultLabel.setText("Invalid input.");
            }
        });

        vbox.getChildren().addAll(
                new Label("Astronaut ID:"), astronautIdField,
                new Label("Full Name:"), fullNameField,
                new Label("Experience (years):"), experienceField,
                new Label("Role:"), roleField,
                new Label("Supervisor ID (optional):"), supervisorIdField,
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
