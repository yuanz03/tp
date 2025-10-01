package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddTeamCommand;
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
     * Returns the part of command string for the given {@code team}'s details.
     */
    public static String getTeamDetails(Team team) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + team.getName().fullName + " ");
        return sb.toString();
    }
}
