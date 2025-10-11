package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTeams.U12;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.model.team.Team;

public class DeleteTeamCommandParserTest {

    private final DeleteTeamCommandParser parser = new DeleteTeamCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTeamCommand() {
        // Valid team name
        assertParseSuccess(parser, TEAM_DESC_AMY, new DeleteTeamCommand(U12));
    }

    @Test
    public void parse_validArgsWithWhitespace_returnsDeleteTeamCommand() {
        // Valid team name with leading whitespace
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TEAM_DESC_AMY, new DeleteTeamCommand(U12));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // Missing tm/ prefix
        assertParseFailure(parser, VALID_TEAM_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTeamName_throwsParseException() {
        // Missing team name after prefix
        assertParseFailure(parser, " " + PREFIX_TEAM, Team.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidTeamName_throwsParseException() {
        // Invalid team name with special characters
        assertParseFailure(parser, INVALID_TEAM_DESC, Team.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        // Duplicate tm/ prefix
        assertParseFailure(parser, TEAM_DESC_AMY + TEAM_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TEAM));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        // Whitespace only
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        // Non-empty preamble
        assertParseFailure(parser, "some random text" + TEAM_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE));
    }
}
