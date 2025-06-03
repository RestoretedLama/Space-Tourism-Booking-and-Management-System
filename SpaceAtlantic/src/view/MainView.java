package view;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.*;

public class MainView {

    private VBox root;
    private ComboBox<Planet> fromPlanetBox = new ComboBox<>();
    private ComboBox<Planet> toPlanetBox = new ComboBox<>();
    private ComboBox<CargoType> cargoTypeBox = new ComboBox<>();
    private TextField cargoWeightField = new TextField();

    private ComboBox<String> genderBox = new ComboBox<>();
    private CheckBox luggageCheck = new CheckBox("Bringing luggage?");
    private TextField luggageWeightField = new TextField();
    private ComboBox<String> baseToBox = new ComboBox<>();

    private ToggleGroup actionGroup = new ToggleGroup();
    private RadioButton cargoOption = new RadioButton("Send Cargo");
    private RadioButton travelOption = new RadioButton("Travel");

    private Label resultLabel = new Label();
    private MainController controller = new MainController();

    public MainView() {
        root = new VBox(10);
        root.setPadding(new Insets(20));

        fromPlanetBox.getItems().addAll(Planet.values());
        toPlanetBox.getItems().addAll(Planet.values());
        cargoTypeBox.getItems().addAll(CargoType.values());
        genderBox.getItems().addAll("Male", "Female", "Other");
        baseToBox.getItems().addAll("Base Alpha", "Base Beta");

        cargoOption.setToggleGroup(actionGroup);
        travelOption.setToggleGroup(actionGroup);
        cargoOption.setSelected(true);

        VBox cargoSection = new VBox(5, new Label("From Planet:"), fromPlanetBox,
                new Label("To Planet:"), toPlanetBox,
                new Label("Cargo Type:"), cargoTypeBox,
                new Label("Cargo Weight (kg):"), cargoWeightField);

        VBox travelSection = new VBox(5, new Label("From Planet:"), fromPlanetBox,
                new Label("To Planet:"), toPlanetBox,
                new Label("Target Base on Arrival:"), baseToBox,
                new Label("Gender:"), genderBox,
                luggageCheck, new Label("Luggage Weight (kg):"), luggageWeightField);
        travelSection.setDisable(true);

        actionGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            boolean isCargo = cargoOption.isSelected();
            cargoSection.setDisable(!isCargo);
            travelSection.setDisable(isCargo);
        });

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> handleSubmit());

        root.getChildren().addAll(
                new HBox(10, cargoOption, travelOption),
                cargoSection,
                travelSection,
                submitButton,
                resultLabel
        );
    }

    private void handleSubmit() {
        Planet from = fromPlanetBox.getValue();
        Planet to = toPlanetBox.getValue();
        if (from == null || to == null || from == to) {
            resultLabel.setText("Source and destination planets must be different!");
            return;
        }

        if (cargoOption.isSelected()) {
            CargoType type = cargoTypeBox.getValue();
            double weight;
            try {
                weight = Double.parseDouble(cargoWeightField.getText());
            } catch (Exception e) {
                resultLabel.setText("Invalid cargo weight!");
                return;
            }
            Cargo cargo = new Cargo(from, to, type, weight);
            resultLabel.setText(controller.processCargo(cargo));
        } else {
            String gender = genderBox.getValue();
            String baseTo = baseToBox.getValue();
            boolean hasLuggage = luggageCheck.isSelected();
            double luggageWeight = 0;
            if (hasLuggage) {
                try {
                    luggageWeight = Double.parseDouble(luggageWeightField.getText());
                } catch (Exception e) {
                    resultLabel.setText("Invalid luggage weight!");
                    return;
                }
            }
            Travel travel = new Travel(from, to, baseTo, hasLuggage, luggageWeight, gender);
            resultLabel.setText(controller.processTravel(travel));
        }
    }

    public VBox getRoot() {
        return root;
    }
}
