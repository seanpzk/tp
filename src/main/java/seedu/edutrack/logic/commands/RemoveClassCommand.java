package seedu.edutrack.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.commons.util.ToStringBuilder;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.exceptions.ClassNotFoundException;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.exceptions.StudentNotFoundException;

/**
 * Deletes a Class identified using index from the EduTrack.
 */
public class RemoveClassCommand extends Command {

    public static final String COMMAND_WORD = "remove /c";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the class identified by the index number used in the displayed class list.\n"
            + "Parameters: CLASS_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_CLASS_SUCCESS = "Deleted Class: %1$s";
    public static final String MESSAGE_MISSING_CLASS_INDEX = "The index (%1$s) you provided does not exist!";
    public static final String MESSAGE_MISSING_CLASS_NAME = "The Class name (%s) you provided does not exist!";

    private final Index targetClassIndex;

    /**
     * Constructor for the Remove Class Command.
     *
     * @param classIndex Index to remove from the Class unique list
     */
    public RemoveClassCommand(Index classIndex) {
        requireNonNull(classIndex);
        this.targetClassIndex = classIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Class classToRemove = model.retrieveClass(targetClassIndex);
        removeAllClassStudents(model, classToRemove);
        try {
            model.removeClass(classToRemove);
        } catch (ClassNotFoundException e) {
            // typically will not occur unless the model and student list are not in sync
            throw new CommandException(MESSAGE_MISSING_CLASS_INDEX);
        }
        return new CommandResult(String.format(MESSAGE_REMOVE_CLASS_SUCCESS, Messages.formatClass(classToRemove)));
    }

    /**
     * Removes all students in the given class.
     *
     * @param model The model manager of the application
     * @param classToRemove Class instance which the user chose to delete
     */
    private static void removeAllClassStudents(Model model, Class classToRemove) {
        List<Student> studentList = classToRemove.getStudentList();
        // Copy is required because cannot delete item while iterating through array
        List<Student> studentListCopy = new ArrayList<>(studentList);
        if (!studentListCopy.isEmpty()) {
            // Removes existing students in this class as well
            for (Student studentToRemove : studentListCopy) {
                try {
                    model.deleteStudent(studentToRemove, classToRemove);
                } catch (StudentNotFoundException e) {
                    //  do nothing
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveClassCommand)) {
            return false;
        }

        RemoveClassCommand otherRemoveClassCommand = (RemoveClassCommand) other;
        return this.targetClassIndex.equals(otherRemoveClassCommand.targetClassIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("classIndex", this.targetClassIndex)
                .toString();
    }
}
