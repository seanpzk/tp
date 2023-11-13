package seedu.edutrack.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ScheduleTest {
    @Test
    public void isValidSchedule() {
        // empty schedule
        assertTrue(Schedule.isValidSchedule(""));

        // invalid schedule
        assertFalse(Schedule.isValidSchedule("wed 10:00-12:00")); // missing comma
        assertFalse(Schedule.isValidSchedule("friday, 08:35-09:30")); // wrong day format
        assertFalse(Schedule.isValidSchedule("thu, 8:35-09:30")); // wrong time format
        assertFalse(Schedule.isValidSchedule("thu, 8:35 - 09:30")); // wrong time format

        // invalid time range
        assertFalse(Schedule.isValidSchedule("mon, 13:00-12:00"));
        assertFalse(Schedule.isValidSchedule("mon, 10:60-12:00"));

        // valid schedule
        assertTrue(Schedule.isValidSchedule("wed, 10:00-12:00"));
        assertTrue(Schedule.isValidSchedule("fri, 08:35-09:30"));
    }

    @Test
    public void equals() {
        Schedule schedule = new Schedule("mon, 08:00-09:00");
        Schedule sameSchedule = new Schedule("mon, 08:00-09:00");
        Schedule differentSchedule = new Schedule("tue, 08:00-09:00");

        // same value -> returns true
        assertTrue(schedule.equals(sameSchedule));

        // same object -> returns true
        assertTrue(schedule.equals(schedule));

        // null -> returns false
        assertFalse(schedule.equals(null));

        // different types -> returns false
        assertFalse(schedule.equals(5.0f));

        // different values -> returns false
        assertFalse(schedule.equals(differentSchedule));
    }
}
