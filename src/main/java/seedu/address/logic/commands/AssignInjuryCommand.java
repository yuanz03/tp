package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
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
 * Assigns an injury status to an existing {@code Person} in the PlayBook.
 * <p>
 * Usage: {@code assigninjury pl/<player> i/<injury>}.
 */
public class AssignInjuryCommand extends Command {

    public static final String COMMAND_WORD = "assigninjury";
    public static final String MESSAGE_ASSIGN_INJURY_SUCCESS = "%1$s's injury status has been set to: %2$s";
    public static final String MESSAGE_ASSIGNED_SAME_INJURY = "%1$s's injury status is already set as: %2$s";
    public static final String MESSAGE_INVALID_INJURY_ASSIGNMENT = "'FIT' cannot be assigned as an injury status!\n"
            + "Please use the unassigninjury pl/<player> i/<injury> command instead to restore 'FIT' status.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns an injury status to a player in the PlayBook.\n"
            + "Parameters: " + PREFIX_PLAYER + "PLAYER_NAME " + PREFIX_INJURY + "INJURY "
            + "Example: " + COMMAND_WORD + " " + PREFIX_PLAYER + "John Doe " + PREFIX_INJURY + "ACL";

    private final Name personNameToAssign;
    private final Injury injuryToAssign;

    /**
     * Creates an {@code AssignInjuryCommand} that assigns the specified {@code injury} to the specified {@code Person}.
     */
    public AssignInjuryCommand(Name personName, Injury injury) {
        requireAllNonNull(personName, injury);
        this.personNameToAssign = personName;
        this.injuryToAssign = injury;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToAssign;

        // Check if the player exists
        try {
            personToAssign = model.getPersonByName(personNameToAssign);
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, personNameToAssign));
        }

        // Check if the player has already been assigned the same injury
        if (personToAssign.getInjuries().contains(injuryToAssign)) {
            throw new CommandException(String.format(MESSAGE_ASSIGNED_SAME_INJURY,
                    personToAssign.getName(), personToAssign.getInjuries()));
        }

        // Disallow assigning "FIT" as an injury status
        if (injuryToAssign.equals(Person.DEFAULT_INJURY_STATUS)) {
            throw new CommandException(MESSAGE_INVALID_INJURY_ASSIGNMENT);
        }

        model.addInjury(personToAssign, injuryToAssign);
        return CommandResult.showPersonCommandResult(String.format(MESSAGE_ASSIGN_INJURY_SUCCESS,
                personToAssign.getName(), model.getPersonByName(personNameToAssign).getInjuries()));
    }

    /**
     * Returns true if this {@code AssignInjuryCommand} is equal to another object.
     * Two {@code AssignInjuryCommand} are considered equal if they have the same
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
                .add("personToAssign", personNameToAssign)
                .add("injuryToAssign", injuryToAssign)
                .toString();
    }
}
