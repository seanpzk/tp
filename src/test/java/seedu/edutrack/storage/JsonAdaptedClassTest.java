package seedu.edutrack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.edutrack.storage.JsonAdaptedClass.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.edutrack.testutil.Assert.assertThrows;
import static seedu.edutrack.testutil.TypicalClasses.CS2105;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edutrack.commons.exceptions.IllegalValueException;
import seedu.edutrack.model.module.ClassName;

public class JsonAdaptedClassTest {

    private static final String INVALID_CLASS_NAME = "cs 2103t";
    private static final List<JsonAdaptedStudent> EMPTY_STUDENT_LIST = new ArrayList<>();
    private static final String EMPTY_CLASS_NOTE = " ";
    private static final String EMPTY_CLASS_SCHEDULE = " ";
    private static final int VALID_CLASS_TOTAL_LESSONS = 0;

    @Test
    public void toModelType_validClassDetails_returnsClass() throws Exception {
        JsonAdaptedClass c = new JsonAdaptedClass("CS2105", EMPTY_STUDENT_LIST,
                EMPTY_CLASS_NOTE, EMPTY_CLASS_SCHEDULE, VALID_CLASS_TOTAL_LESSONS);
        assertEquals(CS2105, c.toModelType());
    }

    @Test
    public void toModelType_invalidClassName_throwsIllegalValueException() {
        JsonAdaptedClass c =
                new JsonAdaptedClass(INVALID_CLASS_NAME, EMPTY_STUDENT_LIST,
                        EMPTY_CLASS_NOTE, EMPTY_CLASS_SCHEDULE, VALID_CLASS_TOTAL_LESSONS);
        String expectedMessage = ClassName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, c::toModelType);
    }

    @Test
    public void toModelType_nullClassName_throwsIllegalValueException() {
        JsonAdaptedClass c = new JsonAdaptedClass((String) null, new ArrayList<JsonAdaptedStudent>(),
                EMPTY_CLASS_NOTE, EMPTY_CLASS_SCHEDULE, VALID_CLASS_TOTAL_LESSONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ClassName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, c::toModelType);
    }
}
