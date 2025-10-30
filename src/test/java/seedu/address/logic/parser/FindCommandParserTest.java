package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_duplicateKeywords_throwsParseException() {
        // Single duplicate
        assertParseFailure(parser, "alice alice",
            "Duplicate name keyword found: \"alice\". Please remove duplicate keywords.");

        // Multiple duplicates
        assertParseFailure(parser, "alice bob alice bob",
            "Duplicate name keywords found: \"bob\", \"alice\". Please remove duplicate keywords.");
    }

    @Test
    public void parse_caseInsensitiveDuplicates_throwsParseException() {
        // Case insensitive duplicates
        assertParseFailure(parser, "Alice alice",
            "Duplicate name keyword found: \"Alice\". Please remove duplicate keywords.");

        assertParseFailure(parser, "BOB bob Bob",
            "Duplicate name keyword found: \"BOB\". Please remove duplicate keywords.");
    }
}
