package infrastructure.repository;

import model.repositories.ReservationRepository;
import model.reservation.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryReservationRepository implements ReservationRepository {

   List<Reservation> reservations;

   public InMemoryReservationRepository() {
      this.reservations = new ArrayList<>();
   }

   @Override
   public List<Reservation> getAll() {
      return reservations;
   }

   @Override
   public void add(Reservation reservation) {
      reservations.add(reservation);
   }

   @Override
   public List<Reservation> trouverParDate(String ressourceId, LocalDate date) {
      return reservations
         .stream()
         .filter(reservation -> reservation.estDeLaMemeDate(date))
         .filter(reservation -> reservation.getRessourceId().equals(ressourceId))
         .collect(Collectors.toList());
   }

}
