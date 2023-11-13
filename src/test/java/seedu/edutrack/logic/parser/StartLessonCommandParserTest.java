package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.Messages.MESSAGE_EMPTY_CLASS_NAME;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edutrack.logic.commands.StartLessonCommand;
import seedu.edutrack.model.module.ClassName;

public class StartLessonCommandParserTest {
    private StartLessonCommandParser parser = new StartLessonCommandParser();


    @Test
    public void parse_validArgs_returnStartLessonCommand() {
        ClassName className = new ClassName("cs2103");
        StartLessonCommand expectedCommand = new StartLessonCommand(className);
        assertParseSuccess(parser, " /c cs2103", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid argument: empty class name
        assertParseFailure(parser, " /c ", String.format(MESSAGE_EMPTY_CLASS_NAME));
    }
}
