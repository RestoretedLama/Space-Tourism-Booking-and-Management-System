package controller;

import javafx.scene.control.TextArea;
import model.Cargo;
import model.Travel;

public class MainController {

    public static void handleSubmission(
            boolean isCargo,
            String from,
            String to,
            String cargoType,
            String cargoWeight,
            boolean hasLuggage,
            String luggageWeight,
            String gender,
            TextArea resultArea
    ) {
        if (from == null || to == null || gender == null) {
            resultArea.setText("Lütfen tüm alanları doldurun.");
            return;
        }

        if (isCargo) {
            if (cargoType == null || cargoWeight == null || cargoWeight.isEmpty()) {
                resultArea.setText("Lütfen kargo türünü ve ağırlığını girin.");
                return;
            }
            Cargo cargo = new Cargo(from, to, cargoType, Double.parseDouble(cargoWeight), gender);
            resultArea.setText(cargo.toString());
        } else {
            double luggage = 0;
            if (hasLuggage) {
                if (luggageWeight == null || luggageWeight.isEmpty()) {
                    resultArea.setText("Lütfen bavul ağırlığını girin.");
                    return;
                }
                luggage = Double.parseDouble(luggageWeight);
            }
            Travel travel = new Travel(from, to, hasLuggage, luggage, gender);
            resultArea.setText(travel.toString());
        }
    }
}
