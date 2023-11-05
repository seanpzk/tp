package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.common.Memo;
import seedu.address.model.module.Class;
import seedu.address.model.student.exceptions.StudentAlreadyMarkedAbsent;
import seedu.address.model.student.exceptions.StudentAlreadyMarkedPresent;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Student {
    // Default fields
    private static final Id DEFAULT_ID = new Id("A0000000Z");
    private static final Memo DEFAULT_MEMO = new Memo("");

    // Identity fields
    private final Name name;
    private final Id id;

    // Data fields
    private final Memo memo;
    private Memo classParticipation;

    private Class studentClass = null;

    // The current lesson's attendance (Present/Absent)
    private CurrentLessonAttendance currentLessonAttendance;
    // Cumulative number of lessons attended
    private LessonsAttended lessonsAttended;

    /**
     * Constructs a Student with Name and Index that represents one-based class index.
     *
     * @param name Name to represent Student.
     */
    public Student(Name name) {
        requireNonNull(name);
        this.name = name;
        this.id = DEFAULT_ID;
        this.memo = DEFAULT_MEMO;
        this.currentLessonAttendance = new CurrentLessonAttendance(false);
        this.lessonsAttended = new LessonsAttended();
        this.classParticipation = DEFAULT_MEMO;
    }


    /**
     * Every field must be present and not null.
     * Mainly used for retrieving data from storage
     */
    public Student(Name name, Class studentClass, Id id, Memo memo, CurrentLessonAttendance currentLessonAttendance,
                   LessonsAttended lessonsAttended, Memo classParticipation) {
        requireAllNonNull(name, id, memo, currentLessonAttendance, lessonsAttended);
        this.name = name;
        this.studentClass = studentClass;
        this.id = id;
        this.memo = memo;
        this.currentLessonAttendance = currentLessonAttendance;
        this.lessonsAttended = lessonsAttended;
        this.classParticipation = classParticipation;
    }

    /**
     * Returns the Student's name
     *
     * @return Name of Student.
     */
    public Name getName() {
        return name;
    }


    /**
     * Updates Class containing Student after Student is initialized.
     * This can only be done once when Student is first initialized.
     *
     * @param studentClass Class containing Student.
     */
    public void setStudentClass(Class studentClass) {
        if (this.studentClass == null) {
            this.studentClass = studentClass;
        }
    }

    public Class getStudentClass() {
        return this.studentClass;
    }

    public CurrentLessonAttendance getCurrentAttendance() {
        return this.currentLessonAttendance;
    }
    public LessonsAttended getLessonsAttended() {
        return this.lessonsAttended;
    }

    public void setLessonsAttended(int num) {
        this.lessonsAttended.setTotalLessons(num);
    }

    public int getNumberOfLessonsAttended() {
        LessonsAttended l = this.lessonsAttended;
        return l.getTotalLessons();
    }

    public Id getId() {
        return id;
    }

    public Memo getMemo() {
        return memo;
    }

    public Memo getClassParticipation() {
        return classParticipation;
    }

    /**
     * Returns true if both students are the same.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName())
                && otherStudent.getId().equals(getId())
                && otherStudent.getMemo().equals(getMemo())
                && otherStudent.getCurrentAttendance().equals(getCurrentAttendance())
                && otherStudent.getLessonsAttended().equals(getLessonsAttended())
                && otherStudent.getClassParticipation().equals(getClassParticipation());
    }

    /**
     * Creates a duplicate object of this student.
     *
     * @return Student - Duplicate student that lives on a different part of memory
     */
    public Student duplicateStudent() {
        return new Student(new Name(this.name.fullName),
                this.getStudentClass(),
                this.getId(),
                this.getMemo(),
                new CurrentLessonAttendance(this.currentLessonAttendance.getIsPresent()),
                new LessonsAttended(this.lessonsAttended.getTotalLessons()),
                this.getClassParticipation());
    }

    /**
     * Marks a student as present for the current lesson.
     *
     * @throws StudentAlreadyMarkedPresent If the Student has already been marked present
     */
    public void markStudentPresent() throws StudentAlreadyMarkedPresent {
        this.currentLessonAttendance.setPresent();
        this.lessonsAttended.incrementLessons();
    }

    /**
     * Marks a student as absent for the current lesson.
     *
     * @throws StudentAlreadyMarkedAbsent If the Student has already been marked absent
     */
    public void markStudentAbsent() throws StudentAlreadyMarkedAbsent {
        this.currentLessonAttendance.setAbsent();
        this.lessonsAttended.decrementLessons();
    }

    /**
     * Sets a student attendance back to absent at the start of a new lesson.
     */
    public void startNewLesson() {
        try {
            this.currentLessonAttendance.setAbsent();
        } catch (StudentAlreadyMarkedAbsent ignored) {
            // do nothing
        }
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return name.equals(otherStudent.name)
                && id.equals(otherStudent.id)
                && memo.equals(otherStudent.memo)
                && currentLessonAttendance.equals(otherStudent.currentLessonAttendance)
                && lessonsAttended.equals(otherStudent.lessonsAttended)
                && classParticipation.equals(otherStudent.classParticipation);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    /**
     * Obtain String representation of the attendance of this Student.
     *
     * @return String - Y - Present current class. N - Absent for current class
     */
    public String getAttendanceStringRep() {
        return this.currentLessonAttendance.toString();
    }

    /**
     * Obtain String representation of the total number of lessons attended this Student attended.
     *
     * @return String - String representation of a integer
     */
    public String getTotalAttendanceStringRep() {
        return this.lessonsAttended.toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("id", id)
                .add("memo", memo)
                .add("currentLessonAttendance", currentLessonAttendance)
                .add("lessonsAttended", lessonsAttended)
                .toString();
    }

}
