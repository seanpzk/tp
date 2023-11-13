package seedu.edutrack.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.edutrack.commons.core.LogsCenter;
import seedu.edutrack.model.module.Class;


/**
 * Panel containing the list of classes.
 */

public class ClassListPanel extends UiPart<Region> {
    private static final String FXML = "ClassListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ClassListPanel.class);

    @FXML
    private ListView<Class> classListView;

    /**
     * Creates a {@code ClassListPanel} with the given {@code ObservableList}.
     */
    public ClassListPanel(ObservableList<Class> classList) {
        super(FXML);
        classListView.setItems(classList);
        classListView.setCellFactory(listView -> new ClassListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Class} using a {@code ClassCard}.
     */
    class ClassListViewCell extends ListCell<Class> {
        @Override
        protected void updateItem(Class c, boolean empty) {
            super.updateItem(c, empty);

            if (empty || c == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ClassCard(c, getIndex() + 1).getRoot());
            }
        }
    }
}
