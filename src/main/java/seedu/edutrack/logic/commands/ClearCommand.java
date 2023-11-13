package seedu.edutrack.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.Model;

/**
 * Clears the edutrack book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "EduTrack's data has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setEduTrack(new EduTrack());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
