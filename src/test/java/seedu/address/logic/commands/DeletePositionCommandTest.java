package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

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
        assertThrows(CommandException.class, DeletePositionCommand.MESSAGE_NOT_FOUND,
                () -> new DeletePositionCommand("lw").execute(model));
    }
}


