package use_case.reservations;

import model.resource.Resource;
import model.resource.ResourceIsClosedException;
import model.resource.ResourceNotFoundException;
import model.reservation.ReservationRepository;
import model.resource.ResourceRepository;
import model.user.UserRepository;
import model.reservation.ConflictualReservationsException;
import model.reservation.Reservation;
import model.resource.ResourceId;
import model.user.UserId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReserveResource {
    UserRepository userRepository;
    ReservationRepository reservationRepository;
    ResourceRepository resourceRepository;

    public ReserveResource(UserRepository userRepository, ReservationRepository reservationRepository, ResourceRepository resourceRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.resourceRepository = resourceRepository;
    }

    public Reservation reserve(UserId userId, ResourceId resourceId, LocalTime startTime, LocalTime endTime, LocalDate date) throws ResourceNotFoundException, ResourceIsClosedException, ConflictualReservationsException {
        Reservation reservation = reservationRepository.create(userId, resourceId, startTime, endTime, date);
        reservation.checkTimeCoherence();

        Resource resource = resourceRepository.findById(resourceId);
        List<Reservation> reservationRepositoryByDate = reservationRepository.findByDate(resourceId, date);

        resource.verifyIsOpen(startTime, endTime, date);
        reservation.verifyConflicts(startTime, endTime, date, reservationRepositoryByDate);

        reservationRepository.add(reservation);
        return reservation;
    }
}
