package model.reservation;

import model.resource.ResourceId;
import model.user.UserId;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConflictualReservationsException extends Exception {
    public ConflictualReservationsException(UserId userId, ResourceId resourceId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        super("L'utilisateur " + userId.getValue() + " ne peut pas faire de réservation sur la ressource " + resourceId + " à la date " + date + " entre " + startTime + " et " + endTime + " car il y a déjà une réservation à cette date et à cette heure.");
    }
}