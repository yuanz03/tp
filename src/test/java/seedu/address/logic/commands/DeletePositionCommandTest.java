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

public class DeletePositionCommandTest {

    @Test
    public void constructor_nullPositionName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeletePositionCommand(null));
    }

    @Test
    public void execute_deleteExisting_success() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand("FW").execute(model);

        CommandResult result = new DeletePositionCommand("FW").execute(model);
        assertEquals(String.format(DeletePositionCommand.MESSAGE_SUCCESS, "FW"),
                result.getFeedbackToUser());
        assertFalse(model.hasPosition(new Position("FW")));
    }

    @Test
    public void execute_deleteExistingAndMissing_behaviour() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand("LW").execute(model);
        CommandResult res = new DeletePositionCommand("lw").execute(model);
        assertEquals(String.format(DeletePositionCommand.MESSAGE_SUCCESS, "lw"), res.getFeedbackToUser());
        assertThrows(CommandException.class, DeletePositionCommand.MESSAGE_NOT_FOUND, () ->
                new DeletePositionCommand("lw").execute(model));
    }

    @Test
    public void execute_positionNotFound_throwsCommandException() {
        Model model = new ModelManager();

        assertThrows(CommandException.class, DeletePositionCommand.MESSAGE_NOT_FOUND, () ->
                new DeletePositionCommand("FW").execute(model));
    }

    @Test
    public void execute_caseInsensitiveDelete_success() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand("FW").execute(model);

        // Should work with different case
        CommandResult result = new DeletePositionCommand("fw").execute(model);
        assertEquals(String.format(DeletePositionCommand.MESSAGE_SUCCESS, "fw"),
                result.getFeedbackToUser());
        assertFalse(model.hasPosition(new Position("FW")));
    }

    @Test
    public void execute_whitespaceInName_trimsCorrectly() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand("FW").execute(model);

        CommandResult result = new DeletePositionCommand(" FW ").execute(model);
        assertEquals(String.format(DeletePositionCommand.MESSAGE_SUCCESS, "FW"),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPositionName_throwsCommandException() {
        Model model = new ModelManager();

        // Contains space
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new DeletePositionCommand("F W").execute(model));

        // Empty string
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new DeletePositionCommand("").execute(model));
    }

    @Test
    public void execute_deleteAfterAdd_modelUpdated() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand("FW").execute(model);
        new NewPositionCommand("MF").execute(model);

        assertTrue(model.hasPosition(new Position("FW")));
        assertTrue(model.hasPosition(new Position("MF")));

        new DeletePositionCommand("FW").execute(model);

        assertFalse(model.hasPosition(new Position("FW")));
        assertTrue(model.hasPosition(new Position("MF")));
    }

    @Test
    public void equals() {
        DeletePositionCommand deleteFW = new DeletePositionCommand("FW");
        DeletePositionCommand deleteGK = new DeletePositionCommand("GK");

        // same object -> returns true
        assertTrue(deleteFW.equals(deleteFW));

        // same values -> returns true
        DeletePositionCommand deleteFwCopy = new DeletePositionCommand("FW");
        assertTrue(deleteFW.equals(deleteFwCopy));

        // different types -> returns false
        assertFalse(deleteFW.equals(1));

        // null -> returns false
        assertFalse(deleteFW.equals(null));

        // different position -> returns false
        assertFalse(deleteFW.equals(deleteGK));
    }
}


