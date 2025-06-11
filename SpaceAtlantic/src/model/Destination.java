package model;

public class Destination {
    private int id;
    private String planet;
    private String region;
    private double distance;

    public Destination(int id, String planet, String region, double distance) {
        this.id = id;
        this.planet = planet;
        this.region = region;
        this.distance = distance;
    }

    public int getId() { return id; }
    public String getPlanet() { return planet; }
    public String getRegion() { return region; }
    public double getDistance() { return distance; }

    public String getSummary() {
        return "Destination #" + id + ": " + planet +
                (region != null && !region.isEmpty() ? " - " + region : "") +
                ", Distance: " + distance + " million km";
    }

    @Override
    public String toString() {
        return planet + (region != null ? " - " + region : "") + " (" + distance + "M km)";
    }
}
