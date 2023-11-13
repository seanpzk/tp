package seedu.edutrack.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's Id in EduTrack.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class Id {

    public static final String MESSAGE_CONSTRAINTS = "Student id "
            + "should only contain alphanumeric characters, and it should begin with A and end with A-Z.";
    public static final String VALIDATION_REGEX = "^A[0-9]+[A-Z]$";
    public final String id;

    /**
     * Constructs a {@code Id}.
     *
     * @param id A valid Id.
     */
    public Id(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        this.id = id;
    }

    /**
     * Returns true if a given string is a valid id.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Id)) {
            return false;
        }

        Id otherId = (Id) other;
        return this.id.equals(otherId.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
