package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.AlreadyNotCaptainException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Marks a person in the address book as no longer a team captain.
 * <p>
 * The person is identified by their {@link Name}. If the person does not exist
 * or is already not a captain, a {@link CommandException} is thrown.
 * <p>
 * Example usage:
 * <pre>
 * {@code stripCaptain p/Sergio Ramos}
 * </pre>
 */
public class StripCaptainCommand extends Command {
    public static final String COMMAND_WORD = "stripCaptain";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the person "
            + "as no longer captain.\n"
            + "Parameters: " + PREFIX_PLAYER + "PLAYER "
            + "Example: " + COMMAND_WORD + " " + PREFIX_PLAYER + "Sergio Ramos";

    public static final String MESSAGE_SUCCESS = "; %1$s is no longer team captain.";
    public static final String MESSAGE_NOT_CAPTAIN = "%1$s is not a captain!";

    private final Name targetName;

    public StripCaptainCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person targetPerson;

        try {
            targetPerson = model.getPersonByName(targetName);
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND));
        } catch (AlreadyNotCaptainException e) {
            throw new CommandException(String.format(MESSAGE_NOT_CAPTAIN));
        }

        // catch person already exists as not-captain

        model.stripCaptain(targetPerson);

        return CommandResult.showPersonCommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(targetPerson)));

    }
}
