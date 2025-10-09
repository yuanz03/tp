package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NewPositionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class NewPositionCommandParserTest {
    private final NewPositionCommandParser parser = new NewPositionCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        NewPositionCommand cmd = parser.parse(" ps/LW");
        assertEquals(new NewPositionCommand("LW").getClass(), cmd.getClass());
    }

    @Test
    public void parse_missingFlag_failure() {
        assertThrows(ParseException.class, NewPositionCommand.MESSAGE_MISSING_FLAG, () -> parser.parse(" LW"));
    }

    @Test
    public void parse_invalidFormat_failure() {
        assertThrows(ParseException.class, NewPositionCommand.MESSAGE_INVALID_FORMAT,
                () -> parser.parse(" ps/LW ps/ST"));
    }
}


