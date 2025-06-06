package view.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Review;

public class ReviewTabBuilder {
    public static VBox build() {
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
        return vbox;
    }
}