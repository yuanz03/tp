package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_NAME_DESC_16;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTeams.U16;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddTeamCommand;
import seedu.address.model.team.Team;
import seedu.address.testutil.TeamBuilder;

public class AddTeamCommandParserTest {

    private AddTeamCommandParser parser = new AddTeamCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Team expectedTeam = new TeamBuilder(U16).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TEAM_NAME_DESC_16, new AddTeamCommand(expectedTeam));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeamCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid team name
        assertParseFailure(parser, INVALID_TEAM_NAME_DESC, Team.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        // duplicate name prefix
        assertParseFailure(parser, TEAM_NAME_DESC_16 + TEAM_NAME_DESC_16,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TEAM));
    }

}
