package seedu.edutrack.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;

import java.util.stream.Stream;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.MarkAllStudentAbsentCommand;
import seedu.edutrack.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAllStudentAbsentCommand object.
 */
public class MarkAllStudentAbsentCommandParser implements Parser<MarkAllStudentAbsentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAllStudentAbsentCommand
     * and returns an MarkAllStudentAbsentCommand object for execution.
     * @throws ParseException if the user input does not follow the expected format
     */
    public MarkAllStudentAbsentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLASS);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLASS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAllStudentAbsentCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLASS);
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLASS).get());
            return new MarkAllStudentAbsentCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAllStudentAbsentCommand.MESSAGE_USAGE), pe);
        }
    }

    // Checks if all the prefixes are supplied by the user
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
