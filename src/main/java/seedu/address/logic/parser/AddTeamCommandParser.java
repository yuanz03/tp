package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.AddTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.Team;

/**
 * Parses input arguments and creates a new AddTeamCommand object
 */
public class AddTeamCommandParser implements Parser<AddTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTeamCommand
     * and returns an AddTeamCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TEAM);

        if (!arePrefixesPresent(argMultimap, PREFIX_TEAM)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeamCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM);
        Team team = ParserUtil.parseTeam(argMultimap.getValue(PREFIX_TEAM).get());
        return new AddTeamCommand(team);
    }
}
