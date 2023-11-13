package seedu.edutrack.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.edutrack.commons.exceptions.IllegalValueException;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;
import seedu.edutrack.model.student.UniqueStudentList;

/**
 * Jackson-friendly version of {@link Class}.
 */
class JsonAdaptedClass {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Class's %s field is missing!";

    private final String className;
    private final String classNote;
    private final String classSchedule;
    private final int classTotalLessons;
    private final List<JsonAdaptedStudent> studentList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedClass} with the given class details.
     */
    @JsonCreator
    public JsonAdaptedClass(@JsonProperty("className") String className,
                            @JsonProperty("studentList") List<JsonAdaptedStudent> studentList,
                            @JsonProperty("classNote") String classNote,
                            @JsonProperty("classSchedule") String classSchedule,
                            @JsonProperty("totalLessons") int classTotalLessons) {
        this.className = className;
        this.classNote = classNote;
        this.classSchedule = classSchedule;
        this.classTotalLessons = classTotalLessons;
        if (studentList != null) {
            this.studentList.addAll(studentList);
        }
    }

    /**
     * Converts a given {@code Class} into this class for Jackson use.
     */
    public JsonAdaptedClass(Class source) {
        className = source.getClassName().toString();
        classNote = source.getClassMemo().toString();
        classSchedule = source.getClassSchedule().toString();
        classTotalLessons = source.getTotalLessons();
        studentList.addAll(source.getStudentList().stream()
                .map(JsonAdaptedStudent::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Class} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Class toModelType() throws IllegalValueException {
        final UniqueStudentList students = new UniqueStudentList();

        if (className == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClassName.class.getSimpleName()));
        }
        if (!ClassName.isValidClassName(className)) {
            throw new IllegalValueException(ClassName.MESSAGE_CONSTRAINTS);
        }
        final ClassName modelClassName = new ClassName(className);

        final Memo modelClassMemo;
        if (classNote == null) {
            modelClassMemo = new Memo(" ");
        } else {
            modelClassMemo = new Memo(classNote);
        }

        final Schedule modelClassSchedule;

        if (classSchedule == null) {
            modelClassSchedule = new Schedule(" ");
        } else if (!Schedule.isValidSchedule(classSchedule)) {
            throw new IllegalValueException(Schedule.MESSAGE_CONSTRAINTS);
        } else {
            modelClassSchedule = new Schedule(classSchedule);
        }

        final int modelClassTotalLessons = classTotalLessons;

        Class modelClass = new Class(modelClassName, students, modelClassMemo, modelClassSchedule);
        modelClass.setTotalLessons(modelClassTotalLessons);

        for (JsonAdaptedStudent student : studentList) {
            modelClass.addStudentToClass(student.toModelType());
        }

        return modelClass;
    }

}
