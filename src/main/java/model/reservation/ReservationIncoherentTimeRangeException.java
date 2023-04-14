package model.reservation;

import java.time.LocalTime;

public class ReservationIncoherentTimeRangeException extends Exception {
    public ReservationIncoherentTimeRangeException(LocalTime startTime, LocalTime endTime) {
        super("L'heure de début de la réservation (" + startTime + ") est après l'heure de fin de la réservation (" + endTime + ") ou égale à l'heure de fin de la réservation.");
    }
}