package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.AssignCaptainCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AssignCaptainCommand object
 */
public class AssignCaptainCommandParser implements Parser<AssignCaptainCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignCaptainCommand
     * and returns a AssignCaptainCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCaptainCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAYER);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAYER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCaptainCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PLAYER);

        Name playerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYER).get());
        return new AssignCaptainCommand(playerName);
    }
}
