package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.UnassignCaptainCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new UnassignCaptainCommand object
 */
public class UnassignCaptainCommandParser implements Parser<UnassignCaptainCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnassignCaptainCommand
     * and returns a UnassignCaptainCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnassignCaptainCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAYER);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAYER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnassignCaptainCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PLAYER);

        Name playerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYER).get());
        return new UnassignCaptainCommand(playerName);
    }
}
