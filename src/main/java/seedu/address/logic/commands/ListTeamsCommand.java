package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ListTeamsCommand extends Command {
    public static final String COMMAND_WORD = "listteams";

    public static final String MESSAGE_SUCCESS = "Listed all teams";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false);
    }
}
