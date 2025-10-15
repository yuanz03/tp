package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.position.Position;
import seedu.address.testutil.PersonBuilder;

public class AssignPositionCommandTest {

    @Test
    public void constructor_nullPlayerName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignPositionCommand(null, "FW"));
    }

    @Test
    public void constructor_nullPositionName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignPositionCommand("Alice", null));
    }

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
    public void execute_positionNotFound_throwsCommandException() {
        Model model = new ModelManager();
        Person alice = new PersonBuilder().withName("Alice").withTeam("A").build();
        model.addPerson(alice);

        // Position doesn't exist
        assertThrows(CommandException.class, String.format(
                AssignPositionCommand.MESSAGE_POSITION_NOT_FOUND, "LW"), () ->
                new AssignPositionCommand("Alice", "LW").execute(model));
    }

    @Test
    public void execute_playerNotFound_throwsCommandException() throws Exception {
        Model model = new ModelManager();
        // Add position but no person
        new NewPositionCommand("LW").execute(model);

        assertThrows(CommandException.class, String.format(
                AssignPositionCommand.MESSAGE_PLAYER_NOT_FOUND, "Alice"), () ->
                new AssignPositionCommand("Alice", "LW").execute(model));
    }

    @Test
    public void execute_missingEntities_fail() {
        Model model = new ModelManager();
        // No person, no position
        assertThrows(CommandException.class, String.format(
                AssignPositionCommand.MESSAGE_POSITION_NOT_FOUND, "LW"), () ->
                new AssignPositionCommand("Alice", "LW").execute(model));
    }

    @Test
    public void execute_invalidPositionName_throwsCommandException() {
        Model model = new ModelManager();
        Person alice = new PersonBuilder().withName("Alice").withTeam("A").build();
        model.addPerson(alice);

        // Invalid position name (contains space)
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new AssignPositionCommand("Alice", "F W").execute(model));
    }

    @Test
    public void execute_caseInsensitivePlayerName_success() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand("FW").execute(model);
        Person alice = new PersonBuilder().withName("Alice").withTeam("A").build();
        model.addPerson(alice);

        // Should work with different case
        CommandResult res = new AssignPositionCommand("alice", "FW").execute(model);
        assertEquals(String.format(AssignPositionCommand.MESSAGE_SUCCESS, "alice", "FW"),
                res.getFeedbackToUser());
    }

    @Test
    public void execute_caseInsensitivePositionName_success() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand("FW").execute(model);
        Person alice = new PersonBuilder().withName("Alice").withTeam("A").build();
        model.addPerson(alice);

        // Should work with different case - message returns actual position name from model
        CommandResult res = new AssignPositionCommand("Alice", "fw").execute(model);
        assertEquals(String.format(AssignPositionCommand.MESSAGE_SUCCESS, "Alice", "FW"),
                res.getFeedbackToUser());
    }

    @Test
    public void execute_whitespaceInNames_trimsCorrectly() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand("FW").execute(model);
        Person alice = new PersonBuilder().withName("Alice").withTeam("A").build();
        model.addPerson(alice);

        // Should trim whitespace
        CommandResult res = new AssignPositionCommand(" Alice ", " FW ").execute(model);
        assertEquals(String.format(AssignPositionCommand.MESSAGE_SUCCESS, "Alice", "FW"),
                res.getFeedbackToUser());
    }

    @Test
    public void equals() {
        AssignPositionCommand assignAliceFW = new AssignPositionCommand("Alice", "FW");
        AssignPositionCommand assignBobFW = new AssignPositionCommand("Bob", "FW");
        AssignPositionCommand assignAliceGK = new AssignPositionCommand("Alice", "GK");

        // same object -> returns true
        assertTrue(assignAliceFW.equals(assignAliceFW));

        // same values -> returns true
        AssignPositionCommand assignAliceFWCopy = new AssignPositionCommand("Alice", "FW");
        assertTrue(assignAliceFW.equals(assignAliceFWCopy));

        // different types -> returns false
        assertFalse(assignAliceFW.equals(1));

        // null -> returns false
        assertFalse(assignAliceFW.equals(null));

        // different player -> returns false
        assertFalse(assignAliceFW.equals(assignBobFW));

        // different position -> returns false
        assertFalse(assignAliceFW.equals(assignAliceGK));
    }
}


