package model;

public class LaunchSite {
    private int siteId;
    private String siteName;
    private String location;
    private String country;
    private double altitudeMeters;
    private int capacity;

    public LaunchSite(int siteId, String siteName, String location, String country, double altitudeMeters, int capacity) {
        this.siteId = siteId;
        this.siteName = siteName;
        this.location = location;
        this.country = country;
        this.altitudeMeters = altitudeMeters;
        this.capacity = capacity;
    }

    public int getSiteId() { return siteId; }
    public String getSiteName() { return siteName; }
    public String getLocation() { return location; }
    public String getCountry() { return country; }
    public double getAltitudeMeters() { return altitudeMeters; }
    public int getCapacity() { return capacity; }

    public String getSummary() {
        return "Launch Site #" + siteId + ": " + siteName + " (" + country + "), Altitude: " + altitudeMeters + "m";
    }
}
