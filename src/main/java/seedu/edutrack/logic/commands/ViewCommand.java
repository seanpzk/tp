package seedu.edutrack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.model.Model.PREDICATE_SHOW_ALL_CLASSES;

import java.util.function.Predicate;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.commons.util.ToStringBuilder;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.Student;

/**
 * Lists all students in the specified class to the user.
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view /c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all students in the specified class.\n"
            + "Parameters: CLASS_INDEX\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_SUCCESS = "Listed all students in %1$s";

    public static final String MESSAGE_INVALID_CLASS_DISPLAYED_INDEX = "The class index provided is invalid";

    private final Index classIndex;

    /**
     * Creates a ViewCommand to list the students in the specified class.
     */
    public ViewCommand(Index classIndex) {
        requireNonNull(classIndex);
        this.classIndex = classIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // get the class object and get the unique student list
        if (classIndex.getZeroBased() >= model.getClassListSize() || classIndex.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_CLASS_DISPLAYED_INDEX);
        }
        model.updateFilteredClassList(PREDICATE_SHOW_ALL_CLASSES);

        Class classToView = model.getClassByIndex(classIndex);
        model.updateFilteredClassList(new Predicate<Class>() {
            @Override
            public boolean test(Class classToTest) {
                return classToTest.equals(classToView);
            }
        });
        model.updateFilteredStudentList(new Predicate<Student>() {
            @Override
            public boolean test(Student student) {
                return classToView.getStudentList().contains(student);
            }
        });
        return new CommandResult(String.format(MESSAGE_SUCCESS, classToView.getClassName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && classIndex.equals(((ViewCommand) other).classIndex)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder("ViewCommand")
                .add("classIndex", classIndex)
                .toString();
    }
}
