package seedu.edutrack.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.edutrack.logic.parser.Prefix;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.Student;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "Student index provided is invalid";
    public static final String MESSAGE_INVALID_CLASS_DISPLAYED_INDEX = "The class index provided is invalid";
    public static final String MESSAGE_INDEX_INPUT_TOO_LARGE = "The class index provided is invalid";
    public static final String MESSAGE_INDEX_IS_NEGATIVE = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_NUMBER_OF_LESSON_NEGATIVE = "Number of lessons must be at least 0.";
    public static final String MESSAGE_EMPTY_CLASS_LIST = "There are no classes available at the moment! Create one!";
    public static final String MESSAGE_CLASS_NAME_CONTAINS_SPACE = "Class name should not contain spaces.";
    public static final String MESSAGE_EMPTY_CLASS_NAME = "Class name not specified.";
    public static final String MESSAGE_MISSING_CLASS_NAME = "The Class name (%s) you provided does not exist!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_EMPTY_STUDENT_LIST = "There are no students available at the moment! "
            + "Create one!";
    public static final String MESSAGE_DUPLICATE_FIELDS = "Multiple values specified for the following single-valued "
            + "field(s): ";



    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields = Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code student} for display to the user.
     */
    public static String formatStudent(Student student) {
        final StringBuilder builder = new StringBuilder();
        builder.append(student.getName());
        builder.append("; Id: " + student.getId());
        builder.append("; Memo: " + student.getMemo());
        return builder.toString();
    }

    /**
     * Formats the {@code class} for display to the user.
     */
    public static String formatClass(Class c) {
        final StringBuilder builder = new StringBuilder();
        builder.append(c.getClassName());
        return builder.toString();
    }

}
