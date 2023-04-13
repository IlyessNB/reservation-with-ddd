package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    String reservationId;
    String utilisateurId;
    LocalTime heureDebut;
    LocalTime heureFin;
    LocalDate date;
    String ressourceId;


    public Reservation(String reservationId, String utilisateurId, LocalTime heureDebut, LocalTime heureFin, LocalDate date, String ressourceId) {
        this.reservationId = reservationId;
        this.utilisateurId = utilisateurId;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.date = date;
        this.ressourceId = ressourceId;
    }

    public static Reservation reserver(String utilisateurId, String ressourceId, LocalTime heureDebut, LocalTime heureFin, LocalDate date) {

        return new Reservation(LocalDateTime.now().toString(), utilisateurId, heureDebut, heureFin, date, ressourceId);
    }
}
