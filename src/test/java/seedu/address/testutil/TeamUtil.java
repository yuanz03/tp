package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.logic.commands.AddTeamCommand;
import seedu.address.logic.commands.AssignTeamCommand;
import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.model.person.Name;
import seedu.address.model.team.Team;

/**
 * A utility class for Team.
 */
public class TeamUtil {
    /**
     * Returns an add command string for adding the {@code team}.
     */
    public static String getAddCommand(Team team) {
        return AddTeamCommand.COMMAND_WORD + " " + getTeamDetails(team);
    }

    /**
     * Returns an assign team command string for assigning the {@code team} to a {@code playerName}.
     */
    public static String getAssignTeamCommand(Name playerName, Team team) {
        return AssignTeamCommand.COMMAND_WORD + " " + PREFIX_PLAYER + playerName + " " + PREFIX_TEAM + team.getName();
    }

    /**
     * Returns delete team command string for deleting the {@code team}.
     */
    public static String getDeleteTeamCommand(Team team) {
        return DeleteTeamCommand.COMMAND_WORD + " " + PREFIX_TEAM + team.getName();
    }

    /**
     * Returns the part of command string for the given {@code team}'s details.
     */
    public static String getTeamDetails(Team team) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TEAM + team.getName() + " ");
        return sb.toString();
    }
}
