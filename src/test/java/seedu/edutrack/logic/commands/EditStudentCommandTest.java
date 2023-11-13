package seedu.edutrack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.edutrack.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.edutrack.logic.commands.CommandTestUtil.VALID_BOB_ID;
import static seedu.edutrack.logic.commands.CommandTestUtil.VALID_MEMO;
import static seedu.edutrack.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edutrack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edutrack.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.edutrack.testutil.TypicalStudents.getTypicalEduTrack;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.testutil.EditStudentDescriptorBuilder;
import seedu.edutrack.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditStudentCommand.
 */
public class EditStudentCommandTest {

    private Model model = new ModelManager(getTypicalEduTrack(), new UserPrefs());
    private ClassName className = new ClassName("T1");

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Student editedStudent = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(INDEX_FIRST_PERSON, className, descriptor);

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.formatStudent(editedStudent));

        Model expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());
        expectedModel.setStudent(expectedModel.getFilteredStudentList().get(0), editedStudent);

        assertCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastPerson.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student editedStudent = studentInList.withName(VALID_NAME_BOB)
                .withId(VALID_BOB_ID).withMemo(VALID_MEMO).build();

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withId(VALID_BOB_ID).withMemo(VALID_MEMO).build();
        EditStudentCommand editCommand = new EditStudentCommand(indexLastPerson, className, descriptor);

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.formatStudent(editedStudent));

        Model expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());
        expectedModel.setStudent(lastStudent, editedStudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditStudentCommand editStudentCommand = new EditStudentCommand(INDEX_FIRST_PERSON, className,
                new EditStudentDescriptor());
        Student editedStudent = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.formatStudent(editedStudent));

        Model expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());

        assertCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student editedStudent = new StudentBuilder(studentInFilteredList).withName(VALID_NAME_BOB).build();
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_FIRST_PERSON, className,
                new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.formatStudent(editedStudent));

        Model expectedModel = new ModelManager(new EduTrack(model.getEduTrack()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStudentUnfilteredList_failure() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(firstStudent).build();
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_SECOND_PERSON, className, descriptor);

        assertCommandFailure(editCommand, model, EditStudentCommand.MESSAGE_DUPLICATE_STUDENT);
    }

    @Test
    public void execute_duplicateStudentFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in EduTrack
        Student studentInList = model.getEduTrack().getStudentList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_FIRST_PERSON, className,
                new EditStudentDescriptorBuilder(studentInList).build());

        assertCommandFailure(editCommand, model, EditStudentCommand.MESSAGE_DUPLICATE_STUDENT);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(outOfBoundIndex, className, descriptor);

        assertCommandFailure(editStudentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditStudentCommand standardCommand = new EditStudentCommand(INDEX_FIRST_PERSON, className, DESC_AMY);

        // same values -> returns true
        EditStudentDescriptor copyDescriptor = new EditStudentDescriptor(DESC_AMY);
        EditStudentCommand commandWithSameValues = new EditStudentCommand(INDEX_FIRST_PERSON, className,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(INDEX_SECOND_PERSON, className, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(INDEX_FIRST_PERSON, className, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditStudentDescriptor editPersonDescriptor = new EditStudentDescriptor();
        EditStudentCommand editStudentCommand = new EditStudentCommand(index, className, editPersonDescriptor);
        String expected = EditStudentCommand.class.getCanonicalName() + "{studentIndex=" + index
                + ", studentClassName=" + className
                + ", editStudentDescriptor=" + editPersonDescriptor + "}";
        assertEquals(expected, editStudentCommand.toString());
    }

}
