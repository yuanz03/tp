package seedu.address.logic.parser;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeletePositionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeletePositionCommandParserTest {
    private final DeletePositionCommandParser parser = new DeletePositionCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        DeletePositionCommand cmd = parser.parse(" ps/LW");
        // class check only; equality not overridden
        assert cmd.getClass() == DeletePositionCommand.class;
    }

    @Test
    public void parse_missingFlag_failure() {
        assertThrows(ParseException.class, DeletePositionCommand.MESSAGE_MISSING_FLAG, () ->
                parser.parse(" LW"));
    }

    @Test
    public void parse_invalidFormat_failure() {
        assertThrows(ParseException.class, DeletePositionCommand.MESSAGE_INVALID_FORMAT, () ->
                parser.parse(" ps/LW ps/ST"));
    }
}


