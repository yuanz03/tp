package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INJURY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INJURY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PLAYER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INJURY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnassignInjuryCommand;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UnassignInjuryCommandParserTest {
    private UnassignInjuryCommandParser parser = new UnassignInjuryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PLAYER_DESC_BOB + INJURY_DESC_BOB,
                new UnassignInjuryCommand(expectedPerson.getName(), expectedPerson.getInjuries().iterator().next()));
    }

    @Test
    public void parse_noArguments_failure() {
        // Empty input
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignInjuryCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_missingPlayerField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignInjuryCommand.MESSAGE_USAGE);
        assertParseFailure(parser, INJURY_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_missingInjuryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignInjuryCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PLAYER_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_missingPlayerPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignInjuryCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_NAME_BOB + INJURY_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_missingInjuryPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignInjuryCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PLAYER_DESC_BOB + VALID_INJURY_BOB, expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignInjuryCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PLAYER_DESC_BOB + INJURY_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidPlayer_failure() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_PLAYER_DESC + INJURY_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidInjury_failure() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PLAYER_DESC_BOB + INVALID_INJURY_DESC,
                Injury.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_differentPrefixOrder_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INJURY_DESC_BOB + PLAYER_DESC_BOB,
                new UnassignInjuryCommand(expectedPerson.getName(), expectedPerson.getInjuries().iterator().next()));
    }

    @Test
    public void parse_duplicatePlayerPrefix_failure() {
        // duplicate player prefix
        assertParseFailure(parser, PLAYER_DESC_BOB + PLAYER_DESC_BOB + INJURY_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER));

        // duplicate injury prefix
        assertParseFailure(parser, PLAYER_DESC_BOB + INJURY_DESC_BOB + INJURY_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INJURY));
    }
}
