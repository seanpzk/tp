package seedu.edutrack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.edutrack.logic.commands.RemoveClassCommand.MESSAGE_MISSING_CLASS_NAME;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_LESSONQUANTITY;

import java.util.List;

import seedu.edutrack.commons.util.ToStringBuilder;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.exceptions.ClassNotFoundException;
import seedu.edutrack.model.student.Student;

/**
 * Sets the number of lessons conducted for a Class.
 */
public class SetLessonCommand extends Command {
    public static final String COMMAND_WORD = "setlesson /c";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the total number of lessons in the Class.\n"
            + "Parameters: "
            + PREFIX_CLASS + " CLASS_NAME"
            + "\n"
            + PREFIX_LESSONQUANTITY + " NUMBER_OF_LESSON"
            + "\n"
            + "Example: "
            + COMMAND_WORD + " cs2103" + " /l 5";
    public static final String MESSAGE_SET_LESSON_SUCCESS = "Successfully set the number of lessons in %s to %d!";
    private ClassName className;
    private int numLessons;

    /**
     * Command to set the total number of lessons of a Class.
     * @param className Classname to represent the Class.
     */
    public SetLessonCommand(ClassName className, int numLessons) {
        requireAllNonNull(className);
        assert numLessons >= 0 : "number of lessons should be at least 0";
        this.className = className;
        this.numLessons = numLessons;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        Class copy = null;
        try {
            Class c = model.getClass(className);
            copy = c;
            model.setClassLesson(c, numLessons);
            // Verify Student's total lessons attended do not exceed the total lessons of Clas
            List<Student> studentList = c.getStudentList();
            for (Student studentToVerify : studentList) {
                if (!model.isValidAttendanceForStudent(studentToVerify, c)) {
                    if (numLessons == 0 && studentToVerify.isPresentForLesson()) {
                        studentToVerify.markStudentAbsent();
                    }
                    Student editedStudent = studentToVerify.duplicateStudent();
                    model.setStudentLesson(studentToVerify, c, editedStudent, numLessons);
                }
            }
            model.updateFilteredClassList((cl) -> cl.isSameClass(c));
        } catch (ClassNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_MISSING_CLASS_NAME, className));
        }
        return new CommandResult(String.format(MESSAGE_SET_LESSON_SUCCESS,
                Messages.formatClass(copy), numLessons));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetLessonCommand)) {
            return false;
        }

        SetLessonCommand otherSetLessonCommand = (SetLessonCommand) other;
        return this.className.equals(otherSetLessonCommand.className)
                && this.numLessons == otherSetLessonCommand.numLessons;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("className", this.className.toString())
                .add("numLessons", String.valueOf(this.numLessons))
                .toString();
    }
}
