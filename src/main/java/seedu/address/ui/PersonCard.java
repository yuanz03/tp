package seedu.address.ui;

import java.util.Comparator;
import javax.annotation.processing.Generated;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
@Generated(value = "javafx", comments = "GUI component excluded from test coverage")
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label captainBadge;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label teamLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label injuryLabel;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;

        // Header: Index and Name
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        // Captain Badge (only show if captain)
        boolean isCaptain = person.isCaptain();
        captainBadge.setVisible(isCaptain);
        captainBadge.setManaged(isCaptain);

        // Team and Position Labels
        teamLabel.setText("📋 " + person.getTeam().getName());
        teamLabel.getStyleClass().add("team-info-label");

        positionLabel.setText("⚽ " + person.getPosition().getName());
        positionLabel.getStyleClass().add("position-info-label");

        // Injury Status
        if (person.getInjury().equals(Person.DEFAULT_INJURY_STATUS)) {
            injuryLabel.setText("🏥 " + person.getInjury().getInjuryName());
            injuryLabel.getStyleClass().add("fit-status");
        } else {
            injuryLabel.setText("🚑 " + person.getInjury().getInjuryName());
            injuryLabel.getStyleClass().add("injured-status");
        }

        // Contact Information (icons are in FXML)
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        address.setText(person.getAddress().value);

        // Tags
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add("tag-label");
                    tags.getChildren().add(tagLabel);
                });
    }
}
