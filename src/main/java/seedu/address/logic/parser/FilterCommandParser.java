package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FilterByInjuryPredicate;
import seedu.address.model.position.FilterByPositionPredicate;
import seedu.address.model.team.FilterByTeamPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TEAM, PREFIX_INJURY, PREFIX_POSITION);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM, PREFIX_INJURY, PREFIX_POSITION);

        List<Prefix> presentPrefixes = Stream.of(PREFIX_TEAM, PREFIX_INJURY, PREFIX_POSITION)
                .filter(p -> argMultimap.getValue(p).isPresent())
                .sorted(Comparator.comparingInt(p -> args.indexOf(p.getPrefix())))
                .collect(Collectors.toList());

        for (Prefix p : presentPrefixes) {
            String value = argMultimap.getValue(p).get();
            if (p == PREFIX_TEAM) {
                ParserUtil.parseTeam(value);
            } else if (p == PREFIX_INJURY) {
                ParserUtil.parseInjury(value);
            } else if (p == PREFIX_POSITION) {
                ParserUtil.parsePosition(value);
            }
        }

        boolean hasTeam = argMultimap.getValue(PREFIX_TEAM).isPresent();
        boolean hasInjury = argMultimap.getValue(PREFIX_INJURY).isPresent();
        boolean hasPosition = argMultimap.getValue(PREFIX_POSITION).isPresent();

        if (hasTeam) {
            ParserUtil.parseTeam(argMultimap.getValue(PREFIX_TEAM).get());
        }

        if (hasInjury) {
            ParserUtil.parseInjury(argMultimap.getValue(PREFIX_INJURY).get());
        }

        if (hasPosition) {
            ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION).get());
        }

        if ((!hasTeam && !hasInjury && !hasPosition) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        FilterByTeamPredicate teamPred = argMultimap.getValue(PREFIX_TEAM)
                .map(name -> new FilterByTeamPredicate(name))
                .orElse(FilterByTeamPredicate.ALWAYS_TRUE);
        FilterByInjuryPredicate injuryPred = argMultimap.getValue(PREFIX_INJURY)
                .map(inj -> new FilterByInjuryPredicate(inj))
                .orElse(FilterByInjuryPredicate.ALWAYS_TRUE);
        FilterByPositionPredicate positionPred = argMultimap.getValue(PREFIX_POSITION)
                .map(pos -> new FilterByPositionPredicate(pos))
                .orElse(FilterByPositionPredicate.ALWAYS_TRUE);

        return new FilterCommand(teamPred, injuryPred, positionPred,
                argMultimap.getValue(PREFIX_TEAM),
                argMultimap.getValue(PREFIX_INJURY),
                argMultimap.getValue(PREFIX_POSITION));
    }
}
