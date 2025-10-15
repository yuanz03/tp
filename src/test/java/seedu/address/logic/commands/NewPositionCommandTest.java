package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.position.Position;

public class NewPositionCommandTest {

    @Test
    public void constructor_nullPositionName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NewPositionCommand(null));
    }

    @Test
    public void execute_newPosition_success() throws Exception {
        Model model = new ModelManager();
        NewPositionCommand command = new NewPositionCommand("FW");
        CommandResult result = command.execute(model);
        
        assertEquals(String.format(NewPositionCommand.MESSAGE_SUCCESS, "FW"), 
                result.getFeedbackToUser());
        assertTrue(model.hasPosition(new Position("FW")));
    }

    @Test
    public void execute_addAndDuplicate_behaviour() throws Exception {
        Model model = new ModelManager();
        NewPositionCommand add = new NewPositionCommand("LW");
        CommandResult res = add.execute(model);
        assertEquals(String.format(NewPositionCommand.MESSAGE_SUCCESS, "LW"), res.getFeedbackToUser());
        
        // duplicate
        assertThrows(CommandException.class, () -> new NewPositionCommand("lw").execute(model));
    }

    @Test
    public void execute_duplicatePosition_throwsCommandException() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand("FW").execute(model);
        
        // Exact duplicate
        assertThrows(CommandException.class, String.format(
                NewPositionCommand.MESSAGE_DUPLICATE, "FW"), () ->
                new NewPositionCommand("FW").execute(model));
    }

    @Test
    public void execute_duplicatePositionDifferentCase_throwsCommandException() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand("FW").execute(model);
        
        // Different case should still be duplicate
        assertThrows(CommandException.class, () -> new NewPositionCommand("fw").execute(model));
        assertThrows(CommandException.class, () -> new NewPositionCommand("Fw").execute(model));
    }

    @Test
    public void execute_invalidPositionName_throwsCommandException() {
        Model model = new ModelManager();
        
        // Empty string
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new NewPositionCommand("").execute(model));
        
        // Contains space
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new NewPositionCommand("F W").execute(model));
        
        // Special characters
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new NewPositionCommand("FW@").execute(model));
    }

    @Test
    public void execute_whitespaceInName_trimsCorrectly() throws Exception {
        Model model = new ModelManager();
        NewPositionCommand command = new NewPositionCommand(" FW ");
        CommandResult result = command.execute(model);
        
        assertEquals(String.format(NewPositionCommand.MESSAGE_SUCCESS, "FW"), 
                result.getFeedbackToUser());
    }

    @Test
    public void execute_multiplePositions_allAdded() throws Exception {
        Model model = new ModelManager();
        
        new NewPositionCommand("FW").execute(model);
        new NewPositionCommand("MF").execute(model);
        new NewPositionCommand("GK").execute(model);
        
        assertTrue(model.hasPosition(new Position("FW")));
        assertTrue(model.hasPosition(new Position("MF")));
        assertTrue(model.hasPosition(new Position("GK")));
    }

    @Test
    public void equals() {
        NewPositionCommand addFW = new NewPositionCommand("FW");
        NewPositionCommand addGK = new NewPositionCommand("GK");

        // same object -> returns true
        assertTrue(addFW.equals(addFW));

        // same values -> returns true
        NewPositionCommand addFWCopy = new NewPositionCommand("FW");
        assertTrue(addFW.equals(addFWCopy));

        // different types -> returns false
        assertFalse(addFW.equals(1));

        // null -> returns false
        assertFalse(addFW.equals(null));

        // different position -> returns false
        assertFalse(addFW.equals(addGK));
    }
}


