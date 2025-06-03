import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SpaceTourismApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Uzay Turizmi Uygulaması");

        // Ana seçenek: Kargo mu, Seyahat mi?
        ToggleGroup actionGroup = new ToggleGroup();
        RadioButton cargoRadio = new RadioButton("Kargo Gönder");
        RadioButton travelRadio = new RadioButton("Seyahat Et");
        cargoRadio.setToggleGroup(actionGroup);
        travelRadio.setToggleGroup(actionGroup);

        HBox actionBox = new HBox(10, cargoRadio, travelRadio);

        // Gezegenden - Gezegene
        ComboBox<String> fromPlanet = new ComboBox<>();
        fromPlanet.getItems().addAll("Dünya", "Mars", "Venüs", "Jüpiter");

        ComboBox<String> toPlanet = new ComboBox<>();
        toPlanet.getItems().addAll("Dünya", "Mars", "Venüs", "Jüpiter");

        HBox planetBox = new HBox(10, new Label("Nereden:"), fromPlanet, new Label("Nereye:"), toPlanet);

        // Kargo bileşenleri
        ComboBox<String> cargoType = new ComboBox<>();
        cargoType.getItems().addAll("Gıda", "Elektronik", "Ekipman", "İlaç");

        TextField cargoWeight = new TextField();
        cargoWeight.setPromptText("Ağırlık (kg)");

        VBox cargoBox = new VBox(10, new Label("Kargo Türü:"), cargoType, new Label("Kargo Ağırlığı:"), cargoWeight);
        cargoBox.setPadding(new Insets(10));
        cargoBox.setVisible(false);

        // Seyahat bileşenleri
        CheckBox hasLuggage = new CheckBox("Bavul getiriyor musunuz?");
        TextField luggageWeight = new TextField();
        luggageWeight.setPromptText("Bavul Ağırlığı (kg)");
        luggageWeight.setDisable(true);

        hasLuggage.setOnAction(e -> luggageWeight.setDisable(!hasLuggage.isSelected()));

        VBox travelBox = new VBox(10, hasLuggage, luggageWeight);
        travelBox.setPadding(new Insets(10));
        travelBox.setVisible(false);

        // Cinsiyet seçimi
        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Kadın", "Erkek", "Diğer");

        // Gönder butonu
        Button submitButton = new Button("Gönder");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefRowCount(5);

        // Seçime göre bileşenleri göster/gizle
        cargoRadio.setOnAction(e -> {
            cargoBox.setVisible(true);
            travelBox.setVisible(false);
        });

        travelRadio.setOnAction(e -> {
            travelBox.setVisible(true);
            cargoBox.setVisible(false);
        });

        // Gönder butonu işlemi
        submitButton.setOnAction(e -> {
            String from = fromPlanet.getValue();
            String to = toPlanet.getValue();
            String gender = genderBox.getValue();

            if (from == null || to == null || gender == null || actionGroup.getSelectedToggle() == null) {
                resultArea.setText("Lütfen tüm gerekli alanları doldurun.");
                return;
            }

            if (cargoRadio.isSelected()) {
                String type = cargoType.getValue();
                String weightStr = cargoWeight.getText();
                if (type == null || weightStr.isEmpty()) {
                    resultArea.setText("Lütfen kargo türünü ve ağırlığını girin.");
                    return;
                }
                resultArea.setText("Kargo Gönderimi:\n" +
                        "Gönderen Gezegeni: " + from + "\n" +
                        "Hedef Gezegeni: " + to + "\n" +
                        "Kargo Türü: " + type + "\n" +
                        "Ağırlık: " + weightStr + " kg\n" +
                        "Cinsiyet: " + gender);
            } else {
                String luggageInfo = hasLuggage.isSelected() ? "Evet, " + luggageWeight.getText() + " kg" : "Hayır";
                if (hasLuggage.isSelected() && luggageWeight.getText().isEmpty()) {
                    resultArea.setText("Lütfen bavul ağırlığını girin.");
                    return;
                }
                resultArea.setText("Seyahat Bilgisi:\n" +
                        "Kalkış Gezegeni: " + from + "\n" +
                        "Varış Gezegeni: " + to + "\n" +
                        "Bavul: " + luggageInfo + "\n" +
                        "Cinsiyet: " + gender);
            }
        });

        VBox root = new VBox(15, actionBox, planetBox, cargoBox, travelBox,
                new Label("Cinsiyet Seçiniz:"), genderBox, submitButton, resultArea);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
