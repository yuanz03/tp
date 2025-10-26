package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for {@code ListCaptainCommand}.
 */
public class ListCaptainCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listCaptains() {
        // Create a captain first
        Person captain = new PersonBuilder().withName("Captain Player").build();
        captain.assignCaptain(); // Make this person a captain
        model.addPerson(captain);

        ListCaptainCommand command = new ListCaptainCommand();
        String expectedMessage = ListCaptainCommand.MESSAGE_SUCCESS;
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_CAPTAINS);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noCaptains_throwsCommandException() {
        // Clear all captains from the model
        model.updateFilteredPersonList(person -> !person.isCaptain());

        ListCaptainCommand command = new ListCaptainCommand();
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals("There are currently no captains in the PlayBook.", exception.getMessage());
    }
}
