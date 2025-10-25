package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.AssignInjuryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@code AssignInjuryCommand} object.
 */
public class AssignInjuryCommandParser implements Parser<AssignInjuryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AssignInjuryCommand}
     * and returns a {@code AssignInjuryCommand} object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public AssignInjuryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAYER, PREFIX_INJURY);

        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(AssignInjuryCommand.MESSAGE_EMPTY_COMMAND
                    + AssignInjuryCommand.MESSAGE_USAGE));
        }

        boolean hasPlayerPrefix = arePrefixesPresent(argMultimap, PREFIX_PLAYER);
        boolean hasInjuryPrefix = arePrefixesPresent(argMultimap, PREFIX_INJURY);

        if (!hasPlayerPrefix && !hasInjuryPrefix) {
            throw new ParseException(String.format(AssignInjuryCommand.MESSAGE_MISSING_BOTH_PREFIXES
                    + AssignInjuryCommand.MESSAGE_USAGE));
        }
        if (!hasPlayerPrefix) {
            throw new ParseException(String.format(AssignInjuryCommand.MESSAGE_MISSING_PLAYER_PREFIX
                    + AssignInjuryCommand.MESSAGE_USAGE));
        }
        if (!hasInjuryPrefix) {
            throw new ParseException(String.format(AssignInjuryCommand.MESSAGE_MISSING_INJURY_PREFIX
                    + AssignInjuryCommand.MESSAGE_USAGE));
        }

        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PLAYER, PREFIX_INJURY);
        } catch (ParseException exception) {
            throw new ParseException(exception.getMessage() + "\n" + AssignInjuryCommand.MESSAGE_USAGE);
        }

        Name playerName;
        Injury injury;

        try {
            playerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYER).get());
        } catch (ParseException exception) {
            throw new ParseException(exception.getMessage() + "\n" + AssignInjuryCommand.MESSAGE_USAGE);
        }

        try {
            injury = ParserUtil.parseInjury(argMultimap.getValue(PREFIX_INJURY).get());
        } catch (ParseException exception) {
            throw new ParseException(exception.getMessage() + "\n" + AssignInjuryCommand.MESSAGE_USAGE);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(AssignInjuryCommand.MESSAGE_NON_EMPTY_PREAMBLE
                    + AssignInjuryCommand.MESSAGE_USAGE));
        }
        return new AssignInjuryCommand(playerName, injury);
    }
}
