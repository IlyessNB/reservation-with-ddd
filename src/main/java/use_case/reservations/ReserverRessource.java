package use_case.reservations;


// On va créer une reservation avec l'id de l'utilisateur, l'id de l'établissement,
// l'id de la ressource choisie, avec la date et l'horaire désirés

import infrastructure.repository.EtablissementRepository;
import infrastructure.repository.ReservationRepository;
import infrastructure.repository.RessourceRepository;
import infrastructure.repository.UtilisateurRepository;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReserverRessource {

    UtilisateurRepository utilisateurRepository;
    ReservationRepository reservationRepository;
    RessourceRepository ressourceRepository;

    public ReserverRessource(UtilisateurRepository utilisateurRepository, ReservationRepository reservationRepository, RessourceRepository ressourceRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.reservationRepository = reservationRepository;
        this.ressourceRepository = ressourceRepository;
    }


    public Reservation reserver(String utilisateurId, String ressourceId, LocalTime heureDebut, LocalTime heureFin, LocalDate date) throws UtilisateurNonTrouveException, RessourceNonTrouveeException {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId);
        Ressource ressource = ressourceRepository.findById(ressourceId);

        Reservation reservation = Reservation.reserver(utilisateurId, ressourceId, heureDebut, heureFin, date);
        reservationRepository.add(reservation);
        return reservation;

    }
}
