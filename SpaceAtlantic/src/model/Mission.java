package model;

public class Mission {
    private int id;
    private String name;
    private String rocketName;
    private String destinationName;
    private String launchSiteName;
    private int travelDays;
    private String supervisorName;
    private String crewName;
    private java.time.LocalDate launchDate;
    private java.time.LocalDate returnDate;
    private double amount;

    public Mission(int id, String name, String rocketName, String destinationName, String launchSiteName, int travelDays,
                   String supervisorName, String crewName, double amount, java.time.LocalDate launchDate, java.time.LocalDate returnDate) {
        this.id = id;
        this.name = name;
        this.rocketName = rocketName;
        this.destinationName = destinationName;
        this.launchSiteName = launchSiteName;
        this.travelDays = travelDays;
        this.supervisorName = supervisorName;
        this.crewName = crewName;
        this.amount = amount;
        this.launchDate = launchDate;
        this.returnDate = returnDate;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getRocketName() { return rocketName; }
    public String getDestinationName() { return destinationName; }
    public String getLaunchSiteName() { return launchSiteName; }
    public int getTravelDays() { return travelDays; }
    public String getSupervisorName() { return supervisorName; }
    public String getCrewName() { return crewName; }
    public java.time.LocalDate getLaunchDate() { return launchDate; }
    public java.time.LocalDate getReturnDate() { return returnDate; }
    public double getAmount() { return amount; }

    public String getSummary() {
        return "Mission #" + id + " (" + name + ")" +
                ": Rocket=" + rocketName +
                ", Destination=" + destinationName +
                ", Launch Site=" + launchSiteName +
                ", Travel Days=" + travelDays +
                ", Supervisor=" + supervisorName +
                ", Crew=" + crewName +
                ", Amount=" + amount +
                ", Launch Date=" + launchDate +
                ", Return Date=" + returnDate;
    }
    
    @Override
    public String toString() {
        return name + " - " + destinationName + " (" + travelDays + " days)";
    }
}
