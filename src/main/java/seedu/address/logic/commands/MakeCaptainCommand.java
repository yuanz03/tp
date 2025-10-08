package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.AlreadyCaptainException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class MakeCaptainCommand extends Command {
    public static final String COMMAND_WORD = "makeCaptain";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the person "
            + "as captain.\n"
            + "Parameters: " + PREFIX_PLAYER + "PLAYER "
            + "Example: " + COMMAND_WORD + " " + PREFIX_PLAYER + "Sergio Ramos";

    public static final String MESSAGE_SUCCESS = "; %1$s is now the team captain.";
    public static final String MESSAGE_ALREADY_CAPTAIN = "%1$s is already a captain!";

    private final Name targetName;

    public MakeCaptainCommand(Name targetName) {
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
        } catch (AlreadyCaptainException e) {
            throw new CommandException(String.format(MESSAGE_ALREADY_CAPTAIN));
        }

        // catch person already exists as captain

        model.makeCaptain(targetPerson);

        return CommandResult.showPersonCommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(targetPerson)));

    }
}
