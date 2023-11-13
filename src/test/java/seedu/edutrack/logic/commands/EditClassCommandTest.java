package seedu.edutrack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_FIRST_CLASS;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_SECOND_CLASS;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.EditClassCommand.EditClassDescriptor;
import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.testutil.ClassBuilder;
import seedu.edutrack.testutil.EditClassDescriptorBuilder;
import seedu.edutrack.testutil.TypicalClasses;

public class EditClassCommandTest {
    private Model model = new ModelManager(TypicalClasses.getTypicalEduTrack(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Class editedClass = new ClassBuilder().build();
        EditClassDescriptor descriptor = new EditClassDescriptorBuilder(editedClass).build();
        EditClassCommand editClassCommand = new EditClassCommand(INDEX_FIRST_CLASS, descriptor);

        String expectedMessage = String.format(EditClassCommand.MESSAGE_EDIT_CLASS_SUCCESS,
                Messages.formatClass(editedClass), editedClass.getClassSchedule(), editedClass.getClassMemo());

        Model expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());
        expectedModel.setClass(INDEX_FIRST_CLASS, editedClass);

        assertCommandSuccess(editClassCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyMemoSpecifiedUnfilteredList_success() {
        Class editedClass = new ClassBuilder().withMemo(" ").build();
        EditClassDescriptor descriptor = new EditClassDescriptorBuilder(editedClass).build();
        EditClassCommand editClassCommand = new EditClassCommand(INDEX_FIRST_CLASS, descriptor);

        String expectedMessage = String.format(EditClassCommand.MESSAGE_EDIT_CLASS_SUCCESS,
                Messages.formatClass(editedClass), editedClass.getClassSchedule(), editedClass.getClassMemo());

        Model expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());
        expectedModel.setClass(INDEX_FIRST_CLASS, editedClass);

        assertCommandSuccess(editClassCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateClassUnfilteredList_failure() {
        Class firstClass = model.getFilteredClassList().get(INDEX_FIRST_CLASS.getZeroBased());
        EditClassDescriptor descriptor = new EditClassDescriptorBuilder(firstClass).build();
        EditClassCommand editCommand = new EditClassCommand(INDEX_SECOND_CLASS, descriptor);

        assertCommandFailure(editCommand, model, EditClassCommand.MESSAGE_DUPLICATE_CLASS);
    }

    @Test
    public void execute_invalidClassIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClassList().size() + 1);
        EditClassDescriptor descriptor = new EditClassDescriptorBuilder().withClassName("cs2103t").build();
        EditClassCommand editCommand = new EditClassCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditClassDescriptor descriptor1 = new EditClassDescriptorBuilder().withClassName("cs2103t").build();
        EditClassDescriptor descriptor2 = new EditClassDescriptorBuilder().withClassName("cs2105").build();
        final EditClassCommand standardCommand = new EditClassCommand(INDEX_FIRST_CLASS, descriptor1);

        // same values -> returns true
        EditClassDescriptor copyDescriptor = new EditClassDescriptor(descriptor1);
        EditClassCommand commandWithSameValues = new EditClassCommand(INDEX_FIRST_CLASS, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditClassCommand(INDEX_SECOND_CLASS, descriptor1)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditClassCommand(INDEX_FIRST_CLASS, descriptor2)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditClassDescriptor editClassDescriptor = new EditClassDescriptor();
        EditClassCommand editCommand = new EditClassCommand(index, editClassDescriptor);
        String expected = EditClassCommand.class.getCanonicalName() + "{index=" + index + ", editClassDescriptor="
                + editClassDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }
}
