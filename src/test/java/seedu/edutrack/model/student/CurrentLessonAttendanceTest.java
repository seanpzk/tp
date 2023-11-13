package seedu.edutrack.model.student;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedAbsentException;
import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedPresentException;

public class CurrentLessonAttendanceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CurrentLessonAttendance(null));
    }

    @Test
    public void getIsPresent() {
        CurrentLessonAttendance presentLessonAttendance = new CurrentLessonAttendance(true);
        assertTrue(presentLessonAttendance.getIsPresent());
        CurrentLessonAttendance absentLessonAttendance = new CurrentLessonAttendance(false);
        assertFalse(absentLessonAttendance.getIsPresent());
    }

    @Test
    public void setPresent_studentAbsent_success() {
        CurrentLessonAttendance absentLessonAttendance = new CurrentLessonAttendance(false);
        assertDoesNotThrow(() -> absentLessonAttendance.setPresent());
    }

    @Test
    public void setPresent_studentPresent_throwsStudentAlreadyMarkedPresentException() {
        CurrentLessonAttendance presentLessonAttendance = new CurrentLessonAttendance(true);
        assertThrows(StudentAlreadyMarkedPresentException.class, () -> presentLessonAttendance.setPresent());
    }

    @Test
    public void setAbsent_studentPresent_success() {
        CurrentLessonAttendance presentLessonAttendance = new CurrentLessonAttendance(true);
        assertDoesNotThrow(() -> presentLessonAttendance.setAbsent());
    }

    @Test
    public void setAbsent_studentAbsent_throwsStudentAlreadyMarkedAbsentException() {
        CurrentLessonAttendance absentLessonAttendance = new CurrentLessonAttendance(false);
        assertThrows(StudentAlreadyMarkedAbsentException.class, () -> absentLessonAttendance.setAbsent());
    }

    @Test
    public void toString_test() {
        CurrentLessonAttendance presentLessonAttendance = new CurrentLessonAttendance(true);
        String expectedPresent = "Y";
        assertEquals(expectedPresent, presentLessonAttendance.toString());
        CurrentLessonAttendance absentLessonAttendance = new CurrentLessonAttendance(false);
        String expectedAbsent = "N";
        assertEquals(expectedAbsent, absentLessonAttendance.toString());
    }

    @Test
    public void equals() {
        CurrentLessonAttendance presentLessonAttendance = new CurrentLessonAttendance(true);
        CurrentLessonAttendance absentLessonAttendance = new CurrentLessonAttendance(false);
        CurrentLessonAttendance presentLessonAttendanceDuplicate = new CurrentLessonAttendance(true);
        CurrentLessonAttendance absentLessonAttendanceDuplicate = new CurrentLessonAttendance(false);

        // Same object -> returns true
        assertTrue(presentLessonAttendance.equals(presentLessonAttendance));
        assertTrue(absentLessonAttendance.equals(absentLessonAttendance));

        // Same isPresent but different object -> returns true
        assertTrue(presentLessonAttendanceDuplicate.equals(presentLessonAttendance));
        assertTrue(absentLessonAttendanceDuplicate.equals(absentLessonAttendance));

        // Different isPresent value -> returns false
        assertFalse(presentLessonAttendance.equals(absentLessonAttendance));

        // null -> returns false
        assertFalse(presentLessonAttendance.equals(null));
    }

    @Test
    public void hashCode_sameInstance_sameHashCode() {
        CurrentLessonAttendance presentLessonAttendance = new CurrentLessonAttendance(true);
        assertTrue(presentLessonAttendance.hashCode() == presentLessonAttendance.hashCode());
    }
}
