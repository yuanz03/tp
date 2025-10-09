package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INJURY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INJURY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PLAYER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AssignInjuryCommand;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AssignInjuryCommandParserTest {

    private AssignInjuryCommandParser parser = new AssignInjuryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PLAYER_DESC_BOB + INJURY_DESC_BOB,
                new AssignInjuryCommand(expectedPerson.getName(), expectedPerson.getInjury()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignInjuryCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
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
    public void parse_duplicatePrefix_failure() {
        // duplicate player prefix
        assertParseFailure(parser, PLAYER_DESC_BOB + PLAYER_DESC_BOB + INJURY_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER));

        // duplicate injury prefix
        assertParseFailure(parser, PLAYER_DESC_BOB + INJURY_DESC_BOB + INJURY_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INJURY));
    }
}
