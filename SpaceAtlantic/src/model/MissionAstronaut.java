package model;

public class MissionAstronaut {
    private int astronautId;
    private int missionId;
    private String role;

    public MissionAstronaut(int astronautId, int missionId, String role) {
        this.astronautId = astronautId;
        this.missionId = missionId;
        this.role = role;
    }

    public int getAstronautId() { return astronautId; }
    public int getMissionId() { return missionId; }
    public String getRole() { return role; }

    public String getSummary() {
        return "Astronaut " + astronautId + " assigned to Mission " + missionId + " as " + role;
    }
}
