package use_case.reservations;

import infrastructure.factories.ReservationFactory;
import model.reservation.ConflictualReservationsException;
import model.reservation.DateWithTimeRange;
import model.reservation.Reservation;
import model.reservation.ReservationRepository;
import model.resource.*;
import model.user.UserId;
import model.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReserveResource {
    UserRepository userRepository;
    ReservationRepository reservationRepository;
    ResourceRepository resourceRepository;
    ReservationFactory reservationFactory;

    public ReserveResource(UserRepository userRepository, ReservationRepository reservationRepository, ResourceRepository resourceRepository, ReservationFactory reservationFactory) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.resourceRepository = resourceRepository;
        this.reservationFactory = reservationFactory;
    }

    public Reservation reserve(UserId userId, ResourceId resourceId, LocalTime startTime, LocalTime endTime, LocalDate date) throws ResourceNotFoundException, ResourceIsClosedException, ConflictualReservationsException {
        DateWithTimeRange timeRange = DateWithTimeRange.of(startTime, endTime, date);
        final Reservation reservation = reservationFactory.create(userId, resourceId, timeRange);
        final Resource resource = resourceRepository.findById(resourceId);
        final List<Reservation> reservationsInTimeRange = reservationRepository.findByTimeRange(resourceId, reservation.getTimeRange());

        resource.verifyIsOpen(reservation.getTimeRange());
        reservation.verifyConflicts(reservationsInTimeRange);

        reservationRepository.save(reservation);
        return reservation;
    }
}
