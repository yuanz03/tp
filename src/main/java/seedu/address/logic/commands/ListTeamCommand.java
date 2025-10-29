package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all teams in the PlayBook to the user.
 */
public class ListTeamCommand extends Command {
    public static final String COMMAND_WORD = "listteam";

    public static final String MESSAGE_SUCCESS = "Listed all teams";

    private static final Logger logger = LogsCenter.getLogger(ListTeamCommand.class.getName());

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.log(Level.INFO, "Executing {0} command", COMMAND_WORD);

        requireNonNull(model);

        // Assert model state
        assert model.getAddressBook() != null : "Model should have address book";

        model.updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);

        int listSize = model.getFilteredTeamList().size();
        logger.log(Level.INFO, "Found {0} items in list", listSize);

        if (listSize == 0) {
            logger.log(Level.WARNING, "No items found for {0} command", COMMAND_WORD);
            throw new CommandException(Messages.MESSAGE_NO_TEAMS);
        }

        logger.log(Level.INFO, "{0} command completed successfully", COMMAND_WORD);
        return CommandResult.showTeamCommandResult(MESSAGE_SUCCESS);
    }
}
