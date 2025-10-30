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
        validateArgumentsNotEmpty(trimmedArgs);

        String[] nameKeywords = splitKeywords(trimmedArgs);
        validateNoDuplicateKeywords(nameKeywords);

        logParsingSuccess(nameKeywords);
        return createFindCommand(nameKeywords);
    }

    /**
     * Validates that arguments are not empty.
     */
    private void validateArgumentsNotEmpty(String trimmedArgs) throws ParseException {
        if (trimmedArgs.isEmpty()) {
            logger.warning("Empty arguments provided to find command");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Splits the trimmed arguments into individual keywords.
     */
    private String[] splitKeywords(String trimmedArgs) {
        return trimmedArgs.split("\\s+");
    }

    /**
     * Validates that there are no duplicate keywords in the input array.
     */
    private void validateNoDuplicateKeywords(String[] keywords) throws ParseException {
        Set<String> duplicates = findDuplicateKeywords(keywords);
        if (!duplicates.isEmpty()) {
            throw createDuplicateKeywordsException(duplicates);
        }
    }

    /**
     * Finds duplicate keywords in the input array.
     */
    private Set<String> findDuplicateKeywords(String[] keywords) {
        Map<String, String> normalizedToOriginal = createNormalizedKeywordMap();
        Set<String> duplicates = new HashSet<>();

        for (String keyword : keywords) {
            processKeywordForDuplicates(keyword, normalizedToOriginal, duplicates);
        }
        return duplicates;
    }

    /**
     * Creates a new map for tracking normalized keywords.
     */
    private Map<String, String> createNormalizedKeywordMap() {
        return new HashMap<>();
    }

    /**
     * Processes a single keyword for duplicate detection.
     */
    private void processKeywordForDuplicates(String keyword, Map<String, String> normalizedToOriginal,
                                           Set<String> duplicates) {
        String normalized = keyword.trim().toLowerCase();
        if (!normalized.isEmpty()) {
            if (normalizedToOriginal.containsKey(normalized)) {
                duplicates.add(normalizedToOriginal.get(normalized));
            } else {
                normalizedToOriginal.put(normalized, keyword.trim());
            }
        }
    }

    /**
     * Creates exception for duplicate keywords.
     */
    private ParseException createDuplicateKeywordsException(Set<String> duplicates) {
        String duplicateList = formatDuplicateList(duplicates);
        String pluralSuffix = duplicates.size() > 1 ? "s" : "";
        return new ParseException(String.format(
                "Duplicate name keyword%s found: %s. Please remove duplicate keywords.",
                pluralSuffix, duplicateList));
    }

    /**
     * Formats the duplicate list for the error message.
     */
    private String formatDuplicateList(Set<String> duplicates) {
        return duplicates.stream()
                .map(dup -> "\"" + dup + "\"")
                .collect(Collectors.joining(", "));
    }

    /**
     * Logs successful parsing of keywords.
     */
    private void logParsingSuccess(String[] nameKeywords) {
        logger.info("Parsed " + nameKeywords.length + " keywords: " + Arrays.toString(nameKeywords));
        logger.info("Successfully parsed find command");
    }

    /**
     * Creates a new FindCommand with the parsed keywords.
     */
    private FindCommand createFindCommand(String[] nameKeywords) {
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
