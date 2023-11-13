package seedu.edutrack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.model.Model.PREDICATE_SHOW_ALL_CLASSES;

import seedu.edutrack.model.Model;

/**
 * Lists all the classes in the EduTrack to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all classes";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClassList(PREDICATE_SHOW_ALL_CLASSES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
