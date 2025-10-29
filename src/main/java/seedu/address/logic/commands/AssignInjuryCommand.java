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
 * Assigns an injury status to an existing {@code Person} in the PlayBook.
 * <p>
 * Usage: {@code assigninjury pl/<player> i/<injury>}.
 */
public class AssignInjuryCommand extends Command {

    public static final String COMMAND_WORD = "assigninjury";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns an injury status to a player in the PlayBook.\n"
            + "Parameters: " + PREFIX_PLAYER + "PLAYER_NAME " + PREFIX_INJURY + "INJURY\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PLAYER + "John Doe " + PREFIX_INJURY + "ACL";

    private static final Logger logger = LogsCenter.getLogger(AssignInjuryCommand.class);

    private final Name personNameToAssign;
    private final Injury injuryToAssign;

    /**
     * Creates an {@code AssignInjuryCommand} that assigns the specified {@code injury}
     * to the {@code Person} identified by {@code personName}.
     */
    public AssignInjuryCommand(Name personName, Injury injury) {
        requireAllNonNull(personName, injury);
        this.personNameToAssign = personName;
        this.injuryToAssign = injury;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing AssignInjuryCommand: " + injuryToAssign.getInjuryName() + " to "
                + personNameToAssign);

        Person personToAssign = findPersonByName(model, personNameToAssign);

        validateNotDefaultInjury(injuryToAssign);
        validateNoDuplicateInjury(personToAssign, injuryToAssign);

        Person updatedPerson = model.addInjury(personToAssign, injuryToAssign);
        logger.info("Successfully assigned injury " + injuryToAssign.getInjuryName() + " to "
                + updatedPerson.getName() + ". Current injuries: " + updatedPerson.getInjuries().toString());

        return CommandResult.showPersonCommandResult(String.format(Messages.MESSAGE_ASSIGN_INJURY_SUCCESS,
                updatedPerson.getName(), updatedPerson.getInjuries()));
    }

    private Person findPersonByName(Model model, Name name) throws CommandException {
        try {
            return model.getPersonByName(name);
        } catch (PersonNotFoundException e) {
            logger.warning("Player not found: " + name);
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, name));
        }
    }

    private void validateNotDefaultInjury(Injury injury) throws CommandException {
        if (injury.equals(Injury.DEFAULT_INJURY_STATUS)) {
            logger.warning("Assignment of the default status " + injury.getInjuryName() + " is not allowed");
            throw new CommandException(Messages.MESSAGE_INVALID_INJURY_ASSIGNMENT);
        }
    }

    private void validateNoDuplicateInjury(Person person, Injury injury) throws CommandException {
        if (person.getInjuries().contains(injury)) {
            logger.warning("Player " + person.getName() + " is already assigned to injury " + injury.getInjuryName());
            throw new CommandException(String.format(Messages.MESSAGE_ASSIGNED_SAME_INJURY,
                    person.getName(), injury.getInjuryName()));
        }
    }

    /**
     * Returns true if this {@code AssignInjuryCommand} is equal to another object.
     * Two {@code AssignInjuryCommand} objects are considered equal if they have the same
     * {@code personNameToAssign} and {@code injuryToAssign}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignInjuryCommand)) {
            return false;
        }

        AssignInjuryCommand otherAssignInjuryCommand = (AssignInjuryCommand) other;
        return personNameToAssign.equals(otherAssignInjuryCommand.personNameToAssign)
                && injuryToAssign.equals(otherAssignInjuryCommand.injuryToAssign);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personNameToAssign", personNameToAssign)
                .add("injuryToAssign", injuryToAssign)
                .toString();
    }
}
