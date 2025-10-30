package seedu.address.logic.parser;

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
import seedu.address.logic.commands.AssignInjuryCommand;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AssignInjuryCommandParserTest {
    private AssignInjuryCommandParser parser = new AssignInjuryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PLAYER_DESC_BOB + INJURY_DESC_BOB,
                new AssignInjuryCommand(expectedPerson.getName(), expectedPerson.getInjuries().iterator().next()));
    }

    @Test
    public void parse_noArguments_failure() {
        // Empty input
        String expectedMessage = String.format(Messages.MESSAGE_EMPTY_COMMAND, AssignInjuryCommand.COMMAND_WORD)
                + "\n" + AssignInjuryCommand.MESSAGE_USAGE;
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, expectedMessage));
    }

    @Test
    public void parse_missingPlayerField_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_MISSING_PLAYER_PREFIX, AssignInjuryCommand.COMMAND_WORD)
                + "\n" + AssignInjuryCommand.MESSAGE_USAGE;
        assertParseFailure(parser, INJURY_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, expectedMessage));
    }

    @Test
    public void parse_missingInjuryField_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_MISSING_INJURY_PREFIX, AssignInjuryCommand.COMMAND_WORD)
                + "\n" + AssignInjuryCommand.MESSAGE_USAGE;
        assertParseFailure(parser, PLAYER_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, expectedMessage));
    }

    @Test
    public void parse_missingPlayerPrefix_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_MISSING_PLAYER_PREFIX, AssignInjuryCommand.COMMAND_WORD)
                + "\n" + AssignInjuryCommand.MESSAGE_USAGE;
        assertParseFailure(parser, VALID_NAME_BOB + INJURY_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, expectedMessage));
    }

    @Test
    public void parse_missingInjuryPrefix_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_MISSING_INJURY_PREFIX, AssignInjuryCommand.COMMAND_WORD)
                + "\n" + AssignInjuryCommand.MESSAGE_USAGE;
        assertParseFailure(parser, PLAYER_DESC_BOB + VALID_INJURY_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, expectedMessage));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_NON_EMPTY_PREAMBLE, AssignInjuryCommand.COMMAND_WORD)
                + "\n" + AssignInjuryCommand.MESSAGE_USAGE;
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PLAYER_DESC_BOB + INJURY_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, expectedMessage));
    }

    @Test
    public void parse_invalidPlayer_failure() {
        String expectedMessage = String.format("Invalid player name: %s\n%s", "hubby*", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_PLAYER_DESC + INJURY_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidInjury_failure() {
        String expectedMessage = String.format("Invalid injury: %s\n%s", "@CL", Injury.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PLAYER_DESC_BOB + INVALID_INJURY_DESC, expectedMessage);
    }

    @Test
    public void parse_differentPrefixOrder_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INJURY_DESC_BOB + PLAYER_DESC_BOB,
                new AssignInjuryCommand(expectedPerson.getName(), expectedPerson.getInjuries().iterator().next()));
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        // duplicate player prefix
        String expectedDuplicatePlayerMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER) + "\n"
                + AssignInjuryCommand.MESSAGE_USAGE;
        assertParseFailure(parser, PLAYER_DESC_BOB + PLAYER_DESC_BOB + INJURY_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, expectedDuplicatePlayerMessage));

        // duplicate injury prefix
        String expectedDuplicateInjuryMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INJURY) + "\n"
                + AssignInjuryCommand.MESSAGE_USAGE;
        assertParseFailure(parser, PLAYER_DESC_BOB + INJURY_DESC_BOB + INJURY_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, expectedDuplicateInjuryMessage));
    }

    @Test
    public void parse_validInjuryWithSpaces_success() {
        String injuryWithSpaces = " " + PREFIX_INJURY + "Broken Fibula";
        Person expectedPerson = new PersonBuilder(BOB).withInjuries("Broken Fibula").build();

        assertParseSuccess(parser, PLAYER_DESC_BOB + injuryWithSpaces,
                new AssignInjuryCommand(expectedPerson.getName(), expectedPerson.getInjuries().iterator().next()));
    }

    @Test
    public void parse_validInjuryWithNumbers_success() {
        String injuryWithNumbers = " " + PREFIX_INJURY + "Grade 2 Ankle Sprain";
        Person expectedPerson = new PersonBuilder(BOB).withInjuries("Grade 2 Ankle Sprain").build();

        assertParseSuccess(parser, PLAYER_DESC_BOB + injuryWithNumbers,
                new AssignInjuryCommand(expectedPerson.getName(), expectedPerson.getInjuries().iterator().next()));
    }
}
