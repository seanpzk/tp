package seedu.edutrack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edutrack.testutil.Assert.assertThrows;
import static seedu.edutrack.testutil.TypicalStudents.getTypicalEduTrack;

import org.junit.jupiter.api.Test;

import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.UniqueStudentList;

class AddClassCommandTest {

    private static final String CLASSNAME_STUB = "cs2103t";
    private static final String EMPTY_MEMO = " ";

    final ClassName className = new ClassName(CLASSNAME_STUB);

    final Class c = new Class(className, new UniqueStudentList(), new Memo(EMPTY_MEMO), new Schedule());

    private Model model = new ModelManager(getTypicalEduTrack(), new UserPrefs());

    @Test
    public void constructor_nullClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddClassCommand(null));
    }

    @Test
    public void execute_addClass_success() {
        AddClassCommand addClassCommand = new AddClassCommand(c);
        String expectedMessage = String.format(AddClassCommand.MESSAGE_SUCCESS, CLASSNAME_STUB.toUpperCase());
        Model expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());

        assertCommandSuccess(addClassCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateClass_throwsCommandException() {
        AddClassCommand addClassCommand1 = new AddClassCommand(c);

        try {
            addClassCommand1.execute(model);
        } catch (CommandException e) {
            e.printStackTrace();
        }

        AddClassCommand addClassCommand2 = new AddClassCommand(c);

        assertThrows(CommandException.class, String.format(AddClassCommand.MESSAGE_DUPLICATE_CLASS,
                CLASSNAME_STUB.toUpperCase()), () -> addClassCommand2.execute(model));
    }

    @Test
    public void equals() {
        ClassName className1 = new ClassName("CS2103T");
        ClassName className2 = new ClassName("CS2100");
        Class sampleClass1 = new Class(className1, new UniqueStudentList(), new Memo(EMPTY_MEMO), new Schedule());
        Class sampleClass2 = new Class(className2, new UniqueStudentList(), new Memo(EMPTY_MEMO), new Schedule());

        AddClassCommand command1 = new AddClassCommand(sampleClass1);
        AddClassCommand command2 = new AddClassCommand(sampleClass1);
        AddClassCommand command3 = new AddClassCommand(sampleClass2);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different person -> returns false
        assertFalse(command1.equals(command3));
    }

    @Test
    public void toStringMethod() {
        ClassName className = new ClassName("CS2103T");
        Class sampleClass = new Class(className, new UniqueStudentList(), new Memo(EMPTY_MEMO), new Schedule());

        AddClassCommand command = new AddClassCommand(sampleClass);

        String expected = AddClassCommand.class.getCanonicalName() + "{class=" + sampleClass + "}";
        assertEquals(expected, command.toString());
    }
}
