package seedu.edutrack.logic.commands;

import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edutrack.testutil.TypicalStudents.getTypicalEduTrack;

import org.junit.jupiter.api.Test;

import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyEduTrack_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyEduTrack_success() {
        Model model = new ModelManager(getTypicalEduTrack(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEduTrack(), new UserPrefs());
        expectedModel.setEduTrack(new EduTrack());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
