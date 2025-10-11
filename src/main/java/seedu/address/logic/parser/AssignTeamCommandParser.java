package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.AssignTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.team.Team;

/**
 * Parses input arguments and creates a new AssignTeamCommand object
 */
public class AssignTeamCommandParser implements Parser<AssignTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignTeamCommand
     * and returns an AssignTeamCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AssignTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PLAYER, PREFIX_TEAM);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAYER, PREFIX_TEAM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTeamCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PLAYER, PREFIX_TEAM);
        Name playerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYER).get());
        Team team = ParserUtil.parseTeam(argMultimap.getValue(PREFIX_TEAM).get());

        return new AssignTeamCommand(playerName, team);
    }
}
