package seedu.edutrack.model.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MemoTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Memo(null));
    }

    @Test
    public void equals() {
        Memo memo = new Memo("Valid Memo");

        // same values -> returns true
        assertTrue(memo.equals(new Memo("Valid Memo")));

        // same object -> returns true
        assertTrue(memo.equals(memo));

        // null -> returns false
        assertFalse(memo.equals(null));

        // different types -> returns false
        assertFalse(memo.equals(5.0f));

        // different values -> returns false
        assertFalse(memo.equals("Other Valid Memo"));
    }
}
