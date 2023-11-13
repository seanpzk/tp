package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.MarkAllStudentAbsentCommand;

public class MarkAllStudentAbsentCommandParserTest {
    private MarkAllStudentAbsentCommandParser parser = new MarkAllStudentAbsentCommandParser();

    @Test
    public void parse_validArgs_success() {
        MarkAllStudentAbsentCommand expectedCommand = new MarkAllStudentAbsentCommand(Index.fromOneBased(2));
        assertParseSuccess(parser, " /c 2", expectedCommand);
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Argument should be numbers
        int index = 1;
        assertParseFailure(parser, "/c " + index,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAllStudentAbsentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonIndexArg_throwsParseException() {
        // Argument should be numbers
        int index = 1;
        assertParseFailure(parser, "/c " + "cs2102",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAllStudentAbsentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_outOfRange_throwsParseException() {
        // Argument should be numbers
        int index = 1;
        assertParseFailure(parser, "/c " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAllStudentAbsentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongIdentifier_throwsParseException() {
        // Argument should be numbers
        int index = 1;
        assertParseFailure(parser, "/s " + index,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAllStudentAbsentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonIntClassIndex_throwsParseException() {
        assertParseFailure(parser, " /c shouldBeClassIndex",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAllStudentAbsentCommand.MESSAGE_USAGE));
    }
}
