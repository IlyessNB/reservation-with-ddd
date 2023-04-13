package infrastructure.repository;

import model.reservation.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepository {

   List<Reservation> reservations;

   public ReservationRepository() {
      this.reservations = new ArrayList<>();
   }
   public List<Reservation> getAll() {
      return reservations;
   }

   public void add(Reservation reservation) {
      reservations.add(reservation);
   }

   public List<Reservation> trouverParDate(String ressourceId, LocalDate date) {
      return reservations
         .stream()
         .filter(reservation -> reservation.estDeLaMemeDate(date))
         .filter(reservation -> reservation.getRessourceId().equals(ressourceId))
         .collect(Collectors.toList());
   }

}
