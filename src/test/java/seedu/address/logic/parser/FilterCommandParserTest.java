package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INJURY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INJURY_NAME_DESC_SINGLE_CHAR;
import static seedu.address.logic.commands.CommandTestUtil.INJURY_PREFIX_WITH_NO_FIELD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INJURY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_NAME_DESC_LOWER_CASE_LW;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_NAME_DESC_SINGLE_CHAR;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_PREFIX_WITH_NO_FIELD;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_NAME_DESC_LOWER_CASE_16;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_NAME_DESC_SINGLE_CHAR;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_PREFIX_WITH_NO_FIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INJURY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOWER_CASE_POSITION_LW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOWER_CASE_TEAM_NAME_16;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SINGLE_CHAR_INJURY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SINGLE_CHAR_POSITION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SINGLE_CHAR_TEAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.FilterByInjuryPredicate;
import seedu.address.model.person.Injury;
import seedu.address.model.position.FilterByPositionPredicate;
import seedu.address.model.position.Position;
import seedu.address.model.team.FilterByTeamPredicate;
import seedu.address.model.team.Team;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        String input = TEAM_DESC_AMY;
        FilterByTeamPredicate expectedPredicate = new FilterByTeamPredicate(VALID_TEAM_AMY);
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(VALID_TEAM_AMY),
                Optional.empty(),
                Optional.empty());
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        String input = VALID_TEAM_AMY; // no tm/ prefix
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTeamName_throwsParseException() {
        String input = INVALID_TEAM_DESC;
        assertParseFailure(parser, input,
                String.format("Invalid team name: %s\n%s", "U@16", Team.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidInjury_throwsCommandException() {
        String input = INVALID_INJURY_DESC;
        assertParseFailure(parser, input,
                String.format("Invalid injury: %s\n%s", "@CL", Injury.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidPosition_throwsCommandException() {
        String input = INVALID_POSITION_DESC;
        assertParseFailure(parser, input,
                String.format("Invalid position name: %s\n%s", "MF*", Position.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_emptyTeamName_throwsParseException() {
        String input = TEAM_PREFIX_WITH_NO_FIELD;
        assertParseFailure(parser, input,
                String.format("Invalid team name: %s\n%s", "", Team.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_preambleNotEmpty_throwsParseException() {
        String input = "xyz" + TEAM_DESC_AMY; // premeble not empty
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validInjuryOnly_returnsFilterCommand() {
        String input = INJURY_DESC_BOB;
        FilterByInjuryPredicate expectedInjPred = new FilterByInjuryPredicate(VALID_INJURY_BOB);
        FilterCommand expectedCommand = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                expectedInjPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.empty(),
                Optional.of(VALID_INJURY_BOB),
                Optional.empty());
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_validTeamOnly_returnsFilterCommand() {
        String input = TEAM_DESC_BOB;
        FilterByTeamPredicate expectedTeamPred = new FilterByTeamPredicate(VALID_TEAM_BOB);
        FilterCommand expectedCommand = new FilterCommand(
                expectedTeamPred,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(VALID_TEAM_BOB),
                Optional.empty(),
                Optional.empty());
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_validBothAnyOrder_returnsFilterCommand() {
        String input1 = INJURY_DESC_BOB + TEAM_DESC_BOB;
        String input2 = TEAM_DESC_BOB + INJURY_DESC_BOB;
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(VALID_TEAM_BOB);
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(VALID_INJURY_BOB);
        FilterCommand expectedCommand = new FilterCommand(teamPred,
                injPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(VALID_TEAM_BOB),
                Optional.of(VALID_INJURY_BOB),
                Optional.empty());
        assertParseSuccess(parser, input1, expectedCommand);
        assertParseSuccess(parser, input2, expectedCommand);
    }

    @Test
    public void parse_invalidFields_throwsParseException() {
        // invalid team with valid injury
        String input = INVALID_TEAM_DESC + VALID_INJURY_BOB;
        assertParseFailure(parser, input,
                String.format("Invalid team name: %s\n%s", "U@16Meniscus tear", Team.MESSAGE_CONSTRAINTS));
        // valid team with invalid injury
        input = TEAM_DESC_BOB + INVALID_INJURY_DESC;
        assertParseFailure(parser, input,
                String.format("Invalid injury: %s\n%s", "@CL", Injury.MESSAGE_CONSTRAINTS));
        // invalid team with invalid injury
        input = INVALID_TEAM_DESC + INVALID_INJURY_DESC;
        assertParseFailure(parser, input,
                String.format("Invalid team name: %s\n%s", "U@16", Team.MESSAGE_CONSTRAINTS));
        // invalid injury with invalid team
        input = INVALID_INJURY_DESC + INVALID_TEAM_DESC;
        assertParseFailure(parser, input,
                String.format("Invalid injury: %s\n%s", "@CL", Injury.MESSAGE_CONSTRAINTS));
        // all fields invalid
        input = INVALID_TEAM_DESC + INVALID_INJURY_DESC + INVALID_POSITION_DESC;
        assertParseFailure(parser, input,
                String.format("Invalid team name: %s\n%s", "U@16", Team.MESSAGE_CONSTRAINTS));
        // valid position with invalid team
        input = INVALID_TEAM_DESC + POSITION_DESC_BOB;
        assertParseFailure(parser, input,
                String.format("Invalid team name: %s\n%s", "U@16", Team.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_missingFields_throwsParseException() {
        // valid team with missing injury
        String input = TEAM_DESC_BOB + INJURY_PREFIX_WITH_NO_FIELD;
        assertParseFailure(parser, input,
                String.format("Invalid injury: %s\n%s", "", Injury.MESSAGE_CONSTRAINTS));
        // valid injury with missing team
        input = INJURY_DESC_BOB + TEAM_PREFIX_WITH_NO_FIELD;
        assertParseFailure(parser, input,
                String.format("Invalid team name: %s\n%s", "", Team.MESSAGE_CONSTRAINTS));
        // missing position and valid team and injury
        input = POSITION_PREFIX_WITH_NO_FIELD + TEAM_DESC_BOB + INJURY_DESC_BOB;
        assertParseFailure(parser, input,
                String.format("Invalid position name: %s\n%s", "", Position.MESSAGE_CONSTRAINTS));
        // missing all fields
        input = TEAM_PREFIX_WITH_NO_FIELD + INJURY_PREFIX_WITH_NO_FIELD + POSITION_PREFIX_WITH_NO_FIELD;
        assertParseFailure(parser, input,
                String.format("Invalid team name: %s\n%s", "", Team.MESSAGE_CONSTRAINTS));
        // missing position with valid team
        input = TEAM_DESC_BOB + POSITION_PREFIX_WITH_NO_FIELD;
        assertParseFailure(parser, input,
                String.format("Invalid position name: %s\n%s", "", Position.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_validPositionOnly_returnsFilterCommand() {
        String input = POSITION_DESC_BOB;
        FilterByPositionPredicate expectedPred = new FilterByPositionPredicate(VALID_POSITION_BOB);
        FilterCommand expected = new FilterCommand(
            FilterByTeamPredicate.ALWAYS_TRUE,
            FilterByInjuryPredicate.ALWAYS_TRUE,
            expectedPred,
            Optional.empty(),
            Optional.empty(),
            Optional.of(VALID_POSITION_BOB));
        assertParseSuccess(parser, input, expected);
    }

    @Test
    public void parse_allFlagsAnyOrder_positionFirstThenTeamInjury() {
        String input = POSITION_DESC_BOB + TEAM_DESC_BOB + INJURY_DESC_BOB;
        FilterByTeamPredicate tp = new FilterByTeamPredicate(VALID_TEAM_BOB);
        FilterByInjuryPredicate ip = new FilterByInjuryPredicate(VALID_INJURY_BOB);
        FilterByPositionPredicate pp = new FilterByPositionPredicate(VALID_POSITION_BOB);
        FilterCommand expected = new FilterCommand(tp, ip, pp,
            Optional.of(VALID_TEAM_BOB), Optional.of(VALID_INJURY_BOB), Optional.of(VALID_POSITION_BOB));
        assertParseSuccess(parser, input, expected);
    }

    @Test
    public void parse_duplicateFlags_throwsParseException() {
        String dupTeam = TEAM_DESC_BOB + TEAM_DESC_BOB;
        assertParseFailure(parser, dupTeam, MESSAGE_DUPLICATE_FIELDS + String.join(" ", "tm/"));
        String dupInjury = INJURY_DESC_BOB + INJURY_DESC_BOB;
        assertParseFailure(parser, dupInjury, MESSAGE_DUPLICATE_FIELDS + String.join(" ", "i/"));
        String dupPosition = POSITION_DESC_BOB + POSITION_DESC_BOB;
        assertParseFailure(parser, dupPosition, MESSAGE_DUPLICATE_FIELDS + String.join(" ", "ps/"));
    }

    @Test
    public void parse_allFieldsEmpty_throwsParseException() {
        String input = TEAM_PREFIX_WITH_NO_FIELD + INJURY_PREFIX_WITH_NO_FIELD + POSITION_PREFIX_WITH_NO_FIELD;
        assertParseFailure(parser, input, String.format("Invalid team name: %s\n%s",
                 "", Team.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_validTeamInvalidPosition_throwsParseException() {
        String input = TEAM_DESC_AMY + INVALID_POSITION_DESC;
        assertParseFailure(parser, input, String.format("Invalid position name: %s\n%s",
                "MF*", Position.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_validPositionInvalidTeam_throwsParseException() {
        String input = INVALID_TEAM_DESC + POSITION_DESC_BOB;
        assertParseFailure(parser, input, String.format("Invalid team name: %s\n%s",
                "U@16", Team.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        String input = "   ";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_singleCharacterTeam_returnsFilterCommand() {
        String input = TEAM_NAME_DESC_SINGLE_CHAR;
        FilterByTeamPredicate expectedPredicate = new FilterByTeamPredicate(VALID_SINGLE_CHAR_TEAM);
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(VALID_SINGLE_CHAR_TEAM),
                Optional.empty(),
                Optional.empty());
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_singleCharacterInjury_returnsFilterCommand() {
        String input = INJURY_NAME_DESC_SINGLE_CHAR;
        FilterByInjuryPredicate expectedPredicate = new FilterByInjuryPredicate(VALID_SINGLE_CHAR_INJURY);
        FilterCommand expectedCommand = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                expectedPredicate,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.empty(),
                Optional.of(VALID_SINGLE_CHAR_INJURY),
                Optional.empty());
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_singleCharacterPosition_returnsFilterCommand() {
        String input = POSITION_NAME_DESC_SINGLE_CHAR;
        FilterByPositionPredicate expectedPredicate = new FilterByPositionPredicate(VALID_SINGLE_CHAR_POSITION);
        FilterCommand expectedCommand = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                expectedPredicate,
                Optional.empty(),
                Optional.empty(),
                Optional.of(VALID_SINGLE_CHAR_POSITION));
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_validTeamValidPositionInvalidInjury_throwsParseException() {
        String input = TEAM_DESC_AMY + POSITION_DESC_BOB + INVALID_INJURY_DESC;
        assertParseFailure(parser, input,
                String.format("Invalid injury: %s\n%s", "@CL", Injury.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_validInjuryValidPositionInvalidTeam_throwsParseException() {
        String input = INVALID_TEAM_DESC + POSITION_DESC_BOB + INJURY_DESC_BOB;
        assertParseFailure(parser, input,
                String.format("Invalid team name: %s\n%s", "U@16", Team.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_validTeamValidInjuryInvalidPosition_throwsParseException() {
        String input = TEAM_DESC_AMY + INJURY_DESC_BOB + INVALID_POSITION_DESC;
        assertParseFailure(parser, input,
                String.format("Invalid position name: %s\n%s", "MF*", Position.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_caseInsensitiveTeam_returnsFilterCommand() {
        String input = TEAM_NAME_DESC_LOWER_CASE_16; // lowercase team name
        FilterByTeamPredicate expectedPredicate = new FilterByTeamPredicate(VALID_LOWER_CASE_TEAM_NAME_16);
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(VALID_LOWER_CASE_TEAM_NAME_16),
                Optional.empty(),
                Optional.empty());
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_caseInsensitivePosition_returnsFilterCommand() {
        String input = POSITION_NAME_DESC_LOWER_CASE_LW; // lowercase position
        FilterByPositionPredicate expectedPredicate = new FilterByPositionPredicate(VALID_LOWER_CASE_POSITION_LW);
        FilterCommand expectedCommand = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                expectedPredicate,
                Optional.empty(),
                Optional.empty(),
                Optional.of(VALID_LOWER_CASE_POSITION_LW));
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_allFlagsAnyOrder_injuryFirstThenTeamPosition() {
        String input = INJURY_DESC_BOB + TEAM_DESC_BOB + POSITION_DESC_BOB;
        FilterByTeamPredicate tp = new FilterByTeamPredicate(VALID_TEAM_BOB);
        FilterByInjuryPredicate ip = new FilterByInjuryPredicate(VALID_INJURY_BOB);
        FilterByPositionPredicate pp = new FilterByPositionPredicate(VALID_POSITION_BOB);
        FilterCommand expected = new FilterCommand(tp, ip, pp,
                Optional.of(VALID_TEAM_BOB), Optional.of(VALID_INJURY_BOB), Optional.of(VALID_POSITION_BOB));
        assertParseSuccess(parser, input, expected);
    }

    @Test
    public void parse_allFlagsAnyOrder_teamFirstThenInjuryPosition() {
        String input = TEAM_DESC_BOB + INJURY_DESC_BOB + POSITION_DESC_BOB;
        FilterByTeamPredicate tp = new FilterByTeamPredicate(VALID_TEAM_BOB);
        FilterByInjuryPredicate ip = new FilterByInjuryPredicate(VALID_INJURY_BOB);
        FilterByPositionPredicate pp = new FilterByPositionPredicate(VALID_POSITION_BOB);
        FilterCommand expected = new FilterCommand(tp, ip, pp,
                Optional.of(VALID_TEAM_BOB), Optional.of(VALID_INJURY_BOB), Optional.of(VALID_POSITION_BOB));
        assertParseSuccess(parser, input, expected);
    }

    @Test
    public void parse_duplicateTeamKeywords_throwsParseException() {
        assertParseFailure(parser, " tm/U16 U16 Boys",
                "Duplicate team keyword found: \"U16\". Please remove duplicate keywords.");
    }

    @Test
    public void parse_duplicateInjuryKeywords_throwsParseException() {
        assertParseFailure(parser, " i/ACL ACL Hamstring",
                "Duplicate injury keyword found: \"ACL\". Please remove duplicate keywords.");
    }

    @Test
    public void parse_caseInsensitiveDuplicateTeamKeywords_throwsParseException() {
        assertParseFailure(parser, " tm/U16 u16 Boys",
                "Duplicate team keyword found: \"U16\". Please remove duplicate keywords.");
    }

    @Test
    public void parse_caseInsensitiveDuplicateInjuryKeywords_throwsParseException() {
        assertParseFailure(parser, " i/ACL Acl aCl",
                "Duplicate injury keyword found: \"ACL\". Please remove duplicate keywords.");
    }
}
