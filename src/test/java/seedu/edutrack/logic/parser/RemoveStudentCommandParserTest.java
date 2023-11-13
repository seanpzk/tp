package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.Messages.MESSAGE_INDEX_IS_NEGATIVE;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.RemoveStudentCommand;
import seedu.edutrack.model.module.ClassName;

public class RemoveStudentCommandParserTest {
    private RemoveStudentCommandParser parser = new RemoveStudentCommandParser();
    private final String missingClassName = "cs0000";


    @Test
    public void parse_validArgs_returnRemoveStudentCommand() {
        int studentIndex = 1;
        Index studentIndexToRemove = Index.fromOneBased(studentIndex);
        ClassName className = new ClassName("cs2103");
        RemoveStudentCommand expectedCommand = new RemoveStudentCommand(studentIndexToRemove, className);
        assertParseSuccess(parser, " /s 1 /c cs2103", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid argument: input negative student index
        assertParseFailure(parser, " /s -1 /c cs2103",
                String.format(MESSAGE_INDEX_IS_NEGATIVE, RemoveStudentCommand.MESSAGE_USAGE));

    }
}
