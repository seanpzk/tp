package seedu.edutrack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.edutrack.commons.exceptions.DataLoadingException;
import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.ReadOnlyEduTrack;

/**
 * Represents a storage for {@link EduTrack}.
 */
public interface EduTrackStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEduTrackFilePath();

    /**
     * Returns EduTrack data as a {@link ReadOnlyEduTrack}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyEduTrack> readEduTrack() throws DataLoadingException;

    /**
     * @see #getEduTrackFilePath()
     */
    Optional<ReadOnlyEduTrack> readEduTrack(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyEduTrack} to the storage.
     * @param eduTrack cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEduTrack(ReadOnlyEduTrack eduTrack) throws IOException;

    /**
     * @see #saveEduTrack(ReadOnlyEduTrack)
     */
    void saveEduTrack(ReadOnlyEduTrack eduTrack, Path filePath) throws IOException;

}
