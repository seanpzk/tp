package seedu.edutrack.testutil;

import seedu.edutrack.logic.commands.EditClassCommand.EditClassDescriptor;
import seedu.edutrack.model.common.Memo;
import seedu.edutrack.model.module.Class;
import seedu.edutrack.model.module.ClassName;
import seedu.edutrack.model.module.Schedule;

/**
 * A utility class to help with building EditClassDescriptor objects.
 */
public class EditClassDescriptorBuilder {
    private EditClassDescriptor descriptor;

    public EditClassDescriptorBuilder() {
        descriptor = new EditClassDescriptor();
    }

    public EditClassDescriptorBuilder(EditClassDescriptor descriptor) {
        this.descriptor = new EditClassDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditClassDescriptor} with fields containing
     * {@code class}'s details
     */
    public EditClassDescriptorBuilder(Class c) {
        descriptor = new EditClassDescriptor();
        descriptor.setClassName(c.getClassName());
        descriptor.setClassSchedule(c.getClassSchedule());
        descriptor.setClassMemo(c.getClassMemo());
    }

    /**
     * Sets the {@code ClassName} of the {@code EditClassDescriptor} that we are
     * building.
     */
    public EditClassDescriptorBuilder withClassName(String className) {
        descriptor.setClassName(new ClassName(className));
        return this;
    }

    /**
     * Sets the {@code Memo} of the {@code EditClassDescriptor} that we are
     * building.
     */
    public EditClassDescriptorBuilder withMemo(String m) {
        descriptor.setClassMemo(new Memo(m));
        return this;
    }

    /**
     * Sets the {@code Schedule} of the {@code EditClassDescriptor} that we are
     * building.
     */
    public EditClassDescriptorBuilder withSchedule(String s) {
        descriptor.setClassSchedule(new Schedule(s));
        return this;
    }

    public EditClassDescriptor build() {
        return descriptor;
    }
}
