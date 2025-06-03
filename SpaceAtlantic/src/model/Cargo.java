package model;

import model.CargoType;
import model.Planet;

public class Cargo {
    private Planet fromPlanet;
    private Planet toPlanet;
    private CargoType type;
    private double weight;

    public Cargo(Planet fromPlanet, Planet toPlanet, CargoType type, double weight) {
        this.fromPlanet = fromPlanet;
        this.toPlanet = toPlanet;
        this.type = type;
        this.weight = weight;
    }

    public String getSummary() {
        return "Sending " + weight + "kg of " + type +
                " cargo from " + fromPlanet + " to " + toPlanet;
    }
}
