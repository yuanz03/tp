package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        logger.info("Parsing find command arguments: " + args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.warning("Empty arguments provided to find command");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        // Check for duplicate keywords
        validateNoDuplicateKeywords(nameKeywords);

        logger.info("Parsed " + nameKeywords.length + " keywords: " + Arrays.toString(nameKeywords));

        logger.info("Successfully parsed find command");
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    /**
     * Validates that there are no duplicate keywords in the input array.
     *
     * @throws ParseException if duplicate keywords are found
     */
    private void validateNoDuplicateKeywords(String[] keywords) throws ParseException {
        Map<String, String> normalizedToOriginal = new HashMap<>(); // Track first occurrence
        Set<String> duplicates = new HashSet<>();

        for (String keyword : keywords) {
            String normalized = keyword.trim().toLowerCase(); // Case-insensitive comparison
            if (!normalized.isEmpty()) {
                if (normalizedToOriginal.containsKey(normalized)) {
                    // Found duplicate - use the first occurrence for error message
                    duplicates.add(normalizedToOriginal.get(normalized));
                } else {
                    // First time seeing this keyword - store the original case
                    normalizedToOriginal.put(normalized, keyword.trim());
                }
            }
        }

        if (!duplicates.isEmpty()) {
            String duplicateList = duplicates.stream()
                    .map(dup -> "\"" + dup + "\"")
                    .collect(Collectors.joining(", "));
            throw new ParseException(String.format(
                    "Duplicate name keyword%s found: %s. Please remove duplicate keywords.",
                    duplicates.size() > 1 ? "s" : "", duplicateList));
        }
    }
}
