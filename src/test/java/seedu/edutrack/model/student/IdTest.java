package seedu.edutrack.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Id(null));
    }

    @Test
    public void constructor_invalidId_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new Id(invalidId));
    }

    @Test
    public void isValidId() {
        // null email
        assertThrows(NullPointerException.class, () -> Id.isValidId(null));

        // blank email
        assertFalse(Id.isValidId("")); // empty string
        assertFalse(Id.isValidId(" ")); // spaces only

        // missing parts
        assertFalse(Id.isValidId("0122345Z")); // missing A at the start
        assertFalse(Id.isValidId("AZ")); // missing numbers in the middle
        assertFalse(Id.isValidId("A0123483")); // missing [A-Z] at the end

        // invalid parts
        assertFalse(Id.isValidId("B0123458Z")); // invalid first character
        assertFalse(Id.isValidId("AB012348H")); // more than 1 alphabetical character
        assertFalse(Id.isValidId("a0128376Z")); // not capitalised first character
        assertFalse(Id.isValidId("A0128376z")); // not capitalised end character
        assertFalse(Id.isValidId("a0128376z")); // not capitalised first and last character
        assertFalse(Id.isValidId("A0123483Z ")); // trailing space
        assertFalse(Id.isValidId(" A0123483Z")); // whitespace at the start
        assertFalse(Id.isValidId("A01234.3Z ")); // invalid characters
        assertFalse(Id.isValidId("A012 3483Z ")); // random whitespace

        // valid email
        assertTrue(Id.isValidId("A123Z")); // less than 8 numbers in the middle
        assertTrue(Id.isValidId("A0123456A")); // End with A
        assertTrue(Id.isValidId("A0123456Z")); // End with Z
    }

    @Test
    public void equals() {
        Id id = new Id("A0000000Z");

        // same values -> returns true
        assertTrue(id.equals(new Id("A0000000Z")));

        // same object -> returns true
        assertTrue(id.equals(id));

        // null -> returns false
        assertFalse(id.equals(null));

        // different types -> returns false
        assertFalse(id.equals(5.0f));

        // different values -> returns false
        assertFalse(id.equals(new Id("A11111111Z")));
    }

    @Test
    public void hashCode_sameInstance_sameHashCode() {
        Id presentLessonAttendance = new Id("A0123323P");
        assertTrue(presentLessonAttendance.hashCode() == presentLessonAttendance.hashCode());
    }
}
