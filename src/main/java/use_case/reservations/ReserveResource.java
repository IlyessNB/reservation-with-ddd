package use_case.reservations;

import infrastructure.factories.ReservationFactory;
import model.reservation.*;
import model.resource.*;
import model.user.UserId;
import model.user.UserNotFoundException;
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

    public Reservation reserve(UserId userId, ResourceId resourceId, LocalTime startTime, LocalTime endTime, LocalDate date) throws ResourceNotFoundException, ResourceIsClosedException, ConflictualReservationsException, UserNotFoundException, ReservationIncoherentTimeRangeException, ReservationDateIsInPastException {
        userRepository.exists(userId);

        DateWithTimeRange timeRange = DateWithTimeRange.of(startTime, endTime, date);
        final Resource resource = resourceRepository.findById(resourceId);
        final Reservation reservation = reservationFactory.create(userId, resourceId, timeRange);
        final List<Reservation> reservationsInTimeRange = reservationRepository.findByDateWithTimeRange(resourceId, reservation.getDateWithTimeRange());

        resource.verifyIsOpen(reservation.getDateWithTimeRange());
        reservation.verifyConflicts(reservationsInTimeRange);

        reservationRepository.save(reservation);
        return reservation;
    }
}
