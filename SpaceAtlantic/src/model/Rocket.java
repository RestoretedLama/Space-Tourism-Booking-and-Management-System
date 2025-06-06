package model;

public class Rocket {
    private int rocketId;
    private String name;
    private String type;
    private double maxRangeMillionKm;

    public Rocket(int rocketId, String name, String type, double maxRangeMillionKm) {
        this.rocketId = rocketId;
        this.name = name;
        this.type = type;
        this.maxRangeMillionKm = maxRangeMillionKm;
    }

    public int getRocketId() { return rocketId; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getMaxRangeMillionKm() { return maxRangeMillionKm; }

    public String getSummary() {
        return "Rocket #" + rocketId + ": " + name + " (" + type + "), Max Range: " + maxRangeMillionKm + " million km";
    }
}
