package model.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConflictualReservationsException extends Exception {
    public ConflictualReservationsException(String userId, String resourceId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        super("L'utilisateur " + userId + " ne peut pas faire de réservation sur la ressource " + resourceId + " à la date " + date + " entre " + startTime + " et " + endTime + " car il y a déjà une réservation à cette date et à cette heure.");
    }
}