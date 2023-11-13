package seedu.edutrack.testutil;

import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_MEMO;
import static seedu.edutrack.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.edutrack.logic.commands.EditStudentCommand;
import seedu.edutrack.model.student.Student;

/**
 * A utility class for Person.
 */
public class StudentUtil {

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Student person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given
     * {@code EditStudentDescriptor}'s details.
     */
    public static String getEditStudentDescriptorDetails(EditStudentCommand.EditStudentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getId().ifPresent(id -> sb.append(PREFIX_ID).append(id.id).append(" "));
        descriptor.getMemo().ifPresent(memo -> sb.append(PREFIX_MEMO).append(memo.memo).append(" "));
        return sb.toString();
    }
}
