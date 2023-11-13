package seedu.edutrack.logic.commands;

import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.edutrack.logic.commands.CommandTestUtil.showClassAtIndex;
import static seedu.edutrack.testutil.TypicalClasses.getTypicalEduTrack;

import org.junit.jupiter.api.Test;

//import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model = new ModelManager(getTypicalEduTrack(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEduTrack(), new UserPrefs());

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    //    @Test
    //    public void execute_listIsFiltered_showsEverything() {
    //        showClassAtIndex(model, Index.fromOneBased(1));
    //        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    //    }
}
