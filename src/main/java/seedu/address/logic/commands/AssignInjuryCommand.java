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
 * Assigns an injury status to a person in the address book.
 */
public class AssignInjuryCommand extends Command {

    public static final String COMMAND_WORD = "assigninjury";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns an injury status to a person in the address book.\n"
            + "Parameters: " + PREFIX_PLAYER + "PLAYER " + PREFIX_INJURY + "INJURY "
            + "Example: " + COMMAND_WORD + " " + PREFIX_PLAYER + "John Doe " + PREFIX_INJURY + "ACL";

    public static final String MESSAGE_ASSIGN_INJURY_SUCCESS = "%1$s's injury status has been set to: %2$s";
    public static final String MESSAGE_ASSIGNED_SAME_INJURY = "%1$s's injury status is already set as: %2$s";

    private final Name personNameToAssign;
    private final Injury injuryToAssign;

    /**
     * Creates an AssignInjuryCommand that assigns the specified {@code injury} to the given person.
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

        try {
            personToAssign = model.getPersonByName(personNameToAssign);
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, personNameToAssign));
        }

        if (model.isDuplicateInjuryAssigned(personToAssign, injuryToAssign)) {
            throw new CommandException(String.format(MESSAGE_ASSIGNED_SAME_INJURY,
                    personToAssign.getName(), injuryToAssign));
        }

        model.updatePersonInjuryStatus(personToAssign, injuryToAssign);
        return CommandResult.showPersonCommandResult(String.format(MESSAGE_ASSIGN_INJURY_SUCCESS,
                personToAssign.getName(), injuryToAssign));
    }

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
