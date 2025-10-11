package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.FilterByTeamPredicate;
import seedu.address.model.team.Team;

/**
 * Filters and lists all persons in address book whose team matches the argument team name.
 * Team name matching is case insensitive.
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " tm/TEAM_NAME : Filters players by the given team.";

    private final FilterByTeamPredicate predicate;

    public FilterCommand(FilterByTeamPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String teamName = predicate.getTeamName();

        Team dummy = new Team(teamName);
        if (!model.hasTeam(dummy)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEAM);
        }

        model.updateFilteredPersonList(predicate);

        int resultSize = model.getFilteredPersonList().size();
        if (resultSize == 0) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_NO_PLAYERS_IN_TEAM, teamName));
        }
        return new CommandResult(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
