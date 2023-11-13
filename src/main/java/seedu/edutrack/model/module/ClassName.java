package seedu.edutrack.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.commons.util.AppUtil.checkArgument;

/**
 * Represents the name of a class in EduTrack.
 * Guarantees: immutable; is valid as declared in {@link #isValidClassName(String)}
 */
public class ClassName {

    public static final String MESSAGE_CONSTRAINTS = "Class name should not contain spaces.";
    public static final String MESSAGE_EMPTY_CLASS_NAME = "Class name not specified.";
    public static final String VALIDATION_REGEX = "^[^\\s]*$";
    public final String className;

    /**
     * Constructs a {@code ClassName}.
     *
     * @param className A valid class name.
     * @throws IllegalArgumentException If the given class name is invalid.
     */
    public ClassName(String className) {
        requireNonNull(className);
        checkArgument(isValidClassName(className), MESSAGE_CONSTRAINTS);
        this.className = className.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid class name.
     *
     * @param test The string to test.
     * @return True if the string is a valid class name, false otherwise.
     */
    public static boolean isValidClassName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is not empty string.
     *
     * @param test The string to test.
     * @return True if the string is not empty, false otherwise.
     */
    public static boolean isEmptyClassName(String test) {
        return test.trim().isEmpty();
    }

    @Override
    public String toString() {
        return className;
    }

    /**
     * Checks if this class name is equal to another object.
     *
     * @param other The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClassName)) {
            return false;
        }

        ClassName otherName = (ClassName) other;
        return className.equals(otherName.className);
    }

    @Override
    public int hashCode() {
        return className.hashCode();
    }

}
