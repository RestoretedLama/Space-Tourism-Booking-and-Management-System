package view;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.*;
import view.tabs.*;

public class MainView {
    private TabPane tabPane;
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

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().addAll(
                new Tab("Guest Registration", GuestTabBuilder.build(controller, tabPane)),
                new Tab("Travel", TravelTabBuilder.build(controller, tabPane)),
                new Tab("Booking", BookingTabBuilder.build(tabPane)),
                new Tab("Payment", PaymentTabBuilder.build(tabPane)),
                new Tab("Review", ReviewTabBuilder.build(tabPane)),
                new Tab("Mission", MissionTabBuilder.build(tabPane)),
                new Tab("Astronaut", AstronautTabBuilder.build(tabPane)),
                new Tab("Rocket & Destination", RocketAndDestinationTabBuilder.build())
        );

        root.setCenter(tabPane);
    }

    public Pane getRoot() {
        return root;
    }

}
