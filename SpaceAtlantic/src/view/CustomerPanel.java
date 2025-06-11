package view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CustomerPanel {

    private VBox root;

    // Example controls for booking form
    private TextField customerNameField = new TextField();
    private TextField passengerCountField = new TextField();
    private ComboBox<String> genderBox = new ComboBox<>();
    private ComboBox<String> travelTypeBox = new ComboBox<>();
    private Button bookBtn = new Button("Book Trip");
    private Label messageLabel = new Label();

    public CustomerPanel() {
        root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getChildren().add(new Label("Customer Panel İçeriği Buraya Gelecek"));
        Label header = new Label("Customer Panel - Booking Form");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);

        form.add(new Label("Full Name:"), 0, 0);
        form.add(customerNameField, 1, 0);

        form.add(new Label("Passenger Count:"), 0, 1);
        form.add(passengerCountField, 1, 1);

        form.add(new Label("Gender:"), 0, 2);
        genderBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        form.add(genderBox, 1, 2);

        form.add(new Label("Travel Type:"), 0, 3);
        travelTypeBox.setItems(FXCollections.observableArrayList("Tourism", "Cargo"));
        form.add(travelTypeBox, 1, 3);

        root.getChildren().addAll(header, form, bookBtn, messageLabel);

        // Booking button action (example)
        bookBtn.setOnAction(e -> {
            try {
                String name = customerNameField.getText();
                int passengerCount = Integer.parseInt(passengerCountField.getText());
                String gender = genderBox.getValue();
                String travelType = travelTypeBox.getValue();

                if (name.isEmpty() || gender == null || travelType == null) {
                    messageLabel.setText("Lütfen tüm alanları doldurun.");
                    return;
                }

                // Burada rezervasyon işlemi yapılabilir (controller çağrısı vb)
                messageLabel.setText("Rezervasyon başarıyla alındı! (Simüle edildi)");

            } catch (NumberFormatException ex) {
                messageLabel.setText("Geçerli bir yolcu sayısı girin.");
            } catch (Exception ex) {
                messageLabel.setText("Hata oluştu: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }


    public VBox getRoot() {
        return root;
    }
    public VBox getContent() {
        return root;
    }
}
