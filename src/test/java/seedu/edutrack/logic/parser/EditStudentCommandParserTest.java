package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.commands.CommandTestUtil.CLASS_PARTICIPATION_DESC;
import static seedu.edutrack.logic.commands.CommandTestUtil.ID_DESC_AMY;
import static seedu.edutrack.logic.commands.CommandTestUtil.INVALID_ID_DESC;
import static seedu.edutrack.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.edutrack.logic.commands.CommandTestUtil.MEMO_DESC;
import static seedu.edutrack.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edutrack.logic.commands.CommandTestUtil.VALID_AMY_ID;
import static seedu.edutrack.logic.commands.CommandTestUtil.VALID_CLASS_PARTICIPATION;
import static seedu.edutrack.logic.commands.CommandTestUtil.VALID_MEMO;
import static seedu.edutrack.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASSPARTICIPATION;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_MEMO;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.EditStudentCommand;
import seedu.edutrack.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.student.Id;
import seedu.edutrack.model.student.Name;
import seedu.edutrack.testutil.EditStudentDescriptorBuilder;


public class EditStudentCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStudentCommand.MESSAGE_USAGE);

    private EditStudentCommandParser parser = new EditStudentCommandParser();
    private ClassName className = new ClassName("T1");

    @Test
    public void parse_missingParts_failure() {
        // no student index specified
        assertParseFailure(parser, " /s /c T1", MESSAGE_INVALID_FORMAT);

        // no class name specified
        assertParseFailure(parser, " /s 1 /c", MESSAGE_INVALID_FORMAT);

        // no compulsory class field specified
        assertParseFailure(parser, " /s 1 T1", MESSAGE_INVALID_FORMAT);

        // no index and no compulsory field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no optional field specified
        assertParseFailure(parser, " /s 1 /c T1", EditStudentCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, " /s -5 /c T1" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, " /s 0 /c T1" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, " /s 1 some random string /c T1", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, " /s 1 /c T1 /i string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " /s 1 /c T1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, " /s 1 /c T1" + INVALID_ID_DESC, Id.MESSAGE_CONSTRAINTS); // invalid id

        // invalid id followed by valid name
        assertParseFailure(parser, " /s 1 /c T1" + INVALID_ID_DESC + NAME_DESC_AMY, Id.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " /s 1 /c T1" + INVALID_NAME_DESC + INVALID_ID_DESC + MEMO_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = " /s " + targetIndex.getOneBased() + " /c T1 " + NAME_DESC_AMY + ID_DESC_AMY
                + MEMO_DESC + CLASS_PARTICIPATION_DESC;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withId(VALID_AMY_ID).withMemo(VALID_MEMO).withClassParticipation(VALID_CLASS_PARTICIPATION).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, className, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = " /s " + targetIndex.getOneBased() + " /c T1 " + NAME_DESC_AMY;
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, className, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // id
        userInput = " /s " + targetIndex.getOneBased() + " /c T1 " + ID_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withId(VALID_AMY_ID).build();
        expectedCommand = new EditStudentCommand(targetIndex, className, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // memo
        userInput = " /s " + targetIndex.getOneBased() + " /c T1 " + MEMO_DESC;
        descriptor = new EditStudentDescriptorBuilder().withMemo(VALID_MEMO).build();
        expectedCommand = new EditStudentCommand(targetIndex, className, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // class participation
        userInput = " /s " + targetIndex.getOneBased() + " /c T1 " + CLASS_PARTICIPATION_DESC;
        descriptor = new EditStudentDescriptorBuilder().withClassParticipation(VALID_CLASS_PARTICIPATION).build();
        expectedCommand = new EditStudentCommand(targetIndex, className, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = " /s " + targetIndex.getOneBased() + " /c T1 " + NAME_DESC_AMY + INVALID_ID_DESC;

        assertParseFailure(parser, userInput, Id.MESSAGE_CONSTRAINTS);

        // invalid followed by valid
        userInput = " /s " + targetIndex.getOneBased() + " /c T1 " + INVALID_ID_DESC + NAME_DESC_AMY;

        assertParseFailure(parser, userInput, Id.MESSAGE_CONSTRAINTS);

        // multiple valid fields repeated
        userInput = " /s " + targetIndex.getOneBased() + " /c T1 " + NAME_DESC_AMY + ID_DESC_AMY
                + MEMO_DESC + CLASS_PARTICIPATION_DESC + NAME_DESC_AMY + ID_DESC_AMY
                + MEMO_DESC + CLASS_PARTICIPATION_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ID,
                        PREFIX_MEMO, PREFIX_CLASSPARTICIPATION));

        // multiple invalid values
        userInput = " /s " + targetIndex.getOneBased() + " /c T1 " + INVALID_NAME_DESC + INVALID_ID_DESC
                + INVALID_NAME_DESC + INVALID_ID_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ID));
    }
}
