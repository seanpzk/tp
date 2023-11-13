package seedu.edutrack.model.util;

import seedu.edutrack.model.EduTrack;
import seedu.edutrack.model.ReadOnlyEduTrack;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.CurrentLessonAttendance;
import seedu.edutrack.model.student.Id;
import seedu.edutrack.model.student.LessonsAttended;
import seedu.edutrack.model.student.Name;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.UniqueStudentList;

/**
 * Contains utility methods for populating {@code EduTrack} with sample data.
 */
public class SampleDataUtil {

    /**
     * Initialises 2 classes.
     *
     * @return Array of Classes.
     */
    public static Class[] getSampleClasses() {
        return new Class[] {
            new Class(new ClassName("T1"), new UniqueStudentList(),
                    new Memo("First Class"), new Schedule("mon, 10:00-12:00")),
            new Class(new ClassName("T2"), new UniqueStudentList(),
                    new Memo("Second Class"), new Schedule("tue, 10:00-12:00"))
        };
    }

    /**
     * Initialises 5 students.
     *
     * @return Array of Students.
     */
    public static Student[] getSampleStudentList1() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), null, new Id("A0783471Z"), new Memo("Quiet."),
                    new CurrentLessonAttendance(true), new LessonsAttended(7), new Memo("Answered some questions.")),
            new Student(new Name("Bernice Yu"), null, new Id("A0125264Z"), new Memo(""),
                    new CurrentLessonAttendance(true), new LessonsAttended(7), new Memo("")),
            new Student(new Name("Charlotte Oliveiro"), null, new Id("A0753421Z"), new Memo("Requires more help."),
                new CurrentLessonAttendance(true), new LessonsAttended(7), new Memo("")),
            new Student(new Name("David Li"), null, new Id("A0123641Z"), new Memo("Usually late."),
                    new CurrentLessonAttendance(true), new LessonsAttended(7), new Memo("Presented his answers.")),
            new Student(new Name("Irfan Ibrahim"), null, new Id("A0827231Z"), new Memo("Funny."),
                new CurrentLessonAttendance(true), new LessonsAttended(7), new Memo("Answered some questions.")),
            new Student(new Name("Roy Balakrishnan"), null, new Id("A0723471Z"), new Memo(""),
                    new CurrentLessonAttendance(true), new LessonsAttended(7), new Memo(""))
        };
    }

    /**
     * Initialises another 5 students.
     *
     * @return Array of 5 Students.
     */
    public static Student[] getSampleStudentList2() {
        return new Student[] {
            new Student(new Name("Carol Mcleod"), null, new Id("A1383296T"), new Memo(""),
                    new CurrentLessonAttendance(true), new LessonsAttended(7), new Memo("")),
            new Student(new Name("Kylie Dominguez"), null, new Id("A9359958U"), new Memo(""),
                    new CurrentLessonAttendance(false), new LessonsAttended(2), new Memo("")),
            new Student(new Name("Lily Luna"), null, new Id("A5997977P"), new Memo(""),
                    new CurrentLessonAttendance(true), new LessonsAttended(7), new Memo("")),
            new Student(new Name("Aarav Salazar"), null, new Id("A6447199Z"), new Memo("Usually late."),
                    new CurrentLessonAttendance(false), new LessonsAttended(3), new Memo("")),
            new Student(new Name("Sian Pena"), null, new Id("A0657231Z"), new Memo("Funny."),
                    new CurrentLessonAttendance(false), new LessonsAttended(5), new Memo("")),
            new Student(new Name("John Doe"), null, new Id("A0832341Z"), new Memo(""),
                    new CurrentLessonAttendance(true), new LessonsAttended(7), new Memo(""))
        };
    }

    /**
     * Creates a sample EduTrack.
     *
     * @return ReadOnlyEduTrack with sample data.
     */
    public static ReadOnlyEduTrack getSampleEduTrack() {
        EduTrack sampleEt = new EduTrack();
        Class[] sampleClasses = getSampleClasses();
        for (Class sampleClass : getSampleClasses()) {
            sampleClass.setTotalLessons(8);
            sampleEt.addClass(sampleClass);
            if (sampleClass.equals(sampleClasses[0])) {
                for (Student sampleStudent : getSampleStudentList1()) {
                    sampleEt.addStudent(sampleStudent);
                    sampleClass.addStudentToClass(sampleStudent);
                }
            } else {
                for (Student sampleStudent : getSampleStudentList2()) {
                    sampleEt.addStudent(sampleStudent);
                    sampleClass.addStudentToClass(sampleStudent);
                }
            }
        }
        return sampleEt;
    }

}
