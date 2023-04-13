package use_case.reservations;


// On va créer une reservation avec l'id de l'utilisateur, l'id de l'établissement,
// l'id de la ressource choisie, avec la date et l'horaire désirés

import infrastructure.repository.ReservationRepository;
import infrastructure.repository.RessourceRepository;
import infrastructure.repository.UtilisateurRepository;
import model.etablissement.ressource.Ressource;
import model.etablissement.ressource.RessourceFermeeException;
import model.etablissement.ressource.RessourceNonTrouveeException;
import model.reservation.Reservation;
import model.reservation.ReservationsConflictuellesException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReserverRessource {

    UtilisateurRepository utilisateurRepository;
    ReservationRepository reservationRepository;
    RessourceRepository ressourceRepository;

    public ReserverRessource(UtilisateurRepository utilisateurRepository, ReservationRepository reservationRepository, RessourceRepository ressourceRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.reservationRepository = reservationRepository;
        this.ressourceRepository = ressourceRepository;
    }


    public Reservation reserver(String utilisateurId, String ressourceId, LocalTime heureDebut, LocalTime heureFin, LocalDate date) throws RessourceNonTrouveeException, RessourceFermeeException, ReservationsConflictuellesException {
        Ressource ressource = ressourceRepository.findById(ressourceId);
        List<Reservation> reservationsRessourceACeJour = reservationRepository.trouverParDate(ressourceId, date);

        ressource.estOuverte(heureDebut, heureFin, date);

        Reservation reservation = Reservation.creer(utilisateurId, ressourceId, heureDebut, heureFin, date);
        reservation.peutEtreEffectuee(heureDebut, heureFin, date, reservationsRessourceACeJour);
        
        reservationRepository.add(reservation);
        return reservation;
    }
}
