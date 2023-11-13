package seedu.edutrack.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.commons.util.ToStringBuilder;
import seedu.edutrack.logic.Messages;
import seedu.edutrack.logic.commands.exceptions.CommandException;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.UniqueStudentList;

/**
 * Represents a Class in EduTrack.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Class {
    private final ClassName className;
    private final UniqueStudentList students;

    private final Memo classMemo;

    private final Schedule classSchedule;
    private int totalLessons;

    /**
     * Constructs a {@code Class} object that automatically creates a Memo and Schedule that hold empty value.
     *
     * @param className The name of the class. Must not be null.
     * @param students The list of students in the class. Must not be null.
     */
    public Class(ClassName className, UniqueStudentList students) {
        this(className, students, new Memo(" "), new Schedule());
    }

    /**
     * Constructs a {@code Class} object.
     *
     * @param className The name of the class. Must not be null.
     * @param students The list of students in the class. Must not be null.
     * @param classMemo A class note that can be empty. Must not be null.
     * @param classSchedule A class schedule that can be empty. Must not be null.
     */
    public Class(ClassName className, UniqueStudentList students, Memo classMemo, Schedule classSchedule) {
        requireAllNonNull(className, students, classMemo, classSchedule);
        this.className = className;
        this.students = students;
        this.classMemo = classMemo;
        this.classSchedule = classSchedule;
        this.totalLessons = 0;
    }

    public ClassName getClassName() {
        return className;
    }
    public Memo getClassMemo() {
        return classMemo;
    }
    public Schedule getClassSchedule() {
        return classSchedule;
    }
    public UniqueStudentList getUniqueStudentList() {
        return students;
    }
    /**
     * Starts a lesson and update GUI.
     */
    public void startLesson() {
        increaseTotalLessons();
    }
    public int getTotalLessons() {
        return totalLessons;
    }

    public void setTotalLessons(int newTotalLessons) {
        totalLessons = newTotalLessons;
    }
    /**
     * Increases total number of lessons in the class.
     */
    public void increaseTotalLessons() {
        totalLessons++;
    }

    /**
     * Checks if the given class is the same as this class.
     *
     * @param otherClass The other class to compare with.
     * @return True if the classes have the same class name, false otherwise.
     */
    public boolean isSameClass(Class otherClass) {
        if (otherClass == this) {
            return true;
        }

        return otherClass != null
                && otherClass.getClassName().equals(getClassName());
    }

    /**
     * Adds Student to Class and set the Class field in Student to the respective Class.
     * @param toAdd
     */
    public void addStudentToClass(Student toAdd) {
        students.add(toAdd);
        toAdd.setStudentClass(this);
    }

    /**
     * Removes Student from the Class it is in.
     * @param s Student to be removed from its Class.
     */
    public void removeStudent(Student s) {
        students.remove(s);
        assert(this.hasStudentInClass(s) == false) : "Class should not have studentToBeDeleted anymore.";
    }

    /**
     * Obtains the student based on the index in the UniqueStudentList of this class.
     *
     * @param targetStudentIndex The index of the student to retrieve
     * @return Student The student object corresponding to the indexStudentIndex
     * @throws CommandException If an error occurs during command execution.
     */
    public Student getStudentInClass(Index targetStudentIndex) throws CommandException {
        List<Student> studentList = this.getStudentList();

        if (studentList.size() == 0) {
            throw new CommandException(Messages.MESSAGE_EMPTY_STUDENT_LIST);
        }
        if (targetStudentIndex.getZeroBased() >= studentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return studentList.get(targetStudentIndex.getZeroBased());
    }
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    /**
     * Checks if this class is equal to another object.
     *
     * @param other The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Class)) {
            return false;
        }

        Class otherClass = (Class) other;
        return className.equals(otherClass.className);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(className);
    }

    /**
     * Returns true if a student with the same identity as {@code student} exists in
     * the class.
     *
     * @param student The student.
     * @return True if student is in class, false otherwise.
     */
    public boolean hasStudentInClass(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Replaces the given student {@code target} in the list with
     * {@code editedStudent}.
     * {@code target} must exist in the class
     * The student identity of {@code editedStudent} must not be the same as another
     * existing student in the class
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("className", className)
                .add("studentList", students)
                .add("classSchedule", classSchedule)
                .add("classMemo", classMemo)
                .add("totalLessons", totalLessons)
                .toString();
    }

    public String getClassInfo() {
        return "Current class: " + className.toString() + "\n" + "Schedule: " + classSchedule.toString()
                + "\n" + "Memo: " + classMemo.toString();
    }
}
