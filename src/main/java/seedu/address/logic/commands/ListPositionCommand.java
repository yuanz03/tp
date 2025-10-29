package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POSITIONS;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all positions in the PlayBook to the user.
 */
public class ListPositionCommand extends Command {
    public static final String COMMAND_WORD = "listposition";

    public static final String MESSAGE_SUCCESS = "Listed all positions";

    private static final Logger logger = LogsCenter.getLogger(ListPositionCommand.class);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.log(Level.INFO, "Executing {0} command", COMMAND_WORD);

        requireNonNull(model);

        // Assert model state
        assert model.getAddressBook() != null : "Model should have address book";

        model.updateFilteredPositionList(PREDICATE_SHOW_ALL_POSITIONS);

        int listSize = model.getFilteredPositionList().size();
        logger.log(Level.INFO, "Found {0} items in list", listSize);

        if (listSize == 0) {
            logger.log(Level.WARNING, "No items found for {0} command", COMMAND_WORD);
            throw new CommandException(Messages.MESSAGE_NO_POSITIONS);
        }

        logger.log(Level.INFO, "{0} command completed successfully", COMMAND_WORD);
        return CommandResult.showPositionCommandResult(MESSAGE_SUCCESS);
    }
}
