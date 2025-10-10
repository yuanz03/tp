package seedu.address.logic.parser;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignPositionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AssignPositionCommandParserTest {
    private final AssignPositionCommandParser parser = new AssignPositionCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        AssignPositionCommand cmd = parser.parse(" p/Alice ps/LW");
        assert cmd.getClass() == AssignPositionCommand.class;
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
}


