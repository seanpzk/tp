package seedu.edutrack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edutrack.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.UniqueStudentList;
import seedu.edutrack.testutil.TypicalClasses;

public class ViewCommandTest {
    private Model model = new ModelManager(TypicalClasses.getTypicalEduTrack(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalClasses.getTypicalEduTrack(), new UserPrefs());

    @Test
    public void execute_viewClass_success() {
        ViewCommand viewCommand = new ViewCommand(Index.fromOneBased(1));
        String expectedClass = model.getClassByIndex(Index.fromOneBased(1)).getClassName().toString();
        String expectedMessage = String.format(ViewCommand.MESSAGE_SUCCESS, expectedClass);
        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewClass_failure() {
        ViewCommand viewCommand = new ViewCommand(Index.fromOneBased(100));
        assertThrows(CommandException.class, () -> viewCommand.execute(model));
    }

    @Test
    public void equals() {
        ClassName className = new ClassName("cs2103");
        ViewCommand viewCommand = new ViewCommand(Index.fromOneBased(1));
        ViewCommand viewCommandCopy = new ViewCommand(Index.fromOneBased(1));
        ViewCommand viewCommandDiff = new ViewCommand(Index.fromOneBased(2));
        assertTrue(viewCommand.equals(viewCommand));
        assertTrue(viewCommand.equals(viewCommandCopy));
        assertFalse(viewCommand.equals(viewCommandDiff));
        assertFalse(viewCommand.equals(null));
        assertFalse(viewCommand.equals(new AddClassCommand(new Class(className, new UniqueStudentList(),
                new Memo(" "), new Schedule()))));
    }

    @Test
    public void toStringTest() {
        ViewCommand viewCommand = new ViewCommand(Index.fromOneBased(1));
        // expected : ViewCommand{classIndex=seedu.edutrack.commons.core.index.Index{zeroBasedIndex=0}}
        String expected = ViewCommand.class.getSimpleName() + "{classIndex=" + Index.fromOneBased(1) + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
