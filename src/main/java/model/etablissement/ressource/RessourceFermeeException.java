package model.etablissement.ressource;

import java.time.LocalDate;
import java.time.LocalTime;

public class RessourceFermeeException extends Exception {
    public RessourceFermeeException(String ressourceId, LocalDate date, LocalTime heureDebut, LocalTime heureFin) {
        super("Ressource avec l'id " + ressourceId + " est fermée le " + date + " de " + heureDebut + " à " + heureFin + ".");
    }
}
