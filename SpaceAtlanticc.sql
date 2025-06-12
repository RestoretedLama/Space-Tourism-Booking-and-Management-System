-- 1
CREATE DATABASE SpaceTourismDB;
USE SpaceTourismDB;
-- 2
CREATE TABLE Guests (
    guest_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100),
    age INT,
    nationality VARCHAR(50),
    gender ENUM('Male', 'Female', 'Other'),
    contact_info VARCHAR(100)
);
-- 3
CREATE TABLE Astronauts (
    astronaut_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100),
    role ENUM('Supervisor', 'Crew') NOT NULL,
    supervisor_id INT DEFAULT NULL,
    FOREIGN KEY (supervisor_id) REFERENCES Astronauts(astronaut_id)
);

INSERT INTO Astronauts (full_name, role) VALUES
('Neil Armstrong',  'Supervisor'),
('Edwin "Buzz" Aldrin', 'Supervisor'),
('Charles "Pete" Conrad', 'Supervisor'),
('Alan Shepard', 'Supervisor'),
('John Young', 'Supervisor'),
('Alan Bean', 'Crew'),
('Edgar Mitchell', 'Crew'),
('David Scott',  'Crew'),
('James Irwin', 'Crew'),
('Charles Duke', 'Crew');
-- 4
CREATE TABLE Destinations (
    destination_id INT AUTO_INCREMENT PRIMARY KEY,
    planet_name VARCHAR(100),
    region_name VARCHAR(100),
    distance_million_km DECIMAL(10,3)
);

INSERT INTO Destinations (planet_name, region_name, distance_million_km) VALUES
('Moon', NULL, 0.384),
('Jupiter', 'Europa', 628.30),
('Jupiter', 'Ganymede', 628.50),
('Saturn', 'Mimas', 1275.00),
('Saturn', 'Enceladus', 1275.50),
('Saturn', 'Tethys', 1276.00),
('Uranus', 'Miranda', 2720.00),
('Uranus', 'Ariel', 2720.50),
('Neptune', 'Triton', 4350.00),
('Pluto', 'Charon', 5900.00);
-- 5

CREATE TABLE Rockets (
    rocket_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    type VARCHAR(50),
    max_range_million_km DECIMAL(10,2),
    capacity INT  
);

INSERT INTO Rockets (name, type, max_range_million_km, capacity) VALUES
('SpaceX Starship', 'Heavy', 6000.00, 5),
('Eclipse', 'Medium', 3000.00, 4),
('Nova', 'Light', 1300.00, 3),
('New Glenn', 'Heavy', 700.00, 2),
('Red Dwarf', 'Medium', 0.50, 2);

-- 6
CREATE TABLE Launch_Sites (
    site_id INT AUTO_INCREMENT PRIMARY KEY,
    site_name VARCHAR(150),
    location VARCHAR(100),
    country VARCHAR(50)
);

INSERT INTO Launch_Sites (site_name, location, country) VALUES
('Cape Canaveral Space Launch Complex 40', 'Florida', 'USA'),
('Vandenberg Space Force Base Space Launch Complex 4E', 'California', 'USA');
-- 7
CREATE TABLE Missions (
    mission_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200),
    rocket_id INT,
    destination_id INT,
    launch_site_id INT,
    travel_time_days INT,
    capacity INT,
    amount DECIMAL(10,2) DEFAULT 0.00,
    launch_date DATE,
    return_date DATE,
    FOREIGN KEY (rocket_id) REFERENCES Rockets(rocket_id),
    FOREIGN KEY (destination_id) REFERENCES Destinations(destination_id),
    FOREIGN KEY (launch_site_id) REFERENCES Launch_Sites(site_id)
);
-- 8
CREATE TABLE Mission_Astronauts (
    mission_id INT,
    astronaut_id INT,
    role ENUM('Supervisor', 'Crew') NOT NULL,
    PRIMARY KEY (mission_id, astronaut_id),
    FOREIGN KEY (mission_id) REFERENCES Missions(mission_id),
    FOREIGN KEY (astronaut_id) REFERENCES Astronauts(astronaut_id)
);
-- 9
CREATE TABLE Bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_id INT,
    mission_id INT,
    seat_number INT,
    launch_date DATE,
    return_date DATE,
    status ENUM('Confirmed', 'Pending', 'Cancelled'),
    UNIQUE (mission_id, seat_number, status),
    FOREIGN KEY (guest_id) REFERENCES Guests(guest_id),
    FOREIGN KEY (mission_id) REFERENCES Missions(mission_id)
);
-- 10
CREATE VIEW AvailableSeats AS
SELECT 
    m.mission_id,
    m.capacity - COUNT(b.booking_id) AS available_seats
FROM Missions m
LEFT JOIN Bookings b ON m.mission_id = b.mission_id AND b.status = 'Confirmed'
GROUP BY m.mission_id;
-- 11
CREATE TABLE Payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT,
    amount DECIMAL(10,2),
    payment_date DATE,
    payment_method ENUM('Credit Card', 'Crypto', 'Bank Transfer'),
    status ENUM('Paid', 'Pending', 'Failed'),
    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id)
);
-- 12
CREATE TABLE Reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    review_date DATE,
    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id)
);