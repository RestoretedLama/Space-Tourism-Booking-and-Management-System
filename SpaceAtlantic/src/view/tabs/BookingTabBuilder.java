package view.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Booking;

public class BookingTabBuilder {
    public static VBox build() {
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

        return vbox;
    }
}