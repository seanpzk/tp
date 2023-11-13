package seedu.edutrack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASSPARTICIPATION;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_MEMO;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.edutrack.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.NameContainsKeywordsPredicate;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String INVALID_NAME = "R@chel"; // '@' not allowed in student name
    public static final String INVALID_ID = "B0000000Z"; // 'B' not allowed as first character
    public static final String VALID_BOB_ID = "A1928456U";
    public static final String VALID_AMY_ID = "A0128764Z";
    public static final String VALID_MEMO = "Takes longer to understand concepts";
    public static final String MEMO_DESC = " " + PREFIX_MEMO + VALID_MEMO;
    public static final String VALID_CLASS_PARTICIPATION = "Presented their answer today.";
    public static final String CLASS_PARTICIPATION_DESC = " " + PREFIX_CLASSPARTICIPATION + VALID_CLASS_PARTICIPATION;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String ID_DESC_AMY = " " + PREFIX_ID + VALID_AMY_ID;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + INVALID_NAME;
    public static final String INVALID_ID_DESC = " " + PREFIX_ID + INVALID_ID;
    public static final String INVALID_STUDENT_DESC = " " + PREFIX_STUDENT + INVALID_NAME;
    public static final String INVALID_CLASS_INDEX_DESC = " " + PREFIX_CLASS + "@1"; // '@' not allowed in class index
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditStudentCommand.EditStudentDescriptor DESC_AMY;
    public static final EditStudentCommand.EditStudentDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult}
     * <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to
     * {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the edutrack book, filtered person list and selected person in
     * {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.

        EduTrack expectedEduTrack = new EduTrack(actualModel.getEduTrack());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedEduTrack, actualModel.getEduTrack());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given
     * {@code targetIndex} in the
     * {@code model}'s edutrack book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student person = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the class at the given
     * {@code targetIndex} in the
     * {@code model}'s edutrack book.
     */

    public static void showClassAtIndex(Model model, Index targetIndex) {
        model.getClassListSize();
        assertTrue(targetIndex.getZeroBased() < model.getClassListSize());

        Class classToView = model.getClassByIndex(targetIndex);
        model.updateFilteredStudentList(student -> classToView.getStudentList().contains(student));

        assertEquals(classToView.getStudentList().size(), model.getFilteredStudentList().size());
    }
}
