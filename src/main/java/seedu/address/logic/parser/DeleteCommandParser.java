package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_TOO_MANY_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.team.Team;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAYER, PREFIX_TEAM, PREFIX_POSITION);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        if (argMultimap.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        // If more than 2 prefixes present, return error
        if (argMultimap.getSize() > 2) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_PREFIXES, DeleteCommand.MESSAGE_USAGE));
        }

        // Handle delete player
        if (argMultimap.getValue(PREFIX_PLAYER).isPresent()) {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PLAYER);
            Name playerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYER).get());
            return DeleteCommand.createDeletePlayerCommand(playerName);
        }

        // Handle delete team
        if (argMultimap.getValue(PREFIX_TEAM).isPresent()) {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM);
            Team team = ParserUtil.parseTeam(argMultimap.getValue(PREFIX_TEAM).get());
            return DeleteCommand.createDeleteTeamCommand(team);
        }

        // Handle delete position
        if (argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POSITION);
            throw new ParseException("NOT IMPLEMENTED YET");
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

}
