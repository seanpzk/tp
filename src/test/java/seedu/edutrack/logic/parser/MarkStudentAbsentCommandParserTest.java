package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.MarkStudentAbsentCommand;
import seedu.edutrack.model.module.ClassName;

public class MarkStudentAbsentCommandParserTest {
    private MarkStudentAbsentCommandParser parser = new MarkStudentAbsentCommandParser();
    private final String missingClassName = "cs0000";

    @Test
    public void parse_validArgs_returnMarkStudentPresentCommand() {
        int index = 1;
        ClassName className = new ClassName("validClass");

        MarkStudentAbsentCommand expectedCommand = new MarkStudentAbsentCommand(Index.fromOneBased(index), className);
        assertParseSuccess(parser, " /s " + index + " /c " + "validClass", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Argument should be numbers
        int index = 1;
        assertParseFailure(parser, "/s " + index + " /c " + missingClassName,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkStudentAbsentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongIdentifier_throwsParseException() {
        // Argument should be numbers
        int index = 1;
        assertParseFailure(parser, "/c " + index + " /s " + missingClassName,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkStudentAbsentCommand.MESSAGE_USAGE));
    }
}
