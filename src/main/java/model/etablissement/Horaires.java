package model.etablissement;

import java.time.LocalTime;

public class Horaires {
    private final LocalTime heureOuverture;
    private final LocalTime heureFermeture;


    public Horaires(LocalTime heureOuverture, LocalTime heureFermeture) {
        this.heureOuverture = heureOuverture;
        this.heureFermeture = heureFermeture;
    }

    public LocalTime getHeureOuverture() {
        return heureOuverture;
    }

    public LocalTime getHeureFermeture() {
        return heureFermeture;
    }
}
