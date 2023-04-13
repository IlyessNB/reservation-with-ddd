package model.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationsConflictuellesException extends Exception {
    public ReservationsConflictuellesException(String idUtilisateur, String idRessource, LocalDate date, LocalTime heureDebut, LocalTime heureFin) {
        super("L'utilisateur " + idUtilisateur + " ne peut pas faire de réservation sur la ressource " + idRessource + " à la date " + date + " entre " + heureDebut + " et " + heureFin + " car il y a déjà une réservation à cette date et à cette heure.");
    }
}