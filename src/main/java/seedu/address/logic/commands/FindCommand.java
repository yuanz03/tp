package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in PlayBook whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all players whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Duplicate keywords are not allowed when using the command.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private static final Logger logger = LogsCenter.getLogger(FindCommand.class);

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        logger.info("Executing FindCommand with predicate: " + predicate);

        requireNonNull(model);

        // Assert model state
        assert model.getAddressBook() != null : "Model should have address book";

        model.updateFilteredPersonList(predicate);

        int foundCount = model.getFilteredPersonList().size();
        logger.info("Found " + foundCount + " players matching search criteria");

        // Custom message for no matches
        if (foundCount == 0) {
            List<String> keywords = predicate.getKeywords();
            String keywordsString;

            if (keywords.isEmpty() || (keywords.size() == 1 && keywords.get(0).isEmpty())) {
                keywordsString = "";
            } else {
                keywordsString = keywords.stream()
                        .filter(keyword -> !keyword.isEmpty())
                        .map(keyword -> "\"" + keyword + "\"")
                        .collect(Collectors.joining(", "));
            }

            String noMatchMessage = "No player with name matching keyword(s): " + keywordsString;
            logger.info("No players found matching search criteria");
            return CommandResult.showPersonCommandResult(noMatchMessage);
        }

        logger.info("FindCommand completed successfully");

        return CommandResult.showPersonCommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
