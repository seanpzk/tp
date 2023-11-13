package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.RemoveClassCommand;

public class RemoveClassCommandParserTest {
    private RemoveClassCommandParser parser = new RemoveClassCommandParser();
    private final String missingClassName = "cs0000";

    @Test
    public void parse_validArgs_returnRemoveClassCommand() {
        int index = 1;
        RemoveClassCommand expectedCommand = new RemoveClassCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, " /c " + index, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Argument should be numbers
        assertParseFailure(parser, "/c" + missingClassName,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveClassCommand.MESSAGE_USAGE));
    }

    @Test
    public void parase_nonIntClassIndex_throwsParseException() {
        assertParseFailure(parser, " /c shouldBeClassIndex",
                Messages.MESSAGE_INVALID_CLASS_DISPLAYED_INDEX);
    }
}
