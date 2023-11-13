package seedu.edutrack.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.stream.Stream;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.MarkStudentPresentCommand;
import seedu.edutrack.logic.parser.exceptions.ParseException;
import seedu.edutrack.model.module.ClassName;

/**
 * Parses input arguments and creates a new MarkStudentPresentCommand object.
 */
public class MarkStudentPresentCommandParser implements Parser<MarkStudentPresentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkStudentPresentCommand
     * and returns an MarkStudentPresentCommand object for execution.
     * @throws ParseException if the user input does not follow the expected format
     */
    public MarkStudentPresentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_CLASS);

        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT, PREFIX_CLASS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkStudentPresentCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT, PREFIX_CLASS);
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get());
        ClassName className = ParserUtil.parseClassName(argMultimap.getValue(PREFIX_CLASS).get());
        return new MarkStudentPresentCommand(index, className);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
