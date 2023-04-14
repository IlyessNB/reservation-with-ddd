package infrastructure.factories;

import model.common.IdGenerator;
import model.reservation.*;
import model.resource.ResourceId;
import model.user.UserId;

public class ReservationFactory {
    private final IdGenerator idGenerator;

    public ReservationFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Reservation create(UserId userId, ResourceId resourceId, DateWithTimeRange timeRange) throws ReservationIncoherentTimeRangeException, ReservationDateIsInPastException {
        ReservationId reservationId = new ReservationId(idGenerator.generate());
        return Reservation.of(reservationId, userId, resourceId, timeRange);
    }
}
