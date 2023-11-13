package seedu.edutrack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.edutrack.commons.exceptions.DataLoadingException;
import seedu.edutrack.model.ReadOnlyEduTrack;
import seedu.edutrack.model.ReadOnlyUserPrefs;
import seedu.edutrack.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends EduTrackStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getEduTrackFilePath();

    @Override
    Optional<ReadOnlyEduTrack> readEduTrack() throws DataLoadingException;

    @Override
    void saveEduTrack(ReadOnlyEduTrack eduTrack) throws IOException;

}
