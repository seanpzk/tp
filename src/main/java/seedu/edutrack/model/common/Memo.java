package seedu.edutrack.model.common;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.commons.util.AppUtil.checkArgument;

/**
 * Represents a memo.
 * Guarantees: immutable; is valid as declared in {@link #isValidMemo(String)}
 */
public class Memo {
    public static final String MESSAGE_CONSTRAINTS = "Memo can take any values";

    /*
     * The first character of the note must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^.*";

    public final String memo;

    /**
     * Constructs an {@code Memo}.
     */
    public Memo(String memo) {
        requireNonNull(memo);
        checkArgument(isValidMemo(memo), MESSAGE_CONSTRAINTS);
        this.memo = memo;
    }

    /**
     * Returns true if a given string is a valid memo.
     */
    public static boolean isValidMemo(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.memo;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Memo)) {
            return false;
        }

        Memo otherMemo = (Memo) other;
        return this.memo.equals(otherMemo.memo);
    }

    @Override
    public int hashCode() {
        return this.memo.hashCode();
    }

}
