package model;

public class Mission {
    private int missionId;
    private String departureLocation;
    private int travelTimeDays;
    private int capacity;
    private int siteId;
    private int destinationId;
    private int rocketId;

    public Mission(int missionId, String departureLocation, int travelTimeDays, int capacity,
                   int siteId, int destinationId, int rocketId) {
        this.missionId = missionId;
        this.departureLocation = departureLocation;
        this.travelTimeDays = travelTimeDays;
        this.capacity = capacity;
        this.siteId = siteId;
        this.destinationId = destinationId;
        this.rocketId = rocketId;
    }

    public int getMissionId() { return missionId; }
    public String getDepartureLocation() { return departureLocation; }
    public int getTravelTimeDays() { return travelTimeDays; }
    public int getCapacity() { return capacity; }
    public int getSiteId() { return siteId; }
    public int getDestinationId() { return destinationId; }
    public int getRocketId() { return rocketId; }

    public String getSummary() {
        return "Mission #" + missionId + ": " + departureLocation + " → Destination " + destinationId +
                " in " + travelTimeDays + " days (Capacity: " + capacity + ")";
    }
}
