package model;

public class Mission {
    private int id;
    private String rocketName;
    private String destinationName;
    private String launchSiteName;
    private int travelDays;
    private String supervisorName;
    private String crewName;

    public Mission(int id, String rocketName, String destinationName, String launchSiteName, int travelDays,
                   String supervisorName, String crewName) {
        this.id = id;
        this.rocketName = rocketName;
        this.destinationName = destinationName;
        this.launchSiteName = launchSiteName;
        this.travelDays = travelDays;
        this.supervisorName = supervisorName;
        this.crewName = crewName;
    }

    // Getters
    public int getId() { return id; }
    public String getRocketName() { return rocketName; }
    public String getDestinationName() { return destinationName; }
    public String getLaunchSiteName() { return launchSiteName; }
    public int getTravelDays() { return travelDays; }
    public String getSupervisorName() { return supervisorName; }
    public String getCrewName() { return crewName; }

    public String getSummary() {
        return "Mission #" + id +
                ": Rocket=" + rocketName +
                ", Destination=" + destinationName +
                ", Launch Site=" + launchSiteName +
                ", Travel Days=" + travelDays +
                ", Supervisor=" + supervisorName +
                ", Crew=" + crewName;
    }
}
