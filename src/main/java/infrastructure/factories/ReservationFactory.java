package infrastructure.factories;

import model.common.IdGenerator;
import model.reservation.DateWithTimeRange;
import model.reservation.Reservation;
import model.reservation.ReservationId;
import model.resource.ResourceId;
import model.user.UserId;

public class ReservationFactory {
    private final IdGenerator idGenerator;

    public ReservationFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Reservation create(UserId userId, ResourceId resourceId, DateWithTimeRange timeRange) {
        ReservationId reservationId = new ReservationId(idGenerator.generate());
        return Reservation.of(reservationId, userId, resourceId, timeRange);
    }
}