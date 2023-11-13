package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;

import java.util.stream.Stream;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.ViewCommand;
import seedu.edutrack.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not follow the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLASS);

        try {

            // ensures that class index is provided
            if (!arePrefixesPresent(argMultimap, PREFIX_CLASS)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
            }

            //ensures that the index provided is more than 0
            if (Integer.parseInt(argMultimap.getValue(PREFIX_CLASS).get()) <= 0) {
                throw new ParseException(String.format(ViewCommand.MESSAGE_INVALID_CLASS_DISPLAYED_INDEX,
                        ViewCommand.MESSAGE_USAGE));
            }

            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLASS);
            Index classIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLASS).get());

            return new ViewCommand(classIndex);
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(ViewCommand.MESSAGE_INVALID_CLASS_DISPLAYED_INDEX, ViewCommand.MESSAGE_USAGE));
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
