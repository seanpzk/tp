package seedu.edutrack.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.edutrack.commons.exceptions.IllegalValueException;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.student.CurrentLessonAttendance;
import seedu.edutrack.model.student.Id;
import seedu.edutrack.model.student.LessonsAttended;
import seedu.edutrack.model.student.Name;
import seedu.edutrack.model.student.Student;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String name;
    private final String id;
    private final String memo;
    private final Boolean currentLessonAttendance;
    private final Integer lessonsAttended;
    private final String classParticipation;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name,
                              @JsonProperty("id") String id,
                              @JsonProperty("memo") String memo,
                              @JsonProperty("currentLessonAttendance") Boolean currentLessonAttendance,
                              @JsonProperty("lessonsAttended") Integer lessonsAttended,
                              @JsonProperty("classParticipation") String classParticipation) {
        this.name = name;
        this.id = id;
        this.memo = memo;
        this.currentLessonAttendance = currentLessonAttendance;
        this.lessonsAttended = lessonsAttended;
        this.classParticipation = classParticipation;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        name = source.getName().fullName;
        id = source.getId().toString();
        memo = source.getMemo().toString();
        currentLessonAttendance = source.getCurrentAttendance().getIsPresent();
        lessonsAttended = source.getLessonsAttended().getTotalLessons();
        classParticipation = source.getClassParticipation().toString();
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's
     * {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted student.
     */
    public Student toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        if (!Id.isValidId(id)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
        if (!Memo.isValidMemo(memo)) {
            throw new IllegalValueException(Memo.MESSAGE_CONSTRAINTS);
        }

        if (currentLessonAttendance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CurrentLessonAttendance.class.getSimpleName()));
        }

        if (lessonsAttended == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LessonsAttended.class.getSimpleName()));
        }

        if (!Memo.isValidMemo(classParticipation)) {
            throw new IllegalValueException(Memo.MESSAGE_CONSTRAINTS);
        }

        final Name modelName = new Name(name);
        // set as null for now because Student's class will be handled by Class when Student is added into a Class
        final Class modelClass = null;
        final Id modelId = new Id(id);
        final Memo modelMemo = new Memo(memo);
        final CurrentLessonAttendance modelCurrentLessonAttendance = new CurrentLessonAttendance(
                currentLessonAttendance);
        final LessonsAttended modelLessonsAttended = new LessonsAttended(
                lessonsAttended);
        final Memo modelClassParticipation = new Memo(classParticipation);

        return new Student(modelName, modelClass, modelId, modelMemo, modelCurrentLessonAttendance,
                modelLessonsAttended, modelClassParticipation);
    }

}
