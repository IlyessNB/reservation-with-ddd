package use_case.institution;

import infrastructure.common.UUIDGenerator;
import infrastructure.repositories.InMemoryReservationRepository;
import infrastructure.repositories.InMemoryResourceRepository;
import infrastructure.repositories.InMemoryUserRepository;
import model.common.IdGenerator;
import model.reservation.Reservation;
import model.resource.Resource;
import model.resource.Timetable;
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
    static ReserveResource reserveResource;
    static InMemoryUserRepository inMemoryUserRepository;
    static InMemoryReservationRepository inMemoryReservationRepository;
    static InMemoryResourceRepository inMemoryResourceRepository;

    @BeforeAll
    static void beforeAll() {
        IdGenerator idGenerator = new UUIDGenerator();
        inMemoryUserRepository = new InMemoryUserRepository(idGenerator);
        inMemoryReservationRepository = new InMemoryReservationRepository(idGenerator);
        inMemoryResourceRepository = new InMemoryResourceRepository(idGenerator);
        reserveResource = new ReserveResource(
                inMemoryUserRepository,
                inMemoryReservationRepository,
                inMemoryResourceRepository
        );
    }

    @Test
    void test_faire_une_reservation_avec_succes() {
        // Given
        User user = inMemoryUserRepository.create("DOE", "John", "jdoe@gmail.com");
        inMemoryUserRepository.add(user);

        Map<DayOfWeek, List<Timetable>> horaires = new HashMap<>();
        horaires.put(DayOfWeek.MONDAY, new ArrayList<>() {{
            add(new Timetable(LocalTime.of(8, 0), LocalTime.of(20, 0)));
        }});
        horaires.put(DayOfWeek.TUESDAY, new ArrayList<>() {{
            add(new Timetable(LocalTime.of(8, 0), LocalTime.of(20, 0)));
        }});
        horaires.put(DayOfWeek.WEDNESDAY, new ArrayList<>() {{
            add(new Timetable(LocalTime.of(8, 0), LocalTime.of(20, 0)));
        }});
        horaires.put(DayOfWeek.THURSDAY, new ArrayList<>() {{
            add(new Timetable(LocalTime.of(8, 0), LocalTime.of(20, 0)));
        }});
        horaires.put(DayOfWeek.FRIDAY, new ArrayList<>() {{
            add(new Timetable(LocalTime.of(8, 0), LocalTime.of(20, 0)));
        }});
        horaires.put(DayOfWeek.SATURDAY, new ArrayList<>() {{
            add(null);
        }});
        horaires.put(DayOfWeek.SUNDAY, new ArrayList<>() {{
            add(null);
        }});

        Resource resource = inMemoryResourceRepository.create("1", "Salle de réunion de 10 personnes", new ArrayList<>(), horaires);
        inMemoryResourceRepository.add(resource);

        // When
        Reservation reservation = null;
        try {
            reservation = reserveResource.reserve(
                    user.getUserId(),
                    resource.getResourceId(),
                    LocalTime.of(10, 0),
                    LocalTime.of(11, 0),
                    LocalDate.of(2023, 4, 13)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Then
        Assertions.assertNotNull(reservation);
        Assertions.assertEquals(reservation.getResourceId(), resource.getResourceId());

    }
}