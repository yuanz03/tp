package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.team.Team;

/**
 * Deletes a specified person, team or position from PlayBook
 * Only allows deleting one at a time
 * Usage: delete [pl/PLAYER_NAME] [tm/TEAM_NAME] [ps/POSITION_NAME]
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the player, team or position from PlayBook.\n"
            + "Parameters: "
            + "[" + PREFIX_PLAYER + "PLAYER_NAME] "
            + "[" + PREFIX_TEAM + "TEAM_NAME] "
            + "[" + PREFIX_POSITION + "POSITION_NAME]\n"
            + "Note: Must provide exactly one parameter above.\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PLAYER + "John Doe ";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Player: %1$s has been deleted successfully!";
    public static final String MESSAGE_DELETE_TEAM_SUCCESS = "Team: %s has been deleted successfully!";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team: %s doesn't exist!";
    public static final String MESSAGE_TEAM_NOT_EMPTY = "Team: %s still has players assigned to it!\n"
            + "Please either delete player if no longer managing player\n"
            + "or reassign player to another team before deleting this team.";


    private final Name personNameToDelete;
    private final Team teamToDelete;

    private DeleteCommand(Name personNameToDelete, Team teamToDelete) {
        this.personNameToDelete = personNameToDelete;
        this.teamToDelete = teamToDelete;
    }

    /**
     * Creates a DeleteCommand to delete the specified {@code Team} by name.
     */
    public static DeleteCommand createDeleteTeamCommand(Team teamToDelete) {
        requireNonNull(teamToDelete);
        return new DeleteCommand(null, teamToDelete);
    }

    /**
     * Creates a DeleteCommand to delete the specified {@code Person} by name.
     */
    public static DeleteCommand createDeletePlayerCommand(Name playerNameToDelete) {
        requireNonNull(playerNameToDelete);
        return new DeleteCommand(playerNameToDelete, null);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isDeletePlayerCommand()) {
            return executeDeletePlayer(model);
        } else if (isDeleteTeamCommand()) {
            return executeDeleteTeam(model);
        } else {
            throw new CommandException("No valid delete command found.");
        }
    }

    /**
     * Checks if the command is to delete a team.
     */
    private boolean isDeleteTeamCommand() {
        return teamToDelete != null;
    }

    /**
     * Checks if the command is to delete a player.
     */
    private boolean isDeletePlayerCommand() {
        return personNameToDelete != null;
    }

    /**
     * Executes the delete team command.
     */
    private CommandResult executeDeleteTeam(Model model) throws CommandException {
        // Check if team exists
        if (!model.hasTeam(teamToDelete)) {
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, teamToDelete.getName()));
        }

        // Check if team is empty
        if (!model.isTeamEmpty(teamToDelete)) {
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_EMPTY, teamToDelete.getName()));
        }

        model.deleteTeam(teamToDelete);
        return CommandResult.showTeamCommandResult(String.format(MESSAGE_DELETE_TEAM_SUCCESS, teamToDelete.getName()));
    }

    /**
     * Executes the delete player command.
     */
    private CommandResult executeDeletePlayer(Model model) throws CommandException {
        Person personToDelete;

        try {
            personToDelete = model.getPersonByName(personNameToDelete);
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, personNameToDelete.toString()));
        }

        model.deletePerson(personToDelete);

        return CommandResult.showPersonCommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        if (this.teamToDelete != null && otherDeleteCommand.teamToDelete != null) {
            return teamToDelete.equals(otherDeleteCommand.teamToDelete);
        } else if (this.personNameToDelete != null && otherDeleteCommand.personNameToDelete != null) {
            return personNameToDelete.equals(otherDeleteCommand.personNameToDelete);
        }
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personToDelete", personNameToDelete)
                .add("teamToDelete", teamToDelete)
                .toString();
    }
}
