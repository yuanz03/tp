package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;

/**
 * Adds a team to the address book.
 */
public class AddTeamCommand extends Command {

    public static final String COMMAND_WORD = "addteam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a team to the address book. "
        + "Parameters: "
        + PREFIX_TEAM + "TEAM_NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TEAM + "u16 ";

    public static final String MESSAGE_SUCCESS = "New team added: %1$s";
    public static final String MESSAGE_DUPLICATE_TEAM = "This team already exists in the address book";

    private final Team toAdd;

    /**
     * Creates an AddTeamCommand to add the specified {@code Team}
     */
    public AddTeamCommand(Team team) {
        requireNonNull(team);
        this.toAdd = team;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTeam(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TEAM);
        }

        model.addTeam(toAdd);
        return CommandResult.showTeamCommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTeamCommand)) {
            return false;
        }

        AddTeamCommand e = (AddTeamCommand) other;
        return toAdd.equals(e.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("toAdd", toAdd)
            .toString();
    }
}
