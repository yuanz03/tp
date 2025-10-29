package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all persons in the PlayBook to the user.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all players";

    private static final Logger logger = LogsCenter.getLogger(ListCommand.class);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.log(Level.INFO, "Executing {0} command", COMMAND_WORD);

        requireNonNull(model);

        // Assert model state
        assert model.getAddressBook() != null : "Model should have address book";

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        int listSize = model.getFilteredPersonList().size();
        logger.log(Level.INFO, "Found {0} items in list", listSize);

        if (listSize == 0) {
            logger.log(Level.WARNING, "No items found for {0} command", COMMAND_WORD);
            throw new CommandException(Messages.MESSAGE_NO_PLAYERS);
        }

        logger.log(Level.INFO, "{0} command completed successfully", COMMAND_WORD);
        return CommandResult.showPersonCommandResult(MESSAGE_SUCCESS);
    }
}
