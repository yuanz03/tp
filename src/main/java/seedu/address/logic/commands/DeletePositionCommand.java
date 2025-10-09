package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.position.Position;

public class DeletePositionCommand extends Command {
    public static final String COMMAND_WORD = "deleteposition";
    public static final String MESSAGE_SUCCESS = "Position %s has been deleted successfully!";
    public static final String MESSAGE_NOT_FOUND = "Position doesn't exist.";
    public static final String MESSAGE_MISSING_FLAG = "Missing 'ps/' flag for deleteposition command";
    public static final String MESSAGE_INVALID_FORMAT =
            "Invalid command format. Please ensure correct form at: deleteposition ps/<position_name>";

    private final String rawPositionName;

    public DeletePositionCommand(String rawPositionName) {
        this.rawPositionName = rawPositionName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        final String name = rawPositionName.trim();
        if (!Position.isValidPositionName(name)) {
            throw new CommandException(Position.MESSAGE_CONSTRAINTS);
        }
        try {
            Position toDelete = model.getPositionByName(name);
            model.deletePosition(toDelete);
            return new CommandResult(String.format(MESSAGE_SUCCESS, name));
        } catch (RuntimeException e) {
            throw new CommandException(MESSAGE_NOT_FOUND);
        }
    }
}


