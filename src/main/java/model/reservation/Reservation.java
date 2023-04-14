package model.reservation;

import model.resource.ResourceId;
import model.user.UserId;

import java.util.List;

public class Reservation {
    private final ReservationId reservationId;
    private final UserId userId;
    private final DateWithTimeRange timeRange;
    private final ResourceId resourceId;

    private Reservation(ReservationId reservationId, UserId userId, DateWithTimeRange timeRange, ResourceId resourceId) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.timeRange = timeRange;
        this.resourceId = resourceId;
    }

    public static Reservation of(ReservationId reservationId, UserId userId, ResourceId resourceId, DateWithTimeRange timeRange) {
        timeRange.checkTimeCoherence();
        timeRange.checkIsNotInPast();
        return new Reservation(reservationId, userId, timeRange, resourceId);
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public DateWithTimeRange getTimeRange() {
        return timeRange;
    }

    public void verifyConflicts(List<Reservation> reservationOnDate) throws ConflictualReservationsException {
        if (reservationOnDate != null && !reservationOnDate.isEmpty()) {
            throw new ConflictualReservationsException(
                    userId,
                    resourceId,
                    timeRange.getDate(),
                    timeRange.getStartTime(),
                    timeRange.getEndTime()
            );
        }
    }
}
