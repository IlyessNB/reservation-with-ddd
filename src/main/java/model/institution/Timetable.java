package model.institution;

import java.time.LocalTime;

public class Timetable {
    private final LocalTime heureOuverture;
    private final LocalTime heureFermeture;


    public Timetable(LocalTime heureOuverture, LocalTime heureFermeture) {
        this.heureOuverture = heureOuverture;
        this.heureFermeture = heureFermeture;
    }

    public LocalTime getOpeningTime() {
        return heureOuverture;
    }

    public LocalTime getClosingTime() {
        return heureFermeture;
    }
}
