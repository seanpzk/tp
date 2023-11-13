package seedu.edutrack.logic.parser;


import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.edutrack.logic.commands.CommandTestUtil.INVALID_CLASS_INDEX_DESC;
import static seedu.edutrack.logic.commands.CommandTestUtil.INVALID_STUDENT_DESC;
import static seedu.edutrack.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.edutrack.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_FIRST_CLASS;

import org.junit.jupiter.api.Test;

import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.AddStudentCommand;
import seedu.edutrack.model.student.Name;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.testutil.StudentBuilder;

public class AddStudentCommandParserTest {
    private AddStudentCommandParser parser = new AddStudentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedPerson = new StudentBuilder()
                .withName("Bob")
                .withCurrentLessonAttendance(false)
                .withLessonsAttended(0)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " /s Bob /c 1",
                new AddStudentCommand(expectedPerson, INDEX_FIRST_CLASS));

    }

    @Test
    public void parse_repeatedFields_failure() {
        String validExpectedCommandString = " /s Bob /c 1";

        // multiple student names
        assertParseFailure(parser, " /s Amy" + validExpectedCommandString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT));

        // multiple classes
        assertParseFailure(parser, " /c 2" + validExpectedCommandString,
                getErrorMessageForDuplicatePrefixes(PREFIX_CLASS));

        // invalid value followed by valid value

        // invalid student name
        assertParseFailure(parser, INVALID_STUDENT_DESC + validExpectedCommandString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT));

        // invalid class index
        assertParseFailure(parser, INVALID_CLASS_INDEX_DESC + validExpectedCommandString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS));

        // valid value followed by invalid value

        // invalid student name
        assertParseFailure(parser, validExpectedCommandString + INVALID_STUDENT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT));


        // invalid class index
        assertParseFailure(parser, validExpectedCommandString + INVALID_CLASS_INDEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE);

        // missing student prefix
        assertParseFailure(parser, "Bob /c 1",
                expectedMessage);

        // missing class prefix
        assertParseFailure(parser, "/s Bob 1",
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "Bob 1",
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid student name
        assertParseFailure(parser, INVALID_STUDENT_DESC + " /c 1", Name.MESSAGE_CONSTRAINTS);

        // invalid class index
        assertParseFailure(parser, " /s Bob" + INVALID_CLASS_INDEX_DESC, ParserUtil.MESSAGE_INVALID_INDEX);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " /s Bob /c 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
    }
}
