package seedu.edutrack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;

import java.util.List;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.commons.util.ToStringBuilder;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedAbsentException;

/**
 * Marks all the student in a class as absent.
 */
public class MarkAllStudentAbsentCommand extends Command {
    public static final String COMMAND_WORD = "unmarkall /c";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks all students in this class as absent.\n"
            + "Parameters: "
            + PREFIX_CLASS + " CLASS_INDEX"
            + "\n"
            + "Example: "
            + COMMAND_WORD + " 1";
    public static final String MESSAGE_UNMARK_STUDENT_ATTENDANCE_SUCCESS = "Successfully unmarked all students in %s!";
    private Index targetClassIndex;

    /**
     * Creates a MarkAllStudentAbsentCommand to mark all {@code Student} as absent.
     */
    public MarkAllStudentAbsentCommand(Index classIndex) {
        requireNonNull(classIndex);
        this.targetClassIndex = classIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Class studentClass = model.retrieveClass(targetClassIndex);
        List<Student> studentList = studentClass.getStudentList();
        for (Student studentToUnmark : studentList) {
            Student editedStudent = studentToUnmark.duplicateStudent();
            try {
                model.markStudentAbsent(studentToUnmark, studentClass, editedStudent);
            } catch (StudentAlreadyMarkedAbsentException e) {
                continue;
            }
        }
        model.updateFilteredClassList((c) -> c.isSameClass(studentClass));
        model.updateFilteredStudentList((s) -> studentClass.getStudentList().contains(s));
        return new CommandResult(String.format(MESSAGE_UNMARK_STUDENT_ATTENDANCE_SUCCESS,
                Messages.formatClass(studentClass)));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAllStudentAbsentCommand)) {
            return false;
        }

        MarkAllStudentAbsentCommand markAllStudentAbsentCommand = (MarkAllStudentAbsentCommand) other;
        return this.targetClassIndex.equals(markAllStudentAbsentCommand.targetClassIndex);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("classIndex", this.targetClassIndex.toString())
                .toString();
    }
}
