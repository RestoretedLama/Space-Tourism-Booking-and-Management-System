package model;

public class Guest {
    private int guestId;
    private String fullName;
    private int age;
    private String nationality;
    private String gender;
    private String contactInfo;

    public Guest(int guestId, String fullName, int age, String nationality, String gender, String contactInfo) {
        this.guestId = guestId;
        this.fullName = fullName;
        this.age = age;
        this.nationality = nationality;
        this.gender = gender;
        this.contactInfo = contactInfo;
    }

    public int getGuestId() {
        return guestId;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getNationality() {
        return nationality;
    }

    public String getGender() {
        return gender;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getSummary() {
        return "Guest: " + fullName + " (" + nationality + ", " + gender + ", " + age + " y/o) - Contact: " + contactInfo;
    }
}
