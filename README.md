# Space Tourism Booking and Management System

A comprehensive Java application for managing space tourism operations, including mission planning, booking management, and customer interactions.

## 🚀 Features

- **Mission Management**
  - Create and manage space missions
  - Track mission capacity and availability
  - Assign astronauts (supervisors and crew)
  - Set launch dates and travel durations

- **Booking System**
  - Real-time seat availability tracking
  - Customer registration and management

- **Customer Experience**
  - User-friendly booking interface
  - Mission details
  - Review and feedback system

- **Administrative Tools**
  - Mission creation and deletion
  - Passenger manifest management
  - Launch site coordination
  - Rocket and destination management

## 🛠 Technology Stack

- **Backend**: Java
- **Frontend**: JavaFX
- **Database**: MySQL
- **Connectivity**: JDBC

## 📋 Database Schema

The system uses the following main tables:
- `Guests`: Customer information
- `Missions`: Space mission details
- `Astronauts`: Crew and supervisor information
- `Destinations`: Available space destinations
- `Rockets`: Spacecraft information
- `Launch_Sites`: Launch facility details
- `Bookings`: Reservation records
- `Payments`: Transaction information
- `Reviews`: Customer feedback

## 🔧 Setup Instructions

1. **Prerequisites**
   - Java JDK (11 or higher)
   - JavaFX SDK
   - MySQL Server
   - MySQL Connector/J

2. **Database Setup**
   ```sql
   source SpaceAtlanticc.sql
   ```

3. **Configure Database Connection**
   - Update database credentials in `DatabaseConnector.java`
   - Default configuration:
     ```java
     URL = "jdbc:mysql://localhost:3306/SpaceTourismDB"
     USER = "root"
     PASSWORD = "1234"
     ```

4. **Run the Application**
   ```bash
   # Compile
   javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml src/SpaceTourismApp.java src/*/*.java

   # Run
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp src:mysql-connector-j-9.3.0_6215d24b6cfdc4198a8fe358c386b2ca.jar SpaceTourismApp
   ```

## 📱 User Interface

The application provides two main interfaces:

### Admin Panel
- Mission creation and management
- Passenger manifest viewing
- System configuration
- Resource management

### Customer Panel
- Destination Selection
- Mission Selection
- Guest Information Entry
- Payment Processing
- Ticket Generation
- Review Submission


## 📊 Data Management

The system implements various SQL features:
- Views for real-time seat availability
- Foreign key constraints for data integrity
- Complex JOIN operations for detailed reports
- Transaction management for booking operations
