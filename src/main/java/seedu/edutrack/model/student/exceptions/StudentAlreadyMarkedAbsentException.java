package seedu.edutrack.model.student.exceptions;

/**
 * Signals that the operation will not result in anything change.
 * (Trying to change a student who is absent from a class to still being absent from the same class)
 */
public class StudentAlreadyMarkedAbsentException extends RuntimeException {

}
