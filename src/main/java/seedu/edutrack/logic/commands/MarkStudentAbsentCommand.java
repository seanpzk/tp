package seedu.edutrack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_STUDENT;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.commons.util.ToStringBuilder;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.exceptions.ClassNotFoundException;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedAbsentException;

/**
 * Marks a student in a Class in the EduTrack as absent.
 */
public class MarkStudentAbsentCommand extends Command {
    public static final String COMMAND_WORD = "unmark" + " /s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a student as being present.\n"
            + "Parameters: "
            + PREFIX_STUDENT + " STUDENT_INDEX"
            + "\n"
            + PREFIX_CLASS + " CLASS_NAME"
            + "\n"
            + "Example: "
            + COMMAND_WORD + " 1" + PREFIX_CLASS + "cs2103";

    public static final String MESSAGE_STUDENT_ALREADY_MARKED = "%s has already been marked absent!";
    public static final String MESSAGE_MARK_STUDENT_ATTENDANCE_SUCCESS = "%s has been marked absent!";
    public static final String MESSAGE_MISSING_CLASS_NAME = "The Class name (%s) you provided does not exist!";

    public final Index targetStudentIndex;
    private ClassName className;

    /**
     * Command to mark a student present for lesson based on its index in the UniqueStudentList
     * @param index Index of student in List
     */
    public MarkStudentAbsentCommand(Index index, ClassName className) {
        requireNonNull(index);
        requireNonNull(className);
        this.targetStudentIndex = index;
        this.className = className;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        model.updateFilteredClassList(Model.PREDICATE_SHOW_ALL_CLASSES);
        Student studentToMark = null;
        try {
            Class studentClass = model.getClass(className);
            studentToMark = model.getStudentInClass(targetStudentIndex, studentClass);
            markAbsent(model, studentToMark, studentClass);
        } catch (StudentAlreadyMarkedAbsentException e) {
            throw new CommandException(String.format(MESSAGE_STUDENT_ALREADY_MARKED, studentToMark.getName()));
        } catch (ClassNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_MISSING_CLASS_NAME, className));
        }
        return new CommandResult(String.format(MESSAGE_MARK_STUDENT_ATTENDANCE_SUCCESS,
                studentToMark.getName()));
    }

    /**
     * Marks the student absent by creating a new edited Student.
     *
     * @param model Model manager of EduTrack
     * @param studentToMark Student to be marked absent
     * @param studentClass Class the target student belongs to
     */
    private static void markAbsent(Model model, Student studentToMark, Class studentClass) {
        Student editedStudent = model.duplicateStudent(studentToMark);
        model.markStudentAbsent(studentToMark, studentClass, editedStudent);
        model.updateFilteredClassList((c) -> c.isSameClass(studentClass));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkStudentAbsentCommand)) {
            return false;
        }

        MarkStudentAbsentCommand otherMarkStudentPresentCommand = (MarkStudentAbsentCommand) other;
        return this.targetStudentIndex.equals(otherMarkStudentPresentCommand.targetStudentIndex)
                && this.className.equals(otherMarkStudentPresentCommand.className);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", this.targetStudentIndex.toString())
                .add("className", this.className.toString())
                .toString();
    }
}
