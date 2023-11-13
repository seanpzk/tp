package seedu.edutrack.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASSPARTICIPATION;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_MEMO;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.NoSuchElementException;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.EditStudentCommand;
import seedu.edutrack.logic.parser.exceptions.ParseException;
import seedu.edutrack.model.module.ClassName;

/**
 * Parses input arguments and creates a new EditStudentCommand object
 */
public class EditStudentCommandParser implements Parser<EditStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditStudentCommand
     * and returns an EditStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditStudentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_CLASS, PREFIX_ID, PREFIX_MEMO, PREFIX_NAME,
                        PREFIX_CLASSPARTICIPATION);

        Index studentIndex;
        ClassName studentClassName;

        try {
            studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get());
            studentClassName = ParserUtil.parseClassName(argMultimap.getValue(PREFIX_CLASS).get());
        } catch (ParseException | NoSuchElementException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStudentCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT, PREFIX_CLASS, PREFIX_NAME, PREFIX_ID, PREFIX_MEMO,
                PREFIX_CLASSPARTICIPATION);

        EditStudentCommand.EditStudentDescriptor editStudentDescriptor = new EditStudentCommand.EditStudentDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editStudentDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            editStudentDescriptor.setId(ParserUtil.parseId(argMultimap.getValue(PREFIX_ID).get()));
        }
        if (argMultimap.getValue(PREFIX_MEMO).isPresent()) {
            editStudentDescriptor.setMemo(ParserUtil.parseMemo(argMultimap.getValue(PREFIX_MEMO).get()));
        }
        if (argMultimap.getValue(PREFIX_CLASSPARTICIPATION).isPresent()) {
            editStudentDescriptor.setClassParticipation(ParserUtil.parseClassParticipation(
                    argMultimap.getValue(PREFIX_CLASSPARTICIPATION).get()));
        }

        if (!editStudentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditStudentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditStudentCommand(studentIndex, studentClassName, editStudentDescriptor);
    }
}
