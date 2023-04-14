package model.reservation;

import java.time.LocalDate;

public class ReservationDateIsInPastException extends Exception {
    public ReservationDateIsInPastException(LocalDate date) {
        super("La date de la réservation est dans le passé (" + date + ").");
    }
}