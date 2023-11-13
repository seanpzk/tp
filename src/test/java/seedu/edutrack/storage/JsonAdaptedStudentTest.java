package seedu.edutrack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.edutrack.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.edutrack.testutil.Assert.assertThrows;
import static seedu.edutrack.testutil.TypicalStudents.BENSON;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.exceptions.IllegalValueException;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.Name;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";

    private static final Class VALID_CLASS = null;
    private static final Boolean VALID_CURRENT_ATTENDANCE = false;
    private static final Integer VALID_LESSONS_ATTENDED = 5;
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_ID = "A0257893R";
    private static final String VALID_MEMO = "Gets distracted easily.";
    private static final String VALID_CLASS_PARTICIPATION = "";


    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent person = new JsonAdaptedStudent(VALID_NAME,
                VALID_ID, VALID_MEMO, VALID_CURRENT_ATTENDANCE, VALID_LESSONS_ATTENDED,
                VALID_CLASS_PARTICIPATION);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStudent person = new JsonAdaptedStudent(INVALID_NAME,
                VALID_ID, VALID_MEMO, VALID_CURRENT_ATTENDANCE, VALID_LESSONS_ATTENDED,
                VALID_CLASS_PARTICIPATION);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent person = new JsonAdaptedStudent((String) null,
                VALID_ID, VALID_MEMO, VALID_CURRENT_ATTENDANCE, VALID_LESSONS_ATTENDED,
                VALID_CLASS_PARTICIPATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullClassParticipation_throwsIllegalValueException() {
        JsonAdaptedStudent person = new JsonAdaptedStudent(VALID_NAME,
                VALID_ID, VALID_MEMO, VALID_CURRENT_ATTENDANCE, VALID_LESSONS_ATTENDED,
                null);
        // check for null pointer exception
        assertThrows(NullPointerException.class, person::toModelType);
    }

}
