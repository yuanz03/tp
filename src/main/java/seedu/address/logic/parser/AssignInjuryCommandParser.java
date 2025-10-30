package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.Messages;
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
        assert args != null : "args should not be null";
        checkEmptyArguments(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAYER, PREFIX_INJURY);

        checkCompulsoryPrefixes(argMultimap);
        verifyNoDuplicatePrefixes(argMultimap);

        Name playerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYER).get());
        Injury injury = ParserUtil.parseInjury(argMultimap.getValue(PREFIX_INJURY).get());

        checkEmptyPreamble(argMultimap);

        return new AssignInjuryCommand(playerName, injury);
    }

    private void checkEmptyArguments(String args) throws ParseException {
        assert args != null : "args should not be null";
        if (args.trim().isEmpty()) {
            throw new ParseException(
                    formatParseErrorMessage(
                            String.format(Messages.MESSAGE_EMPTY_COMMAND, AssignInjuryCommand.COMMAND_WORD)));
        }
    }

    private void checkCompulsoryPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null : "argMultimap should not be null";
        boolean hasPlayerPrefix = arePrefixesPresent(argMultimap, PREFIX_PLAYER);
        boolean hasInjuryPrefix = arePrefixesPresent(argMultimap, PREFIX_INJURY);

        if (!hasPlayerPrefix && !hasInjuryPrefix) {
            throw new ParseException(
                    formatParseErrorMessage(
                            String.format(Messages.MESSAGE_MISSING_BOTH_PREFIXES, AssignInjuryCommand.COMMAND_WORD)));
        }
        if (!hasPlayerPrefix) {
            throw new ParseException(
                    formatParseErrorMessage(
                            String.format(Messages.MESSAGE_MISSING_PLAYER_PREFIX, AssignInjuryCommand.COMMAND_WORD)));
        }
        if (!hasInjuryPrefix) {
            throw new ParseException(
                    formatParseErrorMessage(
                            String.format(Messages.MESSAGE_MISSING_INJURY_PREFIX, AssignInjuryCommand.COMMAND_WORD)));
        }
    }

    private void verifyNoDuplicatePrefixes(ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null : "argMultimap should not be null";
        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PLAYER, PREFIX_INJURY);
        } catch (ParseException exception) {
            throw new ParseException(formatParseErrorMessage(exception.getMessage()));
        }
    }

    private void checkEmptyPreamble(ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null : "argMultimap should not be null";
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    formatParseErrorMessage(
                            String.format(Messages.MESSAGE_NON_EMPTY_PREAMBLE, AssignInjuryCommand.COMMAND_WORD)));
        }
    }

    private String formatParseErrorMessage(String message) {
        assert message != null : "error message should not be null";
        return String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                message + "\n" + AssignInjuryCommand.MESSAGE_USAGE);
    }
}
