package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.team.Team;

/**
 * Assigns an existing {@code Team} to an existing {@code Person}.
 * <p>
 * Usage: {@code assignteam pl/<player> tm/<team>}
 */
public class AssignTeamCommand extends Command {

    public static final String COMMAND_WORD = "assignteam";
    public static final String MESSAGE_SUCCESS = "Player: %s has been successfully assigned to Team: %s!";
    public static final String MESSAGE_PLAYER_NOT_FOUND = "Player: %s doesn't exist!";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team: %s doesn't exist!";
    public static final String MESSAGE_ALREADY_ASSIGNED = "Player: %s is already assigned to Team: %s!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a team to a player in the PlayBook. "
            + "Parameters: "
            + PREFIX_PLAYER + "PLAYER_NAME "
            + PREFIX_TEAM + "TEAM_NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PLAYER + "John Doe "
            + PREFIX_TEAM + "u16 ";

    private final Team team;
    private final Name playerName;

    /**
     * Creates an AssignTeamCommand to assign the specified {@code Person} to the specified {@code Team}
     */
    public AssignTeamCommand(Name playerName, Team team) {
        requireAllNonNull(playerName, team);
        this.team = team;
        this.playerName = playerName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if player exists
        Person player;
        try {
            player = model.getPersonByName(playerName);
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_PLAYER_NOT_FOUND, playerName));
        }

        // Check if team exists
        if (!model.hasTeam(team)) {
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, team.getName()));
        }

        // Check if player is already assigned to the team
        if (player.getTeam() != null && player.getTeam().isSameTeam(team)) {
            throw new CommandException(String.format(MESSAGE_ALREADY_ASSIGNED, playerName, team.getName()));
        }

        model.assignTeam(player, team);
        return CommandResult.showPersonCommandResult(
                String.format(MESSAGE_SUCCESS, player.getName(), team.getName()));
    }

    /**
     * Returns true if this AssignTeamCommand is equal to another object.
     * Two AssignTeamCommand objects are considered
     * equal if they have the same team and playerName
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AssignTeamCommand)) {
            return false;
        }

        AssignTeamCommand otherCommand = (AssignTeamCommand) other;
        return team.equals(otherCommand.team) && playerName.equals(otherCommand.playerName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("team", team)
                .add("player", playerName)
                .toString();
    }
}
