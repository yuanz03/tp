package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_CAPTAINS;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Filters the list of persons in the PlayBook to show only captains.
 * <p>
 * This command updates the filtered person list in the model using
 * {@code PREDICATE_SHOW_CAPTAINS} and returns a message indicating
 * the successful operation.
 */
public class ListCaptainCommand extends Command {
    public static final String COMMAND_WORD = "listcaptain";
    public static final String MESSAGE_SUCCESS = "Listed all captains";
    private static final Logger logger = LogsCenter.getLogger(ListCaptainCommand.class);

    /**
     * Executes the filter captains command by updating the model's filtered person
     * list
     * to include only captains.
     *
     * @param model the model to operate on; must not be null
     * @return a {@link CommandResult} containing the success message
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.log(Level.INFO, "Executing {0} command", COMMAND_WORD);

        requireNonNull(model);

        // Assert model state
        assert model.getAddressBook() != null : "Model should have address book";

        model.updateFilteredPersonList(PREDICATE_SHOW_CAPTAINS);

        int listSize = model.getFilteredPersonList().size();
        logger.log(Level.INFO, "Found {0} items in list", listSize);

        if (listSize == 0) {
            logger.log(Level.WARNING, "No items found for {0} command", COMMAND_WORD);
            throw new CommandException(Messages.MESSAGE_NO_CAPTAINS);
        }

        logger.log(Level.INFO, "{0} command completed successfully", COMMAND_WORD);
        return CommandResult.showPersonCommandResult(MESSAGE_SUCCESS);
    }
}
