package seedu.edutrack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.logic.commands.MarkAllStudentAbsentCommand.MESSAGE_UNMARK_STUDENT_ATTENDANCE_SUCCESS;
import static seedu.edutrack.logic.commands.StartLessonCommand.MESSAGE_START_LESSON_SUCCESS;

import java.util.Objects;

import seedu.edutrack.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    private static final String MESSAGE_CHECK_START_LESSON_SUCCESS = MESSAGE_START_LESSON_SUCCESS.substring(3);
    //"Successfully marked all students in"
    private static final String MESSAGE_CHECK_MARKALL_SUCCESS = MarkAllStudentPresentCommand
            .MESSAGE_MARK_STUDENT_ATTENDANCE_SUCCESS.substring(0, 30);
    //"Successfully unmarked all students in"
    private static final String MESSAGE_CHECK_UNMARKALL_SUCCESS = MESSAGE_UNMARK_STUDENT_ATTENDANCE_SUCCESS
            .substring(0, 32);
    //"has been marked absent!"
    private static final String MESSAGE_CHECK_MARKABSENT_SUCCESS = MarkStudentAbsentCommand
            .MESSAGE_MARK_STUDENT_ATTENDANCE_SUCCESS
            .substring(3);
    //"sucessfully marked present!"
    private static final String MESSAGE_CHECK_MARKPRESENT_SUCCESS = MarkStudentPresentCommand
            .MESSAGE_MARK_STUDENT_ATTENDANCE_SUCCESS
            .substring(3);
    //"Added New Student:"
    private static final String MESSAGE_CHECK_ADDSTUDENT_SUCCESS = AddStudentCommand.MESSAGE_ADD_STUDENT_SUCCESS
            .substring(0, 16);
    //"has been removed from"
    private static final String MESSAGE_CHECK_REMOVESTUDENT_SUCCESS = RemoveStudentCommand
            .MESSAGE_REMOVE_STUDENT_SUCCESS
            .substring(3, 23);
    //"Successfully set the number of lessons in"
    private static final String MESSAGE_CHECK_SETLESSON_SUCCESS = SetLessonCommand.MESSAGE_SET_LESSON_SUCCESS
            .substring(0, 37);



    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

    public boolean isView() {
        return this.feedbackToUser.contains("Listed all students in");
    }

    public boolean isList() {
        return this.feedbackToUser.contains("Listed all classes");
    }

    public boolean isEditClass() {
        return this.feedbackToUser.contains("Edited Class:");
    }

    public boolean isUpdatedStudent() {
        return this.feedbackToUser.contains("Edited Student:")
                || this.feedbackToUser.contains(MESSAGE_CHECK_START_LESSON_SUCCESS)
                || this.feedbackToUser.contains(MESSAGE_CHECK_MARKALL_SUCCESS)
                || this.feedbackToUser.contains(MESSAGE_CHECK_UNMARKALL_SUCCESS)
                || this.feedbackToUser.contains(MESSAGE_CHECK_MARKABSENT_SUCCESS)
                || this.feedbackToUser.contains(MESSAGE_CHECK_MARKPRESENT_SUCCESS)
                || this.feedbackToUser.contains(MESSAGE_CHECK_ADDSTUDENT_SUCCESS)
                || this.feedbackToUser.contains(MESSAGE_CHECK_REMOVESTUDENT_SUCCESS)
                || this.feedbackToUser.contains(MESSAGE_CHECK_SETLESSON_SUCCESS);
    }
}
