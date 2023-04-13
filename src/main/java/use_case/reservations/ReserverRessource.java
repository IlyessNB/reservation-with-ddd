package use_case.reservations;


// On va créer une reservation avec l'id de l'utilisateur, l'id de l'établissement,
// l'id de la ressource choisie, avec la date et l'horaire désirés

import infrastructure.repository.InMemoryReservationRepository;
import infrastructure.repository.InMemoryRessourceRepository;
import infrastructure.repository.InMemoryUtilisateurRepository;
import model.etablissement.ressource.Ressource;
import model.etablissement.ressource.RessourceFermeeException;
import model.etablissement.ressource.RessourceNonTrouveeException;
import model.reservation.Reservation;
import model.reservation.ReservationsConflictuellesException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReserverRessource {

    InMemoryUtilisateurRepository inMemoryUtilisateurRepository;
    InMemoryReservationRepository inMemoryReservationRepository;
    InMemoryRessourceRepository inMemoryRessourceRepository;

    public ReserverRessource(InMemoryUtilisateurRepository inMemoryUtilisateurRepository, InMemoryReservationRepository inMemoryReservationRepository, InMemoryRessourceRepository inMemoryRessourceRepository) {
        this.inMemoryUtilisateurRepository = inMemoryUtilisateurRepository;
        this.inMemoryReservationRepository = inMemoryReservationRepository;
        this.inMemoryRessourceRepository = inMemoryRessourceRepository;
    }


    public Reservation reserver(String utilisateurId, String ressourceId, LocalTime heureDebut, LocalTime heureFin, LocalDate date) throws RessourceNonTrouveeException, RessourceFermeeException, ReservationsConflictuellesException {
        Ressource ressource = inMemoryRessourceRepository.findById(ressourceId);
        List<Reservation> reservationsRessourceACeJour = inMemoryReservationRepository.trouverParDate(ressourceId, date);

        ressource.estOuverte(heureDebut, heureFin, date);

        Reservation reservation = Reservation.creer(utilisateurId, ressourceId, heureDebut, heureFin, date);
        reservation.peutEtreEffectuee(heureDebut, heureFin, date, reservationsRessourceACeJour);
        
        inMemoryReservationRepository.add(reservation);
        return reservation;
    }
}
