package model;

public class Rocket {
    private int id;
    private String name;
    private String type;
    private double maxRange;
    private int capacity;

    public Rocket(int id, String name, String type, double maxRange, int capacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.maxRange = maxRange;
        this.capacity = capacity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getMaxRange() { return maxRange; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString() {
        return name + " (" + type + ", " + maxRange + "M km)";
    }
    public String getSummary() {
        return "Rocket #" + id + ": " + name + " (" + type + "), Max Range: " + maxRange + " million km";
    }

}