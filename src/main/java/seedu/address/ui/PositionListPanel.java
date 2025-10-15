package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.position.Position;

/**
 * Panel containing the list of positions.
 */
public class PositionListPanel extends UiPart<Region> {
    private static final String FXML = "PositionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PositionListPanel.class);

    @FXML
    private ListView<Position> positionListView;

    /**
     * Creates a {@code PositionListPanel} with the given {@code ObservableList}.
     */
    public PositionListPanel(ObservableList<Position> positionList) {
        super(FXML);
        positionListView.setItems(positionList);
        positionListView.setCellFactory(listView -> new PositionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Position} using a {@code PositionCard}.
     */
    class PositionListViewCell extends ListCell<Position> {
        @Override
        protected void updateItem(Position position, boolean empty) {
            super.updateItem(position, empty);

            if (empty || position == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PositionCard(position, getIndex() + 1).getRoot());
            }
        }
    }
}
