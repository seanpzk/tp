package seedu.edutrack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.edutrack.testutil.TypicalStudents.getTypicalEduTrack;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.edutrack.commons.core.GuiSettings;
import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.ReadOnlyEduTrack;
import seedu.edutrack.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonEduTrackStorage eduTrackStorage = new JsonEduTrackStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(eduTrackStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Memo: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void eduTrackReadSave() throws Exception {
        /*
         * Memo: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        EduTrack original = getTypicalEduTrack();
        storageManager.saveEduTrack(original);
        ReadOnlyEduTrack retrieved = storageManager.readEduTrack().get();
        assertEquals(original, new EduTrack(retrieved));
    }

    @Test
    public void getEduTrackFilePath() {
        assertNotNull(storageManager.getEduTrackFilePath());
    }

}
