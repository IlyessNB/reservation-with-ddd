package model.reservation;

import model.resource.ResourceId;

import java.util.List;

public interface ReservationRepository {
    void save(Reservation reservation);

    List<Reservation> findByDateWithTimeRange(ResourceId resourceId, DateWithTimeRange timeRange);
}
