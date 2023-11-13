package seedu.edutrack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edutrack.testutil.Assert.assertThrows;
import static seedu.edutrack.testutil.TypicalClasses.getEmptyEduTrack;
import static seedu.edutrack.testutil.TypicalClasses.getTypicalEduTrack;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;

class RemoveClassCommandTest {

    private static final String CLASSNAME_STUB = "cs2102";

    private Model model = new ModelManager(getTypicalEduTrack(), new UserPrefs());

    @Test
    public void constructor_nullClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveClassCommand(null));
    }

    @Test
    public void execute_nonEmptyClassList_success() {
        RemoveClassCommand removeClassCommand = new RemoveClassCommand(Index.fromOneBased(1));
        String expectedOutcomeMessage = String.format(RemoveClassCommand.MESSAGE_REMOVE_CLASS_SUCCESS,
                CLASSNAME_STUB.toUpperCase());
        Model expectedModel = new ModelManager(model.getEduTrack(), new UserPrefs());

        assertCommandSuccess(removeClassCommand, model, expectedOutcomeMessage, expectedModel);
    }

    @Test
    public void execute_emptyClassList_throwsCommandException() {
        Model emptyModel = new ModelManager(getEmptyEduTrack(), new UserPrefs());
        Index classIndexToRemove = Index.fromOneBased(5);
        RemoveClassCommand removeClassCommand = new RemoveClassCommand(classIndexToRemove);

        assertCommandFailure(removeClassCommand, emptyModel, Messages.MESSAGE_EMPTY_CLASS_LIST);
    }

    @Test
    public void execute_outOfBoundsClassIndex_throwsCommandException() {
        Index classIndexToRemove = Index.fromOneBased(5);
        RemoveClassCommand removeClassCommand = new RemoveClassCommand(classIndexToRemove);

        assertCommandFailure(removeClassCommand, model, Messages.MESSAGE_INDEX_INPUT_TOO_LARGE);
    }

    @Test
    public void equals() {
        RemoveClassCommand firstRemoveClassCommand = new RemoveClassCommand(Index.fromOneBased(1));
        RemoveClassCommand secondRemoveClassCommand = new RemoveClassCommand(
                Index.fromOneBased(2));
        // same object -> returns true
        assertTrue(firstRemoveClassCommand.equals(firstRemoveClassCommand));

        // same values -> returns true
        RemoveClassCommand removeFirstClassCommand = new RemoveClassCommand(Index.fromOneBased(1));
        assertTrue(firstRemoveClassCommand.equals(removeFirstClassCommand));

        // different types -> returns false
        assertFalse(firstRemoveClassCommand.equals(1));

        // null -> returns false
        assertFalse(firstRemoveClassCommand.equals(null));

        // different class -> returns false
        assertFalse(firstRemoveClassCommand.equals(secondRemoveClassCommand));
    }

    @Test
    public void toStringMethod() {
        Index indexToRemove = Index.fromOneBased(1);
        RemoveClassCommand removeClassCommand = new RemoveClassCommand(indexToRemove);
        String expected = RemoveClassCommand.class.getCanonicalName() + "{classIndex=" + indexToRemove.toString() + "}";
        assertEquals(expected, removeClassCommand.toString());
    }
}
