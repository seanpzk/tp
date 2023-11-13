package seedu.edutrack.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.student.UniqueStudentList;

class ClassTest {
    private final Class c = new Class(new ClassName("abc"), new UniqueStudentList(), new Memo(" "), new Schedule());

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Class(null, new UniqueStudentList(),
                new Memo(" "), new Schedule()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(c.equals(new Class(new ClassName("abc"), new UniqueStudentList(),
                new Memo(" "), new Schedule())));

        // same object -> returns true
        assertTrue(c.equals(c));

        // null -> returns false
        assertFalse(c.equals(null));

        // different types -> returns false
        assertFalse(c.equals(5.0f));

        // different values -> returns false
        assertFalse(c.equals(new Class(new ClassName("def"), new UniqueStudentList(),
                new Memo(" "), new Schedule())));
    }

    @Test
    public void toStringMethod() {
        String expected = Class.class.getCanonicalName() + "{className=" + c.getClassName()
                + ", studentList=" + c.getUniqueStudentList()
                + ", classSchedule=" + c.getClassSchedule()
                + ", classMemo=" + c.getClassMemo()
                + ", totalLessons=" + c.getTotalLessons() + "}";
        assertEquals(expected, c.toString());
    }
}
