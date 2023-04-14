package use_case.institution;

import infrastructure.common.UUIDGenerator;
import infrastructure.factories.ReservationFactory;
import infrastructure.factories.ResourceFactory;
import infrastructure.factories.UserFactory;
import infrastructure.repositories.InMemoryReservationRepository;
import infrastructure.repositories.InMemoryResourceRepository;
import infrastructure.repositories.InMemoryUserRepository;
import model.common.IdGenerator;
import model.reservation.ConflictualReservationsException;
import model.reservation.Reservation;
import model.reservation.ReservationId;
import model.reservation.ReservationRepository;
import model.resource.*;
import model.user.User;
import model.user.UserNotFoundException;
import model.user.UserRepository;
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
    static UserFactory userFactory;
    static ResourceFactory resourceFactory;
    static UserRepository userRepository;
    static ReservationRepository reservationRepository;
    static ResourceRepository resourceRepository;

    static ResourceTimetables resourceTimetables = null;

    @BeforeAll
    static void beforeAll() {
        IdGenerator idGenerator = new UUIDGenerator();
        userFactory = new UserFactory(idGenerator);
        resourceFactory = new ResourceFactory(idGenerator);

        userRepository = new InMemoryUserRepository();
        reservationRepository = new InMemoryReservationRepository();
        resourceRepository = new InMemoryResourceRepository();
        reserveResource = new ReserveResource(
                reservationRepository,
                resourceRepository,
                new ReservationFactory(idGenerator),
                userRepository
        );

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

        resourceTimetables = new ResourceTimetables(horaires);
    }

    @Test
    void test_make_a_successful_reservation() {
        // Given
        User user = userFactory.create("DOE", "John", "jdoe@gmail.com");
        userRepository.save(user);

        Resource resource = resourceFactory.create("1", "Salle de réunion de 10 personnes", new ArrayList<>(), resourceTimetables);
        resourceRepository.add(resource);


        final LocalDateTime date = LocalDateTime.now().plusWeeks(2).with(DayOfWeek.MONDAY).with(LocalTime.of(10, 0));
        // When
        Reservation reservation = null;
        Resource resourceName = null;
        try {
            reservation = reserveResource.reserve(
                    user.getUserId(),
                    resource.getResourceId(),
                    LocalTime.of(date.getHour(), date.getMinute()),
                    LocalTime.of(date.getHour() + 1, date.getMinute()),
                    date.toLocalDate()
            );
            resourceName = resourceRepository.findById(reservation.getResourceId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Then
        Assertions.assertNotNull(reservation);
        Assertions.assertEquals(reservation.getResourceId(), resource.getResourceId());
        Assertions.assertEquals(resourceName.getName(), "Salle de réunion de 10 personnes");
    }

    @Test
    void test_make_a_reservation_on_closed_resource() {
        // Given
        User user = userFactory.create("DOE", "John", "jdoe@gmail.com");
        userRepository.save(user);

        Resource resource = resourceFactory.create("1", "Salle de réunion de 10 personnes", new ArrayList<>(), resourceTimetables);
        resourceRepository.add(resource);

        final LocalDateTime date = LocalDateTime.now().plusWeeks(2).with(DayOfWeek.SUNDAY).with(LocalTime.of(10, 0));
        // When
        Assertions.assertThrows(ResourceIsClosedException.class, () -> reserveResource.reserve(
                user.getUserId(),
                resource.getResourceId(),
                LocalTime.of(date.getHour(), date.getMinute()),
                LocalTime.of(date.getHour() + 1, date.getMinute()),
                date.toLocalDate()
        ));
    }

    @Test
    void test_make_a_conflictual_reservation() {
        // Given
        User user = userFactory.create("DOE", "John", "jdoe@gmail.com");
        User user2 = userFactory.create("DUPONT", "Jean", "jdupont@example.com");
        userRepository.save(user);
        userRepository.save(user2);

        Resource resource = resourceFactory.create("1", "Salle de réunion de 10 personnes", new ArrayList<>(), resourceTimetables);
        resourceRepository.add(resource);

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

    @Test
    void test_make_a_reservation_on_a_non_existing_resource() {
        // Given
        User user = userFactory.create("DOE", "John", "jdoe@gmail.com");
        userRepository.save(user);

        Resource resource = resourceFactory.create("1", "Salle de réunion de 10 personnes", new ArrayList<>(), resourceTimetables);
        final LocalDateTime date = LocalDateTime.now().plusWeeks(2).with(DayOfWeek.MONDAY).with(LocalTime.of(10, 0));

        // Then
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            // When
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
    void test_make_a_reservation_on_a_non_existing_user() {
        // Given
        User user = userFactory.create("DOE", "John", "jdoe@gmail.com");

        Resource resource = resourceFactory.create("1", "Salle de réunion de 10 personnes", new ArrayList<>(), resourceTimetables);
        resourceRepository.add(resource);

        final LocalDateTime date = LocalDateTime.now().plusWeeks(2).with(DayOfWeek.MONDAY).with(LocalTime.of(10, 0));

        // Then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            // When
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
    void test_reservation_id_equality() {
        ReservationId reservationId = new ReservationId("12456");
        ReservationId reservationId2 = new ReservationId("12456");
        Assertions.assertEquals(reservationId, reservationId2);
    }

    @Test
    void test_reservation_id_hash() {
        ReservationId reservationId = new ReservationId("12456");
        ReservationId reservationId2 = new ReservationId("12456");
        Assertions.assertEquals(reservationId.hashCode(), reservationId2.hashCode());
    }

    @Test
    void test_reservation_id_value() {
        ReservationId reservationId = new ReservationId("12456");
        Assertions.assertEquals(reservationId.getValue(), "12456");
    }



}