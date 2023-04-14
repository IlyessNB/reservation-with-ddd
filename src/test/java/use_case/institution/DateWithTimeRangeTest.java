package use_case.institution;

import model.reservation.DateWithTimeRange;
import model.reservation.ReservationDateIsInPastException;
import model.reservation.ReservationIncoherentTimeRangeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

class DateWithTimeRangeTest {
    @Test
    void is_same_date_should_return_true_when_date_is_the_same() {
        DateWithTimeRange dateWithTimeRange = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.of(2020, 1, 1));
        DateWithTimeRange dateWithTimeRange2 = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.of(2020, 1, 1));
        Assertions.assertTrue(dateWithTimeRange.isSameDate(dateWithTimeRange2.getDate()));
    }

    @Test
    void is_same_date_should_return_false_when_date_is_not_the_same() {
        DateWithTimeRange dateWithTimeRange = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.of(2020, 1, 1));
        DateWithTimeRange dateWithTimeRange2 = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.of(2020, 1, 2));
        Assertions.assertFalse(dateWithTimeRange.isSameDate(dateWithTimeRange2.getDate()));
    }

    @Test
    void is_overlapping_should_return_true_when_compared_time_range_start_is_during() {
        DateWithTimeRange dateWithTimeRange = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.of(2020, 1, 1));
        DateWithTimeRange dateWithTimeRange2 = DateWithTimeRange.of(LocalTime.of(10, 30), LocalTime.of(11, 30), LocalDate.of(2020, 1, 1));
        Assertions.assertTrue(dateWithTimeRange.isOverLapping(dateWithTimeRange2));
    }

    @Test
    void is_overlapping_should_return_true_when_compared_time_range_end_is_during() {
        DateWithTimeRange dateWithTimeRange = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.of(2020, 1, 1));
        DateWithTimeRange dateWithTimeRange2 = DateWithTimeRange.of(LocalTime.of(9, 30), LocalTime.of(10, 30), LocalDate.of(2020, 1, 1));
        Assertions.assertTrue(dateWithTimeRange.isOverLapping(dateWithTimeRange2));
    }

    @Test
    void is_overlapping_should_return_true_when_compared_time_range_is_same() {
        DateWithTimeRange dateWithTimeRange = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.of(2020, 1, 1));
        DateWithTimeRange dateWithTimeRange2 = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.of(2020, 1, 1));
        Assertions.assertTrue(dateWithTimeRange.isOverLapping(dateWithTimeRange2));
    }

    @Test
    void is_overlapping_should_not_throw_when_compared_time_range_start_is_before_and_end_is_after() {
        DateWithTimeRange dateWithTimeRange = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.of(2020, 1, 1));
        DateWithTimeRange dateWithTimeRange2 = DateWithTimeRange.of(LocalTime.of(9, 30), LocalTime.of(11, 30), LocalDate.of(2020, 1, 1));
        Assertions.assertTrue(dateWithTimeRange.isOverLapping(dateWithTimeRange2));
    }

    @Test
    void check_time_coherence_should_throw_exception_when_start_time_is_before_end_time() {
        DateWithTimeRange dateWithTimeRange = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.of(2020, 1, 1));
        Assertions.assertDoesNotThrow(dateWithTimeRange::checkTimeCoherence);
    }

    @Test
    void check_time_coherence_should_throw_exception_when_end_time_is_equal_to_start_time() {
        DateWithTimeRange dateWithTimeRange = DateWithTimeRange.of(LocalTime.of(11, 0), LocalTime.of(11, 0), LocalDate.of(2020, 1, 1));
        Assertions.assertThrows(ReservationIncoherentTimeRangeException.class, dateWithTimeRange::checkTimeCoherence);
    }

    @Test
    void check_time_coherence_should_throw_exception_when_start_time_is_after_end_time() {
        DateWithTimeRange dateWithTimeRange = DateWithTimeRange.of(LocalTime.of(11, 0), LocalTime.of(10, 0), LocalDate.of(2020, 1, 1));
        Assertions.assertThrows(ReservationIncoherentTimeRangeException.class, dateWithTimeRange::checkTimeCoherence);
    }

    @Test
    void check_is_not_in_past_should_not_throw_when_date_is_not_in_past() {
        DateWithTimeRange dateWithTimeRange = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.now().plusDays(1));
        Assertions.assertDoesNotThrow(dateWithTimeRange::checkIsNotInPast);
    }

    @Test
    void check_is_not_in_past_should_throw_exception_when_date_is_in_past() {
        DateWithTimeRange dateWithTimeRange = DateWithTimeRange.of(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.now().minusDays(1));
        Assertions.assertThrows(ReservationDateIsInPastException.class, dateWithTimeRange::checkIsNotInPast);
    }
}