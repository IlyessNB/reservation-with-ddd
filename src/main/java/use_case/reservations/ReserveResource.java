package use_case.reservations;

import infrastructure.factories.ReservationFactory;
import model.reservation.*;
import model.resource.Resource;
import model.resource.ResourceIsClosedException;
import model.resource.ResourceNotFoundException;
import model.resource.ResourceRepository;
import model.user.UserNotFoundException;
import model.user.UserRepository;

import java.util.List;

public class ReserveResource {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ResourceRepository resourceRepository;
    private final ReservationFactory reservationFactory;

    public ReserveResource(UserRepository userRepository, ReservationRepository reservationRepository, ResourceRepository resourceRepository, ReservationFactory reservationFactory) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.resourceRepository = resourceRepository;
        this.reservationFactory = reservationFactory;
    }

    public Reservation reserve(ReserveResourceDto reserveResourceDto) throws ResourceNotFoundException, ResourceIsClosedException, ConflictualReservationsException, UserNotFoundException, ReservationIncoherentTimeRangeException, ReservationDateIsInPastException {
        userRepository.exists(reserveResourceDto.userId);

        DateWithTimeRange timeRange = DateWithTimeRange.of(reserveResourceDto.startTime, reserveResourceDto.endTime, reserveResourceDto.date);
        final Resource resource = resourceRepository.findById(reserveResourceDto.resourceId);
        final Reservation reservation = reservationFactory.create(reserveResourceDto.userId, reserveResourceDto.resourceId, timeRange);
        final List<Reservation> reservationsInTimeRange = reservationRepository.findByDateWithTimeRange(reserveResourceDto.resourceId, reservation.getDateWithTimeRange());

        resource.verifyIsOpen(reservation.getDateWithTimeRange());
        reservation.verifyConflicts(reservationsInTimeRange);

        reservationRepository.save(reservation);
        return reservation;
    }
}
