package seedu.edutrack.model.module.exceptions;

/**
 * Signals that the operation will result in duplicate Classes (Classes are considered duplicates if they have the same
 * identity).
 */
public class DuplicateClassException extends RuntimeException {

    /**
     * Constructs a {@code DuplicateClassException} with an error message.
     */
    public DuplicateClassException() {
        super("Operation would result in duplicate classes");

    }
}
