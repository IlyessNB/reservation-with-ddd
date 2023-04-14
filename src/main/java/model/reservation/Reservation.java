package model.reservation;

import model.resource.ResourceId;
import model.user.UserId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Reservation {
    ReservationId reservationId;
    UserId userId;
    LocalTime startTime;
    LocalTime endTime;
    LocalDate date;
    ResourceId resourceId;

    private Reservation(ReservationId reservationId, UserId userId, LocalTime startTime, LocalTime endTime, LocalDate date, ResourceId resourceId) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.resourceId = resourceId;
    }

    public static Reservation of(ReservationId reservationId, UserId userId, ResourceId resourceId, LocalTime startTime, LocalTime endTime, LocalDate date) {
        return new Reservation(reservationId, userId, startTime, endTime, date, resourceId);
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public boolean isSameDate(LocalDate date) {
        return this.date.equals(date);
    }

    public void verifyConflicts(LocalTime startTime, LocalTime endTime, LocalDate date, List<Reservation> reservationOnDate) throws ConflictualReservationsException {
        if (reservationOnDate == null || reservationOnDate.isEmpty()) {
            return;
        }
        var foundReservation = reservationOnDate
                .stream()
                .filter(reservation -> reservation.date.equals(date))
                .filter(reservation -> reservation.startTime.isBefore(endTime) && reservation.endTime.isAfter(startTime))
                .findFirst()
                .orElseThrow(() -> new ConflictualReservationsException(
                        userId,
                        resourceId,
                        date,
                        startTime,
                        endTime
                    )
                );
        if (foundReservation != null) {
            throw new ConflictualReservationsException(
                    userId,
                    resourceId,
                    date,
                    startTime,
                    endTime
            );
        }
    }

    public void checkTimeCoherence() {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("La reservation n'est pas valide");
        }
    }
}
