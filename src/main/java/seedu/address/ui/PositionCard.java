package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.position.Position;

/**
 * An UI component that displays information of a {@code Position}.
 */
public class PositionCard extends UiPart<Region> {

    private static final String FXML = "PositionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Position position;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    /**
     * Creates a {@code PositionCard} with the given {@code Position} and index to display.
     */
    public PositionCard(Position position, int displayedIndex) {
        super(FXML);
        this.position = position;
        id.setText(displayedIndex + ". ");
        name.setText(position.getName());
    }
}
