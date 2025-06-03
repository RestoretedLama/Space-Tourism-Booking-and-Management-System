package model;

public class Travel {
    private String from;
    private String to;
    private boolean hasLuggage;
    private double luggageWeight;
    private String gender;

    public Travel(String from, String to, boolean hasLuggage, double luggageWeight, String gender) {
        this.from = from;
        this.to = to;
        this.hasLuggage = hasLuggage;
        this.luggageWeight = luggageWeight;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Seyahat Bilgisi:\n" +
                "Kalkış Gezegeni: " + from + "\n" +
                "Varış Gezegeni: " + to + "\n" +
                "Bavul: " + (hasLuggage ? "Evet, " + luggageWeight + " kg" : "Hayır") + "\n" +
                "Cinsiyet: " + gender;
    }
}
