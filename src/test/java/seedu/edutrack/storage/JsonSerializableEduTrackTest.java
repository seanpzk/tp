package seedu.edutrack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.edutrack.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.exceptions.IllegalValueException;
import seedu.edutrack.commons.util.JsonUtil;
import seedu.edutrack.model.EduTrack;
import seedu.edutrack.testutil.TypicalClasses;
import seedu.edutrack.testutil.TypicalStudents;

public class JsonSerializableEduTrackTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableEduTrackTest");
    private static final Path TYPICAL_STUDENTS_FILE = TEST_DATA_FOLDER.resolve("typicalStudentsEduTrack.json");
    private static final Path INVALID_STUDENT_FILE = TEST_DATA_FOLDER.resolve("invalidStudentEduTrack.json");
    private static final Path DUPLICATE_STUDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentEduTrack.json");

    private static final Path TYPICAL_CLASSES_FILE = TEST_DATA_FOLDER.resolve("typicalClassesEduTrack.json");
    private static final Path INVALID_CLASSES_FILE = TEST_DATA_FOLDER.resolve("invalidClassEduTrack.json");
    private static final Path DUPLICATE_CLASS_FILE = TEST_DATA_FOLDER.resolve("duplicateClassEduTrack.json");

    @Test
    public void toModelType_typicalStudentsFile_success() throws Exception {
        JsonSerializableEduTrack dataFromFile = JsonUtil.readJsonFile(TYPICAL_STUDENTS_FILE,
                JsonSerializableEduTrack.class).get();
        EduTrack eduTrackFromFile = dataFromFile.toModelType();
        EduTrack typicalPersonsEduTrack = TypicalStudents.getTypicalEduTrack();
        assertEquals(eduTrackFromFile, typicalPersonsEduTrack);
    }

    @Test
    public void toModelType_invalidStudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEduTrack dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_FILE,
                JsonSerializableEduTrack.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudents_throwsIllegalValueException() throws Exception {
        JsonSerializableEduTrack dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_FILE,
                JsonSerializableEduTrack.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableEduTrack.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalClassesFile_success() throws Exception {
        JsonSerializableEduTrack dataFromFile = JsonUtil.readJsonFile(TYPICAL_CLASSES_FILE,
                JsonSerializableEduTrack.class).get();
        EduTrack eduTrackFromFile = dataFromFile.toModelType();
        EduTrack typicalClassesEduTrack = TypicalClasses.getTypicalEduTrack();
        assertEquals(eduTrackFromFile, typicalClassesEduTrack);
    }

    @Test
    public void toModelType_invalidClassFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEduTrack dataFromFile = JsonUtil.readJsonFile(INVALID_CLASSES_FILE,
                JsonSerializableEduTrack.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateClasses_throwsIllegalValueException() throws Exception {
        JsonSerializableEduTrack dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CLASS_FILE,
                JsonSerializableEduTrack.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableEduTrack.MESSAGE_DUPLICATE_CLASS,
                dataFromFile::toModelType);
    }

}
