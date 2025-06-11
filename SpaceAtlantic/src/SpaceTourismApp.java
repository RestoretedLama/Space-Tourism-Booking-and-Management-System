import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import view.AdminPanel;
import view.CustomerPanel;

public class SpaceTourismApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();

        AdminPanel adminPanel = new AdminPanel();
        CustomerPanel customerPanel = new CustomerPanel();

        Tab adminTab = new Tab("Admin Panel");
        adminTab.setContent(adminPanel.getRoot());
        adminTab.setClosable(false);

        Tab customerTab = new Tab("Customer Panel");
        customerTab.setContent(customerPanel.getRoot());
        customerTab.setClosable(false);

        tabPane.getTabs().addAll(adminTab, customerTab);

        Scene scene = new Scene(tabPane, 1000, 700);

        primaryStage.setTitle("Uzay Turizmi Uygulaması");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
