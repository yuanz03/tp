package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.position.Position;
import seedu.address.model.position.exceptions.PositionNotFoundException;
import seedu.address.model.team.Team;

/**
 * Deletes a specified person, team or position from PlayBook
 * Only allows deleting one at a time
 * Usage: delete [pl/PLAYER_NAME] [tm/TEAM_NAME] [ps/POSITION_NAME]
 * Where exactly one prefix must be present
 */
public class DeleteCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

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
    public static final String MESSAGE_DELETE_POSITION_SUCCESS = "Position: %s has been deleted successfully!";

    public static final String MESSAGE_TEAM_NOT_FOUND = "Team: %s doesn't exist!";
    public static final String MESSAGE_TEAM_NOT_EMPTY = "Team: %s still has players assigned to it!\n"
            + "Please either delete the player if no longer managing them, "
            + "or reassign the player to another team before deleting this team.";
    public static final String MESSAGE_POSITION_NOT_FOUND = "Position: %s doesn't exist!";
    public static final String MESSAGE_POSITION_ASSIGNED = "Cannot delete position %s as it is currently assigned to"
            + " one or more players.";

    private final Name personNameToDelete;
    private final Team teamToDelete;
    private final Position positionToDelete;

    private DeleteCommand(Name personNameToDelete, Team teamToDelete, Position positionToDelete) {
        this.personNameToDelete = personNameToDelete;
        this.teamToDelete = teamToDelete;
        this.positionToDelete = positionToDelete;
    }

    /**
     * Creates a DeleteCommand to delete the specified {@code Team} by name.
     */
    public static DeleteCommand createDeleteTeamCommand(Team teamToDelete) {
        requireNonNull(teamToDelete);
        return new DeleteCommand(null, teamToDelete, null);
    }

    /**
     * Creates a DeleteCommand to delete the specified {@code Person} by name.
     */
    public static DeleteCommand createDeletePlayerCommand(Name playerNameToDelete) {
        requireNonNull(playerNameToDelete);
        return new DeleteCommand(playerNameToDelete, null, null);
    }

    /**
     * Creates a DeleteCommand to delete the specified {@code Position} by name.
     */
    public static DeleteCommand createDeletePositionCommand(Position positionToDelete) {
        requireNonNull(positionToDelete);
        return new DeleteCommand(null, null, positionToDelete);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing DeleteCommand");

        if (isDeletePlayerCommand()) {
            return executeDeletePlayer(model);
        } else if (isDeleteTeamCommand()) {
            return executeDeleteTeam(model);
        } else if (isDeletePositionCommand()) {
            return executeDeletePosition(model);
        } else {
            throw new CommandException(MESSAGE_USAGE);
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
     * Checks if the command is to delete a position.
     */
    private boolean isDeletePositionCommand() {
        return positionToDelete != null;
    }

    /**
     * Executes the delete team command.
     * Team must exist and be empty before deletion.
     */
    private CommandResult executeDeleteTeam(Model model) throws CommandException {
        if (!model.hasTeam(teamToDelete)) {
            logger.warning("Team not found: " + teamToDelete.getName());
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, teamToDelete.getName()));
        }

        if (!model.isTeamEmpty(teamToDelete)) {
            logger.warning("Cannot delete team " + teamToDelete.getName() + " - team is not empty");
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_EMPTY, teamToDelete.getName()));
        }

        model.deleteTeam(teamToDelete);
        logger.info("Deleted team: " + teamToDelete.getName());
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
            logger.warning("Person not found: " + personNameToDelete);
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, personNameToDelete.toString()));
        }

        model.deletePerson(personToDelete);
        logger.info("Deleted person: " + personToDelete.getName());

        return CommandResult.showPersonCommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete)));
    }

    //@@author gabrieltang515
    /**
     * Executes the delete position command.
     */
    private CommandResult executeDeletePosition(Model model) throws CommandException {

        String name = positionToDelete.getName();
        logger.info("Attempting to delete position: " + name);
        final Position toDelete;
        try {
            toDelete = model.getPositionByName(name);
        } catch (PositionNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_POSITION_NOT_FOUND, name));
        }

        if (model.isPositionAssigned(toDelete)) {
            logger.warning("Cannot delete position " + name + " - still assigned to players");
            throw new CommandException(String.format(MESSAGE_POSITION_ASSIGNED, name));
        }

        model.deletePosition(toDelete);
        logger.info("Deleted position: " + name);

        return new CommandResult(String.format(MESSAGE_DELETE_POSITION_SUCCESS,
                name));
    }

    //@@author
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
        } else if (this.positionToDelete != null && otherDeleteCommand.positionToDelete != null) {
            return positionToDelete.equals(otherDeleteCommand.positionToDelete);
        }
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personToDelete", personNameToDelete)
                .add("teamToDelete", teamToDelete)
                .add("positionToDelete", positionToDelete)
                .toString();
    }
}
