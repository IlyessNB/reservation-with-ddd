package use_case.reservations;

import infrastructure.repository.InMemoryReservationRepository;
import infrastructure.repository.InMemoryResourceRepository;
import infrastructure.repository.InMemoryUserRepository;
import model.institution.resource.Resource;
import model.institution.resource.ResourceIsClosedException;
import model.institution.resource.ResourceNotFoundException;
import model.reservation.ConflictualReservationsException;
import model.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReserveResource {

    InMemoryUserRepository inMemoryUserRepository;
    InMemoryReservationRepository inMemoryReservationRepository;
    InMemoryResourceRepository inMemoryResourceRepository;

    public ReserveResource(InMemoryUserRepository inMemoryUserRepository, InMemoryReservationRepository inMemoryReservationRepository, InMemoryResourceRepository inMemoryResourceRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
        this.inMemoryReservationRepository = inMemoryReservationRepository;
        this.inMemoryResourceRepository = inMemoryResourceRepository;
    }


    public Reservation reserve(String userId, String resourceId, LocalTime startTime, LocalTime endTime, LocalDate date) throws ResourceNotFoundException, ResourceIsClosedException, ConflictualReservationsException {
        Reservation reservation = Reservation.create(userId, resourceId, startTime, endTime, date);
        if (!reservation.check()) {
            throw new IllegalArgumentException("La reservation n'est pas valide");
        }

        Resource resource = inMemoryResourceRepository.findById(resourceId);
        List<Reservation> reservationRepositoryByDate = inMemoryReservationRepository.findByDate(resourceId, date);

        resource.isOpen(startTime, endTime, date);
        reservation.checkConflicts(startTime, endTime, date, reservationRepositoryByDate);

        inMemoryReservationRepository.add(reservation);
        return reservation;
    }
}
