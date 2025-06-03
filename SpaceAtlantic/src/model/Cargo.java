package model;

public class Cargo {
    private String from;
    private String to;
    private String type;
    private double weight;
    private String gender;

    public Cargo(String from, String to, String type, double weight, String gender) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.weight = weight;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Kargo Gönderimi:\n" +
                "Gönderen Gezegeni: " + from + "\n" +
                "Hedef Gezegeni: " + to + "\n" +
                "Kargo Türü: " + type + "\n" +
                "Ağırlık: " + weight + " kg\n" +
                "Cinsiyet: " + gender;
    }
}
