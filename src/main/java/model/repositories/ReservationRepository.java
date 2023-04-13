package model.repositories;

import model.reservation.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {

   List<Reservation> getAll();

   void add(Reservation reservation);

   List<Reservation> findByDate(String resourceId, LocalDate date);

}
