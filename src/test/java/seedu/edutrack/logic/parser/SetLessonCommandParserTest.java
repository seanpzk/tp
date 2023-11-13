package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.Messages.MESSAGE_CLASS_NAME_CONTAINS_SPACE;
import static seedu.edutrack.logic.Messages.MESSAGE_EMPTY_CLASS_NAME;
import static seedu.edutrack.logic.Messages.MESSAGE_NUMBER_OF_LESSON_NEGATIVE;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edutrack.logic.commands.SetLessonCommand;
import seedu.edutrack.model.module.ClassName;

public class SetLessonCommandParserTest {
    private SetLessonCommandParser parser = new SetLessonCommandParser();


    @Test
    public void parse_validArgs_returnSetLessonCommand() {
        ClassName className = new ClassName("cs2103");
        SetLessonCommand expectedCommand = new SetLessonCommand(className, 0);
        assertParseSuccess(parser, " /c cs2103 /l 0", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid argument: negative index
        assertParseFailure(parser, " /c cs2103 /l -1",
                String.format(MESSAGE_NUMBER_OF_LESSON_NEGATIVE));

        // Invalid argument: whitespaces in class name
        assertParseFailure(parser, " /c cs 2103 /l 0",
                String.format(MESSAGE_CLASS_NAME_CONTAINS_SPACE));

        // Invalid argument: empty class name
        assertParseFailure(parser, " /c /l 0", String.format(MESSAGE_EMPTY_CLASS_NAME));
    }
}
