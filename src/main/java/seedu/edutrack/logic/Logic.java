package seedu.edutrack.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.edutrack.commons.core.GuiSettings;
import seedu.edutrack.logic.commands.CommandResult;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.logic.parser.exceptions.ParseException;
import seedu.edutrack.model.ReadOnlyEduTrack;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.Student;
/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the EduTrack.
     *
     * @see seedu.edutrack.model.Model#getEduTrack()
     */
    ReadOnlyEduTrack getEduTrack();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Student> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of classes */
    ObservableList<Class> getFilteredClassList();

    /**
     * Returns the user prefs' EduTrack file path.
     */
    Path getEduTrackFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
