package seedu.edutrack.model;

import java.nio.file.Path;

import seedu.edutrack.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getEduTrackFilePath();

}
