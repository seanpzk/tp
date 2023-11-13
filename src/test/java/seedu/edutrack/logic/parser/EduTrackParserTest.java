package seedu.edutrack.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.edutrack.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.AddClassCommand;
import seedu.edutrack.logic.commands.ClearCommand;
import seedu.edutrack.logic.commands.ExitCommand;
import seedu.edutrack.logic.commands.HelpCommand;
import seedu.edutrack.logic.commands.ListCommand;
import seedu.edutrack.logic.commands.RemoveClassCommand;
import seedu.edutrack.logic.commands.ViewCommand;
import seedu.edutrack.logic.parser.exceptions.ParseException;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.UniqueStudentList;

public class EduTrackParserTest {

    private final EduTrackParser parser = new EduTrackParser();

    @Test
    public void parseCommand_addClass() throws Exception {
        ClassName className = new ClassName("test");
        Class c = new Class(className, new UniqueStudentList(), new Memo(" "), new Schedule());
        AddClassCommand command = (AddClassCommand) parser.parseCommand("add /c test");
        assertEquals(new AddClassCommand(c), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_removeClass() throws Exception {
        RemoveClassCommand command = (RemoveClassCommand) parser.parseCommand("remove /c 1");
        assertEquals(new RemoveClassCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_viewCommand() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + "1");
        assertEquals(new ViewCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_viewCommandInvalidIndex_throwsParseException() {
        assertThrows(ParseException.class, ViewCommand.MESSAGE_INVALID_CLASS_DISPLAYED_INDEX, () -> parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + "0"));
    }

}
