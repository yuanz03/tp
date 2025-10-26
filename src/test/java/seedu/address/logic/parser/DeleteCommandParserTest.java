package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_TOO_MANY_PREFIXES;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PLAYER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Name;
import seedu.address.model.position.Position;
import seedu.address.model.team.Team;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside the DeleteCommand code.
 */
public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validPlayerArgs_returnsDeleteCommand() {
        // Valid player name
        Name personName = new Name(VALID_NAME_AMY);
        assertParseSuccess(parser, PLAYER_DESC_AMY, DeleteCommand.createDeletePlayerCommand(personName));
    }

    @Test
    public void parse_validPlayerArgsWithExtraWhitespace_returnsDeleteCommand() {
        // Valid player name with extra whitespace
        Name personName = new Name(VALID_NAME_AMY);
        assertParseSuccess(parser, PLAYER_DESC_AMY + "  ", DeleteCommand.createDeletePlayerCommand(personName));
    }

    @Test
    public void parse_validTeamArgs_returnsDeleteCommand() {
        // Valid team name
        Team team = new Team(VALID_TEAM_AMY);
        assertParseSuccess(parser, TEAM_DESC_AMY, DeleteCommand.createDeleteTeamCommand(team));
    }

    @Test
    public void parse_validTeamArgsWithExtraWhitespace_returnsDeleteCommand() {
        // Valid team name with extra whitespace
        Team team = new Team(VALID_TEAM_AMY);
        assertParseSuccess(parser, TEAM_DESC_AMY + "  ", DeleteCommand.createDeleteTeamCommand(team));
    }

    @Test
    public void parse_validPositionArgs_returnsDeleteCommand() {
        // Valid position name
        assertParseSuccess(parser, POSITION_DESC_AMY, DeleteCommand.createDeletePositionCommand(VALID_POSITION_AMY));
    }

    @Test
    public void parse_validPositionArgsWithExtraWhitespace_returnsDeleteCommand() {
        // Valid position name with extra whitespace
        assertParseSuccess(parser, POSITION_DESC_AMY + "  ",
                DeleteCommand.createDeletePositionCommand(VALID_POSITION_AMY));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // Missing any prefix
        assertParseFailure(parser, VALID_NAME_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPlayerName_throwsParseException() {
        // Missing player name after prefix
        assertParseFailure(parser, " " + PREFIX_PLAYER,
                String.format("Invalid player name: %s\n%s", "", Name.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_missingTeamName_throwsParseException() {
        // Missing team name after prefix
        assertParseFailure(parser, " " + PREFIX_TEAM,
                String.format("Invalid team name: %s\n%s", "", Team.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_missingPositionName_throwsParseException() {
        // Missing position name after prefix
        assertParseFailure(parser, " " + PREFIX_POSITION,
                String.format("Invalid position name: %s\n%s", "", Position.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidPlayerName_throwsParseException() {
        // Invalid name with special characters
        assertParseFailure(parser, INVALID_PLAYER_DESC,
                String.format("Invalid player name: %s\n%s", "hubby*", Name.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidTeamName_throwsParseException() {
        // Invalid team name with special characters
        assertParseFailure(parser, INVALID_TEAM_DESC,
                String.format("Invalid team name: %s\n%s", "U@16", Team.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidPositionName_throwsParseException() {
        // Invalid position name with special characters
        assertParseFailure(parser, INVALID_POSITION_DESC,
                String.format("Invalid position name: %s\n%s", "MF*", Position.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_duplicatePlayerPrefix_throwsParseException() {
        // Duplicate pl/ prefix
        assertParseFailure(parser, PLAYER_DESC_AMY + PLAYER_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER));
    }

    @Test
    public void parse_duplicateTeamPrefix_throwsParseException() {
        // Duplicate tm/ prefix
        assertParseFailure(parser, TEAM_DESC_AMY + TEAM_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TEAM));
    }

    @Test
    public void parse_multiplePrefixesPlayerAndTeam_throwsParseException() {
        // Multiple different prefixes (pl/ and tm/)
        assertParseFailure(parser, PLAYER_DESC_AMY + TEAM_DESC_AMY,
                String.format(MESSAGE_TOO_MANY_PREFIXES, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multiplePrefixesPlayerAndPosition_throwsParseException() {
        // Multiple different prefixes (pl/ and ps/)
        assertParseFailure(parser, PLAYER_DESC_AMY + POSITION_DESC_AMY,
                String.format(MESSAGE_TOO_MANY_PREFIXES, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multiplePrefixesTeamAndPosition_throwsParseException() {
        // Multiple different prefixes (tm/ and ps/)
        assertParseFailure(parser, TEAM_DESC_AMY + POSITION_DESC_AMY,
                String.format(MESSAGE_TOO_MANY_PREFIXES, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        // Preamble before prefix
        assertParseFailure(parser, "some preamble" + PLAYER_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

}
