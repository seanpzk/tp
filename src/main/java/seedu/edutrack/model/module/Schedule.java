package seedu.edutrack.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.commons.util.AppUtil.checkArgument;

/**
 * Represents a Class's schedule in EduTrack.
 * Guarantees: immutable; is valid as declared in {@link #isValidSchedule(String)}
 */
public class Schedule {

    public static final String MESSAGE_CONSTRAINTS = "Schedule should follow the format: day, hh:mm-hh:mm\n"
            + "E.g: mon, 10:00-12:00";

    public static final String VALIDATION_REGEX = "^[\\w]+,\\s(\\d{2}:\\d{2}-\\d{2}:\\d{2})$";

    public final String value;

    /**
     * Constructs a default empty {@code Schedule}.
     */
    public Schedule() {
        value = " ";
    }
    /**
     * Constructs a {@code Schedule}.
     *
     * @param schedule A valid schedule.
     */
    public Schedule(String schedule) {
        requireNonNull(schedule);
        checkArgument(isValidSchedule(schedule), MESSAGE_CONSTRAINTS);
        value = schedule;
    }

    /**
     * Returns true if a given string is a valid schedule.
     */
    public static boolean isValidSchedule(String test) {
        if (test.trim().equals("")) {
            return true;
        }
        String[] parts = test.trim().split(",");
        return (isValidFormat(test) && isValidDay(parts[0]) && isValidTimeRange(parts[1]));
    }

    /**
     * Checks if a given string matches a specific format defined by a regular expression.
     *
     * @param test The string to be validated.
     * @return True if the string matches the specified format, otherwise false.
     */
    public static boolean isValidFormat(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if a given string represents a valid day of the week (Monday to Friday).
     *
     * @param test The string to be validated.
     * @return True if the string is a valid day ("Mon," "Tue," "Wed," "Thu," or "Fri"), otherwise false.
     */
    public static boolean isValidDay(String test) {
        return test.trim().toLowerCase().matches("mon|tue|wed|thu|fri");
    }

    /**
     * Checks if a given string represents a valid time range in the format "HH:MM-HH:MM."
     *
     * @param test The string to be validated.
     * @return True if the string is a valid time range, otherwise false.
     */
    public static boolean isValidTimeRange(String test) {
        String[] timeArray = test.split("-");
        String startTime = timeArray[0].trim();
        String endTime = timeArray[1].trim();

        String[] startParts = startTime.split(":");
        String[] endParts = endTime.split(":");

        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);
        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);

        boolean validHour = startHour >= 0 && startHour <= 23 && endHour >= 0 && endHour <= 23;
        boolean validMinute = startMinute >= 0 && startMinute <= 59 && endMinute >= 0 && endMinute <= 59;
        boolean validTimeRange = (startHour < endHour) || (startHour == endHour && startMinute < endMinute);

        return validHour && validMinute && validTimeRange;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return value.equals(otherSchedule.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
