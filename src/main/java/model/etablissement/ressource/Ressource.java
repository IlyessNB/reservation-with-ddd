package model.etablissement.ressource;

import model.etablissement.Horaires;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Ressource {
    String id;
    String etablissementId;
    String nom;
    List<String> equipements;

    Map<DayOfWeek, List<Horaires>> ouvertures;

    public Ressource(String id, String etablissementId, String nom, List<String> equipements, Map<DayOfWeek, List<Horaires>> ouvertures) {
        this.id = id;
        this.etablissementId = etablissementId;
        this.nom = nom;
        this.equipements = equipements;
        this.ouvertures = ouvertures;
    }

    public void estOuverte(LocalTime heureDebut, LocalTime heureFin, LocalDate date) throws RessourceFermeeException {
        DayOfWeek jourDeLaSemaine = date.getDayOfWeek();
        List<Horaires> horaires = ouvertures.get(jourDeLaSemaine);
        if (horaires == null || horaires.isEmpty()) {
            throw new RessourceFermeeException(id, date, heureDebut, heureFin);
        }
        for (Horaires horaire : horaires) {
            if (horaire == null || !isBetween(horaire.getHeureOuverture(), horaire.getHeureFermeture(), heureDebut, heureFin)) {
                throw new RessourceFermeeException(id, date, heureDebut, heureFin);
            }
        }
    }

    private boolean isBetween(LocalTime heureOuvertureHoraire, LocalTime heureFermetureHoraire, LocalTime heureDebutReservation, LocalTime heureFinReservation) {
        return heureDebutReservation.isAfter(heureOuvertureHoraire) && heureFinReservation.isBefore(heureFermetureHoraire);
    }

    public String getId() {
        return id;
    }

    public String getEtablissementId() {
        return etablissementId;
    }

    public String getNom() {
        return nom;
    }

    public List<String> getEquipements() {
        return equipements;
    }
}
