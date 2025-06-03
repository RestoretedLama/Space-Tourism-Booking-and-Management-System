package view;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        Tab cargoTab = new Tab("Send Cargo", buildCargoTab());

        Tab travelTab = new Tab("Travel", buildTravelTab());

        tabPane.getTabs().addAll(cargoTab, travelTab);

        root.setCenter(tabPane);
    }

    public Pane getRoot() {
        return root;
    }

    private Pane buildCargoTab() {

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8;");
        vbox.setAlignment(Pos.TOP_LEFT);

        ComboBox<Planet> fromPlanetBox = new ComboBox<>();
        ComboBox<Planet> toPlanetBox = new ComboBox<>();
        fromPlanetBox.getItems().addAll(Planet.values());
        toPlanetBox.getItems().addAll(Planet.values());

        ComboBox<CargoType> cargoTypeBox = new ComboBox<>();
        cargoTypeBox.getItems().addAll(CargoType.values());

        TextField cargoWeightField = new TextField();

        Label resultLabel = new Label();
        resultLabel.setWrapText(true);

        Image cargoImg = new Image(getClass().getResourceAsStream("/resources/icons/icons8-submit-48.png"));
        ImageView cargoIcon = new ImageView(cargoImg);
        cargoIcon.setFitWidth(16);
        cargoIcon.setFitHeight(16);

        Button submitButton = new Button("Submit Cargo", cargoIcon);
        submitButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        submitButton.setGraphicTextGap(10);

        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;"));

        submitButton.setOnAction(e -> {
            Planet from = fromPlanetBox.getValue();
            Planet to = toPlanetBox.getValue();
            CargoType type = cargoTypeBox.getValue();

            double weight;
            try {
                weight = Double.parseDouble(cargoWeightField.getText());
            } catch (Exception ex) {
                resultLabel.setText("Invalid cargo weight.");
                return;
            }

            if (from == null || to == null || from == to) {
                resultLabel.setText("Source and destination planets must be different!");
                return;
            }

            Cargo cargo = new Cargo(from, to, type, weight);
            resultLabel.setText(controller.processCargo(cargo));
        });

        vbox.getChildren().addAll(
                new Label("From Planet:"), fromPlanetBox,
                new Label("To Planet:"), toPlanetBox,
                new Label("Cargo Type:"), cargoTypeBox,
                new Label("Weight (kg):"), cargoWeightField,
                submitButton, resultLabel
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

        Image travelImg = new Image(getClass().getResourceAsStream("/resources/icons/icons8-submit-48.png"));
        ImageView travelIcon = new ImageView(travelImg);
        travelIcon.setFitWidth(16);
        travelIcon.setFitHeight(16);

        Button submitButton = new Button("Submit Travel", travelIcon);
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
}
