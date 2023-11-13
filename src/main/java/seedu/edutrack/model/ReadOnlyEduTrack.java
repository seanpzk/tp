package seedu.edutrack.model;

import javafx.collections.ObservableList;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.Student;

/**
 * Unmodifiable view of an edutrack book
 */
public interface ReadOnlyEduTrack {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Student> getStudentList();

    ObservableList<Class> getClassList();
}
