package seedu.edutrack.model.student.exceptions;

/**
 * Signals that the operation will not result in anything change.
 * (Trying to change a student who attended a class to attending the same class)
 */
public class StudentAlreadyMarkedPresentException extends RuntimeException {

}
