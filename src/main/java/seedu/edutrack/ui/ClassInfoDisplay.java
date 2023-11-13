package seedu.edutrack.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the display of class information.
 */
public class ClassInfoDisplay extends UiPart<Region> {
    private static final String FXML = "ClassInfoDisplay.fxml";

    @FXML
    private TextArea classInfoDisplay;

    public ClassInfoDisplay() {
        super(FXML);
    }

    public void setClassInfo(String classInfo) {
        requireNonNull(classInfo);
        classInfoDisplay.setText(classInfo);
    }

    public String getClassName() {
        // Class info =
        // Current class: CS2103
        // Schedule:
        // Class notes:
        if (classInfoDisplay.getText().isEmpty()) {
            return "";
        }
        String[] classInfo = classInfoDisplay.getText().split("\n");
        String className = classInfo[0].split(": ")[1];
        return className;
    }
}
