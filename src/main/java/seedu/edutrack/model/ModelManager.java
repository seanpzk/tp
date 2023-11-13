package seedu.edutrack.model;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.edutrack.commons.core.GuiSettings;
import seedu.edutrack.commons.core.LogsCenter;
import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.exceptions.ClassNotFoundException;
import seedu.edutrack.model.student.Name;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.exceptions.AttendanceDiscrepancyException;
import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedAbsentException;
import seedu.edutrack.model.student.exceptions.StudentAlreadyMarkedPresentException;

/**
 * Represents the in-memory model of the EduTrack data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final EduTrack eduTrack;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Class> filteredClasses;

    /**
     * Initializes a ModelManager with the given eduTrack and userPrefs.
     */
    public ModelManager(ReadOnlyEduTrack eduTrack, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(eduTrack, userPrefs);

        logger.fine("Initializing with edutrack book: " + eduTrack + " and user prefs " + userPrefs);

        this.eduTrack = new EduTrack(eduTrack);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.eduTrack.getStudentList());
        filteredClasses = new FilteredList<>(this.eduTrack.getClassList());
    }

    public ModelManager() {
        this(new EduTrack(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getEduTrackFilePath() {
        return userPrefs.getEduTrackFilePath();
    }

    @Override
    public void setEduTrackFilePath(Path eduTrackFilePath) {
        requireNonNull(eduTrackFilePath);
        userPrefs.setEduTrackFilePath(eduTrackFilePath);
    }

    //=========== EduTrack ================================================================================

    @Override
    public void setEduTrack(ReadOnlyEduTrack eduTrack) {
        this.eduTrack.resetData(eduTrack);
    }

    @Override
    public ReadOnlyEduTrack getEduTrack() {
        return eduTrack;
    }

    @Override
    public boolean hasStudent(Student person) {
        requireNonNull(person);
        return eduTrack.hasStudent(person);
    }

    @Override
    public void deleteStudent(Student target) {
        eduTrack.removeStudent(target);
    }

    @Override
    public void deleteStudent(Student student, Class studentClass) {
        studentClass.removeStudent(student);
        updateFilteredStudentList((s) -> studentClass.getStudentList().contains(s));
    }

    @Override
    public void addStudent(Student person) {
        eduTrack.addStudent(person);
    }

    @Override
    public void addStudentToClass(Student student, Class studentClass) {
        studentClass.addStudentToClass(student);
        updateFilteredStudentList((s) -> studentClass.getStudentList().contains(s));
    }

    @Override
    public void updateClassChange(Student student, Class studentClass, Student editedStudent) {
        editedStudent.forceChangeStudentClass(studentClass);
        student.forceChangeStudentClass(studentClass);
        if (eduTrack.hasStudent(student)) {
            eduTrack.setStudent(student, editedStudent);
        }
    }

    @Override
    public void addClass(Class c) {
        eduTrack.addClass(c);
        updateFilteredClassList(PREDICATE_SHOW_ALL_CLASSES);
    }

    @Override
    public boolean hasClass(Class c) {
        requireNonNull(c);
        return eduTrack.hasClass(c);
    }

    @Override
    public void setClass(Index index, Class editedClass) {
        requireAllNonNull(index, editedClass);
        eduTrack.setClass(index, editedClass);
    }

    @Override
    public void removeClass(Class c) {
        requireNonNull(c);
        eduTrack.removeClass(c);
    }

    @Override
    public Class retrieveClass(Index targetClassIndex) throws CommandException {
        ObservableList<Class> classList = this.eduTrack.getClassList();
        if (classList.size() == 0) {
            throw new CommandException(Messages.MESSAGE_EMPTY_CLASS_LIST);
        }
        if (targetClassIndex.getZeroBased() >= classList.size()) {
            throw new CommandException(Messages.MESSAGE_INDEX_INPUT_TOO_LARGE);
        }

        return classList.get(targetClassIndex.getZeroBased());
    }

    @Override
    public void markStudentPresent(Student student, Class studentClass, Student editedStudent)
            throws StudentAlreadyMarkedPresentException, AttendanceDiscrepancyException {
        editedStudent.markStudentPresent();
        eduTrack.setStudent(student, editedStudent);
        setStudentInClass(student, editedStudent, studentClass);
        updateFilteredStudentList((s) -> studentClass.getStudentList().contains(s));
    }

    @Override
    public void markStudentAbsent(Student student, Class studentClass, Student editedStudent)
            throws StudentAlreadyMarkedAbsentException {
        editedStudent.markStudentAbsent();
        eduTrack.setStudent(student, editedStudent);
        setStudentInClass(student, editedStudent, studentClass);
        updateFilteredStudentList((s) -> studentClass.getStudentList().contains(s));
    }

    @Override
    public Student getStudentInClass(Index targetStudentIndex, Class studentClass) throws CommandException {
        return studentClass.getStudentInClass(targetStudentIndex);
    }

    @Override
    public Student duplicateStudent(Student studentToDuplicate) {
        return studentToDuplicate.duplicateStudent();
    }

    @Override
    public void startLessonForStudent(Student student, Class studentClass, Student editedStudent) {
        editedStudent.startNewLesson();
        eduTrack.setStudent(student, editedStudent);

        student.startNewLesson();
        updateFilteredStudentList((s) -> studentClass.getStudentList().contains(s));
    }

    @Override
    public boolean isValidAttendanceForStudent(Student student, Class studentClass) {
        return student.getNumberOfLessonsAttended() <= studentClass.getTotalLessons();
    }

    @Override
    public void startLesson(Class c) {
        c.startLesson();
        updateFilteredStudentList((s) -> c.getStudentList().contains(s));
    }

    public void setClassLesson(Class c, int num) {
        c.setTotalLessons(num);
        updateFilteredStudentList((s) -> c.getStudentList().contains(s));
    }


    public void setStudentLesson(Student student, Class studentClass, Student editedStudent, int numLesson) {
        editedStudent.setLessonsAttended(numLesson);
        eduTrack.setStudent(student, editedStudent);
        setStudentInClass(student, editedStudent, studentClass);
        updateFilteredStudentList((s) -> studentClass.getStudentList().contains(s));
    }

    public Class getClass(ClassName className) throws ClassNotFoundException {
        requireNonNull(className);
        return eduTrack.getClass(className);
    }

    public Class getClassByIndex(Index classIndex) {
        requireNonNull(classIndex);
        return eduTrack.getClassByIndex(classIndex);
    }

    @Override
    public Name getStudentName(Student student) {
        return student.getName();
    }

    @Override
    public Student getStudent(ObservableList<Student> list, Index index) {
        return list.get(index.getZeroBased());
    }

    @Override
    public ObservableList<Student> getStudentList(Class studentClass) {
        return studentClass.getStudentList();
    }

    public int getClassListSize() {
        return eduTrack.getClassListSize();
    }
    //=========== Filtered Person List Accessors =============================================================
    @Override
    public void setStudent(Student target, Student editedPerson) {
        requireAllNonNull(target, editedPerson);

        eduTrack.setStudent(target, editedPerson);
    }

    @Override
    public void setStudentInClass(Student target, Student editedStudent, Class targetClass) {
        requireAllNonNull(target, editedStudent, targetClass);

        targetClass.setStudent(target, editedStudent);
    }

    // =========== Filtered Person List Accessors
    // =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedEduTrack}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public ObservableList<Class> getFilteredClassList() {
        return filteredClasses;
    }

    @Override
    public void updateFilteredClassList(Predicate<Class> predicate) {
        requireNonNull(predicate);
        filteredClasses.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return eduTrack.equals(otherModelManager.eduTrack)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredStudents.equals(otherModelManager.filteredStudents);
    }

}
