package seedu.edutrack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + "}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void testViewFeedback() {
        CommandResult commandResult = new CommandResult("Listed all students in CS2103T");
        assertTrue(commandResult.isView());
        assertFalse(commandResult.isList());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testListFeedback() {
        CommandResult commandResult = new CommandResult("Listed all classes");
        assertFalse(commandResult.isView());
        assertTrue(commandResult.isList());
        assertFalse(commandResult.isExit());
    }

    // Test for edit class feedback "Edited Class: "
    @Test
    public void testEditFeedback() {
        CommandResult commandResult = new CommandResult("Edited Class: CS2103T");
        assertFalse(commandResult.isView());
        assertFalse(commandResult.isList());
        assertFalse(commandResult.isExit());
        assertTrue(commandResult.isEditClass());
    }

    // Test for updated students
    @Test
    public void testUpdatedStudentFeedback() {
        CommandResult commandResultEdit = new CommandResult("Edited Student: CS2103T");
        assertFalse(commandResultEdit.isView());
        assertFalse(commandResultEdit.isList());
        assertFalse(commandResultEdit.isExit());
        assertFalse(commandResultEdit.isEditClass());
        assertTrue(commandResultEdit.isUpdatedStudent());
    }

    @Test
    public void testAddStudentFeedback() {
        CommandResult commandResultAdd = new CommandResult("Added New Student: John");
        assertFalse(commandResultAdd.isView());
        assertFalse(commandResultAdd.isList());
        assertFalse(commandResultAdd.isExit());
        assertFalse(commandResultAdd.isEditClass());
        assertTrue(commandResultAdd.isUpdatedStudent());
    }

    @Test
    public void testRemoveStudentFeedback() {
        CommandResult commandResultRemove = new CommandResult("John has been removed from CS2103T");
        assertFalse(commandResultRemove.isView());
        assertFalse(commandResultRemove.isList());
        assertFalse(commandResultRemove.isExit());
        assertFalse(commandResultRemove.isEditClass());
        assertTrue(commandResultRemove.isUpdatedStudent());
    }

    @Test
    public void testSetLessonFeedback() {
        CommandResult commandResultSetLesson = new CommandResult("Successfully set the number of lessons in CS2103T");
        assertFalse(commandResultSetLesson.isView());
        assertFalse(commandResultSetLesson.isList());
        assertFalse(commandResultSetLesson.isExit());
        assertFalse(commandResultSetLesson.isEditClass());
        assertTrue(commandResultSetLesson.isUpdatedStudent());
    }

    @Test
    public void testMarkAllPresentFeedback() {
        CommandResult commandResultMarkAllPresent = new CommandResult("Successfully marked all students in CS2103T");
        assertFalse(commandResultMarkAllPresent.isView());
        assertFalse(commandResultMarkAllPresent.isList());
        assertFalse(commandResultMarkAllPresent.isExit());
        assertFalse(commandResultMarkAllPresent.isEditClass());
        assertTrue(commandResultMarkAllPresent.isUpdatedStudent());
    }

    @Test
    public void testMarkAllAbsentFeedback() {
        CommandResult commandResultMarkAllAbsent = new CommandResult("Successfully unmarked all students in CS2103T");
        assertFalse(commandResultMarkAllAbsent.isView());
        assertFalse(commandResultMarkAllAbsent.isList());
        assertFalse(commandResultMarkAllAbsent.isExit());
        assertFalse(commandResultMarkAllAbsent.isEditClass());
        assertTrue(commandResultMarkAllAbsent.isUpdatedStudent());
    }

    @Test
    public void testMarkStudentAbsentFeedback() {
        CommandResult commandResultMarkStudentAbsent = new CommandResult("John has been marked absent!");
        assertFalse(commandResultMarkStudentAbsent.isView());
        assertFalse(commandResultMarkStudentAbsent.isList());
        assertFalse(commandResultMarkStudentAbsent.isExit());
        assertFalse(commandResultMarkStudentAbsent.isEditClass());
        assertTrue(commandResultMarkStudentAbsent.isUpdatedStudent());
    }

    @Test
    public void testMarkStudentPresentFeedback() {
        CommandResult commandResultMarkStudentPresent = new CommandResult("John successfully marked present!");
        assertFalse(commandResultMarkStudentPresent.isView());
        assertFalse(commandResultMarkStudentPresent.isList());
        assertFalse(commandResultMarkStudentPresent.isExit());
        assertFalse(commandResultMarkStudentPresent.isEditClass());
        assertTrue(commandResultMarkStudentPresent.isUpdatedStudent());
    }

    @Test
    public void testStartLessonFeedback() {
        CommandResult commandResultStartLesson = new CommandResult("CS2103T started a new lesson!");
        assertFalse(commandResultStartLesson.isView());
        assertFalse(commandResultStartLesson.isList());
        assertFalse(commandResultStartLesson.isExit());
        assertFalse(commandResultStartLesson.isEditClass());
        assertTrue(commandResultStartLesson.isUpdatedStudent());
    }
}
