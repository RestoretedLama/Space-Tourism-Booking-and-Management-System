package view.tabs;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Guest;

public class GuestTabBuilder {
    public static VBox build(MainController controller) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField ageField = new TextField();
        TextField nationalityField = new TextField();
        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Male", "Female", "Other");
        TextField contactField = new TextField();

        Label resultLabel = new Label();
        Button submitButton = new Button("Register Guest");

        submitButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String nationality = nationalityField.getText();
                String gender = genderBox.getValue();
                String contact = contactField.getText();

                if (name.isEmpty() || gender == null || contact.isEmpty()) {
                    resultLabel.setText("Please fill in all required fields.");
                    return;
                }

                Guest guest = new Guest(id, name, age, nationality, gender, contact);
                resultLabel.setText(controller.processGuest(guest));
            } catch (Exception ex) {
                resultLabel.setText("Invalid input.");
            }
        });

        vbox.getChildren().addAll(
                new Label("Guest ID:"), idField,
                new Label("Full Name:"), nameField,
                new Label("Age:"), ageField,
                new Label("Nationality:"), nationalityField,
                new Label("Gender:"), genderBox,
                new Label("Contact Info:"), contactField,
                submitButton,
                resultLabel
        );
        return vbox;
    }
}