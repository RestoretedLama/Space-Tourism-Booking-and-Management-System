package model;

import java.time.LocalDate;

public class Payment {
    private int paymentId;
    private double amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String status;
    private int guestId;

    public Payment(int paymentId, double amount, LocalDate paymentDate, String paymentMethod, String status, int guestId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.guestId = guestId;
    }

    // Getters
    public int getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }
    public int getGuestId() { return guestId; }

    public String getSummary() {
        return "Payment #" + paymentId + " by Guest " + guestId + " of $" + amount + " via " + paymentMethod + " on " + paymentDate +
                " [" + status + "]";
    }
}
