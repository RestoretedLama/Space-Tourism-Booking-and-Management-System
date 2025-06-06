package model;

import java.time.LocalDate;

public class Booking {
    private int bookingId;
    private String seatNumber;
    private LocalDate launchDate;
    private LocalDate returnDate;
    private String status;
    private int guestId;
    private int missionId;

    public Booking(int bookingId, String seatNumber, LocalDate launchDate, LocalDate returnDate,
                   String status, int guestId, int missionId) {
        this.bookingId = bookingId;
        this.seatNumber = seatNumber;
        this.launchDate = launchDate;
        this.returnDate = returnDate;
        this.status = status;
        this.guestId = guestId;
        this.missionId = missionId;
    }

    // Getters
    public int getBookingId() { return bookingId; }
    public String getSeatNumber() { return seatNumber; }
    public LocalDate getLaunchDate() { return launchDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public String getStatus() { return status; }
    public int getGuestId() { return guestId; }
    public int getMissionId() { return missionId; }

    public String getSummary() {
        return "Booking #" + bookingId + " for Guest " + guestId + " on Mission " + missionId + ", Seat " + seatNumber +
                ", Launch: " + launchDate + ", Return: " + returnDate + ", Status: " + status;
    }
}
