package seedu.edutrack.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_CLASS_DISPLAYED_INDEX;
import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;

import java.util.stream.Stream;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.RemoveClassCommand;
import seedu.edutrack.logic.parser.exceptions.ParseException;

/**
 * Parses input from the user and creates a RemoveClassCommand.
 */
public class RemoveClassCommandParser implements Parser<RemoveClassCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveClassCommand
     * and returns a RemoveClassCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveClassCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLASS);
        if (!arePrefixesPresent(argMultimap, PREFIX_CLASS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveClassCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLASS);

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLASS).get());
            return new RemoveClassCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_CLASS_DISPLAYED_INDEX);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
