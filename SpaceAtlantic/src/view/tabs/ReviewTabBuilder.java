package view.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Review;

public class ReviewTabBuilder {
    public static VBox build(TabPane tabPane) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField reviewIdField = new TextField();
        TextField guestIdField = new TextField();
        TextField ratingField = new TextField();
        TextField commentField = new TextField();
        DatePicker datePicker = new DatePicker();

        Label resultLabel = new Label();
        Button submitButton = new Button("Submit Review");

        submitButton.setOnAction(e -> {
            try {
                int reviewId = Integer.parseInt(reviewIdField.getText());
                int guestId = Integer.parseInt(guestIdField.getText());
                int rating = Integer.parseInt(ratingField.getText());
                String comment = commentField.getText();

                if (comment.isEmpty() || datePicker.getValue() == null) {
                    resultLabel.setText("Please fill all fields.");
                    return;
                }

                Review review = new Review(reviewId, rating, comment, datePicker.getValue(), guestId);
                resultLabel.setText(review.getSummary());
                tabPane.getSelectionModel().selectNext();
            } catch (Exception ex) {
                resultLabel.setText("Invalid input.");
            }
        });

        vbox.getChildren().addAll(
                new Label("Review ID:"), reviewIdField,
                new Label("Guest ID:"), guestIdField,
                new Label("Rating (1-5):"), ratingField,
                new Label("Comment:"), commentField,
                new Label("Date:"), datePicker,
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
