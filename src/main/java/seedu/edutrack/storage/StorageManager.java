package seedu.edutrack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.edutrack.commons.core.LogsCenter;
import seedu.edutrack.commons.exceptions.DataLoadingException;
import seedu.edutrack.model.ReadOnlyEduTrack;
import seedu.edutrack.model.ReadOnlyUserPrefs;
import seedu.edutrack.model.UserPrefs;

/**
 * Manages storage of EduTrack data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EduTrackStorage eduTrackStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code EduTrackStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(EduTrackStorage eduTrackStorage, UserPrefsStorage userPrefsStorage) {
        this.eduTrackStorage = eduTrackStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ EduTrack methods ==============================

    @Override
    public Path getEduTrackFilePath() {
        return eduTrackStorage.getEduTrackFilePath();
    }

    @Override
    public Optional<ReadOnlyEduTrack> readEduTrack() throws DataLoadingException {
        return readEduTrack(eduTrackStorage.getEduTrackFilePath());
    }

    @Override
    public Optional<ReadOnlyEduTrack> readEduTrack(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eduTrackStorage.readEduTrack(filePath);
    }

    @Override
    public void saveEduTrack(ReadOnlyEduTrack eduTrack) throws IOException {
        saveEduTrack(eduTrack, eduTrackStorage.getEduTrackFilePath());
    }

    @Override
    public void saveEduTrack(ReadOnlyEduTrack eduTrack, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eduTrackStorage.saveEduTrack(eduTrack, filePath);
    }

}
