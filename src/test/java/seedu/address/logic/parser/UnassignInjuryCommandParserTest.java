package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PLAYER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnassignInjuryCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UnassignInjuryCommandParserTest {
    private UnassignInjuryCommandParser parser = new UnassignInjuryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PLAYER_DESC_BOB,
                new UnassignInjuryCommand(expectedPerson.getName()));
    }

    @Test
    public void parse_noArguments_failure() {
        // Empty input
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignInjuryCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_missingPlayerPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignInjuryCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_NAME_BOB, expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignInjuryCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PLAYER_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidPlayer_failure() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_PLAYER_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePlayerPrefix_failure() {
        assertParseFailure(parser, PLAYER_DESC_BOB + PLAYER_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER));
    }
}
