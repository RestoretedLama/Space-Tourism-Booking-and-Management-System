package view.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Payment;

import java.util.Random;

public class PaymentTabBuilder {
    public static VBox build(TabPane tabPane) {
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
                int paymentId = new Random().nextInt(9000) + 1000;
                Integer.parseInt(paymentIdField.getText());
                int guestId = Integer.parseInt(guestIdField.getText());
                double amount = Double.parseDouble(amountField.getText());
                String method = methodBox.getValue();
                String status = statusBox.getValue();

                if (method == null || status == null || datePicker.getValue() == null) {
                    resultLabel.setText("Please fill all fields.");
                    return;
                }

                Payment payment = new Payment(paymentId, amount, datePicker.getValue(), method, status, guestId);
                resultLabel.setText(payment.getSummary());
                tabPane.getSelectionModel().selectNext();
            } catch (Exception ex) {
                resultLabel.setText("Invalid input.");
            }
        });

        vbox.getChildren().addAll(
                //new Label("Payment ID:"), paymentIdField,
                new Label("Guest ID:"), guestIdField,
                new Label("Amount:"), amountField,
                new Label("Date:"), datePicker,
                new Label("Method:"), methodBox,
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
