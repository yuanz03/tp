package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.AssignInjuryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AssignInjuryCommand object.
 */
public class AssignInjuryCommandParser implements Parser<AssignInjuryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AssignInjuryCommand
     * and returns a AssignInjuryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AssignInjuryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAYER, PREFIX_INJURY);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAYER, PREFIX_INJURY) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignInjuryCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PLAYER, PREFIX_INJURY);
        Name playerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYER).get());
        Injury injury = ParserUtil.parseInjury(argMultimap.getValue(PREFIX_INJURY).get());

        return new AssignInjuryCommand(playerName, injury);
    }
}
