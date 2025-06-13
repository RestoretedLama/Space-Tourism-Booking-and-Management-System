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

import java.util.List;

public class MainView {
    private TabPane tabPane;
    private BorderPane root;
    private MainController controller = new MainController();

    public MainView() {
        root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f0f4f8;");
        root.setPrefHeight(700);
        root.setPrefWidth(900);
        root.setMaxHeight(700);
        root.setMaxWidth(900);

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
        tabPane.setPrefHeight(600);
        tabPane.setPrefWidth(800);

        // Controller'dan gerekli listeleri al
        List<Rocket> rockets = controller.getRockets();
        List<Destination> destinations = controller.getDestinations();
        List<LaunchSite> launchSites = controller.getLaunchSites();
        List<Astronaut> supervisors = controller.getSupervisors();
        List<Astronaut> crews = controller.getCrews();

        tabPane.getTabs().addAll(
                new Tab("Guest Registration", GuestTabBuilder.build(controller, tabPane)),
                new Tab("Travel", TravelTabBuilder.build(controller, tabPane)),
                new Tab("Booking", BookingTabBuilder.build(tabPane)),
                new Tab("Payment", PaymentTabBuilder.build(tabPane)),
                new Tab("Review", ReviewTabBuilder.build(tabPane)),
                new Tab("Mission", MissionTabBuilder.build(rockets, destinations, launchSites, supervisors, crews)),
                new Tab("Astronaut", AstronautTabBuilder.build(tabPane)),
                new Tab("Rocket & Destination", RocketAndDestinationTabBuilder.build())
        );

        // Create ScrollPane for the TabPane
        ScrollPane tabScrollPane = new ScrollPane(tabPane);
        tabScrollPane.setFitToWidth(true);
        tabScrollPane.setFitToHeight(true);
        tabScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        tabScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        tabScrollPane.setStyle("-fx-background-color: transparent;");
        tabScrollPane.setPrefViewportHeight(600);
        tabScrollPane.setPrefViewportWidth(800);

        root.setCenter(tabScrollPane);
    }

    public Pane getRoot() {
        return root;
    }
}
