package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Marks a person in the PlayBook as no longer a team captain.
 * <p>
 * The person is identified by their {@link Name}. If the person does not exist
 * or is already not a captain, a {@link CommandException} is thrown.
 * <p>
 * Example usage:
 * <pre>
 * {@code unassignCaptain p/Sergio Ramos}
 * </pre>
 */
public class UnassignCaptainCommand extends Command {
    public static final String COMMAND_WORD = "unassigncaptain";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the player "
            + "as no longer captain.\n"
            + "Parameters: " + PREFIX_PLAYER + "PLAYER_NAME "
            + "Example: " + COMMAND_WORD + " " + PREFIX_PLAYER + "Sergio Ramos";

    public static final String MESSAGE_SUCCESS = "%1$s is no longer team captain.";
    public static final String MESSAGE_NOT_IN_TEAM = "%1$s is not assigned to any team.";
    public static final String MESSAGE_NOT_CAPTAIN = "%1$s is already not a team captain.";

    private final Name targetName;

    /**
     * Creates a {@code unassignCaptainCommand} targeting the person with
     * {@code targetName}.
     *
     * @param targetName the name of the person to strip captaincy from; must not be
     *                   null.
     */
    public UnassignCaptainCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person targetPerson;

        try {
            targetPerson = model.getPersonByName(targetName);
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, targetName));
        }

        if (targetPerson.getTeam() == null) {
            throw new CommandException(String.format(MESSAGE_NOT_IN_TEAM, targetPerson.getName()));
        }

        if (!targetPerson.isCaptain()) {
            throw new CommandException(String.format(MESSAGE_NOT_CAPTAIN, targetPerson.getName()));
        }

        model.unassignCaptain(targetPerson);

        return CommandResult.showPersonCommandResult(String.format(MESSAGE_SUCCESS,
                targetPerson.getName()));

    }

    /**
     * Returns true if both commands target the same {@link Name}.
     *
     * @param other the other object to compare with
     * @return whether both commands are equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnassignCaptainCommand)) {
            return false;
        }

        UnassignCaptainCommand otherCommand = (UnassignCaptainCommand) other;
        return targetName.equals(otherCommand.targetName);
    }
}
