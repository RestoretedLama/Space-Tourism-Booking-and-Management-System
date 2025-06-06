package model;

public class Destination {
    private int destinationId;
    private String planetName;
    private String regionName;
    private double distanceMillionKm;

    public Destination(int destinationId, String planetName, String regionName, double distanceMillionKm) {
        this.destinationId = destinationId;
        this.planetName = planetName;
        this.regionName = regionName;
        this.distanceMillionKm = distanceMillionKm;
    }

    public int getDestinationId() { return destinationId; }
    public String getPlanetName() { return planetName; }
    public String getRegionName() { return regionName; }
    public double getDistanceMillionKm() { return distanceMillionKm; }

    public String getSummary() {
        return "Destination #" + destinationId + ": " + planetName + " (" + regionName + "), Distance: " + distanceMillionKm + "M km";
    }
}
