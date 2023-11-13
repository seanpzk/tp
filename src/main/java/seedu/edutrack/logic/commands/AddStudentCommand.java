package seedu.edutrack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.List;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.commons.util.ToStringBuilder;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.Student;

/**
 * Adds a student to EduTrack.
 */
public class AddStudentCommand extends Command {

    public static final String COMMAND_WORD = "add /s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to a specified class index. "
            + "Parameters: "
            + PREFIX_STUDENT + " STUDENT NAME "
            + PREFIX_CLASS + " CLASS INDEX \n"
            + "Example: " + COMMAND_WORD
            + " John Doe "
            + PREFIX_CLASS + " 1";
    public static final String MESSAGE_ADD_STUDENT_SUCCESS = "Added New Student: %1$s to the class: %2$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in a class";
    private final Student toAdd;
    private final Index classIndex;

    /**
     * Creates an AddStudentCommand to add the specified {@code Student}
     */
    public AddStudentCommand(Student student, Index classIndex) {
        requireNonNull(student);
        requireNonNull(classIndex);
        this.toAdd = student;
        this.classIndex = classIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredClassList(Model.PREDICATE_SHOW_ALL_CLASSES);
        List<Class> lastShownClassList = model.getFilteredClassList();

        if (classIndex.getZeroBased() >= lastShownClassList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLASS_DISPLAYED_INDEX);
        }

        Class classToAddStudent = lastShownClassList.get(classIndex.getZeroBased());

        if (classToAddStudent.hasStudentInClass(this.toAdd) || model.hasStudent(this.toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.addStudent(this.toAdd);
        model.addStudentToClass(this.toAdd, classToAddStudent);
        model.updateFilteredClassList((c) -> c.isSameClass(classToAddStudent));
        return new CommandResult(String.format(MESSAGE_ADD_STUDENT_SUCCESS,
                Messages.formatStudent(this.toAdd), Messages.formatClass(classToAddStudent)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddStudentCommand)) {
            return false;
        }

        AddStudentCommand otherAddStudentCommand = (AddStudentCommand) other;
        return this.toAdd.equals(otherAddStudentCommand.toAdd)
                && this.classIndex.equals(otherAddStudentCommand.classIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .add("classIndex", classIndex)
                .toString();
    }
}
