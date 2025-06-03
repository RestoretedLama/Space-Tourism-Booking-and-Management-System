import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import view.MainView;

public class SpaceTourismApp extends Application {

    public void start(Stage primaryStage) {
        MainView view = new MainView();
        Scene scene = new Scene(view.getRoot(), 1000, 1000);
        primaryStage.setTitle("Uzay Turizmi Uygulaması");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
