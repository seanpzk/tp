package seedu.edutrack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edutrack.testutil.Assert.assertThrows;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_FIRST_CLASS;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.student.Name;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.exceptions.StudentNotFoundException;
import seedu.edutrack.testutil.StudentBuilder;
import seedu.edutrack.testutil.TypicalClasses;

public class RemoveStudentCommandTest {
    private Model model;
    private Class classStub;
    private Student studentStub;
    private ClassName classStubName;
    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalClasses.getTypicalEduTrack(), new UserPrefs());
        classStub = model.getFilteredClassList().get(INDEX_FIRST_CLASS.getZeroBased());
        studentStub = new StudentBuilder().build();
        model.addStudent(studentStub);
        if (!classStub.hasStudentInClass(studentStub)) {
            classStub.addStudentToClass(studentStub);
        }
        classStubName = classStub.getClassName();
    }

    @AfterEach
    public void cleanUp() {
        try {
            model.deleteStudent(studentStub, classStub);
        } catch (StudentNotFoundException e) {
            System.out.println("Cleaning up after test: No student to be removed");
        }
    }


    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveStudentCommand(null, classStubName));
    }
    @Test
    public void constructor_nullClassName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveStudentCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validStudentIndex_success() {
        Student studentToDelete = studentStub;
        Name studentName = studentToDelete.getName();
        RemoveStudentCommand removeStudentCommand = new RemoveStudentCommand(Index.fromOneBased(1), classStubName);
        String expectedMessage = String.format(RemoveStudentCommand.MESSAGE_REMOVE_STUDENT_SUCCESS, studentName,
            classStubName.toString());
        ModelManager expectedModel = new ModelManager(TypicalClasses.getTypicalEduTrack(), new UserPrefs());

        assertCommandSuccess(removeStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_outOfBoundsStudentIndex_throwsCommandException() {
        Index studentIndexToRemove = Index.fromOneBased(3);
        RemoveStudentCommand removeStudentCommand = new RemoveStudentCommand(studentIndexToRemove, classStubName);

        assertCommandFailure(removeStudentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        RemoveStudentCommand firstRemoveStudentCommand = new RemoveStudentCommand(Index.fromOneBased(1),
                classStubName);
        RemoveStudentCommand secondRemoveStudentCommand = new RemoveStudentCommand(Index.fromOneBased(2),
                classStubName);
        // same object -> returns true
        assertTrue(firstRemoveStudentCommand.equals(firstRemoveStudentCommand));

        // same values -> returns true
        RemoveStudentCommand firstRemoveStudentCommandCopy = new RemoveStudentCommand(Index.fromOneBased(1),
                classStubName);
        assertTrue(firstRemoveStudentCommand.equals(firstRemoveStudentCommandCopy));

        // different types -> return false
        assertFalse(firstRemoveStudentCommand.equals(1));

        // null -> return false
        assertFalse(firstRemoveStudentCommand.equals(null));

        // different student
        assertFalse(firstRemoveStudentCommand.equals(secondRemoveStudentCommand));
    }
    @Test
    public void toStringMethod() {
        Index indexToRemove = Index.fromOneBased(1);
        RemoveStudentCommand removeStudentCommand = new RemoveStudentCommand(indexToRemove, classStubName);
        String expected = RemoveStudentCommand.class.getCanonicalName() + "{studentClassName="
                + classStubName.toString() + ", studentIndex=" + indexToRemove.toString() + "}";

        assertEquals(expected, removeStudentCommand.toString());
    }

}
