package seedu.edutrack.model.student;

import static java.util.Objects.requireNonNull;

/**
 * Represents the total number of lessons a Student has attended.
 */
public class LessonsAttended {
    public static final String MESSAGE_CONSTRAINTS = "LessonsAttended should be an integer";
    private Integer totalLessons;

    /**
     * Constructs a {@code LessonsAttended}.
     *
     * @param totalLessons Number of lessons the student attended
     */
    public LessonsAttended(Integer totalLessons) {
        requireNonNull(totalLessons);
        if (!isValidLessonsAttended(totalLessons)) {
            throw new IllegalArgumentException();
        }
        this.totalLessons = totalLessons;
    }

    /**
     * Constructs a {@code Lessons Attended}.
     * Starts with initial count of 0
     */
    public LessonsAttended() {
        this.totalLessons = 0;
    }

    /**
     * Verifies if the lessonsAttended is valid.
     *
     * @param totalLessons Cumulative number of lessons the student attended
     * @return boolean True if lessonsAttended is valid, otherwise false
     */
    public static boolean isValidLessonsAttended(Integer totalLessons) {
        requireNonNull(totalLessons);
        if (totalLessons < 0) {
            return false;
        }
        return true;
    }

    public Integer getTotalLessons() {
        return this.totalLessons;
    }

    /**
     * Increases the number of lessons the student attended by 1.
     */
    public void incrementLessons() {
        this.totalLessons++;
    }

    /**
     * Reduces the number of lessons the student attended by 1.
     */
    public void decrementLessons() {
        this.totalLessons--;
    }

    /**
     * Sets the total number of lessons the student attended.
     * @param num The total number of lessons attended by the student.
     */
    public void setTotalLessons(int num) {
        this.totalLessons = num;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonsAttended)) {
            return false;
        }

        LessonsAttended otherAttendance = (LessonsAttended) other;
        return this.totalLessons.equals(otherAttendance.totalLessons);
    }
    @Override
    public String toString() {
        return this.totalLessons.toString();
    }
}
