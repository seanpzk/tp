package seedu.edutrack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edutrack.testutil.Assert.assertThrows;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_FIRST_CLASS;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_SECOND_CLASS;
import static seedu.edutrack.testutil.TypicalStudents.getTypicalEduTrack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.exceptions.StudentNotFoundException;
import seedu.edutrack.testutil.ClassBuilder;
import seedu.edutrack.testutil.StudentBuilder;
import seedu.edutrack.testutil.TypicalClasses;

public class SetLessonCommandTest {
    private Model model = new ModelManager(getTypicalEduTrack(), new UserPrefs());
    private Class classStub;
    private Student studentStub;
    private ClassName classStubName;
    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalClasses.getTypicalEduTrack(), new UserPrefs());
        classStub = model.getFilteredClassList().get(INDEX_FIRST_CLASS.getZeroBased());
        classStubName = classStub.getClassName();
    }



    @Test
    public void constructor_nullClassName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetLessonCommand(null, 0));
    }

    @Test
    public void constructor_negativeNumLessons_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new SetLessonCommand(classStubName, -1));
    }

    @Test
    public void execute_valid_success() {
        SetLessonCommand setLessonCommand = new SetLessonCommand(classStubName, 0);
        String expectedMessage = String.format(SetLessonCommand.MESSAGE_SET_LESSON_SUCCESS, classStubName, 0);
        ModelManager expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());
        assertCommandSuccess(setLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClass_throwsClassNotFoundException() {
        ClassName invalidClassName = new ClassName("invalidClass");
        SetLessonCommand setLessonCommand = new SetLessonCommand(invalidClassName, 0);

        assertCommandFailure(setLessonCommand, model, String.format(Messages.MESSAGE_MISSING_CLASS_NAME,
                invalidClassName.toString()));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        SetLessonCommand setLessonCommand = new SetLessonCommand(classStubName, 0);
        assertThrows(NullPointerException.class, () -> setLessonCommand.execute(null));
    }

    @Test
    public void execute_reduceStudentAttendance_success() {
        SetLessonCommand setLessonCommand = new SetLessonCommand(classStubName, 3);

        studentStub = new StudentBuilder().build();
        model.addStudent(studentStub);
        classStub.addStudentToClass(studentStub);
        classStub.setTotalLessons(10);
        studentStub.setLessonsAttended(5);

        Class expectedClass = new ClassBuilder(classStub).build();
        Student studentStubCopy = new StudentBuilder(studentStub).build();
        Model expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());
        expectedModel.setClass(Index.fromOneBased(1), expectedClass);
        expectedClass.setTotalLessons(3);
        expectedClass.addStudentToClass(studentStubCopy);
        studentStubCopy.setLessonsAttended(3);

        try {
            setLessonCommand.execute(model);
        } catch (CommandException e) {
            Assertions.fail();
        }

        assertEquals(model.getStudent(model.getStudentList(classStub), Index.fromOneBased(1)),
                expectedModel.getStudent(expectedModel.getStudentList(expectedClass), Index.fromOneBased(1)));

        // clean up
        for (Class c : model.getFilteredClassList()) {
            c.setTotalLessons(0);
        }
        try {
            model.deleteStudent(studentStub, classStub);
        } catch (StudentNotFoundException e) {
            System.out.println("Cleaning up after test: No student to be removed");
        }
    }

    @Test
    public void equals() {
        Class classStub2 = model.getFilteredClassList().get(INDEX_SECOND_CLASS.getZeroBased());
        ClassName classStubName2 = classStub2.getClassName();

        SetLessonCommand firstSetLessonCommand = new SetLessonCommand(classStubName, 0);
        SetLessonCommand secondSetLessonCommand = new SetLessonCommand(classStubName2, 0);
        SetLessonCommand thirdSetLessonCommand = new SetLessonCommand(classStubName, 1);

        // same object -> returns true
        assertTrue(firstSetLessonCommand.equals(firstSetLessonCommand));

        // same values -> returns true
        SetLessonCommand firstSetLessonCommandCopy = new SetLessonCommand(classStubName, 0);
        assertTrue(firstSetLessonCommand.equals(firstSetLessonCommandCopy));

        // different types -> return false
        assertFalse(firstSetLessonCommand.equals(1));

        // null -> return false
        assertFalse(firstSetLessonCommand.equals(null));

        // different classname
        assertFalse(firstSetLessonCommand.equals(secondSetLessonCommand));

        // different numlesson
        assertFalse(firstSetLessonCommand.equals(thirdSetLessonCommand));
    }

    @Test
    public void toStringMethod() {
        SetLessonCommand setLessonCommand = new SetLessonCommand(classStubName, 0);
        String expected = SetLessonCommand.class.getCanonicalName() + "{className=" + classStubName.toString()
                + ", numLessons=" + "0}";

        assertEquals(expected, setLessonCommand.toString());
    }
}
