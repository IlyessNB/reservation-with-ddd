package use_case.institution;

import infrastructure.common.UUIDGenerator;
import infrastructure.factories.ReservationFactory;
import infrastructure.repositories.InMemoryReservationRepository;
import infrastructure.repositories.InMemoryResourceRepository;
import infrastructure.repositories.InMemoryUserRepository;
import model.common.IdGenerator;
import model.reservation.ConflictualReservationsException;
import model.reservation.Reservation;
import model.resource.Resource;
import model.resource.ResourceIsClosedException;
import model.resource.Timetable;
import model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import use_case.reservations.ReserveResource;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
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

    static Map<DayOfWeek, List<Timetable>> horaires = new HashMap<>();

    @BeforeAll
    static void beforeAll() {
        IdGenerator idGenerator = new UUIDGenerator();
        inMemoryUserRepository = new InMemoryUserRepository(idGenerator);
        inMemoryReservationRepository = new InMemoryReservationRepository(idGenerator);
        inMemoryResourceRepository = new InMemoryResourceRepository(idGenerator);
        reserveResource = new ReserveResource(
                inMemoryUserRepository,
                inMemoryReservationRepository,
                inMemoryResourceRepository,
                new ReservationFactory(idGenerator)
        );
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
    }

    @Test
    void test_make_a_successful_reservation() {
        // Given
        User user = inMemoryUserRepository.create("DOE", "John", "jdoe@gmail.com");
        inMemoryUserRepository.save(user);

        Resource resource = inMemoryResourceRepository.create("1", "Salle de réunion de 10 personnes", new ArrayList<>(), horaires);
        inMemoryResourceRepository.add(resource);

        final LocalDateTime date = LocalDateTime.now().plusWeeks(2).with(DayOfWeek.MONDAY).with(LocalTime.of(10, 0));
        // When
        Reservation reservation = null;
        try {
            reservation = reserveResource.reserve(
                    user.getUserId(),
                    resource.getResourceId(),
                    LocalTime.of(date.getHour(), date.getMinute()),
                    LocalTime.of(date.getHour() + 1, date.getMinute()),
                    date.toLocalDate()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Then
        Assertions.assertNotNull(reservation);
        Assertions.assertEquals(reservation.getResourceId(), resource.getResourceId());
    }

    @Test
    void test_make_a_reservation_on_closed_resource() {
        // Given
        User user = inMemoryUserRepository.create("DOE", "John", "jdoe@gmail.com");
        inMemoryUserRepository.save(user);

        Resource resource = inMemoryResourceRepository.create("1", "Salle de réunion de 10 personnes", new ArrayList<>(), horaires);
        inMemoryResourceRepository.add(resource);

        final LocalDateTime date = LocalDateTime.now().plusWeeks(2).with(DayOfWeek.SUNDAY).with(LocalTime.of(10, 0));
        // When
        Assertions.assertThrows(ResourceIsClosedException.class, () -> {
            reserveResource.reserve(
                    user.getUserId(),
                    resource.getResourceId(),
                    LocalTime.of(date.getHour(), date.getMinute()),
                    LocalTime.of(date.getHour() + 1, date.getMinute()),
                    date.toLocalDate()
            );
        });
    }

    @Test
    void test_make_a_conflictual_reservation() {
        // Given
        User user = inMemoryUserRepository.create("DOE", "John", "jdoe@gmail.com");
        User user2 = inMemoryUserRepository.create("DUPONT", "Jean", "jdupont@example.com");
        inMemoryUserRepository.save(user);
        inMemoryUserRepository.save(user2);

        Resource resource = inMemoryResourceRepository.create("1", "Salle de réunion de 10 personnes", new ArrayList<>(), horaires);
        inMemoryResourceRepository.add(resource);

        final LocalDateTime date = LocalDateTime.now().plusWeeks(2).with(DayOfWeek.MONDAY).with(LocalTime.of(10, 0));
        try {
            reserveResource.reserve(
                    user2.getUserId(),
                    resource.getResourceId(),
                    LocalTime.of(date.getHour(), date.getMinute()),
                    LocalTime.of(date.getHour() + 1, date.getMinute()),
                    date.toLocalDate()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Then
        Assertions.assertThrows(ConflictualReservationsException.class, () -> {
        // When
            reserveResource.reserve(
                    user.getUserId(),
                    resource.getResourceId(),
                    LocalTime.of(date.getHour() - 1, date.getMinute()),
                    LocalTime.of(date.getHour(), date.getMinute() + 30),
                    date.toLocalDate()
            );
        });
    }
}