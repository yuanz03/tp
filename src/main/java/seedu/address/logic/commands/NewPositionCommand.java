package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POSITIONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.position.Position;

/**
 * Creates a new {@code Position} and adds it to the PlayBook.
 * <p>
 * Usage: {@code addposition ps/<position_name>}
 */
public class NewPositionCommand extends Command {
    public static final String COMMAND_WORD = "addposition";
    public static final String MESSAGE_SUCCESS = "Position %s has been created successfully!";
    public static final String MESSAGE_DUPLICATE = "%s already exists!";
    public static final String MESSAGE_MISSING_FLAG = "Missing '" + PREFIX_POSITION + "' flag for addposition command";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a position to the PlayBook.\n"
            + "Parameters: " + PREFIX_POSITION + "POSITION_NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_POSITION + "Striker";

    private final String rawPositionName;

    /**
     * Creates a {@code NewPositionCommand}.
     *
     * @param rawPositionName raw position name string to create.
     */
    public NewPositionCommand(String rawPositionName) {
        requireNonNull(rawPositionName);
        this.rawPositionName = rawPositionName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Create a new position after validating name and duplicates
        requireNonNull(model);
        final String normalized = rawPositionName.trim();
        final Position position;
        try {
            position = new Position(normalized);
        } catch (IllegalArgumentException e) {
            throw new CommandException(Position.MESSAGE_CONSTRAINTS);
        }
        if (model.hasPosition(position)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE, normalized));
        }
        model.addPosition(position);
        model.updateFilteredPositionList(PREDICATE_SHOW_ALL_POSITIONS);
        return CommandResult.showPositionCommandResult(String.format(MESSAGE_SUCCESS, normalized));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NewPositionCommand)) {
            return false;
        }

        NewPositionCommand otherCommand = (NewPositionCommand) other;
        return rawPositionName.equals(otherCommand.rawPositionName);
    }
}


