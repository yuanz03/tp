package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;

/**
 * Deletes an existing {@code Team}.
 * There should be no players assigned to the team before deletion.
 * <p>
 * Usage: {@code deleteteam tm/<team_name>}
 */
public class DeleteTeamCommand extends Command {

    public static final String COMMAND_WORD = "deleteteam";
    public static final String MESSAGE_SUCCESS = "Team: %s has been deleted successfully!";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team: %s doesn't exist!";
    public static final String MESSAGE_TEAM_NOT_EMPTY = "Team: %s still has players assigned to it!\n"
            + "Please either delete player if no longer managing player\n"
            + "or reassign player to another team before deleting this team.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes team from the PlayBook. "
            + "Parameters: "
            + PREFIX_TEAM + "TEAM_NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TEAM + "u16 ";

    private final Team toDelete;

    /**
     * Creates an DeleteTeamCommand to delete the specified {@code Team}
     */
    public DeleteTeamCommand(Team toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if team exists
        if (!model.hasTeam(toDelete)) {
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, toDelete.getName()));
        }

        // Check if team is empty
        if (!model.isTeamEmpty(toDelete)) {
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_EMPTY, toDelete.getName()));
        }

        model.deleteTeam(toDelete);
        return CommandResult.showTeamCommandResult(String.format(MESSAGE_SUCCESS, toDelete.getName()));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteTeamCommand)) {
            return false;
        }

        DeleteTeamCommand otherCommand = (DeleteTeamCommand) other;
        return toDelete.isSameTeam(otherCommand.toDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDelete", toDelete)
                .toString();
    }
}
