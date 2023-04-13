package use_case.institution;

import infrastructure.repository.InMemoryInstitutionRepository;
import infrastructure.repository.InMemoryReservationRepository;
import infrastructure.repository.InMemoryResourceRepository;
import infrastructure.repository.InMemoryUserRepository;
import model.institution.Institution;
import model.institution.Timetable;
import model.institution.resource.Resource;
import model.reservation.Reservation;
import model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import use_case.reservations.ReserveResource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ReservationsTest {

    // BeforeAll
    static ReserveResource reserveResource;
    static InMemoryUserRepository inMemoryUserRepository;
    static InMemoryReservationRepository inMemoryReservationRepository;
    static InMemoryInstitutionRepository inMemoryEtablissementRepository;
    static InMemoryResourceRepository inMemoryResourceRepository;

    @BeforeAll
    static void beforeAll() {
        inMemoryUserRepository = new InMemoryUserRepository();
        inMemoryReservationRepository = new InMemoryReservationRepository();
        inMemoryResourceRepository = new InMemoryResourceRepository();
        inMemoryEtablissementRepository = new InMemoryInstitutionRepository();
        reserveResource = new ReserveResource(
                inMemoryUserRepository,
                inMemoryReservationRepository,
                inMemoryResourceRepository
        );
    }

    @Test
    void test_faire_une_reservation_avec_succes() {
        // Given
        User user = new User("1", "DOE", "John", "jdoe@gmail.com");
        inMemoryUserRepository.create(user);

        Institution institution = new Institution("1", "Etablissement de test", "1 rue de test, 75000 Paris");
        inMemoryEtablissementRepository.add(institution);

        Map<DayOfWeek, List<Timetable>> horaires = new HashMap<>();
        horaires.put(DayOfWeek.MONDAY, new ArrayList<>(){{add(new Timetable(LocalTime.of(8, 0), LocalTime.of(20, 0)));}});
        horaires.put(DayOfWeek.TUESDAY, new ArrayList<>(){{add(new Timetable(LocalTime.of(8, 0), LocalTime.of(20, 0)));}});
        horaires.put(DayOfWeek.WEDNESDAY, new ArrayList<>(){{add(new Timetable(LocalTime.of(8, 0), LocalTime.of(20, 0)));}});
        horaires.put(DayOfWeek.THURSDAY, new ArrayList<>(){{add(new Timetable(LocalTime.of(8, 0), LocalTime.of(20, 0)));}});
        horaires.put(DayOfWeek.FRIDAY, new ArrayList<>(){{add(new Timetable(LocalTime.of(8, 0), LocalTime.of(20, 0)));}});
        horaires.put(DayOfWeek.SATURDAY, new ArrayList<>(){{add(null);}});
        horaires.put(DayOfWeek.SUNDAY, new ArrayList<>(){{add(null);}});

        Resource resource = new Resource("1", "1", "Salle de réunion de 10 personnes", new ArrayList<>(), horaires);
        inMemoryResourceRepository.add(resource);

        // When
        Reservation reservation = null;
        try {
            reservation = reserveResource.reserve(
                    user.getUtlisateurId(),
                    resource.getId(),
                    LocalTime.of(10, 0),
                    LocalTime.of(11, 0),
                    LocalDate.of(2023, 4, 13)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Then
        Assertions.assertNotNull(reservation);
        Assertions.assertEquals(reservation.getResourceId(), resource.getId());

    }
}