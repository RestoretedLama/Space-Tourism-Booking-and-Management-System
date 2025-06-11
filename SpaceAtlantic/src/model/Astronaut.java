package model;

public class Astronaut {
    private int astronautId;
    private String fullName;
    private int experienceYears;
    private String role;
    private Integer supervisorId;

    public Astronaut(int astronautId, String fullName, int experienceYears, String role, Integer supervisorId) {
        this.astronautId = astronautId;
        this.fullName = fullName;
        this.experienceYears = experienceYears;
        this.role = role;
        this.supervisorId = supervisorId;
    }

    public int getAstronautId() { return astronautId; }
    public String getFullName() { return fullName; }
    public int getExperienceYears() { return experienceYears; }
    public String getRole() { return role; }
    public Integer getSupervisorId() { return supervisorId; }

    // Yeni eklenen getId() metodu
    public int getId() {
        return astronautId;
    }

    public String getSummary() {
        return "Astronaut #" + astronautId + ": " + fullName + " (" + role + ", " + experienceYears + " yrs)";
    }
}
