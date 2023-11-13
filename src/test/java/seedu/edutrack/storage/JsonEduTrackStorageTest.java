package seedu.edutrack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.edutrack.testutil.Assert.assertThrows;
import static seedu.edutrack.testutil.TypicalStudents.ALICE;
import static seedu.edutrack.testutil.TypicalStudents.HOON;
import static seedu.edutrack.testutil.TypicalStudents.IDA;
import static seedu.edutrack.testutil.TypicalStudents.getTypicalEduTrack;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.edutrack.commons.exceptions.DataLoadingException;
import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.ReadOnlyEduTrack;

public class JsonEduTrackStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonEduTrackStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readEduTrack_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readEduTrack(null));
    }

    private java.util.Optional<ReadOnlyEduTrack> readEduTrack(String filePath) throws Exception {
        return new JsonEduTrackStorage(Paths.get(filePath)).readEduTrack(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEduTrack("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readEduTrack("notJsonFormatEduTrack.json"));
    }

    @Test
    public void readEduTrack_invalidStudentEduTrack_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEduTrack("invalidStudentEduTrack.json"));
    }

    @Test
    public void readEduTrack_invalidAndValidStudentEduTrack_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEduTrack("invalidAndValidStudentEduTrack.json"));
    }

    @Test
    public void readAndSaveEduTrack_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempEduTrack.json");
        EduTrack original = getTypicalEduTrack();
        JsonEduTrackStorage jsonEduTrackStorage = new JsonEduTrackStorage(filePath);

        // Save in new file and read back
        jsonEduTrackStorage.saveEduTrack(original, filePath);
        ReadOnlyEduTrack readBack = jsonEduTrackStorage.readEduTrack(filePath).get();
        assertEquals(original, new EduTrack(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addStudent(HOON);
        original.removeStudent(ALICE);
        jsonEduTrackStorage.saveEduTrack(original, filePath);
        readBack = jsonEduTrackStorage.readEduTrack(filePath).get();
        assertEquals(original, new EduTrack(readBack));

        // Save and read without specifying file path
        original.addStudent(IDA);
        jsonEduTrackStorage.saveEduTrack(original); // file path not specified
        readBack = jsonEduTrackStorage.readEduTrack().get(); // file path not specified
        assertEquals(original, new EduTrack(readBack));

    }

    @Test
    public void saveEduTrack_nullEduTrack_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEduTrack(null, "SomeFile.json"));
    }

    /**
     * Saves {@code EduTrack} at the specified {@code filePath}.
     */
    private void saveEduTrack(ReadOnlyEduTrack eduTrack, String filePath) {
        try {
            new JsonEduTrackStorage(Paths.get(filePath))
                    .saveEduTrack(eduTrack, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEduTrack_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEduTrack(new EduTrack(), null));
    }
}
