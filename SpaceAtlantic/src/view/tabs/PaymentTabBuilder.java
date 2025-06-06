package view.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Payment;

import java.time.LocalDate;

public class PaymentTabBuilder {
    public static VBox build() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField paymentIdField = new TextField();
        TextField guestIdField = new TextField();
        TextField amountField = new TextField();
        DatePicker datePicker = new DatePicker();
        ComboBox<String> methodBox = new ComboBox<>();
        methodBox.getItems().addAll("Credit Card", "Bank Transfer", "Crypto");
        ComboBox<String> statusBox = new ComboBox<>();
        statusBox.getItems().addAll("Paid", "Pending", "Failed");

        Label resultLabel = new Label();
        Button submitButton = new Button("Submit Payment");

        submitButton.setOnAction(e -> {
            try {
                int paymentId = Integer.parseInt(paymentIdField.getText());
                int guestId = Integer.parseInt(guestIdField.getText());
                double amount = Double.parseDouble(amountField.getText());
                String method = methodBox.getValue();
                String status = statusBox.getValue();
                LocalDate date = datePicker.getValue();

                if (method == null || status == null || date == null) {
                    resultLabel.setText("Please fill all fields.");
                    return;
                }

                Payment payment = new Payment(paymentId, amount, date, method, status, guestId);
                resultLabel.setText(payment.getSummary());
            } catch (Exception ex) {
                resultLabel.setText("Invalid input.");
            }
        });

        vbox.getChildren().addAll(
                new Label("Payment ID:"), paymentIdField,
                new Label("Guest ID:"), guestIdField,
                new Label("Amount:"), amountField,
                new Label("Date:"), datePicker,
                new Label("Method:"), methodBox,
                new Label("Status:"), statusBox,
                submitButton, resultLabel
        );
        return vbox;
    }
}
