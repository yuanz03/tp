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
    private FlowPane injuryStatus;
    @FXML
    private FlowPane teamPositionContainer;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        // Show captain badge directly to the right of the name
        boolean isCaptain = person.isCaptain();
        captainBadge.setVisible(isCaptain);
        captainBadge.setManaged(isCaptain);
        Label teamTag = new Label(person.getTeam().getName());
        teamTag.getStyleClass().add("team-tag");
        teamPositionContainer.getChildren().add(teamTag);
        Label positionTag = new Label(person.getPosition().getName());
        positionTag.getStyleClass().add("team-tag");
        teamPositionContainer.getChildren().add(positionTag);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        Label injuryLabel;
        if (person.getInjury().getInjuryName().equals(Person.DEFAULT_INJURY_STATUS)) {
            injuryLabel = new Label("\uD83E\uDDBE  " + person.getInjury().getInjuryName());
            injuryLabel.getStyleClass().add("fit-tag");
        } else {
            injuryLabel = new Label("\uD83D\uDE91  " + person.getInjury().getInjuryName());
            injuryLabel.getStyleClass().add("injured-tag");
        }
        injuryStatus.getChildren().add(injuryLabel);
    }
}
