package seedu.edutrack.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.commons.util.StringUtil;
import seedu.edutrack.logic.parser.exceptions.ParseException;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.Id;
import seedu.edutrack.model.student.Name;


/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_NUMLESSONS = "Number of lessons must be at least 0.";
    public static final String MESSAGE_INVALID_INT_INPUT = "Number of lessons only accept numbers from"
            + " 0 to 2147483647.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        try {
            if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String className} into a {@code ClassName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code className} is invalid.
     */
    public static ClassName parseClassName(String className) throws ParseException {
        String trimmedClassName = className.trim();
        if (ClassName.isEmptyClassName(trimmedClassName)) {
            throw new ParseException(ClassName.MESSAGE_EMPTY_CLASS_NAME);
        }
        if (!ClassName.isValidClassName(trimmedClassName)) {
            throw new ParseException(ClassName.MESSAGE_CONSTRAINTS);
        }
        return new ClassName(trimmedClassName);
    }

    /**
     * Parses a {@code String Note} into an {@code Note}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code note} is invalid.
     */
    public static Id parseId(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!Id.isValidId(trimmedId)) {
            throw new ParseException(Id.MESSAGE_CONSTRAINTS);
        }
        return new Id(trimmedId);
    }

    /**
     * Parses a {@code String Memo} into an {@code Memo}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code memo} is invalid.
     */
    public static Memo parseMemo(String memo) throws ParseException {
        requireNonNull(memo);
        String trimmedMemo = memo.trim();
        if (!Memo.isValidMemo(trimmedMemo)) {
            throw new ParseException(Memo.MESSAGE_CONSTRAINTS);
        }
        return new Memo(trimmedMemo);
    }

    /**
     * Parses a {@code String classSchedule} into a {@code Schedule}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code className} is invalid.
     */
    public static Schedule parseClassSchedule(String classSchedule) throws ParseException {
        String trimmedClassSchedule = classSchedule.trim();

        if (!Schedule.isValidSchedule(trimmedClassSchedule)) {
            throw new ParseException(Schedule.MESSAGE_CONSTRAINTS);
        }
        return new Schedule(trimmedClassSchedule);
    }

    /**
     * Parses a {@code String classParticipation} into a {@code Memo}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code memo} is invalid.
     */
    public static Memo parseClassParticipation(String classPart) throws ParseException {
        requireNonNull(classPart);
        String trimmedMemo = classPart.trim();
        if (!Memo.isValidMemo(trimmedMemo)) {
            throw new ParseException(Memo.MESSAGE_CONSTRAINTS);
        }
        return new Memo(trimmedMemo);
    }

    /**
     * Parses a {@code String numLessons} into an int.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given int is invalid.
     */
    public static int parseNumLessons(String numLessons) throws ParseException {
        requireNonNull(numLessons);
        int trimmedNumLessons;
        try {
            trimmedNumLessons = Integer.valueOf(numLessons.trim());
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_INT_INPUT);
        }
        if (trimmedNumLessons < 0) {
            throw new ParseException(MESSAGE_INVALID_NUMLESSONS);
        }
        return trimmedNumLessons;
    }
}
