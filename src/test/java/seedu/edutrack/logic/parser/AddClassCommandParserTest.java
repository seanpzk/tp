package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edutrack.logic.commands.AddClassCommand;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.UniqueStudentList;

public class AddClassCommandParserTest {
    private AddClassCommandParser parser = new AddClassCommandParser();

    private final String invalidClassName = "cs 2103t";

    private final String validClassName = "cs2103t";

    @Test
    public void parse_allFieldsPresent_success() {
        ClassName className = new ClassName("cs2103t");
        Class expectedClass = new Class(className, new UniqueStudentList(), new Memo(" "), new Schedule());
        AddClassCommand expectedCommand = new AddClassCommand(expectedClass);
        assertParseSuccess(parser, " /c " + validClassName, expectedCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " add /c " + invalidClassName,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassCommand.MESSAGE_USAGE));
    }
}
