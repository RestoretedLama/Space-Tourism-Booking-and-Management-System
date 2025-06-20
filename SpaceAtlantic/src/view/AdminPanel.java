package view;

import controller.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.*;

import java.util.List;

public class AdminPanel {

    private VBox root;
    private ScrollPane scrollPane;
    private AdminController adminController;

    // Form alanları
    private ComboBox<Rocket> rocketBox = new ComboBox<>();
    private ComboBox<Destination> destinationBox = new ComboBox<>();
    private ComboBox<LaunchSite> launchSiteBox = new ComboBox<>();
    private ComboBox<Astronaut> supervisorBox = new ComboBox<>();
    private ComboBox<Astronaut> crewBox = new ComboBox<>();
    private TextField missionNameField = new TextField();
    private TextField travelDaysField = new TextField();
    private TextField amountField = new TextField();
    private DatePicker launchDatePicker = new DatePicker();
    private DatePicker returnDatePicker = new DatePicker();

    // Görev tablosu
    private TableView<Mission> missionTable = new TableView<>();

    private Button createMissionBtn = new Button("Create Mission");
    private Button deleteMissionBtn = new Button("Delete Selected Mission");
    private Label statusLabel = new Label();

    public AdminPanel() {
        root = new VBox(20);
        root.setPadding(new Insets(20));

        adminController = new AdminController();
        
        // Automatically update database schema if needed
        adminController.addMissingColumns();

        setupForm();
        setupMissionTable();

        // Create form section
        VBox formSection = new VBox(10);
        formSection.setPadding(new Insets(15));
        formSection.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        Label formTitle = new Label("Create New Mission");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        formSection.getChildren().addAll(
                formTitle,
                new Label("Rocket:"), rocketBox,
                new Label("Destination:"), destinationBox,
                new Label("Launch Site:"), launchSiteBox,
                new Label("Supervisor Astronaut:"), supervisorBox,
                new Label("Crew Astronaut:"), crewBox,
                new Label("Mission Name:"), missionNameField,
                new Label("Travel Days:"), travelDaysField,
                new Label("Amount:"), amountField,
                new Label("Launch Date:"), launchDatePicker,
                new Label("Return Date:"), returnDatePicker,
                createMissionBtn,
                deleteMissionBtn,
                statusLabel
        );

        // Create table section
        VBox tableSection = new VBox(10);
        tableSection.setPadding(new Insets(15));
        tableSection.setStyle("-fx-background-color: #ffffff; -fx-border-color: #dee2e6; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        Label tableTitle = new Label("All Missions");
        tableTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        tableSection.getChildren().addAll(tableTitle, missionTable);

        root.getChildren().addAll(formSection, tableSection);

        // Create ScrollPane and wrap the root content
        scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPrefViewportHeight(700);
        scrollPane.setPrefViewportWidth(1000);

        loadComboBoxes();
        refreshMissionTable();

        createMissionBtn.setOnAction(e -> createMission());
        deleteMissionBtn.setOnAction(e -> deleteSelectedMission());
    }

    public VBox getRoot() {
        return root;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public ScrollPane getRootAsScrollPane() {
        return scrollPane;
    }

    private void setupForm() {
        missionNameField.setPromptText("Enter mission name (e.g. Apollo 11)");
        travelDaysField.setPromptText("Enter travel days (e.g. 10)");
        amountField.setPromptText("Enter amount (e.g. 5000)");
        launchDatePicker.setPromptText("Select launch date");
        returnDatePicker.setPromptText("Select return date");
    }

    private void setupMissionTable() {
        TableColumn<Mission, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);
        idCol.setMinWidth(50);

        TableColumn<Mission, String> nameCol = new TableColumn<>("Mission Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(180);
        nameCol.setMinWidth(150);

        TableColumn<Mission, String> rocketCol = new TableColumn<>("Rocket");
        rocketCol.setCellValueFactory(new PropertyValueFactory<>("rocketName"));
        rocketCol.setPrefWidth(140);
        rocketCol.setMinWidth(120);

        TableColumn<Mission, String> destCol = new TableColumn<>("Destination");
        destCol.setCellValueFactory(new PropertyValueFactory<>("destinationName"));
        destCol.setPrefWidth(140);
        destCol.setMinWidth(120);

        TableColumn<Mission, String> launchCol = new TableColumn<>("Launch Site");
        launchCol.setCellValueFactory(new PropertyValueFactory<>("launchSiteName"));
        launchCol.setPrefWidth(140);
        launchCol.setMinWidth(120);

        TableColumn<Mission, Integer> daysCol = new TableColumn<>("Days");
        daysCol.setCellValueFactory(new PropertyValueFactory<>("travelDays"));
        daysCol.setPrefWidth(80);
        daysCol.setMinWidth(60);

        TableColumn<Mission, String> supCol = new TableColumn<>("Supervisor");
        supCol.setCellValueFactory(new PropertyValueFactory<>("supervisorName"));
        supCol.setPrefWidth(140);
        supCol.setMinWidth(120);

        TableColumn<Mission, String> crewCol = new TableColumn<>("Crew");
        crewCol.setCellValueFactory(new PropertyValueFactory<>("crewName"));
        crewCol.setPrefWidth(140);
        crewCol.setMinWidth(120);

        TableColumn<Mission, Double> amountCol = new TableColumn<>("Amount ($)");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.setPrefWidth(100);
        amountCol.setMinWidth(80);

        missionTable.getColumns().addAll(idCol, nameCol, rocketCol, destCol, launchCol, daysCol, supCol, crewCol, amountCol);
        missionTable.setPrefHeight(400);
        missionTable.setMinHeight(300);
        missionTable.setMaxHeight(500);
        missionTable.setStyle("-fx-font-size: 12px;");
        missionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void loadComboBoxes() {
        // Temizle
        rocketBox.getItems().clear();
        destinationBox.getItems().clear();
        launchSiteBox.getItems().clear();
        supervisorBox.getItems().clear();
        crewBox.getItems().clear();

        // Rocket, Destination, LaunchSite combo boxlarını AdminController'dan doldur
        adminController.initComboBoxes(destinationBox, launchSiteBox, supervisorBox, crewBox);

        // RocketBox için özel listeyi çekebiliriz, burada örnek olarak sadece comboBox'a items koyuyoruz
        // Eğer istersek, destination seçimine göre rocketBox da filtrelenebilir
        // Şimdilik tüm roketleri listele

        // Örneğin rocketBox'a tüm roketleri çekmek için:
        // Ancak AdminController'da böyle bir metod yoksa, eklenmeli
        // Bu örnekte, örnek amaçlı rocketBox'a kendi listemizi ekleyelim
        // Ya da aşağıdaki gibi loadRockets metodu çağrılabilir

        // Şimdilik rocketBox items boş, destination seçildiğinde rocketBox yükleniyor:
        destinationBox.setOnAction(e -> {
            Destination selected = destinationBox.getSelectionModel().getSelectedItem();
            if (selected != null) {
                adminController.loadRockets(selected, rocketBox);
            }
        });
    }

    private void refreshMissionTable() {
        List<Mission> missions = adminController.getAllMissions();
        ObservableList<Mission> data = FXCollections.observableArrayList(missions);
        missionTable.setItems(data);
    }

    private void createMission() {
        Rocket rocket = rocketBox.getSelectionModel().getSelectedItem();
        Destination dest = destinationBox.getSelectionModel().getSelectedItem();
        LaunchSite site = launchSiteBox.getSelectionModel().getSelectedItem();
        Astronaut supervisor = supervisorBox.getSelectionModel().getSelectedItem();
        Astronaut crew = crewBox.getSelectionModel().getSelectedItem();
        String missionName = missionNameField.getText();
        String travelDaysStr = travelDaysField.getText();
        String amountStr = amountField.getText();
        java.time.LocalDate launchDate = launchDatePicker.getValue();
        java.time.LocalDate returnDate = returnDatePicker.getValue();

        if (rocket == null || dest == null || site == null || supervisor == null || crew == null || travelDaysStr.isEmpty() || amountStr.isEmpty() || launchDate == null || returnDate == null) {
            statusLabel.setText("Please fill all required fields.");
            return;
        }

        int days;
        double amount;
        try {
            days = Integer.parseInt(travelDaysStr);
            if (days <= 0) {
                statusLabel.setText("Travel days must be positive.");
                return;
            }
            amount = Double.parseDouble(amountStr);
            if (amount < 0) {
                statusLabel.setText("Amount must be non-negative.");
                return;
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid travel days or amount.");
            return;
        }
        // Mission oluştur
        adminController.createMission(rocket, dest, site, supervisor, crew, missionName, days, amount, launchDate, returnDate);
        statusLabel.setText("Mission created successfully.");
        // Formu temizle
        missionNameField.clear();
        travelDaysField.clear();
        amountField.clear();
        launchDatePicker.setValue(null);
        returnDatePicker.setValue(null);
        // Listeyi yenile
        refreshMissionTable();
    }

    private void deleteSelectedMission() {
        Mission selectedMission = missionTable.getSelectionModel().getSelectedItem();
        
        if (selectedMission == null) {
            statusLabel.setText("Please select a mission to delete.");
            return;
        }
        
        // Confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Mission");
        alert.setHeaderText("Confirm Mission Deletion");
        alert.setContentText("Are you sure you want to delete mission #" + selectedMission.getId() + 
                           " to " + selectedMission.getDestinationName() + "?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = adminController.deleteMission(selectedMission.getId());
                
                if (success) {
                    statusLabel.setText("Mission deleted successfully.");
                    refreshMissionTable();
                } else {
                    statusLabel.setText("Cannot delete mission. It may have active bookings.");
                }
            }
        });
    }
}
