package seedu.edutrack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_STUDENT;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.commons.util.ToStringBuilder;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.exceptions.ClassNotFoundException;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.exceptions.AttendanceDiscrepancyException;
import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedPresentException;

/**
 * Marks a student in a Class in teh EduTrack as present.
 */
public class MarkStudentPresentCommand extends Command {
    public static final String COMMAND_WORD = "mark" + " /s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a student as being present.\n"
            + "Parameters: "
            + PREFIX_STUDENT + " STUDENT_INDEX"
            + "\n"
            + PREFIX_CLASS + " CLASS_NAME"
            + "\n"
            + "Example: "
            + COMMAND_WORD + " 1 " + PREFIX_CLASS + " cs2103";
    public static final String MESSAGE_STUDENT_ALREADY_MARKED = "%s has already been marked present!";
    public static final String MESSAGE_MARK_STUDENT_ATTENDANCE_SUCCESS = "%s successfully marked present!";
    public static final String MESSAGE_MISSING_CLASS_NAME = "The Class name (%s) you provided does not exist!";
    public static final String MESSAGE_EXISTING_ATTENDANCE_LARGER_THAN_TOTAL = "You should start a new lesson for %s!"
            + "\n"
            + "Existing attendance cannot be larger than it's total!";
    public final Index targetStudentIndex;
    private ClassName className;

    /**
     * Command to mark a student present for lesson based on its index in the UniqueStudentList
     * @param index Index of student in List
     */
    public MarkStudentPresentCommand(Index index, ClassName className) {
        requireNonNull(index);
        requireNonNull(className);
        this.targetStudentIndex = index;
        this.className = className;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredClassList(Model.PREDICATE_SHOW_ALL_CLASSES);
        Class studentClassCopy = null;
        Student studentToMark = null;
        try {
            Class studentClass = model.getClass(this.className);
            studentClassCopy = studentClass;
            studentToMark = model.getStudentInClass(targetStudentIndex, studentClass);
            markPresent(studentClass, studentToMark, model);
        } catch (StudentAlreadyMarkedPresentException e) {
            throw new CommandException(String.format(MESSAGE_STUDENT_ALREADY_MARKED,
                    studentToMark.getName()));
        } catch (AttendanceDiscrepancyException e) {
            throw new CommandException(String.format(MESSAGE_EXISTING_ATTENDANCE_LARGER_THAN_TOTAL,
                    Messages.formatClass(studentClassCopy)));
        } catch (ClassNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_MISSING_CLASS_NAME, className));
        }
        return new CommandResult(String.format(MESSAGE_MARK_STUDENT_ATTENDANCE_SUCCESS,
                studentToMark.getName()));
    }

    /**
     * Marks the student present using a new edited student in both EduTrack and class list.
     *
     * @param studentClass Class the student is in
     * @param studentToMark Student instance you want to mark present
     * @param model Model manager of EduTrack
     * @throws StudentAlreadyMarkedPresentException If student has been marked present
     * @throws AttendanceDiscrepancyException If marking attendance causes the total lesson sum < attended lesson
     * @throws ClassNotFoundException If the class indicated does not exist
     */
    private void markPresent(Class studentClass, Student studentToMark, Model model) throws
            StudentAlreadyMarkedPresentException, AttendanceDiscrepancyException, ClassNotFoundException {
        Student editedStudent = model.duplicateStudent(studentToMark);
        model.markStudentPresent(studentToMark, studentClass, editedStudent);
        model.updateFilteredClassList((c) -> c.isSameClass(studentClass));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkStudentPresentCommand)) {
            return false;
        }

        MarkStudentPresentCommand otherMarkStudentPresentCommand = (MarkStudentPresentCommand) other;
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
