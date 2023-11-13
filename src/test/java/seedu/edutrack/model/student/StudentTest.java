package seedu.edutrack.model.student;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.edutrack.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.edutrack.testutil.TypicalStudents.ALICE;
import static seedu.edutrack.testutil.TypicalStudents.BOB;
import static seedu.edutrack.testutil.TypicalStudents.DANIEL;

import org.junit.jupiter.api.Test;

import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedAbsentException;
import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedPresentException;
import seedu.edutrack.testutil.StudentBuilder;

public class StudentTest {

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(ALICE.isSameStudent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStudent(null));

        // same name, all other attributes different -> returns true
        Student editedAlice = new StudentBuilder(ALICE).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Student editedBob = new StudentBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameStudent(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new StudentBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameStudent(editedBob));
    }

    @Test
    public void duplicateStudent() {
        Student duplicateAlice = ALICE.duplicateStudent();
        assertEquals(ALICE, duplicateAlice);
        // Check if different instance
        assertFalse(ALICE == duplicateAlice);
    }

    @Test
    public void markStudentPresent_studentAttended_throwsStudentAlreadyMarkedPresentException() {
        // BOB attended lesson before
        Student bob = new StudentBuilder(BOB).build();
        Class c = new Class(new ClassName("CS1101S"), new UniqueStudentList(), new Memo(" "), new Schedule());
        bob.setStudentClass(c);
        c.setTotalLessons(6);
        assertThrows(StudentAlreadyMarkedPresentException.class, () -> bob.markStudentPresent());
    }

    @Test
    public void markStudentPresent_studentDidNotAttend_success() {
        // bob has not attended lesson before
        Student bob = new StudentBuilder().withName(VALID_NAME_BOB)
                .withCurrentLessonAttendance(false)
                .withLessonsAttended(0)
                .build();
        Class c = new Class(new ClassName("CS1101S"), new UniqueStudentList(), new Memo(" "), new Schedule());
        bob.setStudentClass(c);
        c.startLesson();
        bob.startNewLesson();
        assertDoesNotThrow(() -> bob.markStudentPresent());
    }

    @Test
    public void markStudentAbsent_studentDidNotAttend_throwsStudentAlreadyMarkedAbsentException() {
        // DANIEL was already marked absent
        Student daniel = new StudentBuilder(DANIEL).build();
        assertThrows(StudentAlreadyMarkedAbsentException.class, () -> daniel.markStudentAbsent());
    }

    @Test
    public void markStudentAbsent_studentAttended_success() {
        // bob was marked present beforehand
        Student bob = new StudentBuilder().withName(VALID_NAME_BOB)
                .withCurrentLessonAttendance(true)
                .withLessonsAttended(5)
                .build();
        assertDoesNotThrow(() -> bob.markStudentAbsent());
    }
    @Test
    public void getAttendanceStringRep() {
        // BOB was marked present beforehand
        // DANIEL was already marked absent
        Student bob = new StudentBuilder(BOB).build();
        Student daniel = new StudentBuilder(DANIEL).build();
        assertEquals(bob.getAttendanceStringRep(), "Y");
        assertEquals(daniel.getAttendanceStringRep(), "N");
    }

    @Test
    public void getTotalAttendanceStringRep() {
        Integer lessonsAttendedFirstStudent = 5;
        Student firstStudent = new StudentBuilder().withName(VALID_NAME_BOB)
                .withCurrentLessonAttendance(true)
                .withLessonsAttended(lessonsAttendedFirstStudent)
                .build();
        Integer lessonsAttendedSecondStudent = 2;
        Student secondStudent = new StudentBuilder().withName(VALID_NAME_AMY)
                .withCurrentLessonAttendance(true)
                .withLessonsAttended(lessonsAttendedSecondStudent)
                .build();
        assertEquals(firstStudent.getTotalAttendanceStringRep(),
                lessonsAttendedFirstStudent.toString());
        assertEquals(secondStudent.getTotalAttendanceStringRep(),
                lessonsAttendedSecondStudent.toString());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Student.class.getCanonicalName()
                + "{name=" + ALICE.getName() + ","
                + " id=" + ALICE.getId() + ","
                + " memo=" + ALICE.getMemo() + ","
                + " currentLessonAttendance=" + ALICE.getCurrentAttendance() + ","
                + " lessonsAttended=" + ALICE.getLessonsAttended() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
