package controller;

import model.*;

public class MainController {

    public String processTravel(Travel travel) {
        if (travel == null) return "Invalid travel data.";

        // Doğru karşılaştırma burada yapılmalı
        if (travel.getFromPlanet() == travel.getToPlanet()) {
            return "Source and destination planets must be different!";
        }

        return travel.getSummary();
    }
    public String processGuest(Guest guest) {
        if (guest == null) return "Invalid guest data.";
        return guest.getSummary();
    }

}
