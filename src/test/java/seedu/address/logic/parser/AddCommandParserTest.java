package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PLAYER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withInjuries(Person.DEFAULT_INJURY_STATUS.getInjuryName())
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PLAYER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TEAM_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .withInjuries(Person.DEFAULT_INJURY_STATUS.getInjuryName()).build();
        assertParseSuccess(parser,
                PLAYER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TEAM_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = PLAYER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TEAM_DESC_BOB + TAG_DESC_FRIEND;

        // multiple player names
        assertParseFailure(parser, PLAYER_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple teams
        assertParseFailure(parser, TEAM_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TEAM));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + PLAYER_DESC_AMY
                + ADDRESS_DESC_AMY + TEAM_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER, PREFIX_ADDRESS, PREFIX_EMAIL,
                        PREFIX_PHONE, PREFIX_TEAM));

        // invalid value followed by valid value

        // invalid player name
        assertParseFailure(parser, INVALID_PLAYER_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid team
        assertParseFailure(parser, INVALID_TEAM_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TEAM));

        // valid value followed by invalid value

        // invalid player name
        assertParseFailure(parser, validExpectedPersonString + INVALID_PLAYER_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid team
        assertParseFailure(parser, validExpectedPersonString + INVALID_TEAM_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TEAM));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person firstPerson = new PersonBuilder(AMY).withInjuries(Person.DEFAULT_INJURY_STATUS.getInjuryName())
                .withTags().build();
        assertParseSuccess(parser,
                PLAYER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TEAM_DESC_AMY,
                new AddCommand(firstPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing player prefix
        assertParseFailure(parser,
                VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TEAM_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser,
                PLAYER_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TEAM_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser,
                PLAYER_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + TEAM_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser,
                PLAYER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + TEAM_DESC_BOB,
                expectedMessage);

        // missing team prefix
        assertParseFailure(parser,
                PLAYER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_TEAM_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                PLAYER_DESC_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_TEAM_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid player name
        assertParseFailure(parser, INVALID_PLAYER_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TEAM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, PLAYER_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TEAM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, PLAYER_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TEAM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, PLAYER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TEAM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid team
        assertParseFailure(parser, PLAYER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TEAM_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Team.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, PLAYER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TEAM_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_PLAYER_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TEAM_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PLAYER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TEAM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
