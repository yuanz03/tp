package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignPositionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AssignPositionCommandParserTest {
    private final AssignPositionCommandParser parser = new AssignPositionCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        AssignPositionCommand cmd = parser.parse(" p/Alice ps/LW");
        assertEquals(AssignPositionCommand.class, cmd.getClass());
    }

    @Test
    public void parse_validArgsReversedOrder_success() throws Exception {
        // Order shouldn't matter
        AssignPositionCommand cmd = parser.parse(" ps/FW p/Bob");
        assertEquals(AssignPositionCommand.class, cmd.getClass());
    }

    @Test
    public void parse_validArgsWithWhitespace_success() throws Exception {
        AssignPositionCommand cmd = parser.parse("  p/Alice Smith  ps/FW  ");
        assertEquals(AssignPositionCommand.class, cmd.getClass());
    }

    @Test
    public void parse_missingPlayerFlag_failure() {
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_MISSING_PLAYER_FLAG, () ->
                parser.parse(" ps/LW"));
    }

    @Test
    public void parse_missingPositionFlag_failure() {
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_MISSING_POSITION_FLAG, () ->
                parser.parse(" p/Alice"));
    }

    @Test
    public void parse_bothFlagsMissing_failure() {
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_INVALID_FORMAT, () ->
                parser.parse(" Alice FW"));
    }

    @Test
    public void parse_emptyArgs_failure() {
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_INVALID_FORMAT, () ->
                parser.parse(""));
    }

    @Test
    public void parse_nullArgs_failure() {
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_INVALID_FORMAT, () ->
                parser.parse(null));
    }

    @Test
    public void parse_emptyPlayerName_failure() {
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_INVALID_FORMAT, () ->
                parser.parse(" p/ ps/FW"));
    }

    @Test
    public void parse_emptyPositionName_success() throws Exception {
        // Empty position name should parse but fail during execution
        AssignPositionCommand cmd = parser.parse(" p/Alice ps/");
        assertEquals(AssignPositionCommand.class, cmd.getClass());
    }

    @Test
    public void parse_playerNameWithSpaces_success() throws Exception {
        AssignPositionCommand cmd = parser.parse(" p/Alice Mary Smith ps/FW");
        assertEquals(AssignPositionCommand.class, cmd.getClass());
    }

    @Test
    public void parse_caseInsensitiveFlags_success() throws Exception {
        // Flags should be case-insensitive due to (?i) in regex
        AssignPositionCommand cmd = parser.parse(" P/Alice PS/FW");
        assertEquals(AssignPositionCommand.class, cmd.getClass());
    }

    @Test
    public void parse_preamble_failure() {
        // Having text before flags should fail
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_INVALID_FORMAT, () ->
                parser.parse(" preamble p/Alice ps/FW"));
    }
}


