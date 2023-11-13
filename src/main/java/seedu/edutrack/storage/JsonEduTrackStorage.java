package seedu.edutrack.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.edutrack.commons.core.LogsCenter;
import seedu.edutrack.commons.exceptions.DataLoadingException;
import seedu.edutrack.commons.exceptions.IllegalValueException;
import seedu.edutrack.commons.util.FileUtil;
import seedu.edutrack.commons.util.JsonUtil;
import seedu.edutrack.model.ReadOnlyEduTrack;

/**
 * A class to access EduTrack data stored as a json file on the hard disk.
 */
public class JsonEduTrackStorage implements EduTrackStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEduTrackStorage.class);

    private Path filePath;

    public JsonEduTrackStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEduTrackFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEduTrack> readEduTrack() throws DataLoadingException {
        return readEduTrack(filePath);
    }

    /**
     * Similar to {@link #readEduTrack()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyEduTrack> readEduTrack(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableEduTrack> jsonEduTrack = JsonUtil.readJsonFile(
                filePath, JsonSerializableEduTrack.class);
        if (!jsonEduTrack.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEduTrack.get().toModelType());
        } catch (IllegalValueException | NullPointerException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveEduTrack(ReadOnlyEduTrack eduTrack) throws IOException {
        saveEduTrack(eduTrack, filePath);
    }

    /**
     * Similar to {@link #saveEduTrack(ReadOnlyEduTrack)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEduTrack(ReadOnlyEduTrack eduTrack, Path filePath) throws IOException {
        requireNonNull(eduTrack);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEduTrack(eduTrack), filePath);
    }

}
