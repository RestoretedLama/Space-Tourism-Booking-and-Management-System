package model;

public class Travel {
    private Planet fromPlanet;
    private Planet toPlanet;
    private String baseTo;
    private boolean hasLuggage;
    private double luggageWeight;
    private String gender;

    public Travel(Planet fromPlanet, Planet toPlanet, String baseTo, boolean hasLuggage, double luggageWeight, String gender) {
        this.fromPlanet = fromPlanet;
        this.toPlanet = toPlanet;
        this.baseTo = baseTo;
        this.hasLuggage = hasLuggage;
        this.luggageWeight = luggageWeight;
        this.gender = gender;
    }

    public Planet getFromPlanet() {
        return fromPlanet;
    }

    public Planet getToPlanet() {
        return toPlanet;
    }

    public String getSummary() {
        String summary = gender + " traveler from " + fromPlanet + " to " + toPlanet + " (Base: " + baseTo + ")";
        if (hasLuggage) {
            summary += " with luggage of " + luggageWeight + "kg.";
        } else {
            summary += " with no luggage.";
        }
        return summary;
    }
}
