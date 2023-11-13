package seedu.edutrack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;

import seedu.edutrack.commons.util.ToStringBuilder;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.module.Class;

/**
 * Adds a class to the list of classes in EduTrack.
 */
public class AddClassCommand extends Command {

    public static final String COMMAND_WORD = "add /c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new class to the list of classes.\n"
            + "Parameters: "
            + PREFIX_CLASS + " CLASS_NAME ";

    public static final String MESSAGE_SUCCESS = "%1$s has been added";

    public static final String MESSAGE_DUPLICATE_CLASS = "%1$s already exists";

    private final Class c;

    /**
     * Creates an AddClassCommand to add the specified {@code Class}
     */
    public AddClassCommand(Class c) {
        requireAllNonNull(c);
        this.c = c;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasClass(c)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_CLASS, c.getClassName()));
        }

        model.addClass(c);
        return new CommandResult(String.format(MESSAGE_SUCCESS, c.getClassName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddClassCommand)) {
            return false;
        }

        AddClassCommand e = (AddClassCommand) other;
        return c.equals(e.c);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("class", c)
                .toString();
    }
}
