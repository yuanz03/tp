package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_NO_POSITIONS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.position.Position;

public class ListPositionCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_emptyPlayBook_showsEmptyPositionList() {
        // Create a model with an empty PlayBook
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmptyModel = new ModelManager(new AddressBook(), new UserPrefs());

        assertCommandFailure(new ListPositionCommand(), emptyModel, MESSAGE_NO_POSITIONS);
    }

    @Test
    public void execute_singlePosition_showsSinglePosition() {
        Model singlePositionModel = new ModelManager(new AddressBook(), new UserPrefs());
        singlePositionModel.addPosition(new Position(VALID_POSITION_BOB));
        Model expectedSinglePositionModel = new ModelManager(new AddressBook(), new UserPrefs());
        expectedSinglePositionModel.addPosition(new Position(VALID_POSITION_BOB));

        assertCommandSuccess(new ListPositionCommand(), singlePositionModel,
                ListPositionCommand.MESSAGE_SUCCESS, expectedSinglePositionModel);
    }
}
