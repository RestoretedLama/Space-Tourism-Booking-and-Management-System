package view.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Booking;

public class BookingTabBuilder {
    public static VBox build(TabPane tabPane) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField bookingIdField = new TextField();
        TextField guestIdField = new TextField();
        TextField missionIdField = new TextField();
        TextField seatNumberField = new TextField();
        DatePicker launchDatePicker = new DatePicker();
        DatePicker returnDatePicker = new DatePicker();
        ComboBox<String> statusBox = new ComboBox<>();
        statusBox.getItems().addAll("Planned", "Completed", "Cancelled");

        Label resultLabel = new Label();
        Button submitButton = new Button("Create Booking");

        submitButton.setOnAction(e -> {
            try {
                int bookingId = Integer.parseInt(bookingIdField.getText());
                int guestId = Integer.parseInt(guestIdField.getText());
                int missionId = Integer.parseInt(missionIdField.getText());
                String seat = seatNumberField.getText();
                String status = statusBox.getValue();

                if (seat.isEmpty() || status == null || launchDatePicker.getValue() == null || returnDatePicker.getValue() == null) {
                    resultLabel.setText("Please fill all fields.");
                    return;
                }

                Booking booking = new Booking(
                        bookingId,
                        seat,
                        launchDatePicker.getValue(),
                        returnDatePicker.getValue(),
                        status,
                        guestId,
                        missionId
                );

                resultLabel.setText(booking.getSummary());
                tabPane.getSelectionModel().selectNext();

            } catch (Exception ex) {
                resultLabel.setText("Invalid input. Please check numbers and dates.");
            }
        });

        vbox.getChildren().addAll(
                new Label("Booking ID:"), bookingIdField,
                new Label("Guest ID:"), guestIdField,
                new Label("Mission ID:"), missionIdField,
                new Label("Seat Number:"), seatNumberField,
                new Label("Launch Date:"), launchDatePicker,
                new Label("Return Date:"), returnDatePicker,
                new Label("Status:"), statusBox,
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
