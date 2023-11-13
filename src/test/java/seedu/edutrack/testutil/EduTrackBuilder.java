package seedu.edutrack.testutil;

import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.student.Student;

/**
 * A utility class to help with building EduTrack objects.
 * Example usage: <br>
 *     {@code EduTrack ab = new EduTrackBuilder().withPerson("John", "Doe").build();}
 */
public class EduTrackBuilder {

    private EduTrack eduTrack;

    public EduTrackBuilder() {
        eduTrack = new EduTrack();
    }

    public EduTrackBuilder(EduTrack eduTrack) {
        this.eduTrack = eduTrack;
    }

    /**
     * Adds a new {@code Person} to the {@code EduTrack} that we are building.
     */
    public EduTrackBuilder withPerson(Student student) {
        eduTrack.addStudent(student);
        return this;
    }

    public EduTrack build() {
        return eduTrack;
    }
}
