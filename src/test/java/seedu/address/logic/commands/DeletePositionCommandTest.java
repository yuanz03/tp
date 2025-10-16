package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class DeletePositionCommandTest {
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
    public void execute_deleteAssignedPosition_throwsCommandException() throws Exception {
        Model model = new ModelManager();
        // Add a position
        new NewPositionCommand("Forward").execute(model);
        // Add a player
        model.addPerson(ALICE);
        // Assign the position to the player
        new AssignPositionCommand(ALICE.getName().fullName, "Forward").execute(model);
        // Attempt to delete the position should fail
        assertThrows(CommandException.class,
                String.format(DeletePositionCommand.MESSAGE_POSITION_ASSIGNED, "Forward"), () ->
                new DeletePositionCommand("Forward").execute(model));
    }
}


