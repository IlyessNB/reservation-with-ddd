package model.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Reservation {

    String reservationId;
    String userId;
    LocalTime startTime;
    LocalTime endTime;
    LocalDate date;
    String resourceId;

    public String getResourceId() {
        return resourceId;
    }

    public boolean isSameDate(LocalDate date) {
        return this.date.equals(date);
    }

    public void checkConflicts(LocalTime startTime, LocalTime endTime, LocalDate date, List<Reservation> reservationOnDate) throws ConflictualReservationsException {
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

    public Reservation(String reservationId, String userId, LocalTime startTime, LocalTime endTime, LocalDate date, String resourceId) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.resourceId = resourceId;
    }

    public static Reservation create(String userId, String resourceId, LocalTime startTime, LocalTime endTime, LocalDate date) {
        return new Reservation(LocalDateTime.now().toString(), userId, startTime, endTime, date, resourceId);
    }

    public boolean check() {
        return startTime.isBefore(endTime);
    }
}
