package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class NewPositionCommandTest {
    @Test
    public void execute_addAndDuplicate_behaviour() throws Exception {
        Model model = new ModelManager();
        NewPositionCommand add = new NewPositionCommand("LW");
        CommandResult res = add.execute(model);
        assertEquals(String.format(NewPositionCommand.MESSAGE_SUCCESS, "LW"), res.getFeedbackToUser());
        // duplicate
        assertThrows(CommandException.class, () -> new NewPositionCommand("lw").execute(model));
    }
}


