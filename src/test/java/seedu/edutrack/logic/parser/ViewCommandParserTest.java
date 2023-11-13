package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edutrack.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.ViewCommand;
import seedu.edutrack.model.Model;
import seedu.edutrack.model.ModelManager;
import seedu.edutrack.model.UserPrefs;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.UniqueStudentList;
import seedu.edutrack.testutil.TypicalClasses;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();
    private final Index validClassIndex = Index.fromOneBased(1);


    private Model model = new ModelManager(TypicalClasses.getTypicalEduTrack(), new UserPrefs());

    @Test
    public void parse_allFieldsPresent_success() {
        ClassName className = new ClassName("cs2103t");
        model.addClass(new Class(className, new UniqueStudentList(), new Memo(" "), new Schedule()));
        ViewCommand expectedCommand = new ViewCommand(validClassIndex);
        assertParseSuccess(parser, " /c " + "1", expectedCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " /c " + "0",
                String.format(ViewCommand.MESSAGE_INVALID_CLASS_DISPLAYED_INDEX));
    }

    @Test
    public void parse_prefixMissing_failure() {
        assertParseFailure(parser, "1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_stringInsteadOfInteger_failure() {
        assertParseFailure(parser, " /c " + "a",
                String.format(ViewCommand.MESSAGE_INVALID_CLASS_DISPLAYED_INDEX));
    }
}
