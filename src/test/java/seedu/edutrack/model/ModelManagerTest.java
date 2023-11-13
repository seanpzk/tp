package seedu.edutrack.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.model.Model.PREDICATE_SHOW_ALL_CLASSES;
import static seedu.edutrack.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.edutrack.testutil.Assert.assertThrows;
import static seedu.edutrack.testutil.TypicalClasses.CS2102;
import static seedu.edutrack.testutil.TypicalStudents.ALICE;
import static seedu.edutrack.testutil.TypicalStudents.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.GuiSettings;
import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.NameContainsKeywordsPredicate;
import seedu.edutrack.model.student.UniqueStudentList;
import seedu.edutrack.testutil.EduTrackBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new EduTrack(), new EduTrack(modelManager.getEduTrack()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setEduTrackFilePath(Paths.get("edu/track/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setEduTrackFilePath(Paths.get("new/edu/track/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setEduTrackFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setEduTrackFilePath(null));
    }

    @Test
    public void setEduTrackFilePath_validPath_setsEduTrackFilePath() {
        Path path = Paths.get("edu/track/file/path");
        modelManager.setEduTrackFilePath(path);
        assertEquals(path, modelManager.getEduTrackFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudent(null));
    }

    @Test

    public void hasPerson_personNotInEduTrack_returnsFalse() {
        assertFalse(modelManager.hasStudent(ALICE));
    }

    @Test
    public void hasPerson_personInEduTrack_returnsTrue() {
        modelManager.addStudent(ALICE);
        assertTrue(modelManager.hasStudent(ALICE));
    }

    @Test
    public void retrieveClass_emptyClassList_throwsCommandException() {
        assertThrows(CommandException.class, () -> modelManager.retrieveClass(Index.fromOneBased(1)));
    }

    @Test
    public void retrieveClass_indexLargerThanClassList_throwsCommandException() {
        modelManager.addClass(new Class(new ClassName("class1"), new UniqueStudentList(),
                new Memo(" "), new Schedule()));
        modelManager.addClass(new Class(new ClassName("class2"), new UniqueStudentList(),
                new Memo(" "), new Schedule()));
        assertThrows(CommandException.class, () -> modelManager.retrieveClass(Index.fromOneBased(3)));
    }

    @Test
    public void retrieveClass_validIndexInClassList_success() {
        modelManager.addClass(new Class(new ClassName("class1"), new UniqueStudentList(),
                new Memo(" "), new Schedule()));
        modelManager.addClass(new Class(new ClassName("class2"), new UniqueStudentList(),
                new Memo(" "), new Schedule()));
        assertDoesNotThrow(() -> modelManager.retrieveClass(Index.fromOneBased(1)));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudentList().remove(0));
    }

    @Test
    public void getFilteredClassList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredClassList().remove(0));
    }

    @Test
    public void getClassByIndex_validIndex_success() {
        modelManager.addClass(CS2102);
        assertEquals(modelManager.getClassByIndex(Index.fromOneBased(1)),
                CS2102);
    }

    @Test
    public void getClassByIndex_invalidIndex_throwsIndexOutOfBoundsException() {
        modelManager.addClass(CS2102);
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.getClassByIndex(Index.fromOneBased(2)));
    }

    @Test
    public void getClassListSize_validIndex_success() {
        modelManager.addClass(CS2102);
        assertEquals(modelManager.getClassListSize(), 1);
    }

    @Test
    public void getClassListSize_invalidIndex_throwsIndexOutOfBoundsException() {
        modelManager.addClass(CS2102);
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.getClassByIndex(Index.fromOneBased(2)));
    }

    @Test
    public void updateFilteredClassList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredClassList(null));
    }

    @Test
    public void updateFilteredClassList_validPredicate_success() {
        modelManager.addClass(CS2102);
        modelManager.updateFilteredClassList(PREDICATE_SHOW_ALL_CLASSES);
        assertEquals(modelManager.getFilteredClassList().size(), 1);
    }

    @Test
    public void equals() {
        EduTrack eduTrack = new EduTrackBuilder().withPerson(ALICE).withPerson(BENSON).build();
        EduTrack differentEduTrack = new EduTrack();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(eduTrack, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(eduTrack, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different eduTrack -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentEduTrack, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");

        modelManager.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(eduTrack, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setEduTrackFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(eduTrack, differentUserPrefs)));
    }
}
