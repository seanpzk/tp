package seedu.edutrack.testutil;

import static seedu.edutrack.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.edutrack.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.UniqueStudentList;

/**
 * A utility class containing a list of {@code Person} objects to be used in
 * tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withId("A0251234Z")
            .withMemo("")
            .withCurrentLessonAttendance(true)
            .withLessonsAttended(5)
            .withClassParticipation("")
            .build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withId("A0257893R")
            .withMemo("Gets distracted easily.")
            .withCurrentLessonAttendance(false)
            .withLessonsAttended(5)
            .withClassParticipation("")
            .build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz")
            .withId("A0267293K")
            .withMemo("")
            .withCurrentLessonAttendance(true)
            .withLessonsAttended(5)
            .withClassParticipation("")
            .build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier")
            .withId("A0129873H")
            .withMemo("")
            .withCurrentLessonAttendance(false)
            .withLessonsAttended(5)
            .withClassParticipation("")
            .build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer")
            .withId("A0918239U")
            .withMemo("")
            .withCurrentLessonAttendance(false)
            .withLessonsAttended(5)
            .withClassParticipation("")
            .build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz")
            .withId("A1280382I")
            .withMemo("")
            .withCurrentLessonAttendance(false)
            .withLessonsAttended(5)
            .withClassParticipation("")
            .build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best")
            .withId("A0257372P")
            .withMemo("")
            .withCurrentLessonAttendance(true)
            .withLessonsAttended(5)
            .withClassParticipation("")
            .build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier")
            .withId("A8973678F")
            .withMemo("Quiet.")
            .withCurrentLessonAttendance(true)
            .withLessonsAttended(5)
            .withClassParticipation("")
            .build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller")
            .withId("A0282987T")
            .withMemo("Needs help in algorithms.")
            .withCurrentLessonAttendance(true)
            .withLessonsAttended(5)
            .withClassParticipation("")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY)
            .withCurrentLessonAttendance(true)
            .withLessonsAttended(5)
            .withClassParticipation("")
            .build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB)
            .withCurrentLessonAttendance(true)
            .withLessonsAttended(5)
            .withClassParticipation("")
            .build();

    private TypicalStudents() {
    } // prevents instantiation

    /**
     * Returns an {@code EduTrack} with all the typical persons.
     */
    public static EduTrack getTypicalEduTrack() {
        EduTrack et = new EduTrack();
        Class t1 = new Class(new ClassName("T1"), new UniqueStudentList(),
                new Memo(""), new Schedule());
        et.addClass(t1);
        for (Student student : getTypicalStudents()) {
            et.addStudent(student);
            t1.addStudentToClass(student);
        }
        return et;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
