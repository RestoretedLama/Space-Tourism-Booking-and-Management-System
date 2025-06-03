package view;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Cargo;
import model.Travel;

public class MainView {

    private VBox root;
    private RadioButton cargoRadio, travelRadio;
    private ComboBox<String> fromPlanet, toPlanet;
    private ComboBox<String> cargoType;
    private TextField cargoWeight, luggageWeight;
    private CheckBox hasLuggage;
    private ComboBox<String> genderBox;
    private TextArea resultArea;

    public MainView() {
        buildUI();
    }

    public VBox getRoot() {
        return root;
    }

    private void buildUI() {
        ToggleGroup actionGroup = new ToggleGroup();
        cargoRadio = new RadioButton("Kargo Gönder");
        travelRadio = new RadioButton("Seyahat Et");
        cargoRadio.setToggleGroup(actionGroup);
        travelRadio.setToggleGroup(actionGroup);
        HBox actionBox = new HBox(10, cargoRadio, travelRadio);

        fromPlanet = new ComboBox<>();
        toPlanet = new ComboBox<>();
        fromPlanet.getItems().addAll("Dünya", "Mars", "Venüs", "Jüpiter");
        toPlanet.getItems().addAll("Dünya", "Mars", "Venüs", "Jüpiter");
        HBox planetBox = new HBox(10, new Label("Nereden:"), fromPlanet, new Label("Nereye:"), toPlanet);

        cargoType = new ComboBox<>();
        cargoType.getItems().addAll("Gıda", "Elektronik", "Ekipman", "İlaç");
        cargoWeight = new TextField();
        cargoWeight.setPromptText("Kargo Ağırlığı (kg)");
        VBox cargoBox = new VBox(10, new Label("Kargo Türü:"), cargoType, cargoWeight);
        cargoBox.setPadding(new Insets(10));
        cargoBox.setVisible(false);

        hasLuggage = new CheckBox("Bavul getiriyor musunuz?");
        luggageWeight = new TextField();
        luggageWeight.setPromptText("Bavul Ağırlığı (kg)");
        luggageWeight.setDisable(true);
        VBox travelBox = new VBox(10, hasLuggage, luggageWeight);
        travelBox.setPadding(new Insets(10));
        travelBox.setVisible(false);

        genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Kadın", "Erkek", "Diğer");

        Button submitButton = new Button("Gönder");
        resultArea = new TextArea();
        resultArea.setEditable(false);

        // Bağlantılar ve olaylar
        hasLuggage.setOnAction(e -> luggageWeight.setDisable(!hasLuggage.isSelected()));
        cargoRadio.setOnAction(e -> {
            cargoBox.setVisible(true);
            travelBox.setVisible(false);
        });
        travelRadio.setOnAction(e -> {
            cargoBox.setVisible(false);
            travelBox.setVisible(true);
        });

        submitButton.setOnAction(e -> {
            MainController.handleSubmission(
                    cargoRadio.isSelected(),
                    fromPlanet.getValue(),
                    toPlanet.getValue(),
                    cargoType.getValue(),
                    cargoWeight.getText(),
                    hasLuggage.isSelected(),
                    luggageWeight.getText(),
                    genderBox.getValue(),
                    resultArea
            );
        });

        root = new VBox(15, actionBox, planetBox, cargoBox, travelBox,
                new Label("Cinsiyet Seçiniz:"), genderBox, submitButton, resultArea);
        root.setPadding(new Insets(20));
    }
}
