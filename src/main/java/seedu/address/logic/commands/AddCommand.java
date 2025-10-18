package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Person;

/**
 * Adds a player to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a player to the address book. "
            + "Parameters: "
            + PREFIX_PLAYER + "PLAYER_NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_TEAM + "TEAM_NAME "
            // TODO: implement support for position
            + "[" + PREFIX_INJURY + "INJURY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PLAYER + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TEAM + "U16 "
            // TODO: implement support for position
            + PREFIX_INJURY + "ACL "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New player added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This player already exists in the address book";
    public static final String MESSAGE_TEAM_NOT_FOUND = "The team '%1$s' does not exist. "
            + "Please create the team first using the 'addteam' command.";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (!model.hasTeam(toAdd.getTeam())) {
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, toAdd.getTeam().getName()));
        }

        model.addPerson(toAdd);

        Injury injury = toAdd.getInjury();

        if (injury != null && injury.equals(new Injury(Person.DEFAULT_INJURY_STATUS))) {
            model.updatePersonInjuryStatus(toAdd, new Injury(Person.DEFAULT_INJURY_STATUS));
        }
        return CommandResult.showPersonCommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
