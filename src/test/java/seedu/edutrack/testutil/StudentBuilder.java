package seedu.edutrack.testutil;


import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.CurrentLessonAttendance;
import seedu.edutrack.model.student.Id;
import seedu.edutrack.model.student.LessonsAttended;
import seedu.edutrack.model.student.Name;
import seedu.edutrack.model.student.Student;

/**
 * A utility class to help with building Person objects.
 */
public class StudentBuilder {

    public static final int DEFAULT_CLASSINDEX = 1;
    public static final String DEFAULT_NAME = "Amy Bee";
    private static final String DEFAULT_ID = "A0000000Z";
    private static final String DEFAULT_MEMO = "";
    private static final Boolean DEFAULT_CURRENT_LESSON_ATTENDANCE = false;
    private static final Integer DEFAULT_LESSONS_ATTENDED = 5;
    private static final String DEFAULT_CLASS_PARTICIPATION = "";
    private Index classIndex;
    private Class studentClass;

    private Name name;
    private Id id;
    private Memo memo;
    private CurrentLessonAttendance currentLessonAttendance;
    private LessonsAttended lessonsAttended;
    private Memo classParticipation;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        id = new Id(DEFAULT_ID);
        memo = new Memo(DEFAULT_MEMO);
        currentLessonAttendance = new CurrentLessonAttendance(DEFAULT_CURRENT_LESSON_ATTENDANCE);
        lessonsAttended = new LessonsAttended(DEFAULT_LESSONS_ATTENDED);
        classIndex = Index.fromOneBased(DEFAULT_CLASSINDEX);
        classParticipation = new Memo(DEFAULT_CLASS_PARTICIPATION);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public StudentBuilder(Student personToCopy) {
        name = personToCopy.getName();
        studentClass = personToCopy.getStudentClass();
        id = personToCopy.getId();
        memo = personToCopy.getMemo();
        currentLessonAttendance = personToCopy.getCurrentAttendance();
        lessonsAttended = personToCopy.getLessonsAttended();
        classParticipation = personToCopy.getClassParticipation();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Id} of the {@code Student} that we are building.
     */
    public StudentBuilder withId(String id) {
        this.id = new Id(id);
        return this;
    }

    /**
     * Sets the {@code Memo} of the {@code Student} that we are building.
     */
    public StudentBuilder withMemo(String memo) {
        this.memo = new Memo(memo);
        return this;
    }

    /**
     * Sets the {@code CurrentLessonAttendance} of the {@code Student} that we are building.
     */
    public StudentBuilder withCurrentLessonAttendance(Boolean isPresent) {
        this.currentLessonAttendance = new CurrentLessonAttendance(isPresent);
        return this;
    }

    /**
     * Sets the {@code LessonsAttended} of the {@code Student} that we are building.
     */
    public StudentBuilder withLessonsAttended(Integer lessonsAttended) {
        this.lessonsAttended = new LessonsAttended(lessonsAttended);
        return this;
    }

    /**
     * Sets the classIndex from One based of the Student that we are building.
     * @param classIndex ClassIndex of Student's Class.
     * @return StudentBuilder to build the Student.
     */
    public StudentBuilder withClassIndex(int classIndex) {
        this.classIndex = Index.fromOneBased(classIndex);
        return this;
    }

    /**
     * Sets the Class of the Student we are building to studentClass.
     * @param studentClass Class of Student.
     * @return StudentBuilder to build the Student.
     */
    public StudentBuilder withStudentClass(Class studentClass) {
        this.studentClass = studentClass;
        return this;
    }

    /**
     * Sets the {@code ClassParticipation} of the {@code Student} that we are building.
     * @param classParticipation
     * @return
     */
    public StudentBuilder withClassParticipation(String classParticipation) {
        this.classParticipation = new Memo(classParticipation);
        return this;
    }

    /**
     * Builds the Student.
     * @return Student.
     */
    public Student build() {
        return new Student(name, studentClass, id, memo, currentLessonAttendance, lessonsAttended,
                           classParticipation);
    }

}
