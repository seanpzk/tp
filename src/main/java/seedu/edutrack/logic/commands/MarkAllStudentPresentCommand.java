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
import seedu.edutrack.model.student.exceptions.AttendanceDiscrepancyException;
import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedPresentException;

/**
 * Marks all the student in a class as present.
 */
public class MarkAllStudentPresentCommand extends Command {
    public static final String COMMAND_WORD = "markall" + " /c";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks all students in this class as being present.\n"
            + "Parameters: "
            + PREFIX_CLASS + " CLASS_INDEX"
            + "\n"
            + "Example: "
            + COMMAND_WORD + " 1";
    public static final String MESSAGE_MARK_STUDENT_ATTENDANCE_SUCCESS = "Successfully marked all students in %s!";
    private Index targetClassIndex;

    /**
     * Creates a MarkAllStudentPresentCommand to mark all {@code Student}
     */
    public MarkAllStudentPresentCommand(Index classIndex) {
        requireNonNull(classIndex);
        this.targetClassIndex = classIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Class studentClass = model.retrieveClass(targetClassIndex);
        List<Student> studentList = studentClass.getStudentList();
        for (Student studentToMark : studentList) {
            Student editedStudent = studentToMark.duplicateStudent();
            try {
                model.markStudentPresent(studentToMark, studentClass, editedStudent);
            } catch (StudentAlreadyMarkedPresentException e) {
                // do nothing
            } catch (AttendanceDiscrepancyException e) {
                // do nothing
            }
        }
        model.updateFilteredClassList((c) -> c.isSameClass(studentClass));
        model.updateFilteredStudentList((s) -> studentClass.getStudentList().contains(s));
        return new CommandResult(String.format(MESSAGE_MARK_STUDENT_ATTENDANCE_SUCCESS,
                Messages.formatClass(studentClass)));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAllStudentPresentCommand)) {
            return false;
        }

        MarkAllStudentPresentCommand otherMarkAllStudentPresentCommand = (MarkAllStudentPresentCommand) other;
        return this.targetClassIndex.equals(otherMarkAllStudentPresentCommand.targetClassIndex);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("classIndex", this.targetClassIndex.toString())
                .toString();
    }
}
