package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Removes an injury status from an existing {@code Person} in the PlayBook.
 * Resets the {@code Person}'s injury status to the default {@code "FIT"} status
 * if the {@code Person} has no more injuries.
 * <p>
 * Usage: {@code unassigninjury pl/<player> i/<injury>}.
 */
public class UnassignInjuryCommand extends Command {

    public static final String COMMAND_WORD = "unassigninjury";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes an injury status from a player in the PlayBook.\n"
            + "Parameters: " + PREFIX_PLAYER + "PLAYER_NAME " + PREFIX_INJURY + "INJURY\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PLAYER + "John Doe " + PREFIX_INJURY + "ACL";

    public static final Logger logger = LogsCenter.getLogger(UnassignInjuryCommand.class);

    private final Name personNameToUnassign;
    private final Injury injuryToUnassign;

    /**
     * Creates an {@code UnassignInjuryCommand} that removes the specified {@code injury}
     * from the {@code Person} identified by {@code personName}.
     */
    public UnassignInjuryCommand(Name personName, Injury injury) {
        requireAllNonNull(personName, injury);
        this.personNameToUnassign = personName;
        this.injuryToUnassign = injury;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing UnassignInjuryCommand: " + injuryToUnassign + " from " + personNameToUnassign);

        Person personToUnassign = findPersonByName(model, personNameToUnassign);

        // Ensure the player is currently injured before attempting to unassign
        validatePlayerIsInjured(model, personToUnassign);

        // Check if the player has the specified injury before updating the injury status
        validatePlayerHasInjury(model, personToUnassign, injuryToUnassign);

        Person updatedPerson = model.deleteInjury(personToUnassign, injuryToUnassign);
        logger.info("Successfully unassigned injury " + injuryToUnassign + " from " + personToUnassign.getName()
                + ". Current injuries: " + updatedPerson.getInjuries());

        return CommandResult.showPersonCommandResult(String.format(Messages.MESSAGE_UNASSIGN_INJURY_SUCCESS,
                updatedPerson.getName(), injuryToUnassign.getInjuryName()));
    }

    private Person findPersonByName(Model model, Name name) throws CommandException {
        try {
            return model.getPersonByName(name);
        } catch (PersonNotFoundException e) {
            logger.warning("Player not found: " + name);
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, name));
        }
    }

    private void validatePlayerIsInjured(Model model, Person person) throws CommandException {
        if (!model.hasNonDefaultInjury(person)) {
            logger.warning("Player " + person.getName() + " is not injured");
            throw new CommandException(String.format(Messages.MESSAGE_INJURY_ALREADY_UNASSIGNED,
                    person.getName()));
        }
    }

    private void validatePlayerHasInjury(Model model, Person person, Injury injury) throws CommandException {
        if (!model.hasSpecificInjury(person, injury)) {
            logger.warning("Player " + person.getName() + " has no record of this injury " + injury.getInjuryName());
            throw new CommandException(String.format(Messages.MESSAGE_INJURY_NOT_FOUND,
                    person.getName(), injury.getInjuryName()));
        }
    }

    /**
     * Returns true if this {@code UnassignInjuryCommand} is equal to another object.
     * Two {@code UnassignInjuryCommand} are considered equal if they have the same
     * {@code personNameToUnassign} and {@code injuryToUnassign}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignInjuryCommand)) {
            return false;
        }

        UnassignInjuryCommand otherUnassignInjuryCommand = (UnassignInjuryCommand) other;
        return personNameToUnassign.equals(otherUnassignInjuryCommand.personNameToUnassign)
                && injuryToUnassign.equals(otherUnassignInjuryCommand.injuryToUnassign);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personNameToUnassign", personNameToUnassign)
                .add("injuryToUnassign", injuryToUnassign)
                .toString();
    }
}
