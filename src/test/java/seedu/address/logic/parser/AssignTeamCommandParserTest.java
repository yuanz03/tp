package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PLAYER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AssignTeamCommand;
import seedu.address.model.person.Name;
import seedu.address.model.team.Team;

public class AssignTeamCommandParserTest {
    private AssignTeamCommandParser parser = new AssignTeamCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Name expectedName = new Name(VALID_NAME_AMY);
        Team expectedTeam = new Team(VALID_TEAM_AMY);

        // whitespace only preamble
        String userInput = PREAMBLE_WHITESPACE + PLAYER_DESC_AMY + TEAM_DESC_AMY;
        assertParseSuccess(parser, userInput, new AssignTeamCommand(expectedName, expectedTeam));

        // different order of prefixes
        userInput = TEAM_DESC_AMY + PLAYER_DESC_AMY;
        assertParseSuccess(parser, userInput, new AssignTeamCommand(expectedName, expectedTeam));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTeamCommand.MESSAGE_USAGE);

        // missing player prefix
        String userInput = TEAM_DESC_AMY;
        assertParseFailure(parser, userInput, expectedMessage);

        // missing team prefix
        userInput = PLAYER_DESC_AMY;
        assertParseFailure(parser, userInput, expectedMessage);

        // all prefixes missing
        userInput = "";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid player name
        String userInput = INVALID_PLAYER_DESC + TEAM_DESC_AMY;
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, userInput, expectedMessage);

        // invalid team name
        userInput = PLAYER_DESC_AMY + INVALID_TEAM_DESC;
        expectedMessage = Team.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        // duplicate player prefix
        String userInput = PLAYER_DESC_AMY + PLAYER_DESC_BOB + TEAM_DESC_AMY;
        String expectedMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER);
        assertParseFailure(parser, userInput, expectedMessage);

        // duplicate team prefix
        userInput = PLAYER_DESC_AMY + TEAM_DESC_AMY + TEAM_DESC_BOB;
        expectedMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TEAM);
        assertParseFailure(parser, userInput, expectedMessage);

        // multiple duplicate prefixes
        userInput = PLAYER_DESC_AMY + PLAYER_DESC_BOB + TEAM_DESC_AMY + TEAM_DESC_BOB;
        expectedMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER, PREFIX_TEAM);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = PREAMBLE_NON_EMPTY + PLAYER_DESC_AMY + TEAM_DESC_AMY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTeamCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
