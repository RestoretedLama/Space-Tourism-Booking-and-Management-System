package view;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.*;

public class MainView {

    private BorderPane root;
    private MainController controller = new MainController();

    public MainView() {
        root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f0f4f8;");

        Label title = new Label("Space Tourism System");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setStyle("-fx-text-fill: #2c3e50;");
        title.setAlignment(Pos.CENTER);

        VBox topBox = new VBox(title);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10, 0, 20, 0));
        root.setTop(topBox);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab guestTab = new Tab("Guest Registration", buildGuestTab());
        Tab travelTab = new Tab("Travel", buildTravelTab());

        tabPane.getTabs().addAll(guestTab, travelTab);
        root.setCenter(tabPane);
    }

    public Pane getRoot() {
        return root;
    }

    private Pane buildGuestTab() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8;");
        vbox.setAlignment(Pos.TOP_LEFT);

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField ageField = new TextField();
        TextField nationalityField = new TextField();
        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Male", "Female", "Other");
        TextField contactField = new TextField();

        Label resultLabel = new Label();
        resultLabel.setWrapText(true);

        Button submitButton = new Button("Register Guest");
        submitButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white;");

        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #8e44ad; -fx-text-fill: white;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white;"));

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

    private Pane buildTravelTab() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8;");
        vbox.setAlignment(Pos.TOP_LEFT);

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
        resultLabel.setWrapText(true);

        Button submitButton = new Button("Submit Travel");
        submitButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        submitButton.setGraphicTextGap(10);

        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;"));

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

        return vbox;
    }
    private Pane buildBookingTab() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8;");
        vbox.setAlignment(Pos.TOP_LEFT);

        TextField bookingIdField = new TextField();
        TextField guestIdField = new TextField();
        TextField missionIdField = new TextField();
        TextField seatNumberField = new TextField();
        DatePicker launchDatePicker = new DatePicker();
        DatePicker returnDatePicker = new DatePicker();
        ComboBox<String> statusBox = new ComboBox<>();
        statusBox.getItems().addAll("Planned", "Completed", "Cancelled");

        Label resultLabel = new Label();
        resultLabel.setWrapText(true);

        Button submitButton = new Button("Create Booking");
        submitButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white;");

        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #d35400; -fx-text-fill: white;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white;"));

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
