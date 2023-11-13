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

import org.junit.jupiter.api.AfterEach;
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

public class StartLessonCommandTest {
    private Model model = new ModelManager(getTypicalEduTrack(), new UserPrefs());
    private Class classStub;
    private Student studentStub;
    private ClassName classStubName;
    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalClasses.getTypicalEduTrack(), new UserPrefs());
        classStub = model.getFilteredClassList().get(INDEX_FIRST_CLASS.getZeroBased());
        classStubName = classStub.getClassName();
        studentStub = new StudentBuilder().build();
        model.addStudent(studentStub);
        classStub.addStudentToClass(studentStub);
    }

    @AfterEach
    public void cleanUp() {
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
    public void constructor_nullClassName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StartLessonCommand(null));
    }

    @Test
    public void execute_valid_success() {
        StartLessonCommand startLessonCommand = new StartLessonCommand(classStubName);
        String expectedMessage = String.format(StartLessonCommand.MESSAGE_START_LESSON_SUCCESS,
                classStubName.toString());
        ModelManager expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());
        assertCommandSuccess(startLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClass_throwsClassNotFoundException() {
        ClassName invalidClassName = new ClassName("invalidClass");
        StartLessonCommand startLessonCommand = new StartLessonCommand(invalidClassName);

        assertCommandFailure(startLessonCommand, model, String.format(Messages.MESSAGE_MISSING_CLASS_NAME,
                invalidClassName.toString()));
    }

    @Test
    public void execute_lessonIncrease_success() {
        StartLessonCommand startLessonCommand = new StartLessonCommand(classStubName);
        Class expectedClass = new ClassBuilder(classStub).build();
        ClassName expectedClassName = expectedClass.getClassName();
        expectedClass.setTotalLessons(expectedClass.getTotalLessons() + 1);
        Model expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());
        expectedModel.setClass(Index.fromOneBased(1), expectedClass);
        try {
            startLessonCommand.execute(model);
        } catch (CommandException e) {
            Assertions.fail();
        }
        assertEquals(model.getClass(classStubName).getTotalLessons(),
                expectedModel.getClass(expectedClassName).getTotalLessons());
    }

    @Test
    public void execute_studentMarkAbsent_success() {
        StartLessonCommand startLessonCommand = new StartLessonCommand(classStubName);
        studentStub.getCurrentAttendance().setPresent();

        Class expectedClass = new ClassBuilder(classStub).build();
        Student studentStubCopy = new StudentBuilder(studentStub).build();
        Model expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());
        expectedModel.setClass(Index.fromOneBased(1), expectedClass);
        expectedClass.addStudentToClass(studentStubCopy);
        studentStubCopy.markStudentAbsent();

        try {
            startLessonCommand.execute(model);
        } catch (CommandException e) {
            Assertions.fail();
        }
        assertEquals(model.getStudent(model.getStudentList(classStub), Index.fromOneBased(1)),
                expectedModel.getStudent(expectedModel.getStudentList(expectedClass), Index.fromOneBased(1)));
    }

    @Test
    public void equals() {
        Class classStub2 = model.getFilteredClassList().get(INDEX_SECOND_CLASS.getZeroBased());
        ClassName classStubName2 = classStub2.getClassName();

        StartLessonCommand firstStartLessonCommand = new StartLessonCommand(classStubName);
        StartLessonCommand secondStartLessonCommand = new StartLessonCommand(classStubName2);
        // same object -> returns true
        assertTrue(firstStartLessonCommand.equals(firstStartLessonCommand));

        // same values -> returns true
        StartLessonCommand firstStartLessonCommandCopy = new StartLessonCommand(classStubName);
        assertTrue(firstStartLessonCommand.equals(firstStartLessonCommandCopy));

        // different types -> return false
        assertFalse(firstStartLessonCommand.equals(1));

        // null -> return false
        assertFalse(firstStartLessonCommand.equals(null));

        // different classname
        assertFalse(firstStartLessonCommand.equals(secondStartLessonCommand));
    }

    @Test
    public void toStringMethod() {
        StartLessonCommand startLessonCommand = new StartLessonCommand(classStubName);
        String expected = StartLessonCommand.class.getCanonicalName() + "{className=" + classStubName.toString() + "}";

        assertEquals(expected, startLessonCommand.toString());
    }
}
