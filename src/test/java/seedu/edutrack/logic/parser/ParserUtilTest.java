package seedu.edutrack.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.edutrack.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.edutrack.testutil.Assert.assertThrows;
import static seedu.edutrack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.edutrack.logic.parser.exceptions.ParseException;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.student.Name;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_CLASSNAME = "cs 2103t";
    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_CLASSNAME = "cs2103t";
    private static final String VALID_CLASS_PARTICIPATION = "Answered 2 questions";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(
                Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseClassName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClassName((String) null));
    }

    @Test
    public void parseClassName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClassName(INVALID_CLASSNAME));
    }

    @Test
    public void parseClassName_validValueWithoutWhitespace_returnsName() throws Exception {
        ClassName expectedClassName = new ClassName(VALID_CLASSNAME);
        assertEquals(expectedClassName, ParserUtil.parseClassName(VALID_CLASSNAME));
    }

    @Test
    public void parseClassName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String classNameWithWhitespace = WHITESPACE + VALID_CLASSNAME + WHITESPACE;
        ClassName expectedClassName = new ClassName(VALID_CLASSNAME);
        assertEquals(expectedClassName, ParserUtil.parseClassName(classNameWithWhitespace));
    }

    @Test
    public void parseClassParticipation_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClassParticipation((String) null));
    }

    @Test
    public void parseClassParticipation_validValue_returnsClassParticipation() throws Exception {
        Memo expectedClassParticipation = new Memo(VALID_CLASS_PARTICIPATION);
        assertEquals(expectedClassParticipation, ParserUtil.parseClassParticipation(VALID_CLASS_PARTICIPATION));
    }
}
