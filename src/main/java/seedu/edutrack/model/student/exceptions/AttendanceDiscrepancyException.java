package seedu.edutrack.model.student.exceptions;

/**
 * Signals that the operation will result in Student's current total attendance becoming larger than class total
 * attendance.
 */
public class AttendanceDiscrepancyException extends RuntimeException {
    public AttendanceDiscrepancyException() {
        super("Operation would result in current attendance being larger than total attendance");
    }
}
