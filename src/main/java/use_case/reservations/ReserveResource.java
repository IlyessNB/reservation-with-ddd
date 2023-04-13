package use_case.reservations;

import model.institution.resource.Resource;
import model.institution.resource.ResourceIsClosedException;
import model.institution.resource.ResourceNotFoundException;
import model.repositories.ReservationRepository;
import model.repositories.ResourceRepository;
import model.repositories.UserRepository;
import model.reservation.ConflictualReservationsException;
import model.reservation.Reservation;

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

    public Reservation reserve(String userId, String resourceId, LocalTime startTime, LocalTime endTime, LocalDate date) throws ResourceNotFoundException, ResourceIsClosedException, ConflictualReservationsException {
        Reservation reservation = Reservation.create(userId, resourceId, startTime, endTime, date);
        if (!reservation.check()) {
            throw new IllegalArgumentException("La reservation n'est pas valide");
        }

        Resource resource = resourceRepository.findById(resourceId);
        List<Reservation> reservationRepositoryByDate = reservationRepository.findByDate(resourceId, date);

        resource.isOpen(startTime, endTime, date);
        reservation.checkConflicts(startTime, endTime, date, reservationRepositoryByDate);

        reservationRepository.add(reservation);
        return reservation;
    }
}
