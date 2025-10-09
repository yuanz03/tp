package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests for {@code FilterCaptainCommand}.
 */
public class FilterCaptainCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_filtersToCaptains() {
        assertCommandSuccess(new FilterCaptainCommand(), model,
                FilterCaptainCommand.MESSAGE_SUCCESS, expectedModel);

        long captainsCount = expectedModel.getFilteredPersonList().stream().filter(Person::isCaptain).count();
        long modelCount = model.getFilteredPersonList().stream().filter(Person::isCaptain).count();
        assertEquals(captainsCount, modelCount);
    }
}
