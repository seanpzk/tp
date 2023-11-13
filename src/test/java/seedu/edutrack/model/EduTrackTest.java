package seedu.edutrack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edutrack.testutil.Assert.assertThrows;
import static seedu.edutrack.testutil.TypicalStudents.ALICE;
import static seedu.edutrack.testutil.TypicalStudents.getTypicalEduTrack;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.exceptions.ClassNotFoundException;
import seedu.edutrack.model.student.Name;
import seedu.edutrack.model.student.Student;
import seedu.edutrack.model.student.exceptions.DuplicateStudentException;
import seedu.edutrack.testutil.StudentBuilder;

public class EduTrackTest {

    private final EduTrack eduTrack = new EduTrack();


    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eduTrack.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduTrack.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyEduTrack_replacesData() {
        EduTrack newData = getTypicalEduTrack();
        eduTrack.resetData(newData);
        assertEquals(newData, eduTrack);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).build();

        List<Student> newPersons = Arrays.asList(ALICE, editedAlice);
        EduTrackStub newData = new EduTrackStub(newPersons);

        assertThrows(DuplicateStudentException.class, () -> eduTrack.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduTrack.hasStudent(null));
    }

    @Test
    public void hasPerson_personNotInEduTrack_returnsFalse() {
        assertFalse(eduTrack.hasStudent(ALICE));
    }

    @Test
    public void hasPerson_personInEduTrack_returnsTrue() {
        eduTrack.addStudent(ALICE);
        assertTrue(eduTrack.hasStudent(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInEduTrack_returnsTrue() {

        eduTrack.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).build();
        assertTrue(eduTrack.hasStudent(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> eduTrack.getStudentList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = EduTrack.class.getCanonicalName() + "{students=" + eduTrack.getStudentList() + "}";
        assertEquals(expected, eduTrack.toString());
    }

    @Test
    public void addStudent_listSizeIncreasesByOne_returnsTrue() {
        EduTrack newData = getTypicalEduTrack();
        eduTrack.resetData(newData);
        int originalSize = eduTrack.getStudentList().size();
        Name studentNameStub = new Name("studentNameStub");
        Index studentClassIndexStub = Index.fromOneBased(1);
        Student studentStub = new Student(studentNameStub);

        eduTrack.addStudent(studentStub);
        int newSize = eduTrack.getStudentList().size();
        int diff = newSize - originalSize;
        assertEquals(diff, 1);
    }

    // This test needs to be refined but just putting here for now to increase coverage to push PR
    // for others to start working.
    @Test
    public void removeStudent_listSizeDecreasesByOne_returnsTrue() {
        EduTrack newData = getTypicalEduTrack();
        eduTrack.resetData(newData);
        int originalSize = eduTrack.getStudentList().size();
        Name studentNameStub = new Name("studentNameStub");
        Index studentClassIndexStub = Index.fromZeroBased(0);
        Student studentStub = new Student(studentNameStub);
        eduTrack.addStudent(studentStub);
        eduTrack.removeStudent(studentStub);
        int newSize = eduTrack.getStudentList().size();
        int diff = newSize - originalSize;
        assertEquals(diff, 0);
    }

    @Test
    public void getClass_eduTrackWithNoClass_returnsFalse() {
        EduTrack newData = getTypicalEduTrack();
        eduTrack.resetData(newData);
        ClassName classNameStub = new ClassName("classNameStub");
        assertThrows(ClassNotFoundException.class, () -> eduTrack.getClass(classNameStub));
    }

    /**
     * A stub ReadOnlyEduTrack whose persons list can violate interface
     * constraints.
     */

    private static class EduTrackStub implements ReadOnlyEduTrack {
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        private final ObservableList<Class> classes = FXCollections.observableArrayList();

        EduTrackStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<Class> getClassList() {
            return classes;
        }
    }

}
