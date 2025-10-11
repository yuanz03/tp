package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import seedu.address.model.Model;

/**
 * Lists all teams in the Play Book to the user.
 */
public class ListTeamsCommand extends Command {
    public static final String COMMAND_WORD = "listteams";

    public static final String MESSAGE_SUCCESS = "Listed all teams";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
        return CommandResult.showTeamCommandResult(MESSAGE_SUCCESS);
    }
}
