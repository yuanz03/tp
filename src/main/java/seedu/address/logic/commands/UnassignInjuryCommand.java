package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Removes an injury status from an existing {@code Person} in the address book.
 * Resets the {@code Person}'s injury status to the default {@code "FIT"} status.
 * <p>
 * Usage: {@code unassigninjury pl/<player>}.
 */
public class UnassignInjuryCommand extends Command {

    public static final String COMMAND_WORD = "unassigninjury";
    public static final String MESSAGE_UNASSIGN_INJURY_SUCCESS = "%1$s's injury status has been removed!";
    public static final String MESSAGE_INJURY_ALREADY_UNASSIGNED =
            "%1$s's injury status has already been set to the default 'FIT' status!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes an injury status from a person in the address book.\n"
            + "Parameters: " + PREFIX_PLAYER + "PLAYER "
            + "Example: " + COMMAND_WORD + " " + PREFIX_PLAYER + "John Doe";

    private final Name personNameToUnassign;

    /**
     * Creates an {@code UnassignInjuryCommand} that removes the current injury status of the specified {@code Person}.
     */
    public UnassignInjuryCommand(Name personName) {
        requireAllNonNull(personName);
        this.personNameToUnassign = personName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToUnassign;

        // Check if the player exists
        try {
            personToUnassign = model.getPersonByName(personNameToUnassign);
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, personNameToUnassign));
        }

        // Check if the player's injury status has already been set to the default 'FIT' status
        if (!model.hasInjury(personToUnassign)) {
            throw new CommandException(String.format(MESSAGE_INJURY_ALREADY_UNASSIGNED, personToUnassign.getName()));
        }

        model.updatePersonInjuryStatus(personToUnassign, new Injury(Person.DEFAULT_INJURY_STATUS));
        return CommandResult.showPersonCommandResult(String.format(MESSAGE_UNASSIGN_INJURY_SUCCESS,
                personToUnassign.getName()));
    }

    /**
     * Returns true if this {@code UnassignInjuryCommand} is equal to another object.
     * Two {@code UnassignInjuryCommand} are considered equal if they have the same {@code personNameToUnassign}.
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
        return personNameToUnassign.equals(otherUnassignInjuryCommand.personNameToUnassign);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personToUnassign", personNameToUnassign)
                .toString();
    }
}
