package model.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Reservation {

    String reservationId;
    String utilisateurId;
    LocalTime heureDebut;
    LocalTime heureFin;
    LocalDate date;
    String ressourceId;

    public String getRessourceId() {
        return ressourceId;
    }

    public boolean estDeLaMemeDate(LocalDate date) {
        return this.date.equals(date);
    }

    public void peutEtreEffectuee(LocalTime heureDebut, LocalTime heureFin, LocalDate date, List<Reservation> reservationsRessourceACeJour) throws ReservationsConflictuellesException {
        if (reservationsRessourceACeJour == null || reservationsRessourceACeJour.isEmpty()) {
            return;
        }
        var foundReservation = reservationsRessourceACeJour
                .stream()
                .filter(reservation -> reservation.date.equals(date))
                .filter(reservation -> reservation.heureDebut.isBefore(heureFin) && reservation.heureFin.isAfter(heureDebut))
                .findFirst()
                .orElseThrow(() -> new ReservationsConflictuellesException(
                        utilisateurId,
                        ressourceId,
                        date,
                        heureDebut,
                        heureFin
                    )
                );
        if (foundReservation != null) {
            throw new ReservationsConflictuellesException(
                    utilisateurId,
                    ressourceId,
                    date,
                    heureDebut,
                    heureFin
            );
        }
    }

    public Reservation(String reservationId, String utilisateurId, LocalTime heureDebut, LocalTime heureFin, LocalDate date, String ressourceId) {
        this.reservationId = reservationId;
        this.utilisateurId = utilisateurId;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.date = date;
        this.ressourceId = ressourceId;
    }

    public static Reservation creer(String utilisateurId, String ressourceId, LocalTime heureDebut, LocalTime heureFin, LocalDate date) {
        return new Reservation(LocalDateTime.now().toString(), utilisateurId, heureDebut, heureFin, date, ressourceId);
    }
}
