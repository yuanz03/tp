package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AssignPositionCommandTest {
    @Test
    public void execute_assign_successAndDuplicate() throws Exception {
        Model model = new ModelManager();
        // Ensure position exists
        new NewPositionCommand("LW").execute(model);
        // Build and add a person named Alice
        Person alice = new PersonBuilder().withName("Alice").withTeam("A").build();
        model.addPerson(alice);

        CommandResult res = new AssignPositionCommand("Alice", "LW").execute(model);
        assertEquals(String.format(AssignPositionCommand.MESSAGE_SUCCESS, "Alice", "LW"),
                res.getFeedbackToUser());

        // Duplicate assignment
        assertThrows(CommandException.class, String.format(
                AssignPositionCommand.MESSAGE_DUPLICATE_ASSIGN, "Alice", "LW"), () ->
                new AssignPositionCommand("Alice", "lw").execute(model));
    }

    @Test
    public void execute_missingEntities_fail() {
        Model model = new ModelManager();
        // No person, no position
        assertThrows(CommandException.class, String.format(
                AssignPositionCommand.MESSAGE_POSITION_NOT_FOUND, "LW"), () ->
                new AssignPositionCommand("Alice", "LW").execute(model));
    }
}


