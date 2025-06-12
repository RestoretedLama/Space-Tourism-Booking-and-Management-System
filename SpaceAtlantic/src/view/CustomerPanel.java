package view;

import controller.CustomerController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.*;

import java.time.LocalDate;
import java.util.List;

public class CustomerPanel {

    private VBox root;
    private CustomerController controller;
    
    // Step navigation
    private int currentStep = 0;
    private Label stepLabel;
    private Button nextButton;
    private Button previousButton;
    
    // Step containers
    private VBox destinationStep;
    private VBox missionStep;
    private VBox guestStep;
    private VBox paymentStep;
    private VBox ticketStep;
    private VBox reviewStep;
    
    // Data storage
    private Destination selectedDestination;
    private Mission selectedMission;
    private int guestId;
    private int bookingId;
    
    // Controls
    private ComboBox<Destination> destinationComboBox;
    private ComboBox<Mission> missionComboBox;
    private TextField nameField;
    private TextField ageField;
    private ComboBox<String> nationalityComboBox;
    private ComboBox<String> genderComboBox;
    private TextField contactField;
    private ComboBox<String> paymentMethodComboBox;
    private TextField amountField;
    
    // Display labels
    private Label ticketDetailsLabel;
    private Label missionDetailsLabel;
    
    public CustomerPanel() {
        controller = new CustomerController();
        initializeUI();
        setupStepNavigation();
        loadInitialData();
    }
    
    private void initializeUI() {
        root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f8f9fa;");
        
        // Header
        Label header = new Label("Space Tourism Booking System");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setStyle("-fx-text-fill: #2c3e50;");
        header.setAlignment(Pos.CENTER);
        
        // Step indicator
        stepLabel = new Label("Step 1: Select Destination");
        stepLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        stepLabel.setStyle("-fx-text-fill: #3498db;");
        
        // Navigation buttons
        HBox navigationBox = new HBox(10);
        previousButton = new Button("Previous");
        nextButton = new Button("Next");
        previousButton.setDisable(true);
        
        navigationBox.getChildren().addAll(previousButton, nextButton);
        navigationBox.setAlignment(Pos.CENTER);
        
        // Initialize step containers
        initializeDestinationStep();
        initializeMissionStep();
        initializeGuestStep();
        initializePaymentStep();
        initializeTicketStep();
        initializeReviewStep();
        
        root.getChildren().addAll(header, stepLabel, destinationStep, navigationBox);
        
        // Button actions
        nextButton.setOnAction(e -> nextStep());
        previousButton.setOnAction(e -> previousStep());
    }
    
    private void initializeDestinationStep() {
        destinationStep = new VBox(15);
        destinationStep.setPadding(new Insets(20));
        destinationStep.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label title = new Label("Select Your Destination");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        destinationComboBox = new ComboBox<>();
        destinationComboBox.setPromptText("Choose your destination...");
        destinationComboBox.setPrefWidth(300);
        
        destinationComboBox.setOnAction(e -> {
            selectedDestination = destinationComboBox.getValue();
            if (selectedDestination != null) {
                nextButton.setDisable(false);
            }
        });
        
        destinationStep.getChildren().addAll(title, destinationComboBox);
    }
    
    private void initializeMissionStep() {
        missionStep = new VBox(15);
        missionStep.setPadding(new Insets(20));
        missionStep.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label title = new Label("Select Available Mission");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        missionComboBox = new ComboBox<>();
        missionComboBox.setPromptText("Choose your mission...");
        missionComboBox.setPrefWidth(300);
        
        missionDetailsLabel = new Label();
        missionDetailsLabel.setWrapText(true);
        missionDetailsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        
        missionComboBox.setOnAction(e -> {
            selectedMission = missionComboBox.getValue();
            if (selectedMission != null) {
                updateMissionDetails();
                nextButton.setDisable(false);
            }
        });
        
        missionStep.getChildren().addAll(title, missionComboBox, missionDetailsLabel);
    }
    
    private void initializeGuestStep() {
        guestStep = new VBox(15);
        guestStep.setPadding(new Insets(20));
        guestStep.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label title = new Label("Guest Information");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        
        nameField = new TextField();
        ageField = new TextField();
        nationalityComboBox = new ComboBox<>(FXCollections.observableArrayList("TURKISH", "AMERICAN", "GERMAN", "JAPANESE", "INDIAN", "FRENCH", "SPANISH", "OTHER"));
        genderComboBox = new ComboBox<>(FXCollections.observableArrayList("Male", "Female", "Other"));
        contactField = new TextField();
        
        form.add(new Label("Full Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Age:"), 0, 1);
        form.add(ageField, 1, 1);
        form.add(new Label("Nationality:"), 0, 2);
        form.add(nationalityComboBox, 1, 2);
        form.add(new Label("Gender:"), 0, 3);
        form.add(genderComboBox, 1, 3);
        form.add(new Label("Contact Info:"), 0, 4);
        form.add(contactField, 1, 4);
        
        guestStep.getChildren().addAll(title, form);
    }
    
    private void initializePaymentStep() {
        paymentStep = new VBox(15);
        paymentStep.setPadding(new Insets(20));
        paymentStep.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label title = new Label("Payment Information");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        paymentMethodComboBox = new ComboBox<>();
        paymentMethodComboBox.setPrefWidth(300);
        amountField = new TextField();
        amountField.setEditable(false);
        amountField.setDisable(true);
        
        VBox form = new VBox(10);
        form.getChildren().addAll(
            new Label("Payment Method:"), paymentMethodComboBox,
            new Label("Amount:"), amountField
        );
        
        paymentStep.getChildren().addAll(title, form);
    }
    
    private void initializeTicketStep() {
        ticketStep = new VBox(15);
        ticketStep.setPadding(new Insets(20));
        ticketStep.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label title = new Label("Your Ticket");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        ticketDetailsLabel = new Label();
        ticketDetailsLabel.setWrapText(true);
        ticketDetailsLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #e8f4fd; -fx-border-radius: 5;");
        
        ticketStep.getChildren().addAll(title, ticketDetailsLabel);
    }
    
    private void initializeReviewStep() {
        reviewStep = new VBox(15);
        reviewStep.setPadding(new Insets(20));
        reviewStep.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label title = new Label("Review Your Experience");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        ComboBox<Integer> ratingComboBox = new ComboBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        ratingComboBox.setPromptText("Select rating (1-5)");
        
        TextArea commentArea = new TextArea();
        commentArea.setPromptText("Share your experience...");
        commentArea.setPrefRowCount(4);
        
        Button submitReviewButton = new Button("Submit Review");
        submitReviewButton.setOnAction(e -> {
            if (ratingComboBox.getValue() != null) {
                boolean success = controller.addReview(bookingId, ratingComboBox.getValue(), commentArea.getText());
                if (success) {
                    showAlert("Review submitted successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Failed to submit review.", Alert.AlertType.ERROR);
                }
            }
        });
        
        reviewStep.getChildren().addAll(title, new Label("Rating:"), ratingComboBox, 
                                      new Label("Comment:"), commentArea, submitReviewButton);
    }
    
    private void setupStepNavigation() {
        // Step navigation logic will be implemented in nextStep() and previousStep()
    }
    
    private void loadInitialData() {
        // Test database content first
        System.out.println("=== Testing Database Content ===");
        boolean hasDestinations = controller.hasAnyDestinations();
        boolean hasMissions = controller.hasAnyMissions();
        
        if (!hasDestinations) {
            System.err.println("WARNING: No destinations found in database!");
        }
        if (!hasMissions) {
            System.err.println("WARNING: No missions found in database!");
        }
        
        // Load destinations
        List<Destination> destinations = controller.getAvailableDestinations();
        System.out.println("Loaded " + destinations.size() + " destinations");
        destinationComboBox.getItems().addAll(destinations);
        
        // Load payment methods
        List<String> paymentMethods = controller.getPaymentMethods();
        paymentMethodComboBox.getItems().addAll(paymentMethods);
    }
    
    private void nextStep() {
        if (currentStep == 0) {
            // Destination selected, load missions
            if (selectedDestination != null) {
                System.out.println("Loading missions for destination: " + selectedDestination.getPlanet() + " (ID: " + selectedDestination.getId() + ")");
                List<Mission> missions = controller.getAvailableMissionsForDestination(selectedDestination.getId());
                System.out.println("Found " + missions.size() + " missions");
                
                missionComboBox.getItems().clear();
                missionComboBox.getItems().addAll(missions);
                
                if (missions.isEmpty()) {
                    showAlert("No available missions found for this destination!", Alert.AlertType.WARNING);
                    return;
                }
                
                currentStep = 1;
                updateStepDisplay();
            }
        } else if (currentStep == 1) {
            // Mission selected, show guest info form
            if (selectedMission != null) {
                currentStep = 2;
                updateStepDisplay();
            } else {
                showAlert("Please select a mission!", Alert.AlertType.WARNING);
            }
        } else if (currentStep == 2) {
            // Guest info validated, process booking
            if (validateGuestInfo()) {
                if (processBooking()) {
                    currentStep = 3;
                    updateStepDisplay();
                }
            }
        } else if (currentStep == 3) {
            // Payment processed, show ticket
            if (processPayment()) {
                currentStep = 4;
                updateStepDisplay();
            }
        } else if (currentStep == 4) {
            // Show review step
            currentStep = 5;
            updateStepDisplay();
        }
    }
    
    private void previousStep() {
        if (currentStep > 0) {
            currentStep--;
            updateStepDisplay();
        }
    }
    
    private void updateStepDisplay() {
        // Clear all steps
        root.getChildren().removeAll(missionStep, guestStep, paymentStep, ticketStep, reviewStep);
        
        // Update step label
        String[] stepNames = {
            "Step 1: Select Destination",
            "Step 2: Select Mission", 
            "Step 3: Guest Information",
            "Step 4: Payment",
            "Step 5: Your Ticket",
            "Step 6: Review"
        };
        stepLabel.setText(stepNames[currentStep]);
        
        // Show current step
        VBox currentStepContainer = null;
        switch (currentStep) {
            case 0: currentStepContainer = destinationStep; break;
            case 1: currentStepContainer = missionStep; break;
            case 2: currentStepContainer = guestStep; break;
            case 3: currentStepContainer = paymentStep; break;
            case 4: currentStepContainer = ticketStep; break;
            case 5: currentStepContainer = reviewStep; break;
        }
        
        if (currentStepContainer != null) {
            root.getChildren().add(2, currentStepContainer);
        }
        
        // Update button states
        previousButton.setDisable(currentStep == 0);
        nextButton.setDisable(false);
        
        if (currentStep == 5) {
            nextButton.setText("Finish");
            nextButton.setOnAction(e -> showAlert("Thank you for using Space Tourism!", Alert.AlertType.INFORMATION));
        } else {
            nextButton.setText("Next");
            nextButton.setOnAction(e -> nextStep());
        }
    }
    
    private boolean validateGuestInfo() {
        if (nameField.getText().isEmpty() || ageField.getText().isEmpty() || 
            nationalityComboBox.getValue() == null || genderComboBox.getValue() == null ||
            contactField.getText().isEmpty()) {
            showAlert("Please fill all fields!", Alert.AlertType.WARNING);
            return false;
        }
        try {
            int age = Integer.parseInt(ageField.getText());
            if (age < 18 || age > 80) {
                showAlert("Age must be between 18 and 80!", Alert.AlertType.WARNING);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid age!", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }
    
    private boolean processBooking() {
        try {
            // Register guest
            guestId = controller.registerGuest(
                nameField.getText(),
                Integer.parseInt(ageField.getText()),
                nationalityComboBox.getValue(),
                genderComboBox.getValue(),
                contactField.getText()
            );
            if (guestId == -1) {
                showAlert("Failed to register guest!", Alert.AlertType.ERROR);
                return false;
            }
            // Create booking
            int availableSeats = controller.getAvailableSeatsForMission(selectedMission.getId());
            if (availableSeats <= 0) {
                showAlert("No available seats for this mission!", Alert.AlertType.ERROR);
                return false;
            }
            bookingId = controller.createBooking(
                guestId,
                selectedMission.getId(),
                1, // seat number
                null, // launchDate kaldırıldı
                null  // returnDate kaldırıldı
            );
            if (bookingId == -1) {
                showAlert("Failed to create booking!", Alert.AlertType.ERROR);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error processing booking: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
    
    private boolean processPayment() {
        if (paymentMethodComboBox.getValue() == null || amountField.getText().isEmpty()) {
            showAlert("Please select payment method and enter amount!", Alert.AlertType.WARNING);
            return false;
        }
        
        try {
            double amount = Double.parseDouble(amountField.getText());
            boolean success = controller.processPayment(bookingId, amount, paymentMethodComboBox.getValue());
            
            if (success) {
                // Load ticket details
                Booking booking = controller.getBookingDetails(bookingId);
                if (booking != null) {
                    updateTicketDisplay(booking);
                }
                return true;
            } else {
                showAlert("Payment failed!", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid amount!", Alert.AlertType.WARNING);
            return false;
        }
    }
    
    private void updateMissionDetails() {
        if (selectedMission != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Mission Name: ").append(selectedMission.getName()).append("\n");
            sb.append("Rocket: ").append(selectedMission.getRocketName()).append("\n");
            sb.append("Destination: ").append(selectedMission.getDestinationName()).append("\n");
            sb.append("Launch Site: ").append(selectedMission.getLaunchSiteName()).append("\n");
            sb.append("Travel Time: ").append(selectedMission.getTravelDays()).append(" days\n");
            sb.append("Supervisor: ").append(selectedMission.getSupervisorName()).append("\n");
            sb.append("Crew: ").append(selectedMission.getCrewName()).append("\n");
            sb.append("Available Seats: ").append(controller.getAvailableSeatsForMission(selectedMission.getId())).append("\n");
            sb.append("Amount: ").append(selectedMission.getAmount()).append("\n");
            sb.append("Launch Date: ").append(selectedMission.getLaunchDate()).append("\n");
            sb.append("Return Date: ").append(selectedMission.getReturnDate()).append("\n");
            missionDetailsLabel.setText(sb.toString());
            // Set payment field
            if (amountField != null) amountField.setText(String.valueOf(selectedMission.getAmount()));
        } else {
            missionDetailsLabel.setText("");
            if (amountField != null) amountField.clear();
        }
    }
    
    private void updateTicketDisplay(Booking booking) {
        ticketDetailsLabel.setText(String.format(
            "Booking ID: %d\nMission Name: %s\nSeat Number: %s\nLaunch Date: %s\nReturn Date: %s\n" +
            "Status: %s\nDestination: %s\nRocket: %s\nAmount: %s",
            booking.getBookingId(),
            selectedMission.getName(),
            booking.getSeatNumber(),
            booking.getLaunchDate(),
            booking.getReturnDate(),
            booking.getStatus(),
            selectedMission.getDestinationName(),
            selectedMission.getRocketName(),
            selectedMission.getAmount()
        ));
    }
    
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Space Tourism");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getRoot() {
        return root;
    }
    
    public VBox getContent() {
        return root;
    }
}
