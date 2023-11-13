package seedu.edutrack.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.edutrack.commons.core.GuiSettings;
import seedu.edutrack.commons.core.LogsCenter;
import seedu.edutrack.logic.commands.Command;
import seedu.edutrack.logic.commands.CommandResult;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.logic.parser.EduTrackParser;
import seedu.edutrack.logic.parser.exceptions.ParseException;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ReadOnlyEduTrack;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT = "Could not save data to file %s due to "
            + "insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final EduTrackParser eduTrackParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and
     * {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        eduTrackParser = new EduTrackParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = eduTrackParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveEduTrack(model.getEduTrack());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyEduTrack getEduTrack() {
        return model.getEduTrack();
    }

    @Override
    public ObservableList<Student> getFilteredPersonList() {
        return model.getFilteredStudentList();
    }

    @Override
    public ObservableList<Class> getFilteredClassList() {
        return model.getFilteredClassList();
    }

    @Override
    public Path getEduTrackFilePath() {
        return model.getEduTrackFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
