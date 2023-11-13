package seedu.edutrack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edutrack.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.UniqueStudentList;
import seedu.edutrack.testutil.StudentBuilder;
import seedu.edutrack.testutil.TypicalClasses;

public class MarkAllStudentAbsentCommandTest {
    private Model model;

    @Test
    public void constructor_nullClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkAllStudentAbsentCommand(null));
    }

    @Test
    public void constructor_validIndex_success() {
        assertDoesNotThrow(() -> new MarkAllStudentAbsentCommand(Index.fromOneBased(1)));
    }

    @Test
    public void execute_userIsUnmarkedAndMarked_success() {
        // Set up test case
        model = new ModelManager(TypicalClasses.getTypicalEduTrack(), new UserPrefs());
        Class cs2109 = new Class(new ClassName("cs2109"), new UniqueStudentList(),
                new Memo(" "), new Schedule());
        cs2109.setTotalLessons(10);

        // set up marked student
        Student studentToRemove1 = new StudentBuilder().withName("Amy Bee")
                .withCurrentLessonAttendance(true).withLessonsAttended(4).build();
        model.addStudent(studentToRemove1);
        model.addClass(cs2109);
        model.addStudentToClass(studentToRemove1, cs2109);

        // set up unmarked student
        Student studentToRemove2 = new StudentBuilder().withName("John")
                .withCurrentLessonAttendance(false).withLessonsAttended(2).build();
        model.addStudent(studentToRemove2);
        model.addStudentToClass(studentToRemove2, cs2109);

        // set up expected students
        // previously marked
        Student editedStudent1 = new StudentBuilder().withName("Amy Bee")
                .withCurrentLessonAttendance(false).withLessonsAttended(3).build();
        // previously unmarked
        Student editedStudent2 = new StudentBuilder().withName("John")
                .withCurrentLessonAttendance(false).withLessonsAttended(2).build();
        MarkAllStudentAbsentCommand command = new MarkAllStudentAbsentCommand(Index.fromOneBased(4));
        Model expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent1);
        expectedModel.setStudent(model.getFilteredStudentList().get(1), editedStudent2);

        String expectedMessage = String.format(MarkAllStudentAbsentCommand.MESSAGE_UNMARK_STUDENT_ATTENDANCE_SUCCESS,
                "CS2109");
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        MarkAllStudentAbsentCommand firstMarkAllStudentAbsentCommand = new MarkAllStudentAbsentCommand(
                Index.fromOneBased(1));
        MarkAllStudentAbsentCommand secondMarkAllStudentAbsentCommand = new MarkAllStudentAbsentCommand(
                Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(firstMarkAllStudentAbsentCommand.equals(firstMarkAllStudentAbsentCommand));

        // same values -> returns true
        MarkAllStudentAbsentCommand firstMarkAllStudentAbsentCommandCopy = new MarkAllStudentAbsentCommand(
                Index.fromOneBased(1));
        assertTrue(firstMarkAllStudentAbsentCommand.equals(firstMarkAllStudentAbsentCommandCopy));

        // different types -> returns false
        assertFalse(firstMarkAllStudentAbsentCommand.equals(1));

        // different instance -> returns false
        assertFalse(firstMarkAllStudentAbsentCommand.equals(secondMarkAllStudentAbsentCommand));
    }

    @Test
    public void toString_test() {
        Index classIndex = Index.fromOneBased(5);
        MarkAllStudentAbsentCommand command = new MarkAllStudentAbsentCommand(classIndex);
        String expected = MarkAllStudentAbsentCommand.class.getCanonicalName() + "{classIndex="
                + classIndex.toString() + "}";
        assertEquals(command.toString(), expected);
    }
}


