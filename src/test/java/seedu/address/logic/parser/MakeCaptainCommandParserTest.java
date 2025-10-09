package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PLAYER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MakeCaptainCommand;
import seedu.address.model.person.Name;

public class MakeCaptainCommandParserTest {

    private final MakeCaptainCommandParser parser = new MakeCaptainCommandParser();

    @Test
    public void parse_validArgs_returnsCommand() {
        Name name = new Name("Amy Bee");
        assertParseSuccess(parser, PLAYER_DESC_AMY, new MakeCaptainCommand(name));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "Amy Bee",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MakeCaptainCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        assertParseFailure(parser, INVALID_PLAYER_DESC, Name.MESSAGE_CONSTRAINTS);
    }
}
