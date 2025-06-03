package controller;

import model.*;

public class MainController {

    public String processCargo(Cargo cargo) {
        if (cargo == null) return "Invalid cargo.";
        if (cargo.getSummary().contains(cargo.toString())) {
            return "Source and destination planets must be different!";
        }
        return cargo.getSummary();
    }

    public String processTravel(Travel travel) {
        if (travel == null) return "Invalid travel data.";
        if (travel.toString().contains(travel.toString())) {
            return "Source and destination planets must be different!";
        }
        return travel.getSummary();
    }
}
