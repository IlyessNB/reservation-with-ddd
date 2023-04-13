package infrastructure.repository;

import model.Reservation;
import model.Ressource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationRepository {

   List<Reservation> reservations = List.of();
   public List<Reservation> getAll() {
      return reservations;
   };
   public void add(Reservation reservation) {
      reservations.add(reservation);
   }

}
