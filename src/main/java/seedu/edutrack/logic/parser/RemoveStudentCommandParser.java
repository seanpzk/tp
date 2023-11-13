package seedu.edutrack.logic.parser;

import static seedu.edutrack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.stream.Stream;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.RemoveStudentCommand;
import seedu.edutrack.logic.parser.exceptions.ParseException;
import seedu.edutrack.model.module.ClassName;

/**
 * Parses input arguments and creates a new RemoveStudentCommand object
 */
public class RemoveStudentCommandParser implements Parser<RemoveStudentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveStudentCommand
     * and returns a RemoveStudentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_CLASS);

        // ensures that student name and class name is provided
        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT, PREFIX_CLASS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveStudentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT, PREFIX_CLASS);
        Index studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get());
        ClassName className = ParserUtil.parseClassName(argMultimap.getValue(PREFIX_CLASS).get());
        return new RemoveStudentCommand(studentIndex, className);
    }

    // Checks if all the prefixes are supplied by the user
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
