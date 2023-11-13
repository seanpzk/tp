package seedu.edutrack.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class ClassNameTest {

    @Test
    public void isValidClassName() {
        // null name
        assertThrows(NullPointerException.class, () -> ClassName.isValidClassName(null));

        // invalid name
        assertFalse(ClassName.isValidClassName("cs 2103t")); // contains spaces

        // valid name
        assertTrue(ClassName.isValidClassName("abc")); // alphabets only
        assertTrue(ClassName.isValidClassName("123")); // numbers only
        assertTrue(ClassName.isValidClassName("cs2103_t")); // contains special characters
        assertTrue(ClassName.isValidClassName("cs2103t")); // alphanumeric characters
    }

    @Test
    public void isEmptyClassName() {
        assertThrows(NullPointerException.class, () -> ClassName.isEmptyClassName(null));

        assertTrue(ClassName.isEmptyClassName("")); // empty string
        assertTrue(ClassName.isEmptyClassName(" ")); // spaces only

        assertFalse(ClassName.isEmptyClassName("abc")); // alphabets only
        assertFalse(ClassName.isEmptyClassName("123")); // numbers only
        assertFalse(ClassName.isEmptyClassName("cs2103t")); // alphanumeric characters
    }

    @Test
    public void equals() {
        ClassName className = new ClassName("cs2103t");

        // same values -> returns true
        assertTrue(className.equals(new ClassName("cs2103t")));

        // same object -> returns true
        assertTrue(className.equals(className));

        // null -> returns false
        assertFalse(className.equals(null));

        // different types -> returns false
        assertFalse(className.equals(5.0f));

        // different values -> returns false
        assertFalse(className.equals(new ClassName("cs2100")));
    }
}
