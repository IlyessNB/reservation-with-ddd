package use_case.etablissement;

import infrastructure.repository.EtablissementRepository;
import infrastructure.repository.ReservationRepository;
import infrastructure.repository.RessourceRepository;
import infrastructure.repository.UtilisateurRepository;
import model.etablissement.Etablissement;
import model.etablissement.Horaires;
import model.etablissement.ressource.Ressource;
import model.reservation.Reservation;
import model.utilisateur.Utilisateur;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import use_case.reservations.ReserverRessource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ReservationsTest {

    // BeforeAll
    static ReserverRessource reserverRessource;
    static UtilisateurRepository utilisateurRepository;
    static ReservationRepository reservationRepository;
    static EtablissementRepository etablissementRepository;
    static RessourceRepository ressourceRepository;

    @BeforeAll
    static void beforeAll() {
        utilisateurRepository = new UtilisateurRepository();
        reservationRepository = new ReservationRepository();
        ressourceRepository = new RessourceRepository();
        etablissementRepository = new EtablissementRepository();
        reserverRessource = new ReserverRessource(
                utilisateurRepository,
                reservationRepository,
                ressourceRepository
        );
    }

    @Test
    void test_faire_une_reservation_avec_succes() {
        // Given
        Utilisateur utilisateur = new Utilisateur("1", "DOE", "John", "jdoe@gmail.com");
        utilisateurRepository.create(utilisateur);

        Etablissement etablissement = new Etablissement("1", "Etablissement de test", "1 rue de test, 75000 Paris");
        etablissementRepository.add(etablissement);

        Map<DayOfWeek, List<Horaires>> horaires = new HashMap<>();
        horaires.put(DayOfWeek.MONDAY, new ArrayList<>(){{add(new Horaires(LocalTime.of(8, 0), LocalTime.of(20, 0)));}});
        horaires.put(DayOfWeek.TUESDAY, new ArrayList<>(){{add(new Horaires(LocalTime.of(8, 0), LocalTime.of(20, 0)));}});
        horaires.put(DayOfWeek.WEDNESDAY, new ArrayList<>(){{add(new Horaires(LocalTime.of(8, 0), LocalTime.of(20, 0)));}});
        horaires.put(DayOfWeek.THURSDAY, new ArrayList<>(){{add(new Horaires(LocalTime.of(8, 0), LocalTime.of(20, 0)));}});
        horaires.put(DayOfWeek.FRIDAY, new ArrayList<>(){{add(new Horaires(LocalTime.of(8, 0), LocalTime.of(20, 0)));}});
        horaires.put(DayOfWeek.SATURDAY, new ArrayList<>(){{add(null);}});
        horaires.put(DayOfWeek.SUNDAY, new ArrayList<>(){{add(null);}});

        Ressource ressource = new Ressource("1", "1", "Salle de réunion de 10 personnes", new ArrayList<>(), horaires);
        ressourceRepository.add(ressource);

        // When
        Reservation reservation = null;
        try {
            reservation = reserverRessource.reserver(
                    utilisateur.getUtlisateurId(),
                    ressource.getId(),
                    LocalTime.of(10, 0),
                    LocalTime.of(11, 0),
                    LocalDate.of(2023, 4, 13)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Then
        Assertions.assertNotNull(reservation);
        Assertions.assertEquals(reservation.getRessourceId(), ressource.getId());

    }
}