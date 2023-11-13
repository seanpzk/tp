package seedu.edutrack.testutil;


import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.UniqueStudentList;

/**
 * A utility class to help with building Class objects.
 */
public class ClassBuilder {

    public static final String DEFAULT_CLASS_NAME = "cs2103t";
    public static final String DEFAULT_MEMO = "prepare materials";
    public static final String DEFAULT_SCHEDULE = "thu, 15:00-16:00";

    private ClassName className;
    private Memo memo;
    private Schedule schedule;
    private int totalLessons;

    /**
     * Creates a {@code ClassBuilder} with the default details.
     */
    public ClassBuilder() {
        className = new ClassName(DEFAULT_CLASS_NAME);
        memo = new Memo(DEFAULT_MEMO);
        schedule = new Schedule(DEFAULT_SCHEDULE);
        totalLessons = 0;
    }

    /**
     * Initializes the ClassBuilder with the data of {@code classToCopy}.
     */
    public ClassBuilder(Class classToCopy) {
        className = classToCopy.getClassName();
        memo = classToCopy.getClassMemo();
        schedule = classToCopy.getClassSchedule();
        totalLessons = classToCopy.getTotalLessons();
    }

    /**
     * Sets the {@code ClassName} of the {@code Class} that we are building.
     */
    public ClassBuilder withClassName(String className) {
        this.className = new ClassName(className);
        return this;
    }


    /**
     * Sets the {@code Memo} of the {@code Class} that we are building.
     */
    public ClassBuilder withMemo(String m) {
        this.memo = new Memo(m);
        return this;
    }

    /**
     * Sets the {@code Memo} of the {@code Class} that we are building.
     */
    public ClassBuilder withSchedule(String s) {
        this.schedule = new Schedule(s);
        return this;
    }

    public Class build() {
        return new Class(className, new UniqueStudentList(), memo, schedule);
    }

}
