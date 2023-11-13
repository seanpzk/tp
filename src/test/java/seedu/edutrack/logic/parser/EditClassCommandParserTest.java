package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_MEMO;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_FIRST_CLASS;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_SECOND_CLASS;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.EditClassCommand;
import seedu.edutrack.logic.commands.EditClassCommand.EditClassDescriptor;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.testutil.EditClassDescriptorBuilder;

public class EditClassCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditClassCommand.MESSAGE_USAGE);

    private EditClassCommandParser parser = new EditClassCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, " /c /n", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, " /c 1", EditClassCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, " /c ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, " /c -2 /n cs2100", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, " /c 0 /m test", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, " /c some random string", MESSAGE_INVALID_FORMAT);

    }
    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " /c 1 /n cs 2100", ClassName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, " /c 1 /t 09:00-10:00", Schedule.MESSAGE_CONSTRAINTS); // invalid schedule

        // invalid schedule followed by valid name
        assertParseFailure(parser, " /c 1 /n cs2100 /t 09:00-10:00", Schedule.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " /c 1 /n cs 2100 /t 09:00-10:00", ClassName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_CLASS;
        String userInput = " /c" + targetIndex.getOneBased() + " /n cs2101 /m prepare slides /t mon, 10:00-12:00";

        EditClassDescriptor descriptor = new EditClassDescriptorBuilder().withClassName("cs2101")
                .withMemo("prepare slides").withSchedule("mon, 10:00-12:00").build();
        EditClassCommand expectedCommand = new EditClassCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CLASS;
        String userInput = " /c" + targetIndex.getOneBased() + " /n cs2101 /t mon, 10:00-12:00";

        EditClassDescriptor descriptor = new EditClassDescriptorBuilder().withClassName("cs2101")
                .withSchedule("mon, 10:00-12:00").build();
        EditClassCommand expectedCommand = new EditClassCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_FIRST_CLASS;
        String userInput = " /c" + targetIndex.getOneBased() + " /n cs2101";
        EditClassDescriptor descriptor = new EditClassDescriptorBuilder().withClassName("cs2101").build();
        EditClassCommand expectedCommand = new EditClassCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // memo
        userInput = " /c" + targetIndex.getOneBased() + " /m prepare slides";
        descriptor = new EditClassDescriptorBuilder().withMemo("prepare slides").build();
        expectedCommand = new EditClassCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // schedule
        userInput = " /c" + targetIndex.getOneBased() + " /t mon, 10:00-12:00";
        descriptor = new EditClassDescriptorBuilder().withSchedule("mon, 10:00-12:00").build();
        expectedCommand = new EditClassCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_CLASS;
        String userInput = " /c" + targetIndex.getOneBased() + " /t mon, 10:00-12:00" + " /t 10:00-12:00";

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE));

        // invalid followed by valid
        userInput = " /c" + targetIndex.getOneBased() + " /t 10:00-12:00" + " /t mon, 10:00-12:00";

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE));

        // mulltiple valid fields repeated
        userInput = " /c" + targetIndex.getOneBased() + " /n cs2030 /t mon, 10:00-12:00 /n cs2040 /m lab "
                + "/t tue, 10:00-12:00 /m prepare slides";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_MEMO, PREFIX_SCHEDULE));

        // multiple invalid values
        userInput = " /c" + targetIndex.getOneBased() + " /n cs 2030 /t mon 10:00-12:00 /n cs 2040 /t tue, 14:00-12:00";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_SCHEDULE));
    }
}
