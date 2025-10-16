package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POSITIONS;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all positions in the Play Book to the user.
 */
public class ListPositionCommand extends Command {
    public static final String COMMAND_WORD = "listposition";

    public static final String MESSAGE_SUCCESS = "Listed all positions";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPositionList(PREDICATE_SHOW_ALL_POSITIONS);

        if (model.getFilteredPositionList().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_POSITIONS);
        }

        return CommandResult.showPositionCommandResult(MESSAGE_SUCCESS);
    }
}
