package seedu.edutrack.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.edutrack.commons.core.GuiSettings;
import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.exceptions.ClassNotFoundException;
import seedu.edutrack.model.student.Name;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedAbsentException;
import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedPresentException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Class> PREDICATE_SHOW_ALL_CLASSES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' edutrack book file path.
     */
    Path getEduTrackFilePath();

    /**
     * Sets the user prefs' edutrack book file path.
     */
    void setEduTrackFilePath(Path eduTrackFilePath);

    /**
     * Replaces edutrack book data with the data in {@code eduTrack}.
     */
    void setEduTrack(ReadOnlyEduTrack eduTrack);

    /** Returns the EduTrack */
    ReadOnlyEduTrack getEduTrack();

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * the edutrack book.
     */
    boolean hasStudent(Student person);

    /**
     * Deletes the given person.
     * The person must exist in the EduTrack.
     */
    void deleteStudent(Student target);

    /**
     * Deletes the given student from class.
     * The person must exist in the class.
     */
    void deleteStudent(Student student, Class studentClass);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the edutrack book.
     */
    void addStudent(Student person);

    /**
     * Adds the given student to the class.
     * The person must not already exist in the class.
     */
    void addStudentToClass(Student student, Class studentClass);

    /**
     * Gets a Student from list based on Student's Index.
     * @param list List containing Student.
     * @return Student corresponding to Index in the List.
     */
    Student getStudent(ObservableList<Student> list, Index index);

    /**
     * Gets List of Student in the Class.
     * @param studentClass Class to retrieve the List of Students from.
     * @return List of Student in the Class.
     */
    ObservableList<Student> getStudentList(Class studentClass);
    /**
     * Replaces the given student {@code target} with {@code editedPerson}.
     * {@code target} must exist in EduTrack.
     * The person identity of {@code editedPerson} must not be the same as another
     * existing person in EduTrack.
     */
    void setStudent(Student target, Student editedPerson);

    /**
     * Replaces the given student {@code target} with {@code editedStudent} in {@code targetClass}.
     * {@code target} must exist in EduTrack.
     * The student identity of {@code editedStudent} must not be the same as another
     * existing student in EduTrack.
     */
    void setStudentInClass(Student target, Student editedStudent, Class targetClass);

    /** Returns Name of the Student */
    Name getStudentName(Student student);

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the filtered class list */
    ObservableList<Class> getFilteredClassList();

    /**
     * Updates the filter of the filtered person list to filter by the given
     * {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    void addClass(Class c);

    boolean hasClass(Class c);

    void removeClass(Class c) throws ClassNotFoundException;

    Class retrieveClass(Index classListIndex) throws CommandException;

    void markStudentPresent(Student student, Class studentClass, Student editedStudent)
            throws StudentAlreadyMarkedPresentException;
    void markStudentAbsent(Student student, Class studentClass, Student editedStudent)
            throws StudentAlreadyMarkedAbsentException;
    void updateClassChange(Student student, Class studentClass, Student editedStudent);
    void startLessonForStudent(Student student, Class studentClass, Student editedStudent);
    boolean isValidAttendanceForStudent(Student student, Class studentClass);
    void startLesson(Class c);
    void setClassLesson(Class c, int num);
    void setStudentLesson(Student student, Class studentClass, Student editedStudent, int numLesson);

    Class getClass(ClassName className) throws ClassNotFoundException;

    Class getClassByIndex(Index classIndex);
    Student getStudentInClass(Index targetStudentIndex, Class studentClass) throws CommandException;
    Student duplicateStudent(Student studentToDuplicate);

    int getClassListSize();

    void updateFilteredClassList(Predicate<Class> predicate);

    /**
     * Replaces the given class at {@code index} with {@code editedClass}.
     * Class at {@code index} must exist in EduTrack.
     * The class identity of {@code editedClass} must not be the same as another
     * existing class in EduTrack.
     */
    void setClass(Index index, Class editedClass);
}
