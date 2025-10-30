package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FilterByInjuryPredicate;
import seedu.address.model.position.FilterByPositionPredicate;
import seedu.address.model.team.FilterByTeamPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    private static final Logger logger = LogsCenter.getLogger(FilterCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        logger.log(Level.INFO, "Parsing filter command arguments: {0}", args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TEAM, PREFIX_INJURY, PREFIX_POSITION);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM, PREFIX_INJURY, PREFIX_POSITION);

        // Assert non-null argument multimap
        assert argMultimap != null : "ArgumentMultimap should not be null";

        // Validate all present prefixes in the order they appear
        validatePresentPrefixes(argMultimap, args);

        boolean hasTeam = argMultimap.getValue(PREFIX_TEAM).isPresent();
        boolean hasInjury = argMultimap.getValue(PREFIX_INJURY).isPresent();
        boolean hasPosition = argMultimap.getValue(PREFIX_POSITION).isPresent();

        if ((!hasTeam && !hasInjury && !hasPosition) || !argMultimap.getPreamble().isEmpty()) {
            logger.log(Level.WARNING, "Invalid filter command format: {0}", args);
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        FilterByTeamPredicate teamPredicate = argMultimap.getValue(PREFIX_TEAM)
                .map(FilterByTeamPredicate::new)
                .orElse(FilterByTeamPredicate.ALWAYS_TRUE);
        FilterByInjuryPredicate injuryPredicate = argMultimap.getValue(PREFIX_INJURY)
                .map(FilterByInjuryPredicate::new)
                .orElse(FilterByInjuryPredicate.ALWAYS_TRUE);
        FilterByPositionPredicate positionPredicate = argMultimap.getValue(PREFIX_POSITION)
                .map(FilterByPositionPredicate::new)
                .orElse(FilterByPositionPredicate.ALWAYS_TRUE);

        logger.log(Level.INFO, "Successfully parsed filter command with criteria");
        return new FilterCommand(teamPredicate, injuryPredicate, positionPredicate,
                argMultimap.getValue(PREFIX_TEAM),
                argMultimap.getValue(PREFIX_INJURY),
                argMultimap.getValue(PREFIX_POSITION));
    }

    /**
     * Validates all present prefixes in the argument multimap in the order they appear in the input.
     *
     * @throws ParseException if any of the present prefix values are invalid
     */
    private void validatePresentPrefixes(ArgumentMultimap argMultimap, String args) throws ParseException {
        List<Prefix> presentPrefixes = Stream.of(PREFIX_TEAM, PREFIX_INJURY, PREFIX_POSITION)
                .filter(p -> argMultimap.getValue(p).isPresent())
                .sorted(Comparator.comparingInt(p -> args.indexOf(p.getPrefix())))
                .collect(Collectors.toList());

        for (Prefix prefix : presentPrefixes) {
            String value = argMultimap.getValue(prefix).get();

            // Check for duplicate keywords in team and injury fields
            if (prefix == PREFIX_TEAM || prefix == PREFIX_INJURY) {
                validateNoDuplicateKeywords(value, prefix);
            }

            if (prefix == PREFIX_TEAM) {
                ParserUtil.parseTeam(value);
            } else if (prefix == PREFIX_INJURY) {
                ParserUtil.parseInjury(value);
            } else if (prefix == PREFIX_POSITION) {
                ParserUtil.parsePosition(value);
            }
        }
    }

    /**
     * Validates that there are no duplicate keywords in the input string.
     *
     * @throws ParseException if duplicate keywords are found
     */
    private void validateNoDuplicateKeywords(String value, Prefix prefix) throws ParseException {
        if (value == null || value.trim().isEmpty()) {
            return;
        }

        String[] keywords = value.trim().split("\\s+");
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
            String fieldName = prefix == PREFIX_TEAM ? "team" : "injury";
            throw new ParseException(String.format(
                    "Duplicate %s keyword%s found: %s. Please remove duplicate keywords.",
                    fieldName, duplicates.size() > 1 ? "s" : "", duplicateList));
        }
    }
}
