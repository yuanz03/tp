package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.Comparator;
import java.util.List;
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

        validatePresentPrefixes(argMultimap, args);
        validateAtLeastOneFilterPresent(argMultimap, args);

        FilterByTeamPredicate teamPredicate = createTeamPredicate(argMultimap);
        FilterByInjuryPredicate injuryPredicate = createInjuryPredicate(argMultimap);
        FilterByPositionPredicate positionPredicate = createPositionPredicate(argMultimap);

        logger.log(Level.INFO, "Successfully parsed filter command with criteria");
        return new FilterCommand(teamPredicate, injuryPredicate, positionPredicate,
                argMultimap.getValue(PREFIX_TEAM),
                argMultimap.getValue(PREFIX_INJURY),
                argMultimap.getValue(PREFIX_POSITION));
    }

    /**
     * Validates that at least one filter criteria is present.
     */
    private void validateAtLeastOneFilterPresent(ArgumentMultimap argMultimap, String args) throws ParseException {
        boolean hasTeam = argMultimap.getValue(PREFIX_TEAM).isPresent();
        boolean hasInjury = argMultimap.getValue(PREFIX_INJURY).isPresent();
        boolean hasPosition = argMultimap.getValue(PREFIX_POSITION).isPresent();

        if ((!hasTeam && !hasInjury && !hasPosition) || !argMultimap.getPreamble().isEmpty()) {
            logger.log(Level.WARNING, "Invalid filter command format: {0}", args);
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Creates team predicate from argument multimap.
     */
    private FilterByTeamPredicate createTeamPredicate(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(PREFIX_TEAM)
                .map(FilterByTeamPredicate::new)
                .orElse(FilterByTeamPredicate.ALWAYS_TRUE);
    }

    /**
     * Creates injury predicate from argument multimap.
     */
    private FilterByInjuryPredicate createInjuryPredicate(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(PREFIX_INJURY)
                .map(FilterByInjuryPredicate::new)
                .orElse(FilterByInjuryPredicate.ALWAYS_TRUE);
    }

    /**
     * Creates position predicate from argument multimap.
     */
    private FilterByPositionPredicate createPositionPredicate(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(PREFIX_POSITION)
                .map(FilterByPositionPredicate::new)
                .orElse(FilterByPositionPredicate.ALWAYS_TRUE);
    }

    /**
     * Validates all present prefixes in the argument multimap in the order they appear in the input.
     *
     * @throws ParseException if any of the present prefix values are invalid
     */
    private void validatePresentPrefixes(ArgumentMultimap argMultimap, String args) throws ParseException {
        List<Prefix> presentPrefixes = getPresentPrefixesInOrder(argMultimap, args);
        validatePrefixValues(presentPrefixes, argMultimap);
    }

    /**
     * Gets all present prefixes in the order they appear in the input.
     */
    private List<Prefix> getPresentPrefixesInOrder(ArgumentMultimap argMultimap, String args) {
        return Stream.of(PREFIX_TEAM, PREFIX_INJURY, PREFIX_POSITION)
                .filter(p -> argMultimap.getValue(p).isPresent())
                .sorted(Comparator.comparingInt(p -> args.indexOf(p.getPrefix())))
                .collect(Collectors.toList());
    }

    /**
     * Validates values for all present prefixes.
     */
    private void validatePrefixValues(List<Prefix> presentPrefixes, ArgumentMultimap argMultimap)
            throws ParseException {
        for (Prefix prefix : presentPrefixes) {
            validatePrefixValue(prefix, argMultimap.getValue(prefix).get());
        }
    }

    /**
     * Validates a single prefix value.
     */
    private void validatePrefixValue(Prefix prefix, String value) throws ParseException {
        if (prefix.equals(PREFIX_TEAM)) {
            ParserUtil.parseTeam(value);
        } else if (prefix.equals(PREFIX_INJURY)) {
            ParserUtil.parseInjury(value);
        } else if (prefix.equals(PREFIX_POSITION)) {
            ParserUtil.parsePosition(value);
        }
        // No default case needed as we only process known prefixes
    }
}
