package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_NO_TEAMS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTeams.U12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ListTeamCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameTeamList() {
        assertCommandSuccess(new ListTeamCommand(), model,
                ListTeamCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        // filter to only U12
        model.updateFilteredTeamList(team -> team.getName().equals(U12.getName()));
        assertCommandSuccess(new ListTeamCommand(), model,
                ListTeamCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyPlayBook_showsEmptyTeamList() {
        // Create a model with an empty PlayBook
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmptyModel = new ModelManager(new AddressBook(), new UserPrefs());

        assertCommandFailure(new ListTeamCommand(), emptyModel, MESSAGE_NO_TEAMS);
    }
}
