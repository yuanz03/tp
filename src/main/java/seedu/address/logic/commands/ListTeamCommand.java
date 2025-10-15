package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all teams in the Play Book to the user.
 */
public class ListTeamCommand extends Command {
    public static final String COMMAND_WORD = "listteam";

    public static final String MESSAGE_SUCCESS = "Listed all teams";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);

        if (model.getFilteredTeamList().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_TEAMS);
        }

        return CommandResult.showTeamCommandResult(MESSAGE_SUCCESS);
    }
}
