package seedu.edutrack.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.Student;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    /**
     * Memo: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Student person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label studentid;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label studentMemo;
    @FXML
    private Label classParticipation;
    @FXML
    private Label attendance;
    @FXML
    private Label overallAttendance;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to
     * display.
     */
    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.person = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        Class c = student.getStudentClass();
        assert(c != null) : "student's class attribute should not be null";
        String studentMemo = student.getMemo().toString();
        String classParticipation = student.getClassParticipation().toString();
        String totalLessons = String.valueOf(c.getTotalLessons());
        String overallAttendance = student.getTotalAttendanceStringRep() + "/" + totalLessons;
        this.attendance.setText(student.getAttendanceStringRep());
        this.overallAttendance.setText(overallAttendance);
        this.studentMemo.setText(studentMemo);
        this.studentid.setText(student.getId().toString());
        this.classParticipation.setText(classParticipation);
    }
}
