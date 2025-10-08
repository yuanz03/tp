package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_CAPTAINS;

import seedu.address.model.Model;

public class FilterIsCaptainCommand extends Command {
    public static final String COMMAND_WORD = "filterCaptains";
    public static final String MESSAGE_SUCCESS = "Listed all captains";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_CAPTAINS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
