package seedu.edutrack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.testutil.Assert.assertThrows;
import static seedu.edutrack.testutil.TypicalClasses.getTypicalEduTrack;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_FIRST_CLASS;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_SECOND_CLASS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.testutil.StudentBuilder;

public class AddStudentCommandTest {
    private Model model;
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEduTrack(), new UserPrefs());
    }

    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(null, Index.fromZeroBased(0)));
    }

    @Test
    public void execute_studentAcceptedByClass_addSuccessful() throws Exception {
        Class classToAddStudent = model.getFilteredClassList().get(INDEX_FIRST_CLASS.getZeroBased());
        Student validPerson = new StudentBuilder().build();

        AddStudentCommand addStudentCommand = new AddStudentCommand(validPerson, INDEX_FIRST_CLASS);

        String expectedMessage = String.format(AddStudentCommand.MESSAGE_ADD_STUDENT_SUCCESS,
                Messages.formatStudent(validPerson), Messages.formatClass(classToAddStudent));

        CommandResult result = addStudentCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Class classToAddStudent = model.getFilteredClassList().get(INDEX_SECOND_CLASS.getZeroBased());
        Student validPerson = new StudentBuilder().build();
        model.addStudentToClass(validPerson, classToAddStudent);

        AddStudentCommand addStudent = new AddStudentCommand(validPerson, INDEX_SECOND_CLASS);

        assertThrows(CommandException.class,
                AddStudentCommand.MESSAGE_DUPLICATE_STUDENT, () -> addStudent.execute(model));
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();
        AddStudentCommand addAliceCommand = new AddStudentCommand(alice, INDEX_FIRST_CLASS);
        AddStudentCommand addBobCommand = new AddStudentCommand(bob, INDEX_FIRST_CLASS);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddStudentCommand addAliceCommandCopy = new AddStudentCommand(alice, INDEX_FIRST_CLASS);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        Student alice = new StudentBuilder().withName("Alice").build();
        AddStudentCommand addStudentCommand = new AddStudentCommand(alice, INDEX_FIRST_CLASS);
        String expected = AddStudentCommand.class.getCanonicalName()
                + "{toAdd=" + alice
                + ", classIndex=" + INDEX_FIRST_CLASS + "}";
        assertEquals(expected, addStudentCommand.toString());
    }
}
