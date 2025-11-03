package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_noArguments_failure() {
        assertParseFailure(parser, "", formatExpectedMessage(Messages.MESSAGE_EMPTY_COMMAND));
    }

    @Test
    public void parse_missingPlayerPrefix_failure() {
        assertParseFailure(parser, VALID_NAME_AMY, formatExpectedMessage(Messages.MESSAGE_MISSING_PLAYER_PREFIX));
    }

    @Test
    public void parse_missingPlayerDetails_failure() {
        String expectedMessage = Messages.MESSAGE_NOT_EDITED + "\n" + EditCommand.MESSAGE_USAGE;;
        assertParseFailure(parser, PLAYER_DESC_AMY,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, expectedMessage));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PLAYER_DESC_AMY + NAME_DESC_AMY,
                formatExpectedMessage(Messages.MESSAGE_NON_EMPTY_PREAMBLE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, PLAYER_DESC_AMY + INVALID_NAME_DESC, invalidNameMessage());

        // invalid phone
        assertParseFailure(parser, PLAYER_DESC_AMY + INVALID_PHONE_DESC, invalidPhoneMessage());

        // invalid email
        assertParseFailure(parser, PLAYER_DESC_AMY + INVALID_EMAIL_DESC, invalidEmailMessage());

        // invalid address
        assertParseFailure(parser, PLAYER_DESC_AMY + INVALID_ADDRESS_DESC, invalidAddressMessage());

        // invalid tag
        assertParseFailure(parser, PLAYER_DESC_AMY + INVALID_TAG_DESC, invalidTagMessage("hubby*"));

        // invalid phone followed by valid email
        assertParseFailure(parser, PLAYER_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY, invalidPhoneMessage());

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, PLAYER_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                invalidTagMessage(""));

        assertParseFailure(parser, PLAYER_DESC_AMY + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                invalidTagMessage(""));

        assertParseFailure(parser, PLAYER_DESC_AMY + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                invalidTagMessage(""));

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                PLAYER_DESC_AMY + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                invalidNameMessage());
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = PLAYER_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(new Name(VALID_NAME_BOB), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = PLAYER_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(new Name(VALID_NAME_AMY), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String userInput = PLAYER_DESC_AMY + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = PLAYER_DESC_AMY + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = PLAYER_DESC_AMY + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = PLAYER_DESC_AMY + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = PLAYER_DESC_AMY + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // invalid followed by valid
        String userInput = PLAYER_DESC_AMY + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        assertParseFailure(parser, userInput, formatDuplicatePrefixesMessage(PREFIX_PHONE));

        // valid followed by invalid
        userInput = PLAYER_DESC_AMY + PHONE_DESC_BOB + INVALID_PHONE_DESC;
        assertParseFailure(parser, userInput, formatDuplicatePrefixesMessage(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = PLAYER_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                formatDuplicatePrefixesMessage(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = PLAYER_DESC_AMY + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                formatDuplicatePrefixesMessage(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        String userInput = PLAYER_DESC_AMY + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(new Name(VALID_NAME_AMY), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    //=========== Helper Methods ========================================================

    private static String formatExpectedMessage(String message) {
        String expectedMessage = String.format(message, EditCommand.COMMAND_WORD) + "\n"
                + EditCommand.MESSAGE_USAGE;
        return String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, expectedMessage);
    }

    private static String formatDuplicatePrefixesMessage(Prefix... prefix) {
        String expectedMessage = Messages.getErrorMessageForDuplicatePrefixes(prefix) + "\n"
                + EditCommand.MESSAGE_USAGE;
        return String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, expectedMessage);
    }

    private static String invalidNameMessage() {
        return String.format("Invalid player name: %s\n%s", "James&", Name.MESSAGE_CONSTRAINTS);
    }

    private static String invalidPhoneMessage() {
        return String.format("Invalid phone number: %s\n%s", "911a", Phone.MESSAGE_CONSTRAINTS);
    }

    private static String invalidEmailMessage() {
        return String.format("Invalid email: %s\n%s", "bob!yahoo", Email.MESSAGE_CONSTRAINTS);
    }

    private static String invalidAddressMessage() {
        return String.format("Invalid address: %s\n%s", "", Address.MESSAGE_CONSTRAINTS);
    }

    private static String invalidTagMessage(String tag) {
        return String.format("Invalid tag name: %s\n%s", tag, Tag.MESSAGE_CONSTRAINTS);
    }
}
