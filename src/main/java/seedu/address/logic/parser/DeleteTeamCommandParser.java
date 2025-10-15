package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.Team;

/**
 * Parses input arguments and creates a new DeleteTeamCommand object
 */
public class DeleteTeamCommandParser implements Parser<DeleteTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTeamCommand
     * and returns an DeleteTeamCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TEAM);

        if (!arePrefixesPresent(argMultimap, PREFIX_TEAM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM);
        Team team = ParserUtil.parseTeam(argMultimap.getValue(PREFIX_TEAM).get());
        return new DeleteTeamCommand(team);
    }
}
